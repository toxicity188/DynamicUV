package kr.toxicity.library.dynamicuv;

/**
 * Manages indexing for UV models, colors, flags, and floats.
 */
public final class UVIndexer {

    private int model;
    private int color;
    private int flag;
    private int floats;

    /**
     * Gets the next model index.
     *
     * @return the next model index
     */
    public int model() {
        return model++;
    }

    /**
     * Gets the next color index.
     *
     * @return the next color index
     */
    public int color() {
        return color++;
    }

    /**
     * Gets the next flag index.
     *
     * @return the next flag index
     */
    public int flag() {
        return flag++;
    }

    /**
     * Gets the next float index.
     *
     * @return the next float index
     */
    public int floats() {
        return floats++;
    }

    /**
     * Shifts the model index by the given amount.
     *
     * @param shift the amount to shift
     * @return the previous model index
     */
    public int shiftModel(int shift) {
        var value = model;
        model += shift;
        return value;
    }

    /**
     * Shifts the color index by the given amount.
     *
     * @param shift the amount to shift
     * @return the previous color index
     */
    public int shiftColor(int shift) {
        var value = color;
        color += shift;
        return value;
    }

    /**
     * Shifts the flag index by the given amount.
     *
     * @param shift the amount to shift
     * @return the previous flag index
     */
    public int shiftFlag(int shift) {
        var value = flag;
        flag += shift;
        return value;
    }

    /**
     * Shifts the float index by the given amount.
     *
     * @param shift the amount to shift
     * @return the previous float index
     */
    public int shiftFloats(int shift) {
        var value = floats;
        floats += shift;
        return value;
    }
}
