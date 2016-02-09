package me.thomasleese.planets.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SunSprite extends Sprite {

    private static final float TEXTURE_SIZE = 133.3f;
    private static Texture TEXTURE =
        new Texture(Gdx.files.internal("graphics/sun.png"));

    public SunSprite() {
        super(TEXTURE);

        setOriginCenter();
        setCenter(0, 0);
    }

    public void setSize(float size) {
        setScale(size / TEXTURE_SIZE, size / TEXTURE_SIZE);
    }

    public float getSize() {
        return getScaleX() * TEXTURE_SIZE;
    }

}
