package me.thomasleese.planets;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.thomasleese.planets.layers.*;
import me.thomasleese.planets.util.SizeManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Planets extends ApplicationAdapter {

    private static final String TAG = "Planets";

	private AssetManager mAssets;

    private OrthographicCamera mCamera;
    private SpriteBatch mSpriteBatch;

    private SizeManager mSizes;

    private List<Layer> mLayers = new ArrayList<Layer>();
    private TimerLayer mTimerLayer;
    private PlanetsLayer mPlanetsLayer;
    private RocketsLayer mRocketsLayer;

    @Override
	public void create () {
        mCamera = new OrthographicCamera();
        mSpriteBatch = new SpriteBatch();

        mSizes = new SizeManager();

        //mTimerLayer = new TimerLayer();

        mLayers.add(new BackgroundLayer());
        //mLayers.add(new StarfieldLayer());
        /*mLayers.add(new ClockLayer());
        mLayers.add(new OrbitsLayer());
        mLayers.add(mPlanetsLayer = new PlanetsLayer());
        mLayers.add(mRocketsLayer = new RocketsLayer(mTimerLayer));
        mLayers.add(new ShootingStarsLayer());
        mLayers.add(mTimerLayer);*/
        mLayers.add(new HighlightShadowLayer());

        /*InputMultiplexer input = new InputMultiplexer();
        input.addProcessor(mRocketsLayer);
        Gdx.input.setInputProcessor(input);*/

        mAssets = new AssetManager();
        for (Layer layer : mLayers) {
            layer.queueAssets(mAssets);
        }
        mAssets.finishLoading();
        for (Layer layer : mLayers) {
            layer.loadAssets(mAssets);
        }
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "Resize: " + width + "x" + height);

        mCamera.setToOrtho(false, width, height);
        mCamera.position.x = 0;
        mCamera.position.y = 0;
        mCamera.update();

        mSizes.resize();

        for (Layer layer : mLayers) {
            layer.resize(mSizes);
        }
    }

    @Override
    public void render() {
        mSpriteBatch.setProjectionMatrix(mCamera.combined);

        /*Calendar now = Calendar.getInstance();
        if (mTimerLayer.isVisible()) {
            now.add(Calendar.HOUR, (int) (-mTimerLayer.getDuration() * 24f));
        }
        mPlanetsLayer.setNow(now);*/

        for (Layer layer : mLayers) {
            layer.render(mSpriteBatch);
        }
    }

}
