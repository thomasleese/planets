package me.thomasleese.planets.sprites;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import me.thomasleese.planets.util.OrbitSprite;
import me.thomasleese.planets.util.SizeManager;

public class ShootingStarSprite extends OrbitSprite {

    public ShootingStarSprite(AssetManager assets) {
        super(assets.get("graphics/sprites/shooting-star.png", Texture.class));

        setOrbitIndex(8);
        setOriginCenter();
    }

    @Override
    public void resize(SizeManager sizes) {
        float scale = sizes.getScreenSize() / 1500f;
        setScale(scale, scale);

        super.resize(sizes);
    }

    public static void loadAssets(AssetManager assets) {
        assets.load("graphics/sprites/shooting-star.png", Texture.class);
    }

}
