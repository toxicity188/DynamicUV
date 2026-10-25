package kr.toxicity.library.dynamicuv;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Base64;
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

    private static final int BUFFER_ALLOC_SIZE = 28;
    private static final JsonArray MAX_UV = new JsonArray(4);
    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();

    static {
        MAX_UV.add(0);
        MAX_UV.add(0);
        MAX_UV.add(16);
        MAX_UV.add(16);
    }

    static @NotNull UVIdentifiedJson compositedJson(@NotNull Collection<UVMappedFace> faces) {
        var buffer = ByteBuffer.allocate(BUFFER_ALLOC_SIZE * faces.size());
        var array = new JsonArray(faces.size());
        var i = 0;
        for (UVMappedFace face : faces) {
            face.write(buffer);
            array.add(face.asJson(i++));
        }
        return new UVIdentifiedJson(identifier(buffer),  array);
    }

    @NotNull UVIdentifiedJson asJson() {
        var elements = new JsonArray(1);
        elements.add(asJson(0));
        var buffer = ByteBuffer.allocate(BUFFER_ALLOC_SIZE);
        write(buffer);
        return new UVIdentifiedJson(identifier(buffer), elements);
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

    private void write(@NotNull ByteBuffer buffer) {
        buffer.putInt(face.ordinal());
        buffer.putFloat(position.x());
        buffer.putFloat(position.y());
        buffer.putFloat(position.z());
        buffer.putFloat(pixel.x());
        buffer.putFloat(pixel.y());
        buffer.putFloat(pixel.z());
    }

    private static @NotNull String identifier(@NotNull ByteBuffer byteBuffer) {
        try {
            var digest = MessageDigest.getInstance("MD5");
            byteBuffer.flip();
            digest.update(byteBuffer);
            return ENCODER.encodeToString(digest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
