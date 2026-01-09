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

    public @NotNull JsonObject asJson(int index) {
        var next = position.plus(face.mapVector(pixel));

        var obj = new JsonObject();
        obj.add("from", ElementVector.min(position, next).asJson());
        obj.add("to", ElementVector.max(position, next).asJson());
        var faces = new JsonObject();
        var uv = new JsonObject();
        uv.add("uv", MAX_UV);
        uv.addProperty("tintindex", index);
        uv.addProperty("texture", "#0");
        faces.add(face.uvName(), uv);
        obj.add("faces", faces);
        return obj;
    }

    public @NotNull JsonObject asJson(@NotNull String textureName) {
        var elements = new JsonArray(1);
        elements.add(asJson(0));
        return UVUtil.packModel(textureName, elements);
    }
}
