package pink.philip.christmas19.snow;

import org.jline.terminal.Terminal;

import java.util.Objects;

/**
 * Renders snow flakes on a screen.
 */
public abstract class SnowRenderingEngine {

    /**
     * The engine calculating and updating flakes.
     */
    protected final SnowEngine source;

    /**
     * The terminal used as the output.
     */
    protected final Terminal output;

    /**
     * Create an instance of this engine.
     *
     * @param source The snow calculating engine.
     * @param output The output terminal.
     */
    protected SnowRenderingEngine(SnowEngine source, Terminal output) {
        this.source = Objects.requireNonNull(source);
        this.output = Objects.requireNonNull(output);
    }

    /**
     * Call a step of the {@link SnowEngine} and output the results on a
     * {@link Terminal}.
     */
    public abstract void stepAndRender();
}
