package kr.toxicity.library.dynamicuv;

import org.jetbrains.annotations.NotNull;

public enum UVAxis {
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

    public abstract void iterate(@NotNull ElementVector from, @NotNull UVSpace space, @NotNull ElementVector div, LineConsumer consumer);

    public interface LineConsumer {
        void consume(float h, float v);
    }
}
