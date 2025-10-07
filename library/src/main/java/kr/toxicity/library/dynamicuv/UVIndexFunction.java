package kr.toxicity.library.dynamicuv;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface UVIndexFunction {
    @NotNull String indexing(int index);
}
