package kr.toxicity.library.dynamicuv;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

/**
 * Utility class for UV operations.
 */
public final class UVUtil {

    static final BufferedImage NORMAL_PIXEL = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
    static final BufferedImage TRANSLUCENT_PIXEL = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

    static {
        Arrays.fill(((DataBufferInt) NORMAL_PIXEL.getRaster().getDataBuffer()).getData(), 0xFFFFFF);
        Arrays.fill(((DataBufferInt) TRANSLUCENT_PIXEL.getRaster().getDataBuffer()).getData(), 0x80FFFFFF);
    }

    private UVUtil() {
        throw new RuntimeException();
    }

    /**
     * Extracts the RGB value from an integer.
     *
     * @param value the integer value
     * @return the RGB value
     */
    public static int rgb(int value) {
        return value & 0xFFFFFF;
    }

    /**
     * Extracts the alpha value from an integer.
     *
     * @param value the integer value
     * @return the alpha value
     */
    public static int alpha(int value) {
        return (value >> 24) & 0xFF;
    }

    /**
     * Packs a model into a JSON object.
     *
     * @param textureName the name of the texture
     * @param elements the elements of the model
     * @return the packed model as a JSON object
     */
    public static @NotNull JsonObject packModel(@NotNull String textureName, @NotNull JsonArray elements) {
        var obj = new JsonObject();
        var textures = new JsonObject();
        textures.addProperty("0", textureName);
        textures.addProperty("particle", textureName);
        obj.add("textures", textures);
        obj.add("elements", elements);
        return obj;
    }
}
