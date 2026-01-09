package kr.toxicity.library.dynamicuv;

import org.jetbrains.annotations.NotNull;

/**
 * A functional interface for generating index strings.
 */
@FunctionalInterface
public interface UVIndexFunction {
    /**
     * Generates an index string for the given index.
     *
     * @param index the index
     * @return the index string
     */
    @NotNull String indexing(int index);
}
