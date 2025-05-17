package kr.toxicity.library.dynamicuv;

import org.jetbrains.annotations.NotNull;

public record UVSpace(int x, int y, int z) {
    public @NotNull UVPos posXY() {
        return new UVPos(x, y);
    }
    public @NotNull UVPos posXZ() {
        return new UVPos(x, z);
    }
    public @NotNull UVPos posZY() {
        return new UVPos(z, y);
    }
}
