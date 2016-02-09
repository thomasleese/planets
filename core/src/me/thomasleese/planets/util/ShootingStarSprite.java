package me.thomasleese.planets.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ShootingStarSprite extends OrbitSprite {

    private static Texture TEXTURE =
        new Texture(Gdx.files.internal("graphics/shooting-star.png"));

    public ShootingStarSprite() {
        super(TEXTURE);

        setOrbitIndex(8);
        setOriginCenter();
    }

    @Override
    public void resize(SizeManager sizes) {
        float scale = sizes.getShootingStarScale();
        setScale(scale, scale);

        super.resize(sizes);
    }

}
