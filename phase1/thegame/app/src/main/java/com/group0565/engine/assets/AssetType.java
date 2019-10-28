package com.group0565.engine.assets;

public enum AssetType {
    TILESHEET(TileSheet.class), AUDIO(AudioAsset.class),
    JSON(JsonFile.class), LANGUAGE(LanguagePack.class);

    public final Class cls;

    AssetType(Class cls) {
        this.cls = cls;
    }
}
