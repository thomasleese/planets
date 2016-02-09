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

    private static final Color[] STAR_COLOURS = new Color[] {
            // whites
            new Color(0.9f, 0.9f, 0.9f, 0.9f), new Color(0.85f, 0.85f, 0.85f, 0.85f),
            new Color(0.8f, 0.8f, 0.8f, 0.8f), new Color(0.75f, 0.75f, 0.75f, 0.75f),
            new Color(0.7f, 0.7f, 0.7f, 0.7f), new Color(0.65f, 0.65f, 0.65f, 0.65f),
            new Color(0.6f, 0.6f, 0.6f, 0.6f), new Color(0.55f, 0.55f, 0.55f, 0.55f),
            new Color(0.5f, 0.5f, 0.5f, 0.5f), new Color(0.45f, 0.45f, 0.45f, 0.45f),
            new Color(0.4f, 0.4f, 0.4f, 0.4f),

            // reds
            new Color(0.7f, 0.5f, 0.5f, 0.6f), new Color(0.9f, 0.6f, 0.6f, 0.6f),

            // greens
            new Color(0.5f, 0.7f, 0.5f, 0.6f), new Color(0.6f, 0.9f, 0.6f, 0.6f),

            // blues
            new Color(0.5f, 0.5f, 0.7f, 0.6f), new Color(0.6f, 0.6f, 0.9f, 0.6f),
    };

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

            nextColour();
        }

        private void nextColour() {
            mChangeColourIn = MathUtils.random(0f, 1.5f);
            mColour = STAR_COLOURS[MathUtils.random(STAR_COLOURS.length - 1)];
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

    public void generateStars(int width, int height) {
        Gdx.app.log(TAG, "Generating stars for " + width + "x" + height + ".");

        int halfWidth = width / 2;
        int halfHeight = height / 2;

        for (int x = -halfWidth; x < halfWidth; x++) {
            for (int y = -halfHeight; y < halfHeight; y++) {
                if (MathUtils.random(300) == 0) {
                    mStars.add(new Star(x, y));
                }
            }
        }
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
