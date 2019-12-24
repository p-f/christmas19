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

import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp.Capability;

import java.util.Objects;

/**
 * A task resetting a terminal.
 */
public class TerminalResetTask implements Runnable {

    /**
     * The terminal.
     */
    private final Terminal terminal;

    /**
     * Create an instance of this task.
     *
     * @param terminal The terminal to reset.
     */
    public TerminalResetTask(Terminal terminal) {
        this.terminal = Objects.requireNonNull(terminal);
    }

    @Override
    public void run() {
        terminal.echo(true);
        terminal.puts(Capability.clear_screen);
        terminal.puts(Capability.cursor_visible);
        terminal.flush();
    }
}
