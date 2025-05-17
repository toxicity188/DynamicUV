package kr.toxicity.library.dynamicuv;

import com.google.gson.JsonArray;
import org.jetbrains.annotations.NotNull;

public record ElementVector(float x, float y, float z) {
    public static final ElementVector CENTER = new ElementVector(8, 8, 8);

    public static @NotNull ElementVector min(@NotNull ElementVector from, @NotNull ElementVector to) {
        return new ElementVector(
                Math.min(from.x(), to.x()),
                Math.min(from.y(), to.y()),
                Math.min(from.z(), to.z())
        );
    }

    public static @NotNull ElementVector max(@NotNull ElementVector from, @NotNull ElementVector to) {
        return new ElementVector(
                Math.max(from.x(), to.x()),
                Math.max(from.y(), to.y()),
                Math.max(from.z(), to.z())
        );
    }

    public @NotNull ElementVector plus(float x, float y, float z) {
        return new ElementVector(this.x + x, this.y + y, this.z + z);
    }
    public @NotNull ElementVector plus(@NotNull ElementVector vector) {
        return plus(vector.x, vector.y, vector.z);
    }
    public @NotNull ElementVector minus(float x, float y, float z) {
        return new ElementVector(this.x + x, this.y + y, this.z + z);
    }
    public @NotNull ElementVector minus(@NotNull ElementVector vector) {
        return minus(vector.x, vector.y, vector.z);
    }

    public @NotNull ElementVector toModelLocation() {
        return mul(16);
    }

    public @NotNull ElementVector inflate(float inflate) {
        return new ElementVector(
                x + inflate / 8,
                y + inflate / 8,
                z + inflate / 8
        );
    }

    public @NotNull ElementVector mul(float x, float y, float z) {
        return new ElementVector(this.x * x, this.y * y, this.z * z);
    }
    public @NotNull ElementVector div(float x, float y, float z) {
        return new ElementVector(this.x / x, this.y / y, this.z / z);
    }
    public @NotNull ElementVector mul(float scala) {
        return mul(scala, scala, scala);
    }
    public @NotNull ElementVector div(float scala) {
        return div(scala, scala, scala);
    }

    public @NotNull JsonArray asJson() {
        var array = new JsonArray(3);
        array.add(x);
        array.add(y);
        array.add(z);
        return array;
    }

    public @NotNull ElementVector xz() {
        return new ElementVector(x, 0, z);
    }
    public @NotNull ElementVector xy() {
        return new ElementVector(x, y, 0);
    }
    public @NotNull ElementVector zy() {
        return new ElementVector(0, y, z);
    }
}
