package kr.toxicity.library.dynamicuv;

import org.jetbrains.annotations.NotNull;

/**
 * Represents the names of the textures used in UV mapping.
 *
 * @param normalPixel the name of the normal pixel texture
 * @param translucentPixel the name of the translucent pixel texture
 */
public record UVTextureName(@NotNull String normalPixel, @NotNull String translucentPixel) {
    /**
     * The default texture names.
     */
    public static final UVTextureName DEFAULT = new UVTextureName(
        "normal_pixel",
        "translucent_pixel"
    );

    /**
     * Creates a UVByteBuilder for the normal pixel texture.
     *
     * @param namespace the UV namespace
     * @return the UVByteBuilder
     */
    public @NotNull UVByteBuilder normalPixel(@NotNull UVNamespace namespace) {
        return UVByteBuilder.normalPixel(namespace, normalPixel);
    }

    /**
     * Creates a UVByteBuilder for the translucent pixel texture.
     *
     * @param namespace the UV namespace
     * @return the UVByteBuilder
     */
    public @NotNull UVByteBuilder translucentPixel(@NotNull UVNamespace namespace) {
        return UVByteBuilder.translucentPixel(namespace, translucentPixel);
    }
}
