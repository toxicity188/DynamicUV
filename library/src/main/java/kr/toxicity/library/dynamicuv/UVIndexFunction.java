package kr.toxicity.library.dynamicuv;

import org.jetbrains.annotations.NotNull;

/**
 * A functional interface for generating index strings.
 */
@FunctionalInterface
public interface UVIndexFunction {

    /**
     * Default function
     */
    UVIndexFunction DEFAULT = s -> s;

    /**
     * Generates an index string for the given index.
     *
     * @param identifier the identifier
     * @return the index string
     */
    @NotNull String indexing(@NotNull String identifier);
}
