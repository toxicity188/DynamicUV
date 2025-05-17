package kr.toxicity.library.dynamicuv;

import java.util.concurrent.atomic.AtomicInteger;

public class UVIndexer {
    private final AtomicInteger color = new AtomicInteger();
    private final AtomicInteger flag = new AtomicInteger();

    public int color() {
        return color.getAndIncrement();
    }
    public int flag() {
        return flag.getAndIncrement();
    }
}
