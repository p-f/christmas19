/*
 * Copyright © 2019 Philip Fritzsche (p-f@users.noreply.github.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pink.philip.christmas19.server.shell;

import org.apache.sshd.common.util.threads.CloseableExecutorService;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.Signal;
import org.apache.sshd.server.command.AbstractCommandSupport;
import org.apache.sshd.server.command.Command;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pink.philip.christmas19.out.TerminalOutputTask;
import pink.philip.christmas19.out.TerminalResetTask;
import pink.philip.christmas19.utils.TextUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static java.lang.Integer.parseInt;

/**
 * Wraps a {@link Terminal} as a {@link Command}.
 */
public class TerminalCommandWrapper extends AbstractCommandSupport
        implements ExitCallback {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TerminalCommandWrapper.class);

    /**
     * The terminal wrapped by this instance.
     */
    private Terminal terminal;

    /**
     * The default error message header.
     */
    private static final String errorMessage = "ChristmasServer 2019 Error";

    /**
     * The default error message header (formatted).
     */
    private static final String errorBanner = TextUtils.boxed(errorMessage,
            '#', "\r\n");

    /**
     * Create a new wrapper instance.
     *
     * @param command         The command name.
     * @param executorService An executor (can be {@code null}).
     */
    public TerminalCommandWrapper(String command,
                                  CloseableExecutorService executorService) {
        super(command, executorService);
        setExitCallback(this);
    }

    @Override
    public void run() {
        LOGGER.info("Running as {}", getSession().getUsername());
        Terminal terminal;
        try {
            terminal = TerminalBuilder.builder()
                    .encoding(StandardCharsets.UTF_8)
                    .system(false)
                    .jna(false)
                    .paused(true)
                    .size(getSize())
                    .type(getTerminalType())
                    .streams(getInputStream(), getOutputStream())
                    .build();
            getEnvironment().addSignalListener((channel, signal) -> {
                LOGGER.info("Got resize to " + getSize());
                terminal.setSize(getSize());
                terminal.raise(Terminal.Signal.WINCH);
            }, Signal.WINCH);
            getEnvironment().addSignalListener(((channel, signal) -> {
                try {
                    LOGGER.info("Got {}", signal);
                    channel.getSession().close();
                } catch (IOException e) {
                    error("Failed to respond to signal " + signal,
                            Optional.of(e));
                }
                terminal.raise(Terminal.Signal.QUIT);
            }), Signal.INT, Signal.HUP, Signal.KILL, Signal.TERM, Signal.QUIT,
                    Signal.STOP);
        } catch (IOException e) {
            error("Failed to initialize terminal.", Optional.of(e));
            return;
        }
        this.terminal = terminal;
        run(terminal);
    }

    /**
     * Run this command with an initialized terminal instance.
     *
     * @param terminal The terminal.
     */
    public void run(Terminal terminal) {
        terminal.writer().println("Welcome!");
        terminal.flush();
        new TerminalOutputTask(terminal).run();
        try {
            getSession().close();
        } catch (IOException e) {
            error("Failed to close session.", Optional.of(e));
        }
        try {
            terminal.close();
        } catch (IOException e) {
            error("Failed to exit terminal.", Optional.of(e));
        }
    }

    /**
     * Print an error.
     *
     * @param message The error message.
     * @param ex The exception (optional).
     */
    protected void error(String message, Optional<Throwable> ex) {
        if (ex.isPresent()) {
            LOGGER.warn(message, ex.get());
        } else {
            LOGGER.warn(message);
        }
        final PrintWriter writer;
        if (terminal != null) {
            writer = terminal.writer();
        } else if (getOutputStream() != null) {
            writer = new PrintWriter(getOutputStream());
        } else {
            return;
        }
        if (getSize().getColumns() < (errorMessage.length() + 4)) {
            writer.println(errorMessage);
        } else {
            writer.println(errorBanner);
        }
        writer.println(message);
        if (ex.isPresent()) {
            writer.println("Details:");
            ex.get().printStackTrace(writer);
        }
        writer.flush();
    }

    /**
     * Get the current size of the terminal.
     *
     * @return The size.
     */
    protected Size getSize() {
        return new Size(parseInt(getEnvironment().getEnv()
                .getOrDefault("COLUMNS", "80")),
                parseInt(getEnvironment().getEnv().getOrDefault("LINES", "24"))
        );
    }

    /**
     * Get the terminal type.
     *
     * @return The terminal type.
     */
    protected String getTerminalType() {
        return getEnvironment().getEnv().getOrDefault("TERM", "");
    }

    @Override
    public void onExit(int exitValue, String exitMessage) {
        LOGGER.info("Exit [{}]: {}", exitMessage, exitMessage);
        if (terminal != null) {
            new TerminalResetTask(terminal).run();
        }
    }

    @Override
    public void onExit(int exitValue) {
        onExit(exitValue, "(no message)");
    }
}
