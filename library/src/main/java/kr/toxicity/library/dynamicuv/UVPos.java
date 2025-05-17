package kr.toxicity.library.dynamicuv;

import org.jetbrains.annotations.NotNull;

public record UVPos(int x, int z) {
    public void iterate(@NotNull UVPos uv, @NotNull UVConsumer consumer) {
        for (int xi = 0; xi < uv.x ; xi++) {
            for (int zi = 0; zi < uv.z ; zi++) {
                consumer.consume(x + xi, z + zi);
            }
        }
    }

    public interface UVConsumer {
        void consume(int x, int z);
    }
}
