package kr.toxicity.library.dynamicuv;

import com.google.gson.JsonArray;
import org.jetbrains.annotations.NotNull;

public interface UVBuildFunction {
    void build(@NotNull UVIndexer indexer, @NotNull UVNamespace namespace, @NotNull JsonArray jsonArray);
}
