package kr.toxicity.library.dynamicuv;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
public final class UVModel {

    private final @NotNull Supplier<UVNamespace> namespace;
    private final @NotNull String modelName;
    private final List<UVElement> elements = new ArrayList<>();

    public UVModel(@NotNull UVNamespace namespace, @NotNull String modelName) {
        this(() -> namespace, modelName);
    }

    public @NotNull UVModel addElement(@NotNull UVElement element) {
        elements.add(element);
        return this;
    }

    public @NotNull String itemModelNamespace() {
        return namespace.get().asset(modelName);
    }

    public @NotNull List<UVByteBuilder> asJson(@NotNull String textureName) {
        var index = 0;
        var indexer = new UVIndexer();
        var builderList = new ArrayList<UVByteBuilder>();
        var composite = new JsonArray();
        var nSpace = namespace.get();
        var texture = nSpace.textureAssets(textureName);
        for (UVElement element : elements) {
            for (UVMappedFace face : element.faces()) {
                var json = face.asJson(texture);
                var name = modelName + "_" + index;
                builderList.add(UVByteBuilder.of(nSpace.model(name), json));
                composite.add(element.colorType().asJson(nSpace.asset(name), indexer));
                index++;
            }
        }
        var model = new JsonObject();
        model.addProperty("type", "minecraft:composite");
        model.add("models", composite);
        var obj = new JsonObject();
        obj.add("model", model);
        builderList.add(UVByteBuilder.of(nSpace.item(modelName), obj));
        return builderList;
    }

    public @NotNull UVModelData write(@NotNull BufferedImage image) {
        var builder = UVModelData.builder();
        for (UVElement element : elements) {
            element.write(builder, image);
        }
        return builder.build();
    }
}
