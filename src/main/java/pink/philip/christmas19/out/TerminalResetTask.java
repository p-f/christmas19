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
