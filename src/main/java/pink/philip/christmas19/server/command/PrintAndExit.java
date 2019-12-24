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
package pink.philip.christmas19.server.command;

import org.apache.sshd.common.util.threads.CloseableExecutorService;
import org.apache.sshd.server.command.AbstractCommandSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintStream;

/**
 * A simple command printing some text and closing the session.
 */
class PrintAndExit extends AbstractCommandSupport {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PrintAndExit.class);

    /**
     * The command to print.
     */
    private final String message;

    /**
     * Create a new instance of this command.
     *
     * @param command The command name.
     * @param executorService An executor.
     * @param message The message to print.
     */
    protected PrintAndExit(String command,
                           CloseableExecutorService executorService,
                           String message) {
        super(command, executorService);
        this.message = message;
    }

    @Override
    public void run() {
        final PrintStream printStream = new PrintStream(getOutputStream());
        printStream.println(message);
        printStream.flush();
        try {
            getSession().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
