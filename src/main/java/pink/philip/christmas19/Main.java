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
