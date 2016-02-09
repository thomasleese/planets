package me.thomasleese.planets.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import me.thomasleese.planets.util.ClockMarkerSprite;
import me.thomasleese.planets.util.ClockUtils;
import me.thomasleese.planets.util.OrbitSprite;
import me.thomasleese.planets.util.SizeManager;

import java.util.Calendar;

public class ClockLayer extends Layer {

    private static final Texture TEXTURE_HAND =
            new Texture(Gdx.files.internal("graphics/clock/hand.png"));
    private static final Texture TEXTURE_DOT =
            new Texture(Gdx.files.internal("graphics/clock/week-day.png"));

    public class Hand {

        private int mIndex;
        private int mTimeUnit;
        private Sprite mSprite;
        private OrbitSprite mDotSprite;
        private float mWidth;
        private boolean mIncludesWeekDay;
        private int mWeekDayDayDots;

        public Hand(int index, int timeUnit, float width, boolean includesWeekDay) {
            mIndex = index;

            setTimeUnit(timeUnit);

            mWidth = width;
            mIncludesWeekDay = includesWeekDay;

            mSprite = new Sprite(TEXTURE_HAND);
            mSprite.setOrigin(0.5f, 0f);
            mSprite.setCenter(0, 0);

            if (includesWeekDay) {
                mDotSprite = new OrbitSprite(TEXTURE_DOT);
                mDotSprite.setOriginCenter();
                mDotSprite.setScale(0.4f, 0.4f);
            }
        }

        public void setTimeUnit(int timeUnit) {
            if (timeUnit != Calendar.SECOND && timeUnit != Calendar.MINUTE
                    && timeUnit != Calendar.HOUR) {
                throw new IllegalArgumentException("Invalid time unit.");
            }

            mTimeUnit = timeUnit;
        }

        public void setLength(float length) {
            mSprite.setScale(mWidth, length);
        }

        public float getLength() {
            return mSprite.getScaleY();
        }

        public void setRotation(float rotation) {
            mSprite.setRotation(rotation);
        }

        public float getRotation() {
            return mSprite.getRotation();
        }

        public float calculateRotation(Calendar now) {
            return ClockUtils.calculateRotation(mTimeUnit, now);
        }

        public void resize(SizeManager sizes) {
            setLength(sizes.getOrbitLength(mIndex));

            if (mIncludesWeekDay) {
                mDotSprite.resize(sizes);
            }
        }

        public void update(Calendar now) {
            float rotation = calculateRotation(now);
            setRotation(rotation);

            mWeekDayDayDots = now.get(Calendar.DAY_OF_WEEK);

            // monday should be the first day of the week
            mWeekDayDayDots -= 1;
            if (mWeekDayDayDots == 0) {
                mWeekDayDayDots = 7;
            }
        }

        public void draw(Batch batch) {
            mSprite.draw(batch);

            if (mIncludesWeekDay) {
                mDotSprite.setOrbitAngle(getRotation());

                for (int i = 0; i < mWeekDayDayDots; i++) {
                    mDotSprite.setOrbitIndex((float) i + 0.5f);
                    mDotSprite.draw(batch);
                }
            }
        }

    }

    private static final float HAND_WIDTH = 2f;

    private ClockMarkerSprite mMarkerSprite;

    private Texture mHandTexture;
    private Texture mWeekDayTexture;

    private Hand mHourHand;
    private Hand mMinuteHand;
    private Hand mSecondHand;

    public ClockLayer() {
        mHourHand = new Hand(3, Calendar.HOUR, HAND_WIDTH, false);
        mMinuteHand = new Hand(5, Calendar.MINUTE, HAND_WIDTH, false);
        mSecondHand = new Hand(7, Calendar.SECOND, HAND_WIDTH, true);
    }

    @Override
    public void queueAssets(AssetManager assets) {
        assets.load("graphics/clock/hand.png", Texture.class);
        assets.load("graphics/clock/week-day.png", Texture.class);

        ClockMarkerSprite.loadAssets(assets);
    }

    @Override
    public void loadAssets(AssetManager assets) {
        mHandTexture = assets.get("graphics/clock/hand.png");
        mWeekDayTexture = assets.get("graphics/clock/week-day.png");

        mMarkerSprite = new ClockMarkerSprite(2, 6f, assets);
    }

    @Override
    public void resize(SizeManager sizes) {
        mHourHand.resize(sizes);
        mMinuteHand.resize(sizes);
        mSecondHand.resize(sizes);
        mMarkerSprite.resize(sizes);
    }

    @Override
    public void render(Batch batch) {
        Calendar now = Calendar.getInstance();

        mHourHand.update(now);
        mMinuteHand.update(now);
        mSecondHand.update(now);

        batch.begin();

        batch.enableBlending();

        mHourHand.draw(batch);
        mMinuteHand.draw(batch);
        mSecondHand.draw(batch);

        mMarkerSprite.setOrbitAngle(0);
        mMarkerSprite.setLength(12f);
        mMarkerSprite.draw(batch);

        mMarkerSprite.setLength(6f);
        for (int i = 1; i < 12; i++) {
            mMarkerSprite.setOrbitAngle(i * 30);
            mMarkerSprite.draw(batch);
        }

        batch.end();
    }

}
