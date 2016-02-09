package me.thomasleese.planets.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import me.thomasleese.planets.util.SizeManager;

import java.util.ArrayList;
import java.util.List;

public class StarfieldLayer extends Layer {

    private static final String TAG = "Starfield";

    private static final Texture TEXTURE_STAR =
        new Texture(Gdx.files.internal("graphics/star.png"));

    public class Star {

        private float mX, mY;
        private Color[] mColours;
        private Color mColour;
        private float mDiameter;
        private float mChangeColourIn = 0;

        public Star(float x, float y) {
            mColours = new Color[] {makeColour(), makeColour(), makeColour(),
                                    makeColour(), makeColour(), makeColour(),
                                    makeColour(), makeColour(), makeColour()};

            float radius = MathUtils.random(0.01f, 1f);

            mDiameter = radius * 2f;
            mX = x - radius;
            mY = y - radius;

            mColour = mColours[0];
        }

        private Color makeColour() {
            int type = MathUtils.random(2);
            float a = MathUtils.random(0.4f, 0.8f);
            if (type == 0) {
                float w = MathUtils.random(0.4f) + 0.6f;
                return new Color(w, w, w, a);
            } else if (type == 1) {
                float w = MathUtils.random(0.4f) + 0.6f;
                float r = MathUtils.random();
                return new Color(r, w, w, a);
            } else if (type == 2) {
                float w = MathUtils.random(0.4f) + 0.6f;
                float b = MathUtils.random();
                return new Color(w, w, b, a);
            } else {
                return Color.WHITE;
            }
        }

        public void update(float dt) {
            mChangeColourIn -= dt;
            if (mChangeColourIn < 0) {
                mChangeColourIn = MathUtils.random(0f, 1.5f);
                mColour = mColours[MathUtils.random(mColours.length - 1)];
            }
        }

        public void draw(Batch batch) {
            batch.setColor(mColour);
            batch.draw(TEXTURE_STAR, mX, mY, mDiameter, mDiameter);
        }

    }

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
