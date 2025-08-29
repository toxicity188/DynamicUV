package kr.toxicity.library.dynamicuv;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public final class UVUtil {
    public static final BufferedImage EMPTY_IMAGE = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);

    static {
        for (int w = 0; w < 16; w++) {
            for (int h = 0; h < 16; h++) {
                EMPTY_IMAGE.setRGB(w, h, 0xFFFFFF);
            }
        }
    }

    private UVUtil() {
        throw new RuntimeException();
    }

    public static int rgb(int value) {
        return value & 0xFFFFFF;
    }

    public static boolean alpha(int value) {
        return ((value >> 24) & 0xFF) > 0;
    }

    public static @NotNull JsonObject packModel(@NotNull String textureName, @NotNull JsonArray elements) {
        var obj = new JsonObject();
        var textures = new JsonObject();
        textures.addProperty("0", textureName);
        textures.addProperty("particle", textureName);
        obj.add("textures", textures);
        obj.add("elements", elements);
        return obj;
    }

    public static @NotNull JsonObject model(@NotNull UVNamespace namespace, @NotNull String modelName, @NotNull UVIndexer indexer, int indexes) {
        var obj = new JsonObject();
        obj.addProperty("type", "model");
        obj.addProperty("model", namespace.asset(modelName));
        var tints = new JsonArray(indexes);
        for (int i = 0; i < indexes; i++) {
            var tint = new JsonObject();
            tint.addProperty("type", "minecraft:custom_model_data");
            tint.addProperty("index", indexer.color());
            tint.addProperty("default", 0);
            tints.add(tint);
        }
        obj.add("tints", tints);
        return obj;
    }
}
