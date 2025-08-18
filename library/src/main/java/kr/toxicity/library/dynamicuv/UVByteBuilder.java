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

public interface UVByteBuilder {

    Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .create();

    @NotNull String path();
    byte[] build();
    long estimatedSize();

    static @NotNull UVByteBuilder emptyImage(@NotNull UVNamespace namespace, @NotNull String textureName) {
        return of(namespace.texture(textureName), UVUtil.EMPTY_IMAGE);
    }

    static @NotNull UVByteBuilder of(@NotNull String path, @NotNull JsonElement element) {
        return of(path, UVUtil.estimateSize(element), () -> GSON.toJson(element).getBytes(StandardCharsets.UTF_8));
    }

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

    static @NotNull UVByteBuilder of(@NotNull String path, long estimatedSize, @NotNull Supplier<byte[]> supplier) {
        return new UVByteBuilder() {
            @Override
            public @NotNull String path() {
                return path;
            }

            @Override
            public byte[] build() {
                return supplier.get();
            }

            @Override
            public long estimatedSize() {
                return estimatedSize;
            }
        };
    }
}
