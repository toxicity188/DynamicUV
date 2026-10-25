package kr.toxicity.library.dynamicuv;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

/**
 * A builder for creating byte arrays for UV assets.
 * @param path the path
 * @param estimatedSize the estimated size
 */
public record UVByteBuilder(
    @NotNull String path,
    long estimatedSize,
    @NotNull Supplier<byte[]> supplier
) {

    /**
     * The GSON instance used for JSON serialization.
     */
    static final Gson GSON = new GsonBuilder()
        .disableHtmlEscaping()
        .create();

    /**
     * Builds the byte array.
     *
     * @return the byte array
     */
    public byte[] build() {
        return supplier.get();
    }

    /**
     * Creates a UVByteBuilder for a normal pixel texture.
     *
     * @param namespace the UV namespace
     * @param textureName the texture name
     * @return the UVByteBuilder
     */
    static @NotNull UVByteBuilder normalPixel(@NotNull UVNamespace namespace, @NotNull String textureName) {
        return of(namespace.texture(textureName), UVUtil.NORMAL_PIXEL);
    }

    /**
     * Creates a UVByteBuilder for a translucent pixel texture.
     *
     * @param namespace the UV namespace
     * @param textureName the texture name
     * @return the UVByteBuilder
     */
    static @NotNull UVByteBuilder translucentPixel(@NotNull UVNamespace namespace, @NotNull String textureName) {
        return of(namespace.texture(textureName), UVUtil.TRANSLUCENT_PIXEL);
    }

    /**
     * Creates a UVByteBuilder from a JSON element.
     *
     * @param path the path of the asset
     * @param estimatedSize the estimated size
     * @param element the JSON element
     * @return the UVByteBuilder
     */
    static @NotNull UVByteBuilder of(@NotNull String path, long estimatedSize, @NotNull JsonElement element) {
        return of(path, estimatedSize, () -> GSON.toJson(element).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Creates a UVByteBuilder from a BufferedImage.
     *
     * @param path the path of the asset
     * @param image the image
     * @return the UVByteBuilder
     */
    static @NotNull UVByteBuilder of(@NotNull String path, @NotNull BufferedImage image) {
        return of(path, 4L * image.getHeight() * image.getWidth(), () -> {
            try (
                var bytes = new ByteArrayOutputStream()
            ) {
                ImageIO.write(image, "png", bytes);
                return bytes.toByteArray();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Creates a UVByteBuilder from a byte array supplier.
     *
     * @param path the path of the asset
     * @param estimatedSize the estimated size
     * @param supplier the byte array supplier
     * @return the UVByteBuilder
     */
    static @NotNull UVByteBuilder of(@NotNull String path, long estimatedSize, @NotNull Supplier<byte[]> supplier) {
        return new UVByteBuilder(path, estimatedSize, supplier);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UVByteBuilder that)) return false;

        return path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }
}
