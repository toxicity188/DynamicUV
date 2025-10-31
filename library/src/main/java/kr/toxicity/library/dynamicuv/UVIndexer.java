package kr.toxicity.library.dynamicuv;

public final class UVIndexer {
    private int model;
    private int color;
    private int flag;

    public int model() {
        return model++;
    }
    public int color() {
        return color++;
    }
    public int flag() {
        return flag++;
    }

    public int shiftModel(int shift) {
        var value = model;
        model += shift;
        return value;
    }

    public int shiftColor(int shift) {
        var value = color;
        color += shift;
        return value;
    }

    public int shiftFlag(int shift) {
        var value = flag;
        flag += shift;
        return value;
    }
}
