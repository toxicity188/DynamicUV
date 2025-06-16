package kr.toxicity.library.dynamicuv;

import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanImmutableList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntImmutableList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.jetbrains.annotations.NotNull;

public record UVModelData(@NotNull BooleanList flags, @NotNull IntList colors) {

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final BooleanArrayList flags = new BooleanArrayList();
        private final IntArrayList colors = new IntArrayList();

        private Builder() {
        }

        public @NotNull BooleanArrayList flags() {
            return flags;
        }

        public @NotNull IntArrayList colors() {
            return colors;
        }

        public @NotNull UVModelData build() {
            return new UVModelData(
                    BooleanImmutableList.of(flags.toArray(new boolean[0])),
                    IntImmutableList.of(colors.toArray(new int[0]))
            );
        }
    }
}
