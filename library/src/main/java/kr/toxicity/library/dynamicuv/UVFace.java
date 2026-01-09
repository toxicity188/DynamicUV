package kr.toxicity.library.dynamicuv;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents the faces of a UV element.
 */
@RequiredArgsConstructor
public enum UVFace {
    /**
     * The north face.
     */
    NORTH("north", ElementVector::xy, UVSpace::posXY) {
        @Override
        public void iterate(@NotNull UVElement element, @NotNull Consumer<UVMappedFace> face) {
            var minZ = Math.min(element.from().z(), element.to().z());
            var pixel = element.pixel().mul(-1);
            UVAxis.XY.iterate(
                element.to(),
                element.space(),
                pixel,
                (x, y) -> face.accept(new UVMappedFace(
                    this,
                    new ElementVector(x, y, minZ),
                    pixel
                ))
            );
        }
    },
    /**
     * The south face.
     */
    SOUTH("south", ElementVector::xy, UVSpace::posXY) {
        @Override
        public void iterate(@NotNull UVElement element, @NotNull Consumer<UVMappedFace> face) {
            var maxZ = Math.max(element.from().z(), element.to().z());
            var pixel = element.pixel().mul(1, -1, -1);
            UVAxis.XY.iterate(
                new ElementVector(element.from().x(), element.to().y(), element.to().z()),
                element.space(),
                pixel,
                (x, y) -> face.accept(new UVMappedFace(
                    this,
                    new ElementVector(x, y, maxZ),
                    pixel
                ))
            );
        }
    },
    /**
     * The east face.
     */
    EAST("east", ElementVector::zy, UVSpace::posZY) {
        @Override
        public void iterate(@NotNull UVElement element, @NotNull Consumer<UVMappedFace> face) {
            var maxX = Math.max(element.from().x(), element.to().x());
            var pixel = element.pixel().mul(-1);
            UVAxis.ZY.iterate(
                element.to(),
                element.space(),
                pixel,
                (z, y) -> face.accept(new UVMappedFace(
                    this,
                    new ElementVector(maxX, y, z),
                    pixel
                ))
            );
        }
    },
    /**
     * The west face.
     */
    WEST("west", ElementVector::zy, UVSpace::posZY) {
        @Override
        public void iterate(@NotNull UVElement element, @NotNull Consumer<UVMappedFace> face) {
            var minX = Math.min(element.from().x(), element.to().x());
            var pixel = element.pixel().mul(-1, -1, 1);
            UVAxis.ZY.iterate(
                new ElementVector(element.to().x(), element.to().y(), element.from().z()),
                element.space(),
                pixel,
                (z, y) -> face.accept(new UVMappedFace(
                    this,
                    new ElementVector(minX, y, z),
                    pixel
                ))
            );
        }
    },
    /**
     * The up face.
     */
    UP("up", ElementVector::xz, UVSpace::posXZ) {
        @Override
        public void iterate(@NotNull UVElement element, @NotNull Consumer<UVMappedFace> face) {
            var maxY = Math.max(element.from().y(), element.to().y());
            var pixel = element.pixel().mul(-1);
            UVAxis.XZ.iterate(
                element.to(),
                element.space(),
                pixel,
                (x, z) -> face.accept(new UVMappedFace(
                        this,
                        new ElementVector(x, maxY, z),
                        pixel
                    )
                ));
        }
    },
    /**
     * The down face.
     */
    DOWN("down", ElementVector::xz, UVSpace::posXZ) {
        @Override
        public void iterate(@NotNull UVElement element, @NotNull Consumer<UVMappedFace> face) {
            var minY = Math.min(element.from().y(), element.to().y());
            var pixel = element.pixel().mul(-1, 1, -1);
            UVAxis.XZ.iterate(
                new ElementVector(element.to().x(), element.from().y(), element.to().z()),
                element.space(),
                pixel,
                (x, z) -> face.accept(new UVMappedFace(
                    this,
                    new ElementVector(x, minY, z),
                    pixel
                ))
            );
        }
    };

    private final String uvName;
    private final Function<ElementVector, ElementVector> vectorMapper;
    private final Function<UVSpace, UVPos> posMapper;

    /**
     * Maps the given vector according to the face's orientation.
     *
     * @param vector the vector to map
     * @return the mapped vector
     */
    public @NotNull ElementVector mapVector(@NotNull ElementVector vector) {
        return vectorMapper.apply(vector);
    }

    /**
     * Gets the UV position for the given space according to the face's orientation.
     *
     * @param space the UV space
     * @return the UV position
     */
    public @NotNull UVPos posOf(@NotNull UVSpace space) {
        return posMapper.apply(space);
    }

    /**
     * Gets the name of the UV face.
     *
     * @return the UV face name
     */
    public @NotNull String uvName() {
        return uvName;
    }

    /**
     * Iterates over the mapped faces for the given element.
     *
     * @param element the UV element
     * @param face the consumer to accept each mapped face
     */
    public abstract void iterate(@NotNull UVElement element, @NotNull Consumer<UVMappedFace> face);
}
