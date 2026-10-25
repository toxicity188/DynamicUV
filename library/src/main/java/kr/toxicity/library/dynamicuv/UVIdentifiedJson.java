package kr.toxicity.library.dynamicuv;

import com.google.gson.JsonArray;
import org.jetbrains.annotations.NotNull;

/**
 * cube element's JSON array with identifier
 * @param identifier the identifier
 * @param array the JSON array
 */
public record UVIdentifiedJson(@NotNull String identifier, @NotNull JsonArray array) {
}
