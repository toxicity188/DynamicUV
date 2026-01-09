package kr.toxicity.library.dynamicuv;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a 2D position in the UV space.
 *
 * @param x the x coordinate
 * @param z the z coordinate
 */
public record UVPos(int x, int z) {

    /**
     * Iterates over a rectangular region starting from this position.
     *
     * @param uv the dimensions of the region to iterate over
     * @param consumer the consumer to accept each coordinate in the region
     */
    public void iterate(@NotNull UVPos uv, @NotNull UVConsumer consumer) {
        for (int xi = 0; xi < uv.x ; xi++) {
            for (int zi = 0; zi < uv.z ; zi++) {
                consumer.consume(x + xi, z + zi);
            }
        }
    }

    /**
     * A functional interface for consuming UV coordinates.
     */
    public interface UVConsumer {
        /**
         * Consumes the given coordinates.
         *
         * @param x the x coordinate
         * @param z the z coordinate
         */
        void consume(int x, int z);
    }
}
