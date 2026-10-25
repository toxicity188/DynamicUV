package kr.toxicity.library.dynamicuv;

import com.google.gson.JsonArray;
import org.jetbrains.annotations.NotNull;

public record UVIdentifiedJson(@NotNull String identifier, @NotNull JsonArray array) {
}
