package kr.toxicity.library.dynamicuv;

import org.jetbrains.annotations.NotNull;

public final class UVNamespace {

    private final String namespace;
    private final String directory;
    private final String textures;
    private final String models;
    private final String items;

    public UVNamespace(@NotNull String namespace, @NotNull String directory) {
        this.namespace = namespace;
        this.directory = directory;
        var dirString = directory.isEmpty() ? "" : "/" + directory;
        textures = "assets/" + namespace + "/textures/item" + dirString;
        models = "assets/" + namespace + "/models" + dirString;
        items = "assets/" + namespace + "/items" + dirString;
    }

    public @NotNull String asset(@NotNull String assetName) {
        return namespace + ":" + directory + (directory.isEmpty() ? "" : "/") + assetName;
    }
    public @NotNull String textureAssets(@NotNull String assetName) {
        return namespace + ":item/" + directory + (directory.isEmpty() ? "" : "/") + assetName;
    }

    public @NotNull String item(@NotNull String assetName) {
        return items + "/" + assetName + ".json";
    }

    public @NotNull String model(@NotNull String assetName) {
        return models + "/" + assetName + ".json";
    }

    public @NotNull String texture(@NotNull String assetName) {
        return textures + "/" + assetName + ".png";
    }
}
