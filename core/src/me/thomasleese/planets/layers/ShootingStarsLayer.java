package me.thomasleese.planets.layers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;
import me.thomasleese.planets.sprites.ShootingStarSprite;
import me.thomasleese.planets.util.ClockUtils;
import me.thomasleese.planets.util.SizeManager;

import java.util.Calendar;

public class ShootingStarsLayer extends Layer {

    private static final String TAG = "ShootingStars";

    private Calendar mSunrise;
    private Calendar mSunset;
    private Calendar mLastUpdate;

    private ShootingStarSprite mSunriseSprite;
    private ShootingStarSprite mSunsetSprite;

    @Override
    public void queueAssets(AssetManager assets) {
        ShootingStarSprite.loadAssets(assets);
    }

    @Override
    public void loadAssets(AssetManager assets) {
        mSunriseSprite = new ShootingStarSprite(assets);
        mSunsetSprite = new ShootingStarSprite(assets);
    }

    @Override
    public void resize(SizeManager sizes) {
        mSunriseSprite.resize(sizes);
        mSunsetSprite.resize(sizes);
    }

    private void update() {
        Calendar now = Calendar.getInstance();

        Calendar sunrise = ClockUtils.getSunrise();
        Calendar sunset = ClockUtils.getSunset();

        mSunriseSprite.setOrbitAngle(ClockUtils.getSunriseAngle());
        mSunsetSprite.setOrbitAngle(ClockUtils.getSunsetAngle());

        boolean afterSunrise = now.after(sunrise);
        boolean afterSunset = now.after(sunset);

        if (afterSunrise && !afterSunset) {
            mSunriseSprite.setColor(.5f, .5f, .5f, .5f);
            mSunsetSprite.setColor(1f, 1f, 1f, 1f);
        } else {
            mSunriseSprite.setColor(1f, 1f, 1f, 1f);
            mSunsetSprite.setColor(.5f, .5f, .5f, .5f);
        }
    }

    @Override
    public void render(Batch batch) {
        update();

        batch.begin();

        mSunriseSprite.draw(batch);
        mSunsetSprite.draw(batch);

        batch.end();
    }

}
