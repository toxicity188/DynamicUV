package kr.toxicity.library.dynamicuv;

import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanImmutableList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntImmutableList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.jetbrains.annotations.NotNull;

public record UVModelData(
    @NotNull BooleanList flags,
    @NotNull IntList colors
) {

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final BooleanList flags = new BooleanArrayList();
        private final IntList colors = new IntArrayList();

        private Builder() {
        }

        public @NotNull BooleanList flags() {
            return flags;
        }

        public @NotNull IntList colors() {
            return colors;
        }

        public @NotNull UVModelData build() {
            return new UVModelData(
                new BooleanImmutableList(flags),
                new IntImmutableList(colors)
            );
        }
    }
}
