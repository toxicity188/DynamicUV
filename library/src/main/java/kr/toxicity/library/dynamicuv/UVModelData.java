package kr.toxicity.library.dynamicuv;

import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanImmutableList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatImmutableList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntImmutableList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Represents the data of a UV model.
 *
 * @param floats the list of float values
 * @param flags the list of boolean flags
 * @param colors the list of integer colors
 */
public record UVModelData(
    @NotNull @Unmodifiable FloatList floats,
    @NotNull @Unmodifiable BooleanList flags,
    @NotNull @Unmodifiable IntList colors
) {

    /**
     * Creates a new builder for UVModelData.
     *
     * @return a new builder
     */
    public static @NotNull Builder builder() {
        return new Builder();
    }

    /**
     * Builder for UVModelData.
     */
    public static final class Builder {

        private final FloatList floats = new FloatArrayList();
        private final BooleanList flags = new BooleanArrayList();
        private final IntList colors = new IntArrayList();

        private Builder() {
        }

        /**
         * Gets the list of floats.
         *
         * @return the list of floats
         */
        public @NotNull FloatList floats() {
            return floats;
        }

        /**
         * Gets the list of flags.
         *
         * @return the list of flags
         */
        public @NotNull BooleanList flags() {
            return flags;
        }

        /**
         * Gets the list of colors.
         *
         * @return the list of colors
         */
        public @NotNull IntList colors() {
            return colors;
        }

        /**
         * Builds the UVModelData.
         *
         * @return the UVModelData
         */
        public @NotNull UVModelData build() {
            return new UVModelData(
                new FloatImmutableList(floats),
                new BooleanImmutableList(flags),
                new IntImmutableList(colors)
            );
        }
    }
}
