package me.thomasleese.planets.sprites;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class ClockMarkerSprite extends OrbitSprite {

    public ClockMarkerSprite(int index, float length, AssetManager assets) {
        super(assets.get("graphics/clock/marker.png", Texture.class));

        setOrbitIndex(index);
        setOrigin(1f, getHeight() / 2f);
        setLength(length);
    }

    public void setLength(float length) {
        setScale(length, 2f);
    }

    public static void loadAssets(AssetManager assets) {
        assets.load("graphics/clock/marker.png", Texture.class);
    }

}
