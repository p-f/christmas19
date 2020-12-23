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
     * The available cells, per column.
     */
    private int[] availableSpace;

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
        availableSpace = new int[size.getColumns()];
        Arrays.fill(availableSpace, size.getRows());
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
                final short col = flake.getPosCol();
                if (newRow > availableSpace[col]) {
                    it.remove();
                    if (availableSpace[col] > 0) {
                        availableSpace[col]--;
                    }
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

    /**
     * Get the number of flakes fallen to the ground, per column.
     */
    public int getFallenFlakesPerCol(int column) {
        final int rows = size.getRows();
        return rows - availableSpace[column];
    }

    /**
     * Get the size.
     *
     * @return The size.
     */
    public Size getSize() {
        return size;
    }
}
