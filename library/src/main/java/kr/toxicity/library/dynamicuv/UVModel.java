package kr.toxicity.library.dynamicuv;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Represents a UV model.
 */
@RequiredArgsConstructor
public final class UVModel {

    private final @NotNull Supplier<UVNamespace> namespace;
    private final @NotNull String modelName;
    private final List<UVElement> elements = new ArrayList<>();
    private @Nullable String packName;

    /**
     * Creates a new UV model.
     *
     * @param namespace the namespace of the model
     * @param modelName the name of the model
     */
    public UVModel(@NotNull UVNamespace namespace, @NotNull String modelName) {
        this(() -> namespace, modelName);
    }

    /**
     * Adds an element to the model.
     *
     * @param element the element to add
     * @return the model
     */
    public @NotNull UVModel addElement(@NotNull UVElement element) {
        elements.add(element);
        return this;
    }

    /**
     * Gets the item model namespace.
     *
     * @return the item model namespace
     */
    public @NotNull String itemModelNamespace() {
        return namespace.get().asset(packName());
    }

    /**
     * Gets the model name.
     *
     * @return the model name
     */
    public @NotNull String modelName() {
        return modelName;
    }

    /**
     * Gets the pack name.
     *
     * @return the pack name
     */
    public @NotNull String packName() {
        return packName != null ? packName : modelName;
    }

    /**
     * Sets the pack name.
     *
     * @param packName the pack name
     */
    public void packName(@Nullable String packName) {
        this.packName = packName;
    }

    /**
     * Converts the model to a list of UVByteBuilders as JSON.
     *
     * @return the list of UVByteBuilders
     */
    public @NotNull List<UVByteBuilder> asJson() {
        return asJson(UVTextureName.DEFAULT);
    }

    /**
     * Converts the model to a list of UVByteBuilders as JSON.
     *
     * @param textureName the texture name
     * @return the list of UVByteBuilders
     */
    public @NotNull List<UVByteBuilder> asJson(@NotNull UVTextureName textureName) {
        return asJson(textureName, i -> modelName + "_" + i);
    }

    /**
     * Converts the model to a list of UVByteBuilders as JSON.
     *
     * @param textureName the texture name
     * @param indexFunction the index function
     * @return the list of UVByteBuilders
     */
    public @NotNull List<UVByteBuilder> asJson(@NotNull UVTextureName textureName, @NotNull UVIndexFunction indexFunction) {
        return asJson(new UVLoadContext(textureName, indexFunction, ((indexer, namespace1, jsonArray) -> {})));
    }

    /**
     * Converts the model to a list of UVByteBuilders as JSON.
     *
     * @param builder the load context builder
     * @return the list of UVByteBuilders
     */
    public @NotNull List<UVByteBuilder> asJson(@NotNull UVLoadContext.Builder builder) {
        return asJson(builder.build());
    }

    /**
     * Converts the model to a list of UVByteBuilders as JSON.
     *
     * @param context the load context
     * @return the list of UVByteBuilders
     */
    public @NotNull List<UVByteBuilder> asJson(@NotNull UVLoadContext context) {
        var indexer = new UVIndexer();
        var builderList = new ArrayList<UVByteBuilder>();
        var composite = new JsonArray();
        var nSpace = namespace.get();
        context.buildFunction().build(indexer, nSpace, composite);
        var texture = nSpace.toTextureAssets(context.textureName());
        for (UVElement element : elements) {
            var modelJson = element.pack(context.indexFunction(), texture, indexer);
            for (JsonObject jsonObject : element.asJson(nSpace, indexer, modelJson)) {
                composite.add(jsonObject);
            }
            for (ModelJson json : modelJson) {
                builderList.add(json.asBuilder(nSpace));
            }
        }
        var model = new JsonObject();
        model.addProperty("type", "minecraft:composite");
        model.add("models", composite);
        var obj = new JsonObject();
        obj.add("model", model);
        builderList.add(UVByteBuilder.of(nSpace.item(packName()), builderList.stream().mapToLong(UVByteBuilder::estimatedSize).sum(), obj));
        return builderList;
    }

    /**
     * Writes the model to a UVModelData object.
     *
     * @param image the image to write to
     * @return the UVModelData object
     */
    public @NotNull UVModelData write(@NotNull BufferedImage image) {
        return write(image, builder -> {});
    }

    /**
     * Writes the model to a UVModelData object.
     *
     * @param image the image to write to
     * @param consumer the consumer to configure the builder
     * @return the UVModelData object
     */
    public @NotNull UVModelData write(@NotNull BufferedImage image, @NotNull Consumer<UVModelData.Builder> consumer) {
        var builder = UVModelData.builder();
        consumer.accept(builder);
        for (UVElement element : elements) {
            element.write(builder, image);
        }
        return builder.build();
    }
}
