package kr.toxicity.library.dynamicuv;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a 3D space in UV coordinates.
 *
 * @param x the x dimension
 * @param y the y dimension
 * @param z the z dimension
 */
public record UVSpace(int x, int y, int z) {

    /**
     * Returns the UV position in the XY plane.
     *
     * @return the UV position in the XY plane
     */
    public @NotNull UVPos posXY() {
        return new UVPos(x, y);
    }

    /**
     * Returns the UV position in the XZ plane.
     *
     * @return the UV position in the XZ plane
     */
    public @NotNull UVPos posXZ() {
        return new UVPos(x, z);
    }

    /**
     * Returns the UV position in the ZY plane.
     *
     * @return the UV position in the ZY plane
     */
    public @NotNull UVPos posZY() {
        return new UVPos(z, y);
    }
}
