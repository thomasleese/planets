package me.thomasleese.planets.util;

import com.badlogic.gdx.Gdx;

public class SizeManager {

    private static final String TAG = "SizeManager";

    private float mScreenSize;

    private float mSunDiameter;
    private float mOrbitRadius;
    private float mOrbitGap;

    public SizeManager() {

    }

    public void resize() {
        final float width = Gdx.graphics.getWidth();
        final float height = Gdx.graphics.getHeight();
        final float size = mScreenSize = Math.min(width, height);
        final float halfSize = size / 2f;

        Gdx.app.log(TAG, "Size = " + size);

        float outerMargin = size / 10f;

        mSunDiameter = (size / 24f);
        mOrbitRadius = mSunDiameter * 2.5f;

        mOrbitGap = (halfSize - mOrbitRadius - outerMargin) / 7f;
    }

    public float getScreenSize() {
        return mScreenSize;
    }

    public float getSunDiameter() {
        return mSunDiameter;
    }

    public float getOrbitRadius() {
        return mOrbitRadius;
    }

    public float getOrbitGap() {
        return mOrbitGap;
    }

    public float getOrbitLength(float times) {
        return mOrbitRadius + mOrbitGap * times;
    }

}
