package kr.toxicity.library.dynamicuv;

import org.jetbrains.annotations.NotNull;

public record UVLoadContext(@NotNull UVIndexFunction indexFunction, @NotNull UVBuildFunction buildFunction) {
}
