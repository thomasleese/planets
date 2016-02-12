package me.thomasleese.planets.sprites;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import me.thomasleese.planets.util.OrbitSprite;
import me.thomasleese.planets.util.SizeManager;

public class RocketSprite extends OrbitSprite {

    public RocketSprite(AssetManager assets) {
        super(assets.get("graphics/sprites/rocket.png", Texture.class));

        setOrbitIndex(7);
        setOriginCenter();
    }

    @Override
    public void resize(SizeManager sizes) {
        float scale = sizes.getScreenSize() / 2750f;
        setScale(scale, scale);

        super.resize(sizes);
    }

    public static void loadAssets(AssetManager assets) {
        assets.load("graphics/sprites/rocket.png", Texture.class);
    }

}
