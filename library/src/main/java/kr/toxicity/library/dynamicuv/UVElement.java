package kr.toxicity.library.dynamicuv;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        this.mappingPos = mappingPos;
        this.colorType = colorType;
        var centerPos = position.toModelLocation().plus(ElementVector.CENTER);
        from = center.div(-2).plus(centerPos);
        to = center.div(2).plus(centerPos);
        pixel = center.div(space.x(), space.y(), space.z());
        var list = new ArrayList<UVMappedFace>();
        for (UVFace value : UVFace.values()) {
            if (!mappingPos.containsKey(value)) continue;
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


    public void write(@NotNull UVModelData.Builder builder, @NotNull BufferedImage image) {
        for (UVFace value : UVFace.values()) {
            var startPos = mappingPos.get(value);
            if (startPos == null) continue;
            startPos.iterate(value.posOf(space), (x, z) -> colorType.write(builder, x < image.getWidth() && z < image.getHeight() ? image.getRGB(x, z) : 0));
        }
    }

    public @NotNull JsonObject asJson(@NotNull String textureName) {
        var obj = new JsonObject();
        var textures = new JsonObject();
        textures.addProperty("0", textureName);
        textures.addProperty("particle", textureName);
        obj.add("textures", textures);
        var elements = new JsonArray();
        for (UVMappedFace face : faces) {
            elements.add(face.asJson());
        }
        obj.add("elements", elements);
        return obj;
    }

    public enum ColorType {
        RGB {
            @Override
            public void write(UVModelData.@NotNull Builder builder, int value) {
                builder.colors().add(UVColorUtil.rgb(value));
            }

            @Override
            public JsonObject asJson(@NotNull String model, @NotNull UVIndexer indexer) {
                var obj = new JsonObject();
                obj.addProperty("type", "model");
                obj.addProperty("model", model);
                var tints = new JsonArray(1);
                var tint = new JsonObject();
                tint.addProperty("type", "minecraft:custom_model_data");
                tint.addProperty("index", indexer.color());
                tint.addProperty("default", 0);
                tints.add(tint);
                obj.add("tints", tints);
                return obj;
            }
        },
        ARGB {

            private static final JsonObject EMPTY = new JsonObject();
            static {
                EMPTY.addProperty("type", "empty");
            }

            @Override
            public void write(UVModelData.@NotNull Builder builder, int value) {
                builder.colors().add(UVColorUtil.rgb(value));
                builder.flags().add(UVColorUtil.alpha(value));
            }

            @Override
            public JsonObject asJson(@NotNull String model, @NotNull UVIndexer indexer) {
                var rgb = RGB.asJson(model, indexer);
                var obj = new JsonObject();
                obj.addProperty("type", "minecraft:condition");
                obj.addProperty("property", "minecraft:custom_model_data");
                obj.addProperty("index", indexer.flag());
                obj.add("on_true", rgb);
                obj.add("on_false", EMPTY);
                return obj;
            }
        }
        ;
        public abstract void write(@NotNull UVModelData.Builder builder, int value);
        public abstract JsonObject asJson(@NotNull String model, @NotNull UVIndexer indexer);
    }
}
