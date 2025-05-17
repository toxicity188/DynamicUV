package kr.toxicity.library.dynamicuv;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public record UVMappedFace(
        @NotNull UVFace face,
        @NotNull ElementVector position,
        @NotNull ElementVector pixel
) {
    private static final JsonArray MAX_UV = new JsonArray(4);

    static {
        MAX_UV.add(0);
        MAX_UV.add(0);
        MAX_UV.add(16);
        MAX_UV.add(16);
    }

    public @NotNull JsonObject asJson() {
        var next = position.plus(face.mapVector(pixel));

        var obj = new JsonObject();
        obj.add("from", ElementVector.min(position, next).asJson());
        obj.add("to", ElementVector.max(position, next).asJson());
        var faces = new JsonObject();
        var uv = new JsonObject();
        uv.add("uv", MAX_UV);
        uv.addProperty("tintindex", 0);
        uv.addProperty("texture", "#0");
        faces.add(face.uvName(), uv);
        obj.add("faces", faces);
        return obj;
    }

    public @NotNull JsonObject asJson(@NotNull String textureName) {
        var obj = new JsonObject();
        var textures = new JsonObject();
        textures.addProperty("0", textureName);
        textures.addProperty("particle", textureName);
        obj.add("textures", textures);
        var elements = new JsonArray(1);
        elements.add(asJson());
        obj.add("elements", elements);
        return obj;
    }
}
