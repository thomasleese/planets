package me.thomasleese.planets.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import me.thomasleese.planets.util.SizeManager;

import java.util.ArrayList;
import java.util.List;

public class StarfieldLayer extends Layer {

    private static final String TAG = "Starfield";

    public class Star {

        private float mCentreX, mCentreY;
        private Color mColour;
        private float mDiameter;
        private float mChangeColourIn = 0;

        public Star(float x, float y) {
            float radius = MathUtils.random(0.02f, 0.9f);

            mDiameter = radius * 2f;
            mCentreX = x - radius;
            mCentreY = y - radius;
            mColour = new Color(1f, 1f, 1f, 1f);

            nextColour();
        }

        private void nextColour() {
            mColour.a = MathUtils.random(0.1f, 0.7f);
            mChangeColourIn = MathUtils.random(0.15f, 1.50f);
        }

        public void update(float dt) {
            mChangeColourIn -= dt;
            if (mChangeColourIn < 0) {
                nextColour();
            }
        }

        public void draw(Batch batch) {
            batch.setColor(mColour);
            batch.draw(mStarTexture, mCentreX, mCentreY, mDiameter, mDiameter);
        }

    }

    private Texture mStarTexture;
    private List<Star> mStars = new ArrayList<Star>();

    private void generateStars(int width, int height) {
        Gdx.app.log(TAG, "Generating stars for " + width + "x" + height + ".");

        int halfWidth = width / 2;
        int halfHeight = height / 2;

        for (int x = -halfWidth; x < halfWidth; x++) {
            for (int y = -halfHeight; y < halfHeight; y++) {
                if (MathUtils.random(1250) == 0) {
                    mStars.add(new Star(x, y));
                }
            }
        }

        Gdx.app.log(TAG, "Made " + mStars.size() + " stars.");
    }

    @Override
    public void queueAssets(AssetManager assets) {
        assets.load("graphics/starfield/star.png", Texture.class);
    }

    @Override
    public void loadAssets(AssetManager assets) {
        mStarTexture = assets.get("graphics/starfield/star.png");
    }

    @Override
    public void resize(SizeManager sizes) {
        mStars.clear();
        generateStars(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(Batch batch) {
        float dt = Gdx.graphics.getDeltaTime();

        batch.begin();

        for (Star star : mStars) {
            star.update(dt);
            star.draw(batch);
        }

        batch.setColor(Color.WHITE);

        batch.end();
    }

}
