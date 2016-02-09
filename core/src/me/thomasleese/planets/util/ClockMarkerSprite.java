package me.thomasleese.planets.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ClockMarkerSprite extends OrbitSprite {

    private static Texture TEXTURE =
        new Texture(Gdx.files.internal("graphics/clock/marker.png"));

    public ClockMarkerSprite(int index, float length) {
        super(TEXTURE);

        setOrbitIndex(index);
        setOrigin(1f, getHeight() / 2f);
        setLength(length);
    }

    public void setLength(float length) {
        setScale(length, 0.4f);
    }

}
