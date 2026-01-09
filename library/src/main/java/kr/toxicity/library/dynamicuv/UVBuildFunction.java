package kr.toxicity.library.dynamicuv;

import com.google.gson.JsonArray;
import org.jetbrains.annotations.NotNull;

/**
 * A functional interface for building UV models.
 */
public interface UVBuildFunction {

    /**
     * Builds the UV model.
     *
     * @param indexer the UV indexer
     * @param namespace the UV namespace
     * @param jsonArray the JSON array to populate
     */
    void build(@NotNull UVIndexer indexer, @NotNull UVNamespace namespace, @NotNull JsonArray jsonArray);
}
