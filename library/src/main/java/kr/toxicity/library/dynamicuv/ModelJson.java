package kr.toxicity.library.dynamicuv;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a model JSON structure.
 *
 * @param name the name of the model
 * @param jsonObject the JSON object representing the model
 * @param elements the number of elements in the model
 */
public record ModelJson(@NotNull String name, @NotNull JsonObject jsonObject, int elements) {

    /**
     * Estimates the size of the model.
     *
     * @return the estimated size
     */
    public long estimatedSize() {
        return 256L * elements;
    }

    /**
     * Converts the model JSON to a UVByteBuilder.
     *
     * @param namespace the UV namespace
     * @return the UVByteBuilder
     */
    public @NotNull UVByteBuilder asBuilder(@NotNull UVNamespace namespace) {
        return UVByteBuilder.of(namespace.model(name), estimatedSize(), jsonObject);
    }

    /**
     * Converts the model JSON to a JSON object suitable for model definition.
     *
     * @param namespace the UV namespace
     * @param indexer the UV indexer
     * @return the JSON object
     */
    public @NotNull JsonObject asModelJson(@NotNull UVNamespace namespace, @NotNull UVIndexer indexer) {
        var obj = new JsonObject();
        obj.addProperty("type", "model");
        obj.addProperty("model", namespace.asset(name));
        var tints = new JsonArray(elements);
        for (int i = 0; i < elements; i++) {
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
