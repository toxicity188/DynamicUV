package kr.toxicity.library.dynamicuv;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.util.*;

public final class UVElement {
    private final Map<UVFace, UVPos> mappingPos;

    private final ElementVector from;
    private final ElementVector to;
    private final ElementVector pixel;
    private final UVSpace space;
    private final ColorType colorType;
    private final List<UVMappedFace> faces;

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

    public @NotNull ColorType colorType() {
        return colorType;
    }

    public @NotNull ElementVector from() {
        return from;
    }
    public @NotNull List<UVMappedFace> faces() {
        return faces;
    }

    public @NotNull UVSpace space() {
        return space;
    }

    public @NotNull ElementVector pixel() {
        return pixel;
    }

    public @NotNull ElementVector to() {
        return to;
    }


    void write(@NotNull UVModelData.Builder builder, @NotNull BufferedImage image) {
        for (Map.Entry<UVFace, UVPos> entry : mappingPos.entrySet()) {
            entry.getValue().iterate(entry.getKey().posOf(space), (x, z) -> colorType.write(builder, x < image.getWidth() && z < image.getHeight() ? image.getRGB(x, z) : 0));
        }
    }

    public @NotNull JsonObject asJson(@NotNull String textureName) {
        var obj = new JsonObject();
        var textures = new JsonObject();
        textures.addProperty("0", textureName);
        textures.addProperty("particle", textureName);
        obj.add("textures", textures);
        var elements = new JsonArray(faces.size());
        for (UVMappedFace face : faces) {
            elements.add(face.asJson(0));
        }
        obj.add("elements", elements);
        return obj;
    }

    @NotNull List<JsonObject> asJson(@NotNull UVNamespace namespace, @NotNull UVIndexer indexer, @NotNull List<ModelJson> modelJsons) {
        return colorType.asJson(namespace, indexer, modelJsons);
    }

    @NotNull List<ModelJson> pack(@NotNull String modelName, @NotNull String textureName, @NotNull UVIndexer indexer) {
        return colorType.pack(modelName, textureName, indexer, faces);
    }

    public enum ColorType {
        RGB {
            @Override
            void write(UVModelData.@NotNull Builder builder, int value) {
                builder.colors().add(UVUtil.rgb(value));
            }

            @Override
            @NotNull List<ModelJson> pack(@NotNull String modelName, @NotNull String textureName, @NotNull UVIndexer indexer, @NotNull List<UVMappedFace> faces) {
                var array = new JsonArray(faces.size());
                var i = 0;
                for (UVMappedFace face : faces) {
                    array.add(face.asJson(i++));
                }
                return Collections.singletonList(new ModelJson(modelName + "_" + indexer.model(), UVUtil.packModel(textureName, array), faces.size()));
            }

            @Override
            @NotNull List<JsonObject> asJson(@NotNull UVNamespace namespace, @NotNull UVIndexer indexer, @NotNull List<ModelJson> modelJsons) {
                return modelJsons.stream()
                        .map(json -> UVUtil.model(namespace, json.name(), indexer, json.elements()))
                        .toList();
            }
        },
        ARGB {

            private static final JsonObject EMPTY = new JsonObject();
            static {
                EMPTY.addProperty("type", "empty");
            }

            @Override
            void write(UVModelData.@NotNull Builder builder, int value) {
                builder.colors().add(UVUtil.rgb(value));
                builder.flags().add(UVUtil.alpha(value));
            }

            @Override
            @NotNull List<JsonObject> asJson(@NotNull UVNamespace namespace, @NotNull UVIndexer indexer, @NotNull List<ModelJson> modelJsons) {
                return modelJsons.stream()
                        .map(json -> UVUtil.model(namespace, json.name(), indexer, json.elements()))
                        .map(rgb -> {
                            var obj = new JsonObject();
                            obj.addProperty("type", "minecraft:condition");
                            obj.addProperty("property", "minecraft:custom_model_data");
                            obj.addProperty("index", indexer.flag());
                            obj.add("on_true", rgb);
                            obj.add("on_false", EMPTY);
                            return obj;
                        })
                        .toList();
            }

            @Override
            @NotNull List<ModelJson> pack(@NotNull String modelName, @NotNull String textureName, @NotNull UVIndexer indexer, @NotNull List<UVMappedFace> faces) {
                return faces.stream()
                        .map(face -> new ModelJson(modelName + "_" + indexer.model(), face.asJson(textureName), 1))
                        .toList();
            }
        }
        ;
        abstract void write(@NotNull UVModelData.Builder builder, int value);
        abstract @NotNull List<JsonObject> asJson(@NotNull UVNamespace namespace, @NotNull UVIndexer indexer, @NotNull List<ModelJson> modelJsons);
        abstract @NotNull List<ModelJson> pack(@NotNull String modelName, @NotNull String textureName, @NotNull UVIndexer indexer, @NotNull List<UVMappedFace> faces);
    }
}
