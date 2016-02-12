package me.thomasleese.planets.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import me.thomasleese.planets.util.CircularOrbitSprite;
import me.thomasleese.planets.util.ClockUtils;
import me.thomasleese.planets.util.SizeManager;

import java.util.Calendar;

public class TimerLayer extends Layer {

    private static final String TAG = "Timer";

    public enum State {
        INACTIVE,
        PREPARING,
        ACTIVE
    }

    private State mState = State.INACTIVE;

    private float mDuration;

    private BitmapFont mBigFont;
    private BitmapFont mSmallFont;

    private CircularOrbitSprite mSprite;

    public TimerLayer() {
        loadFonts();
    }

    private void loadFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Abel-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 22;
        mBigFont = generator.generateFont(parameter);
        parameter.size = 11;
        parameter.color = Color.LIGHT_GRAY;
        mSmallFont = generator.generateFont(parameter);
        generator.dispose();

        mBigFont.setUseIntegerPositions(false);
        mSmallFont.setUseIntegerPositions(false);
    }

    public void setState(State state) {
        mState = state;
    }

    public void setDuration(float duration) {
        mDuration = duration;
    }

    public float getDuration() {
        return mDuration;
    }

    public boolean isVisible() {
        return mState == State.PREPARING || mState == State.ACTIVE;
    }

    @Override
    public void queueAssets(AssetManager assets) {
        assets.load("graphics/timer/circle.png", Texture.class);
    }

    @Override
    public void loadAssets(AssetManager assets) {
        Texture texture = assets.get("graphics/timer/circle.png");
        mSprite = new CircularOrbitSprite(texture, 20f);
        mSprite.setOrbitIndex(8.3f);
    }

    @Override
    public void resize(SizeManager sizes) {
        mSprite.resize(sizes);
    }

    private void drawNumber(Batch batch, int number, String unit) {
        if (number != 1) {
            unit += "s";
        }

        float cx = mSprite.getX() + mSprite.getWidth() / 2f;
        float cy = mSprite.getY() + mSprite.getHeight() / 2f;

        mBigFont.draw(batch, "" + number, cx, cy + 12f, 0, Align.center, false);
        mSmallFont.draw(batch, unit, cx, cy - 6f, 0, Align.center, false);
    }

    @Override
    public void render(Batch batch) {
        if (isVisible()) {
            batch.begin();

            int seconds = (int) mDuration;
            int minutes = 0;

            while (seconds >= 60) {
                minutes += 1;
                seconds -= 60;
            }

            mSprite.setOrbitAngle(ClockUtils.calculateRotation(Calendar.SECOND, Calendar.getInstance()));

            if (minutes <= 0) {
                drawNumber(batch, seconds, "sec");
            } else {
                drawNumber(batch, minutes, "min");
            }

            mSprite.draw(batch);

            batch.end();

            if (mState == State.ACTIVE) {
                mDuration -= Gdx.graphics.getDeltaTime();
                if (mDuration <= 0) {
                    mState = State.INACTIVE;
                }
            }
        }
    }

}
