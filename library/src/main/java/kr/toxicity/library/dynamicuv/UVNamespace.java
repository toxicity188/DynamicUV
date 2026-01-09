package kr.toxicity.library.dynamicuv;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a namespace for UV assets.
 */
public final class UVNamespace {

    private final String namespace;
    private final String directory;
    private final String textures;
    private final String models;
    private final String items;

    /**
     * Creates a new UV namespace.
     *
     * @param namespace the namespace string
     * @param directory the directory string
     */
    public UVNamespace(@NotNull String namespace, @NotNull String directory) {
        this.namespace = namespace;
        this.directory = directory;
        var dirString = directory.isEmpty() ? "" : "/" + directory;
        textures = "assets/" + namespace + "/textures/item" + dirString;
        models = "assets/" + namespace + "/models" + dirString;
        items = "assets/" + namespace + "/items" + dirString;
    }

    /**
     * Formats an asset name with the namespace and directory.
     *
     * @param assetName the asset name
     * @return the formatted asset name
     */
    public @NotNull String asset(@NotNull String assetName) {
        return namespace + ":" + directory + (directory.isEmpty() ? "" : "/") + assetName;
    }

    /**
     * Converts a UVTextureName to texture assets.
     *
     * @param textureName the texture name
     * @return the converted UVTextureName
     */
    public @NotNull UVTextureName toTextureAssets(@NotNull UVTextureName textureName) {
        return new UVTextureName(
            toTextureAssets(textureName.normalPixel()),
            toTextureAssets(textureName.translucentPixel())
        );
    }

    private String toTextureAssets(@NotNull String path) {
        return namespace + ":item/" + directory + (directory.isEmpty() ? "" : "/") + path;
    }

    /**
     * Formats an item asset name.
     *
     * @param assetName the asset name
     * @return the formatted item asset path
     */
    public @NotNull String item(@NotNull String assetName) {
        return items + "/" + assetName + ".json";
    }

    /**
     * Formats a model asset name.
     *
     * @param assetName the asset name
     * @return the formatted model asset path
     */
    public @NotNull String model(@NotNull String assetName) {
        return models + "/" + assetName + ".json";
    }

    /**
     * Formats a texture asset name.
     *
     * @param assetName the asset name
     * @return the formatted texture asset path
     */
    public @NotNull String texture(@NotNull String assetName) {
        return textures + "/" + assetName + ".png";
    }
}
