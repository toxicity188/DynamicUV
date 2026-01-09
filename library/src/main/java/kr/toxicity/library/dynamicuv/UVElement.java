package kr.toxicity.library.dynamicuv;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Represents a UV element.
 */
public final class UVElement {
    private final Map<UVFace, UVPos> mappingPos;

    private final ElementVector from;
    private final ElementVector to;
    private final ElementVector pixel;
    private final UVSpace space;
    private final ColorType colorType;
    private final List<UVMappedFace> faces;

    /**
     * Creates a new UV element.
     *
     * @param scale the scale of the element
     * @param position the position of the element
     * @param space the UV space
     * @param colorType the color type
     * @param mappingPos the mapping positions
     */
    public UVElement(
        @NotNull ElementVector scale,
        @NotNull ElementVector position,
        @NotNull UVSpace space,
        @NotNull ColorType colorType,
        @NotNull Map<UVFace, UVPos> mappingPos
    ) {
        var center = scale.toModelLocation();
        this.space = space;
        this.mappingPos = new EnumMap<>(mappingPos);
        this.colorType = colorType;
        var centerPos = position.toModelLocation().plus(ElementVector.CENTER);
        from = center.div(-2).plus(centerPos);
        to = center.div(2).plus(centerPos);
        pixel = center.div(space.x(), space.y(), space.z());
        var list = new ArrayList<UVMappedFace>();
        for (UVFace value : this.mappingPos.keySet()) {
            value.iterate(
                this,
                list::add
            );
        }
        faces = Collections.unmodifiableList(list);
    }

    /**
     * Gets the color type.
     *
     * @return the color type
     */
    public @NotNull ColorType colorType() {
        return colorType;
    }

    /**
     * Gets the starting vector.
     *
     * @return the starting vector
     */
    public @NotNull ElementVector from() {
        return from;
    }

    /**
     * Gets the list of mapped faces.
     *
     * @return the list of mapped faces
     */
    public @NotNull List<UVMappedFace> faces() {
        return faces;
    }

    /**
     * Gets the UV space.
     *
     * @return the UV space
     */
    public @NotNull UVSpace space() {
        return space;
    }

    /**
     * Gets the pixel vector.
     *
     * @return the pixel vector
     */
    public @NotNull ElementVector pixel() {
        return pixel;
    }

    /**
     * Gets the ending vector.
     *
     * @return the ending vector
     */
    public @NotNull ElementVector to() {
        return to;
    }

    /**
     * Converts the element to a JSON object.
     *
     * @param textureName the texture name
     * @return the JSON object
     */
    public @NotNull JsonObject asJson(@NotNull String textureName) {
        return UVUtil.packModel(textureName, UVMappedFace.compositedJson(faces));
    }

    void write(@NotNull UVModelData.Builder builder, @NotNull BufferedImage image) {
        for (Map.Entry<UVFace, UVPos> entry : mappingPos.entrySet()) {
            entry.getValue().iterate(entry.getKey().posOf(space), (x, z) -> colorType.buildModelData(builder, x < image.getWidth() && z < image.getHeight() ? image.getRGB(x, z) : 0));
        }
    }

    void buildJson(@NotNull UVModel.BuildContext context) {
        colorType.buildJson(context, faces);
    }

    /**
     * Represents the color type of the UV element.
     */
    public enum ColorType {
        /**
         * RGB color type.
         */
        RGB {
            @Override
            void buildModelData(UVModelData.@NotNull Builder builder, int value) {
                builder.colors().add(UVUtil.rgb(value));
            }

            @Override
            void buildJson(@NotNull UVModel.BuildContext context, @NotNull List<UVMappedFace> faces) {
                context.addToComposite(context.newModel(context.textureName.normalPixel(), UVMappedFace.compositedJson(faces)));
            }
        },
        /**
         * ARGB color type.
         */
        ARGB {
            @Override
            void buildModelData(UVModelData.@NotNull Builder builder, int value) {
                builder.colors().add(UVUtil.rgb(value));
                builder.flags().add(UVUtil.alpha(value) > 0);
            }

            @Override
            void buildJson(@NotNull UVModel.BuildContext context, @NotNull List<UVMappedFace> faces) {
                for (UVMappedFace face : faces) {
                    var obj = new JsonObject();
                    obj.addProperty("type", "minecraft:condition");
                    obj.addProperty("property", "minecraft:custom_model_data");
                    obj.addProperty("index", context.indexer.flag());
                    obj.add("on_true", context.newModel(context.textureName.normalPixel(), face.asJson()));
                    obj.add("on_false", EMPTY);
                    context.addToComposite(obj);
                }
            }
        },
        /**
         * Complex ARGB color type.
         */
        COMPLEX_ARGB {
            @Override
            void buildModelData(UVModelData.@NotNull Builder builder, int value) {
                builder.colors().add(UVUtil.rgb(value));
                builder.floats().add(Math.round((float) UVUtil.alpha(value) / 255F * 2F));
            }

            @Override
            void buildJson(@NotNull UVModel.BuildContext context, @NotNull List<UVMappedFace> faces) {
                for (UVMappedFace face : faces) {
                    var obj = new JsonObject();
                    obj.addProperty("type", "minecraft:range_dispatch");
                    obj.addProperty("property", "minecraft:custom_model_data");
                    obj.addProperty("index", context.indexer.floats());
                    obj.add("fallback", EMPTY);
                    var entries = new JsonArray(2);

                    entries.add(entry(1, context.newModel(context.textureName.translucentPixel(), face.asJson())));
                    context.indexer.shiftColor(-1);
                    entries.add(entry(2, context.newModel(context.textureName.normalPixel(), face.asJson())));
                    obj.add("entries", entries);

                    context.addToComposite(obj);
                }
            }

            private static @NotNull JsonObject entry(int threshold, @NotNull JsonObject model) {
                var entry = new JsonObject();
                entry.addProperty("threshold", threshold);
                entry.add("model", model);
                return entry;
            }
        }
        ;

        private static final JsonObject EMPTY = new JsonObject();

        static {
            EMPTY.addProperty("type", "minecraft:empty");
        }

        abstract void buildModelData(@NotNull UVModelData.Builder builder, int value);

        abstract void buildJson(@NotNull UVModel.BuildContext context, @NotNull List<UVMappedFace> faces);
    }
}
