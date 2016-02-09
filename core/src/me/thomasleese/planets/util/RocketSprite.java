package me.thomasleese.planets.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class RocketSprite extends OrbitSprite {

    private static Texture TEXTURE =
        new Texture(Gdx.files.internal("graphics/rocket.png"));

    public RocketSprite() {
        super(TEXTURE);

        setOrbitIndex(7);
        setOriginCenter();
    }

    @Override
    public void resize(SizeManager sizes) {
        float scale = sizes.getRocketScale();
        setScale(scale, scale);

        super.resize(sizes);
    }

}
