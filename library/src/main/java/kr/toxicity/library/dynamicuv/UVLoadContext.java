package kr.toxicity.library.dynamicuv;

import lombok.Builder;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the context for loading UV models.
 *
 * @param textureName the texture name
 * @param indexFunction the index function
 * @param buildFunction the build function
 */
@Builder(builderClassName = "Builder")
public record UVLoadContext(
    @NotNull UVTextureName textureName,
    @NotNull UVIndexFunction indexFunction,
    @NotNull UVBuildFunction buildFunction
) {
}
