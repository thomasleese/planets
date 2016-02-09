package me.thomasleese.planets.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SunSprite extends Sprite {

    private static final float TEXTURE_SIZE = 133.3f;

    public SunSprite(AssetManager assets) {
        super(assets.get("graphics/planets/sun.png", Texture.class));

        setOriginCenter();
        setCenter(0, 0);
    }

    public void setSize(float size) {
        setScale(size / TEXTURE_SIZE, size / TEXTURE_SIZE);
    }

    public float getSize() {
        return getScaleX() * TEXTURE_SIZE;
    }

    public static void loadAssets(AssetManager assets) {
        assets.load("graphics/planets/sun.png", Texture.class);
    }

}
