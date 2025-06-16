package kr.toxicity.library.dynamicuv;

public class UVIndexer {
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
}
