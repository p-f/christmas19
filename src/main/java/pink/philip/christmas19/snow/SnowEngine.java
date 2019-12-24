package pink.philip.christmas19.snow;

import org.jline.terminal.Size;
import pink.philip.christmas19.data.Resources;
import pink.philip.christmas19.data.SnowFlake;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

/**
 * Calculates snow falling.
 */
public class SnowEngine {

    /**
     * A bound of the random delay for new and moved flakes.
     */
    private static final short DELAY_RANDOM = 3;

    /**
     * The size of the canvas.
     */
    private final Size targetSize;

    /**
     * The effectively usable size of the canvas.
     */
    private final Size size;

    /**
     * Random number generator for this class.
     */
    private final Random random;

    /**
     * A list of flakes to handle.
     */
    private List<SnowFlake> flakes;

    /**
     * A list of consumers called on flake updates.
     */
    private List<Consumer<SnowFlake>> updateListeners;

    /**
     * The number of flakes per row.
     */
    private short flakesPerRow = 0;

    /**
     * The allowed difference in flakes between rows.
     */
    private short flakesPerRowDiff = 0;

    /**
     * Create a new instance of this engine.
     *
     * @param targetSize The target terminal size.
     */
    public SnowEngine(Size targetSize) {
        this.targetSize = Objects.requireNonNull(targetSize);
        this.size = new Size(
                Math.min(Short.MAX_VALUE, targetSize.getColumns()),
                Math.min(Short.MAX_VALUE, targetSize.getRows()));
        this.random = ThreadLocalRandom.current();
        flakes = new ArrayList<>();
        updateListeners = new ArrayList<>();
        init();
    }

    /**
     * Get the current list of flakes.
     */
    public List<SnowFlake> getFlakes() {
        return Collections.unmodifiableList(flakes);
    }

    /**
     * Get the list of consumers called on flake updates.
     */
    public List<Consumer<SnowFlake>> getUpdateListeners() {
        return updateListeners;
    }

    /**
     * Initialize or reinitialize this engine.
     */
    public void init() {
        flakes.clear();
        final Resources resources = Resources.getInstance();
        final int cells = size.getColumns() * size.getRows() *
                resources.getScreenFillPercent() / 100;
        flakesPerRow = (short) (cells / size.getColumns());
        flakesPerRowDiff = (short) Math.sqrt(resources.getScreenFillPercent());
    }

    /**
     * Prepare a step, check how many flakes are to be updated. Decrement their
     * {@code delay}.
     *
     * @return The number of flakes that will be moved in the text step.
     */
    public int preStep() {
        spawnNewFlakes();
        int updates = 0;
        for (SnowFlake flake : flakes) {
            final short newDelay = (short) (flake.getDelay() - 1);
            if (newDelay <= 0) {
                updates++;
            }
            flake.setDelay(newDelay);
        }
        return updates;
    }

    /**
     * Run a step, updating all flakes.
     */
    public void step() {
        final Iterator<SnowFlake> it = flakes.iterator();
        while (it.hasNext()) {
            final SnowFlake flake = it.next();
            if (flake.getDelay() <= 0) {
                final short newRow = (short) (flake.getPosRow() + 1);
                if (newRow >= size.getRows()) {
                    it.remove();
                    continue;
                }
                flake.setPosRow(newRow);
                flake.setDelay((short) random.nextInt(DELAY_RANDOM));
                updateListeners.forEach(l -> l.accept(flake));
            }
        }
    }

    /**
     * Create a new flake.
     *
     * @return The new flake.
     */
    private SnowFlake makeFlake() {
        short col = (short) random.nextInt(size.getColumns());
        short delay = (short) random.nextInt(DELAY_RANDOM);
        return new SnowFlake((short) -1, col, delay);
    }

    /**
     * Add number of new flakes. The number will be about {@link #flakesPerRow}.
     */
    private void spawnNewFlakes() {
        final short newFlakesCount = (short) (flakesPerRow + random.nextInt(2 *
                flakesPerRowDiff) - flakesPerRowDiff);
        if (newFlakesCount <= 0) {
            return;
        }
        for (short i = 0; i < newFlakesCount; i++) {
            flakes.add(makeFlake());
        }
    }
}
