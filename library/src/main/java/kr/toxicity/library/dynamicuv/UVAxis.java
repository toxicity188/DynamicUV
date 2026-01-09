package kr.toxicity.library.dynamicuv;

import org.jetbrains.annotations.NotNull;

/**
 * Represents the axes for UV mapping iteration.
 */
public enum UVAxis {
    /**
     * The XZ plane.
     */
    XZ {
        @Override
        public void iterate(@NotNull ElementVector from, @NotNull UVSpace space, @NotNull ElementVector div, LineConsumer consumer) {
            var xi = from.x();
            for (int x = 0; x < space.x(); x++) {
                var zi = from.z();
                for (int z = 0; z < space.z(); z++) {
                    consumer.consume(xi, zi);
                    zi += div.z();
                }
                xi += div.x();
            }
        }
    },
    /**
     * The XY plane.
     */
    XY {
        @Override
        public void iterate(@NotNull ElementVector from, @NotNull UVSpace space, @NotNull ElementVector div, LineConsumer consumer) {
            var xi = from.x();
            for (int x = 0; x < space.x(); x++) {
                var yi = from.y();
                for (int y = 0; y < space.y(); y++) {
                    consumer.consume(xi, yi);
                    yi += div.y();
                }
                xi += div.x();
            }
        }
    },
    /**
     * The ZY plane.
     */
    ZY {
        @Override
        public void iterate(@NotNull ElementVector from, @NotNull UVSpace space, @NotNull ElementVector div, LineConsumer consumer) {
            var zi = from.z();
            for (int z = 0; z < space.z(); z++) {
                var yi = from.y();
                for (int y = 0; y < space.y(); y++) {
                    consumer.consume(zi, yi);
                    yi += div.y();
                }
                zi += div.z();
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
    public abstract void iterate(@NotNull ElementVector from, @NotNull UVSpace space, @NotNull ElementVector div, LineConsumer consumer);

    /**
     * A functional interface for consuming line coordinates.
     */
    public interface LineConsumer {
        /**
         * Consumes the horizontal and vertical coordinates.
         *
         * @param h the horizontal coordinate
         * @param v the vertical coordinate
         */
        void consume(float h, float v);
    }
}
