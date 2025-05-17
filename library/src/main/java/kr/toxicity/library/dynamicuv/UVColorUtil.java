package kr.toxicity.library.dynamicuv;

import java.awt.image.BufferedImage;

public final class UVColorUtil {
    public static final BufferedImage EMPTY_IMAGE = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

    static {
        EMPTY_IMAGE.setRGB(0, 0, 0xFFFFFF);
    }

    private UVColorUtil() {
        throw new RuntimeException();
    }

    public static int rgb(int value) {
        return value & 0xFFFFFF;
    }

    public static boolean alpha(int value) {
        return ((value >> 24) & 0xFF) > 0;
    }
}
