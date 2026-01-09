package kr.toxicity.library.dynamicuv;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Represents a mapped face in UV coordinates.
 *
 * @param face the UV face
 * @param position the position vector
 * @param pixel the pixel vector
 */
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

    static @NotNull JsonArray compositedJson(@NotNull Collection<UVMappedFace> faces) {
        var array = new JsonArray(faces.size());
        var i = 0;
        for (UVMappedFace face : faces) {
            array.add(face.asJson(i++));
        }
        return array;
    }

    @NotNull JsonArray asJson() {
        var elements = new JsonArray(1);
        elements.add(asJson(0));
        return elements;
    }

    private @NotNull JsonObject asJson(int index) {
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
}
