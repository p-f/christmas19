package pink.philip.christmas19.data;

/**
 * A representation of a snowflake.
 */
public class SnowFlake {
    /**
     * The row where this flake is positioned.
     */
    private short posRow;
    /**
     * The column where this flake is positioned.
     */
    private short posCol;
    /**
     * A delay counter for update operations.
     */
    private short delay;

    /**
     * Create a new flake at a certain position.
     *
     * @param posRow The row ({@code y}).
     * @param posCol The column ({@code x}).
     * @param delay  The delay.
     */
    public SnowFlake(short posRow, short posCol, short delay) {
        this.posRow = posRow;
        this.posCol = posCol;
        this.delay = delay;
    }

    public short getPosRow() {
        return posRow;
    }

    public void setPosRow(short posRow) {
        this.posRow = posRow;
    }

    public short getPosCol() {
        return posCol;
    }

    public void setPosCol(short posCol) {
        this.posCol = posCol;
    }

    public short getDelay() {
        return delay;
    }

    public void setDelay(short delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return String.format("SnowFlake{row=%d, col=%d, del=%d}",
                posRow, posCol, delay);
    }
}
