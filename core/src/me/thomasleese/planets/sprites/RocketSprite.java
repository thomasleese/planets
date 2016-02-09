package me.thomasleese.planets.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import me.thomasleese.planets.util.OrbitSprite;
import me.thomasleese.planets.util.SizeManager;

public class RocketSprite extends OrbitSprite {

    private static Texture TEXTURE =
        new Texture(Gdx.files.internal("graphics/sprites/rocket.png"));

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
