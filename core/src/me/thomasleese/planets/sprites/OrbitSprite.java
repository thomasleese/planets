package me.thomasleese.planets.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import me.thomasleese.planets.util.SizeManager;

public class OrbitSprite extends Sprite {

    private SizeManager mSizes;

    private float mOrbitIndex = 0;
    private float mOrbitLength = 0;
    private float mOrbitAngle = 0;

    public OrbitSprite(Texture texture) {
        super(texture);
    }

    public OrbitSprite(TextureRegion texture) {
        super(texture);
    }

    public void resize(SizeManager sizes) {
        setOrbitLength(sizes.getOrbitLength(mOrbitIndex));
        mSizes = sizes;
    }

    public void setOrbitIndex(float index) {
        mOrbitIndex = index;

        if (mSizes != null) {
            resize(mSizes);
        }
    }

    public void setOrbitAngle(float angle) {
        mOrbitAngle = angle;
        reposition();
    }

    public void setOrbitLength(float length) {
        mOrbitLength = length;
        reposition();
    }

    public float getOrbitAngle() {
        return mOrbitAngle;
    }

    public void reposition() {
        setCenter(mOrbitLength * MathUtils.cosDeg(mOrbitAngle + 90),
                  mOrbitLength * MathUtils.sinDeg(mOrbitAngle + 90));
        setRotation(mOrbitAngle - 90);
    }

}
