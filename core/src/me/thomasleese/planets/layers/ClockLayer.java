package me.thomasleese.planets.layers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import me.thomasleese.planets.sprites.ClockMarkerSprite;
import me.thomasleese.planets.util.ClockUtils;
import me.thomasleese.planets.sprites.OrbitSprite;
import me.thomasleese.planets.util.SizeManager;

import java.util.Calendar;

public class ClockLayer extends Layer {

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

            mSprite = new Sprite(mHandTexture);
            mSprite.setOrigin(0.5f, 0f);
            mSprite.setCenter(0, 0);

            if (includesWeekDay) {
                mDotSprite = new OrbitSprite(mWeekDayTexture);
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

        mHourHand = new Hand(3, Calendar.HOUR, HAND_WIDTH, false);
        mMinuteHand = new Hand(5, Calendar.MINUTE, HAND_WIDTH, false);
        mSecondHand = new Hand(7, Calendar.SECOND, HAND_WIDTH, true);
    }

    @Override
    public void resize(SizeManager sizes) {
        mHourHand.resize(sizes);
        mMinuteHand.resize(sizes);
        mSecondHand.resize(sizes);
        mMarkerSprite.resize(sizes);
    }

    private void renderMonthMark(Batch batch, int day) {
        float angle = (day / 365f) * 360f;
        mMarkerSprite.setOrbitAngle(angle);
        mMarkerSprite.draw(batch);
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
        mMarkerSprite.setOrbitIndex(2);
        mMarkerSprite.setLength(12f);
        mMarkerSprite.draw(batch);

        mMarkerSprite.setLength(6f);

        renderMonthMark(batch, 0); // jan
        renderMonthMark(batch, 31); // feb
        renderMonthMark(batch, 31 + 28); // mar
        renderMonthMark(batch, 31 + 28 + 31); // apr
        renderMonthMark(batch, 31 + 28 + 31 + 30); // may
        renderMonthMark(batch, 31 + 28 + 31 + 30 + 31); // jun
        renderMonthMark(batch, 31 + 28 + 31 + 30 + 31 + 30); // jul
        renderMonthMark(batch, 31 + 28 + 31 + 30 + 31 + 30 + 31); // aug
        renderMonthMark(batch, 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31); // sep
        renderMonthMark(batch, 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30); // oct
        renderMonthMark(batch, 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31); // nov
        renderMonthMark(batch, 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30); // dec
        //renderMonthMark(batch, 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30 + 31); // march

        mMarkerSprite.setLength(10f);
        mMarkerSprite.setOrbitIndex(7);
        mMarkerSprite.setOrbitAngle(ClockUtils.getSunriseAngle());
        mMarkerSprite.draw(batch);
        mMarkerSprite.setOrbitAngle(ClockUtils.getSunsetAngle());
        mMarkerSprite.draw(batch);

        batch.end();
    }

}
