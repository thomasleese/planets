package me.thomasleese.planets.layers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import me.thomasleese.planets.sprites.SunSprite;
import me.thomasleese.planets.sprites.GlassBall;
import me.thomasleese.planets.util.SizeManager;

import java.util.Arrays;
import java.util.Calendar;

public class PlanetsLayer extends Layer {

    private static final Calendar J2000 = Calendar.getInstance();

    static {
        J2000.set(2000, Calendar.JANUARY, 0, 0, 0);
    }

    public class Planet implements Comparable<Planet> {

        private String mName;
        private GlassBall mBall;
        private double mOrbitalPeriod;
        private double mOrbitAt2000;
        private float mDiameter;
        private float mScaledDiameter;

        private float mX, mY;

        public Planet(AssetManager assets, String name, Color colour, float diameter, double orbitalPeriod, double orbitAt2000) {
            mName = name;

            mBall = new GlassBall(colour, assets);

            mDiameter = diameter;
            mOrbitalPeriod = orbitalPeriod;
            mOrbitAt2000 = orbitAt2000;
        }

        @Override
        public String toString() {
            return mName;
        }

        @Override
        public int compareTo(Planet other) {
            return (int) (mDiameter - other.mDiameter);
        }

        public float calculateAzimuth(Calendar now) {
            double millisecondsSince2000 = now.getTimeInMillis() - J2000.getTimeInMillis();
            double yearsSince2000 = millisecondsSince2000 / 1000 / 60 / 60 / 24 / 365;

            double orbits = yearsSince2000 / mOrbitalPeriod + mOrbitAt2000;
            while (orbits >= 1) {
                orbits -= 1;
            }

            return (float) (-orbits * MathUtils.PI2);
        }

        public void scaleDiameter(float min, float max, float smallest, float largest) {
            mScaledDiameter = ((max - min) / (largest - smallest)) * (mDiameter - smallest) + min;
            mBall.setSize(mScaledDiameter);
        }

        public boolean intersects(float x, float y) {
            float d = (x - mX) * (x - mX) + (y - mY) * (y - mY);
            float r = mScaledDiameter;
            return d < r * r;
        }

        public void draw(Batch batch, Calendar now, float radius) {
            float azimuth = calculateAzimuth(now);

            mX = radius * MathUtils.cos(azimuth);
            mY = radius * MathUtils.sin(azimuth);

            mBall.setCenter(mX, mY);
            mBall.setRotation(azimuth * MathUtils.radiansToDegrees - 90f);
            mBall.draw(batch);
        }

    }

    private SizeManager mSizes;

    private Planet[] mPlanets;
    private Planet[] mPlanetsBySize;

    private SunSprite mSun;

    private Calendar mNow;

    private void drawSun(Batch batch) {
        mSun.draw(batch);
    }

    private void drawPlanets(Batch batch, Calendar now) {
        float radius = mSizes.getOrbitRadius();
        for (Planet planet : mPlanets) {
            planet.draw(batch, now, radius);
            radius += mSizes.getOrbitGap();
        }
    }

    @Override
    public void queueAssets(AssetManager assets) {
        SunSprite.loadAssets(assets);
        GlassBall.loadAssets(assets);
    }

    @Override
    public void loadAssets(AssetManager assets) {
        mSun = new SunSprite(assets);

        mPlanets = new Planet[] {
                new Planet(assets, "mercury", new Color(0x808080ff), 4780, 0.240846, 0.26),
                new Planet(assets, "venus", new Color(0xff9000ff), 12104, 0.615198, 0.49),
                new Planet(assets, "earth", new Color(0x00c050ff), 12756, 1, 0.75),
                new Planet(assets, "mars", new Color(0xef1000ff), 6780, 1.881, 0.01),
                new Planet(assets, "jupiter", new Color(0xe0a070ff), 139822, 11.86, 0.785),
                new Planet(assets, "saturn", new Color(0xefb020ff), 116464, 29.46, 0.75),
                new Planet(assets, "uranus", new Color(0x00b0c0ff), 50724, 84.01, 0.12),
                new Planet(assets, "neptune", new Color(0x1540ffff), 49248, 164.8, 0.13),
        };

        mPlanetsBySize = Arrays.copyOf(mPlanets, mPlanets.length);
        Arrays.sort(mPlanetsBySize);
    }

    @Override
    public void resize(SizeManager sizes) {
        mSizes = sizes;

        mSun.setSize(sizes.getSunDiameter());

        float smallest = mPlanetsBySize[0].mDiameter;
        float largest = mPlanetsBySize[mPlanetsBySize.length - 1].mDiameter;

        float min = (sizes.getOrbitGap() * 0.2f) * 2f;
        float max = (sizes.getOrbitGap() * 0.3f) * 2f;

        for (Planet planet : mPlanets) {
            planet.scaleDiameter(min, max, smallest, largest);
        }
    }

    public void setNow(Calendar now) {
        mNow = now;
    }

    @Override
    public void render(Batch batch) {
        batch.begin();

        drawSun(batch);
        drawPlanets(batch, mNow);

        batch.end();
    }

}
