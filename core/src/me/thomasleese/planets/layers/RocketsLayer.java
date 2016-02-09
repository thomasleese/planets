package me.thomasleese.planets.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import me.thomasleese.planets.util.ClockUtils;
import me.thomasleese.planets.util.RocketSprite;
import me.thomasleese.planets.util.SizeManager;

import java.util.Calendar;

public class RocketsLayer extends Layer implements InputProcessor {

    private static final String TAG = "Rocket";

    private static Vector2 TMP_POINT = new Vector2();

    private boolean mActive = false;

    private RocketSprite mMainSprite;
    private RocketSprite mGhostSprite;

    private TimerLayer mTimer;

    public RocketsLayer(TimerLayer timer) {
        mMainSprite = new RocketSprite();

        mGhostSprite = new RocketSprite();
        mGhostSprite.setColor(0.75f, 0.75f, 0.75f, 0.75f);

        mTimer = timer;
    }

    @Override
    public void resize(SizeManager sizes) {
        mMainSprite.resize(sizes);
        mGhostSprite.resize(sizes);
    }

    public void setMainAngle(float angle) {
        mMainSprite.setOrbitAngle(angle);
    }

    public void setGhostAngle(float angle) {
        mGhostSprite.setOrbitAngle(angle);
    }

    @Override
    public void render(Batch batch) {
        float secondHandRotation = ClockUtils.calculateRotation(Calendar.SECOND, Calendar.getInstance());

        if (mActive) {
            setGhostAngle(secondHandRotation);

            mTimer.setState(TimerLayer.State.PREPARING);
            mTimer.setDuration(getDifference() * 10f);
        } else {
            if (mTimer.isVisible()) {
                setGhostAngle(secondHandRotation);
                setMainAngle(mGhostSprite.getOrbitAngle() + mTimer.getDuration() / 10f);
            } else {
                setMainAngle(secondHandRotation);
            }
        }

        batch.begin();

        if (mTimer.isVisible()) {
            mGhostSprite.draw(batch);
        }

        mMainSprite.draw(batch);
        batch.end();
    }

    private Vector2 flipTouchCoordinates(int x, int y) {
        x -= Gdx.graphics.getWidth() / 2;
        y = Gdx.graphics.getHeight() - y;
        y -= Gdx.graphics.getHeight() / 2;
        TMP_POINT.x = x;
        TMP_POINT.y = y;
        return TMP_POINT;
    }

    private boolean touchingSprite(Sprite sprite, Vector2 point) {
        float px = point.x;
        float py = point.y;
        float sx = sprite.getX() + sprite.getWidth() / 2;
        float sy = sprite.getY() + sprite.getHeight() / 2;

        float d = (px - sx) * (px - sx) + (py - sy) * (py - sy);
        float r = 50;
        return d < r * r;
    }

    public boolean isActive() {
        return mActive;
    }

    public float getDifference() {
        float difference = mMainSprite.getOrbitAngle() - mGhostSprite.getOrbitAngle();
        if (difference < 0) {
            difference += 360;
        }
        return difference;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        Vector2 point = flipTouchCoordinates(x, y);

        if (touchingSprite(mMainSprite, point)) {
            setGhostAngle(mMainSprite.getOrbitAngle());
            mActive = true;

            Gdx.app.log(TAG, "Activated.");
        }

        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        mActive = false;
        mTimer.setState(TimerLayer.State.ACTIVE);
        Gdx.app.log(TAG, "Deactivated.");
        return true;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        Vector2 point = flipTouchCoordinates(x, y);

        float theta = MathUtils.atan2(point.y, point.x);
        setMainAngle(theta * MathUtils.radiansToDegrees - 90);

        return true;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
