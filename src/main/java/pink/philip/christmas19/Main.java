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
package pink.philip.christmas19;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import pink.philip.christmas19.out.TerminalOutputTask;
import pink.philip.christmas19.out.TerminalResetTask;

/**
 * The main class.
 */
public class Main {

    /**
     * Main method.
     *
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) throws Exception {
        Terminal terminal = TerminalBuilder.builder().build();
        Runtime.getRuntime().addShutdownHook(
                new Thread(new TerminalResetTask(terminal)));
        new TerminalOutputTask(terminal).run();

        Thread.sleep(5000);
    }
}
