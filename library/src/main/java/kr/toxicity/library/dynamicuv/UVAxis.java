package kr.toxicity.library.dynamicuv;

import org.jetbrains.annotations.NotNull;

import static java.lang.Math.fma;

/**
 * Represents the axes for UV mapping iteration.
 */
enum UVAxis {
    /**
     * The XZ plane.
     */
    XZ {
        @Override
        void iterate(@NotNull ElementVector from, @NotNull UVSpace space, @NotNull ElementVector div, LineConsumer consumer) {
            var xf = from.x();
            var zf = from.z();
            var xd = div.x();
            var zd = div.z();
            for (int x = 0; x < space.x(); x++) {
                for (int z = 0; z < space.z(); z++) {
                    consumer.consume(fma(xd, x, xf), fma(zd, z, zf));
                }
            }
        }
    },
    /**
     * The XY plane.
     */
    XY {
        @Override
        void iterate(@NotNull ElementVector from, @NotNull UVSpace space, @NotNull ElementVector div, LineConsumer consumer) {
            var xf = from.x();
            var yf = from.y();
            var xd = div.x();
            var yd = div.y();
            for (int x = 0; x < space.x(); x++) {
                for (int y = 0; y < space.y(); y++) {
                    consumer.consume(fma(xd, x, xf), fma(yd, y, yf));
                }
            }
        }
    },
    /**
     * The ZY plane.
     */
    ZY {
        @Override
        void iterate(@NotNull ElementVector from, @NotNull UVSpace space, @NotNull ElementVector div, LineConsumer consumer) {
            var zf = from.z();
            var yf = from.y();
            var zd = div.z();
            var yd = div.y();
            for (int z = 0; z < space.z(); z++) {
                for (int y = 0; y < space.y(); y++) {
                    consumer.consume(fma(zd, z, zf), fma(yd, y, yf));
                }
            }
        }
    }
    ;

    /**
     * Iterates over the UV space based on the axis.
     *
     * @param from the starting vector
     * @param space the UV space dimensions
     * @param div the division vector
     * @param consumer the consumer to accept the iterated values
     */
    abstract void iterate(@NotNull ElementVector from, @NotNull UVSpace space, @NotNull ElementVector div, LineConsumer consumer);

    /**
     * A functional interface for consuming line coordinates.
     */
    interface LineConsumer {
        /**
         * Consumes the horizontal and vertical coordinates.
         *
         * @param h the horizontal coordinate
         * @param v the vertical coordinate
         */
        void consume(float h, float v);
    }
}
