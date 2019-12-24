package pink.philip.christmas19.snow;

import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp.Capability;
import pink.philip.christmas19.data.SnowFlake;

import java.io.PrintWriter;

/**
 * A basic rendering engine drawing all flakes in some order.
 */
public class BasicRenderingEngine extends SnowRenderingEngine {

    /**
     * Create an instance of this engine.
     *
     * @param source The snow calculating engine.
     * @param output The output terminal.
     */
    public BasicRenderingEngine(SnowEngine source, Terminal output) {
        super(source, output);
    }

    @Override
    public void stepAndRender() {
        source.preStep();
        source.step();
        final PrintWriter writer = output.writer();
        for (SnowFlake flake : source.getFlakes()) {
            if (flake.getPosRow() < 0) continue;
            output.puts(Capability.cursor_address, flake.getPosRow(),
                    flake.getPosCol());
            writer.append("*");
        }
        output.flush();
    }
}
