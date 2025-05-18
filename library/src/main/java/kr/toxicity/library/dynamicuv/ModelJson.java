package kr.toxicity.library.dynamicuv;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public record ModelJson(@NotNull String name, @NotNull JsonObject jsonObject, int elements) {
    public @NotNull UVByteBuilder builder(@NotNull UVNamespace namespace) {
        return UVByteBuilder.of(namespace.model(name), jsonObject);
    }
}
