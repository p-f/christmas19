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
package pink.philip.christmas19.out;

import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.jline.utils.InfoCmp.Capability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pink.philip.christmas19.data.Resources;
import pink.philip.christmas19.snow.BasicRenderingEngine;
import pink.philip.christmas19.snow.SnowEngine;
import pink.philip.christmas19.snow.SnowRenderingEngine;

import java.util.Objects;
import java.util.Random;

/**
 * A task putting out data on a {@link Terminal}.
 */
public class TerminalOutputTask implements Runnable {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TerminalOutputTask.class);

    /**
     * The terminal used as the output.
     */
    private final Terminal terminal;

    /**
     * Was the terminal size changed?
     */
    private boolean changed;

    /**
     * Should this task be exited?
     */
    private boolean quit;

    /**
     * Create a new instance of this task.
     *
     * @param terminal The output terminal.
     */
    public TerminalOutputTask(Terminal terminal) {
        this.terminal = Objects.requireNonNull(terminal);
        changed = false;
        quit = false;
        terminal.handle(Terminal.Signal.WINCH, s -> changed = true);
        terminal.handle(Terminal.Signal.QUIT, s -> quit = true);
        terminal.handle(Terminal.Signal.INT, s -> quit = true);
    }

    @Override
    public void run() {
        final String message = Resources.getInstance().getMessage();
        final String coloredMessage = getColorizedString(message);
        Size size = getSize();
        init();
        position(size.getRows() / 2 - 1,
                (size.getColumns() - message.length()) / 2);
        terminal.writer().append(coloredMessage);
        terminal.flush();
        SnowEngine snow = new SnowEngine(size);
        SnowRenderingEngine render = new BasicRenderingEngine(snow, terminal);

        while (true) {
            if (quit) {
                break;
            }
            if (changed) {
                init();
                snow = new SnowEngine(getSize());
                render = new BasicRenderingEngine(snow, terminal);
                changed = false;
            }
            terminal.puts(Capability.clear_screen);
            terminal.flush();
            render.stepAndRender();
            position(size.getRows() / 2 - 1,
                    (size.getColumns() - message.length()) / 2);
            terminal.writer().append(coloredMessage);
            terminal.flush();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                LOGGER.info("Task interrupt");
                throw new RuntimeException(e);
            }
        }
        terminal.puts(Capability.clear_screen);
        terminal.puts(Capability.cursor_visible);
        terminal.flush();
        terminal.echo(true);
    }

    /**
     * Initialize the terminal.
     */
    private void init() {
        terminal.echo(false);
        terminal.puts(Capability.clear_screen);
        terminal.puts(Capability.cursor_invisible);
        terminal.flush();
    }

    /**
     * Get a colorized form of a string.
     *
     * @param input The input string.
     * @return The colorized string.
     */
    private String getColorizedString(String input) {
        AttributedStringBuilder sb = new AttributedStringBuilder();
        boolean nextCharColor = new Random().nextBoolean();
        for (char c : input.toCharArray()) {
            sb.style(AttributedStyle.DEFAULT.foreground(nextCharColor ?
                    AttributedStyle.RED :  AttributedStyle.GREEN))
                    .append(c);
            nextCharColor = !nextCharColor;
        }
        return sb.toAnsi();
    }

    /**
     * Got to a position in the terminal.
     *
     * @param row    The row to go to.
     * @param column The column to go to.
     */
    private void position(int row, int column) {
        terminal.puts(Capability.cursor_address, row, column);
    }

    /**
     * Get the current size of the terminal.
     *
     * @return The terminal size.
     */
    private Size getSize() {
        final Size size = terminal.getSize();
        if (size == null || size.getColumns() == 0 || size.getRows() == 0) {
            return new Size(80, 24);
        }
        return size;
    }
}
