package me.thomasleese.planets;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;

import java.util.Calendar;

public class Planets extends ApplicationAdapter {

	private AssetManager mAssets;

    private Background mBackground;

	@Override
	public void create () {
        mAssets = new AssetManager();
        mAssets.load("graphics/background/colour-gradient.png", Pixmap.class);

        mAssets.finishLoading();

        mBackground = new Background(mAssets);
	}

	@Override
	public void render () {
        Calendar now = Calendar.getInstance();
        Color colour = mBackground.updateColour(now);

		Gdx.gl.glClearColor(colour.r, colour.g, colour.b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

}
