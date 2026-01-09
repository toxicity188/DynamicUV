package kr.toxicity.library.dynamicuv;

import com.google.gson.JsonArray;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a 3D vector for elements.
 *
 * @param x the x coordinate
 * @param y the y coordinate
 * @param z the z coordinate
 */
public record ElementVector(float x, float y, float z) {

    /**
     * The center vector (8, 8, 8).
     */
    public static final ElementVector CENTER = new ElementVector(8, 8, 8);

    /**
     * Returns a vector with the minimum coordinates of the two given vectors.
     *
     * @param from the first vector
     * @param to the second vector
     * @return the vector with minimum coordinates
     */
    public static @NotNull ElementVector min(@NotNull ElementVector from, @NotNull ElementVector to) {
        return new ElementVector(
            Math.min(from.x(), to.x()),
            Math.min(from.y(), to.y()),
            Math.min(from.z(), to.z())
        );
    }

    /**
     * Returns a vector with the maximum coordinates of the two given vectors.
     *
     * @param from the first vector
     * @param to the second vector
     * @return the vector with maximum coordinates
     */
    public static @NotNull ElementVector max(@NotNull ElementVector from, @NotNull ElementVector to) {
        return new ElementVector(
            Math.max(from.x(), to.x()),
            Math.max(from.y(), to.y()),
            Math.max(from.z(), to.z())
        );
    }

    /**
     * Adds the given coordinates to this vector.
     *
     * @param x the x coordinate to add
     * @param y the y coordinate to add
     * @param z the z coordinate to add
     * @return the resulting vector
     */
    public @NotNull ElementVector plus(float x, float y, float z) {
        return new ElementVector(this.x + x, this.y + y, this.z + z);
    }

    /**
     * Adds the given vector to this vector.
     *
     * @param vector the vector to add
     * @return the resulting vector
     */
    public @NotNull ElementVector plus(@NotNull ElementVector vector) {
        return plus(vector.x, vector.y, vector.z);
    }

    /**
     * Subtracts the given coordinates from this vector.
     *
     * @param x the x coordinate to subtract
     * @param y the y coordinate to subtract
     * @param z the z coordinate to subtract
     * @return the resulting vector
     */
    public @NotNull ElementVector minus(float x, float y, float z) {
        return new ElementVector(this.x - x, this.y - y, this.z - z);
    }

    /**
     * Subtracts the given vector from this vector.
     *
     * @param vector the vector to subtract
     * @return the resulting vector
     */
    public @NotNull ElementVector minus(@NotNull ElementVector vector) {
        return minus(vector.x, vector.y, vector.z);
    }

    /**
     * Converts this vector to model location (multiplies by 16).
     *
     * @return the resulting vector
     */
    public @NotNull ElementVector toModelLocation() {
        return mul(16);
    }

    /**
     * Inflates the vector by the given amount.
     *
     * @param inflate the amount to inflate
     * @return the resulting vector
     */
    public @NotNull ElementVector inflate(float inflate) {
        return new ElementVector(
            x + inflate / 8,
            y + inflate / 8,
            z + inflate / 8
        );
    }

    /**
     * Multiplies the vector coordinates by the given factors.
     *
     * @param x the x factor
     * @param y the y factor
     * @param z the z factor
     * @return the resulting vector
     */
    public @NotNull ElementVector mul(float x, float y, float z) {
        return new ElementVector(this.x * x, this.y * y, this.z * z);
    }

    /**
     * Divides the vector coordinates by the given divisors.
     *
     * @param x the x divisor
     * @param y the y divisor
     * @param z the z divisor
     * @return the resulting vector
     */
    public @NotNull ElementVector div(float x, float y, float z) {
        return new ElementVector(this.x / x, this.y / y, this.z / z);
    }

    /**
     * Multiplies the vector coordinates by the given scalar.
     *
     * @param scala the scalar
     * @return the resulting vector
     */
    public @NotNull ElementVector mul(float scala) {
        return mul(scala, scala, scala);
    }

    /**
     * Divides the vector coordinates by the given scalar.
     *
     * @param scala the scalar
     * @return the resulting vector
     */
    public @NotNull ElementVector div(float scala) {
        return div(scala, scala, scala);
    }

    /**
     * Converts the vector to a JSON array.
     *
     * @return the JSON array
     */
    public @NotNull JsonArray asJson() {
        var array = new JsonArray(3);
        array.add(x);
        array.add(y);
        array.add(z);
        return array;
    }

    /**
     * Returns a new vector with only the X and Z components (Y is 0).
     *
     * @return the XZ vector
     */
    public @NotNull ElementVector xz() {
        return new ElementVector(x, 0, z);
    }

    /**
     * Returns a new vector with only the X and Y components (Z is 0).
     *
     * @return the XY vector
     */
    public @NotNull ElementVector xy() {
        return new ElementVector(x, y, 0);
    }

    /**
     * Returns a new vector with only the Z and Y components (X is 0).
     *
     * @return the ZY vector
     */
    public @NotNull ElementVector zy() {
        return new ElementVector(0, y, z);
    }
}
