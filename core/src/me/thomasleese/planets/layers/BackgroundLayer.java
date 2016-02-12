package me.thomasleese.planets.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import me.thomasleese.planets.util.SizeManager;

import java.util.Calendar;

public class BackgroundLayer extends Layer {

    private static final String TAG = "BackgroundLayer";

    private Texture mTexture;
    private Color mColour;
    private float mSize, mHalfSize;

    @Override
    public void queueAssets(AssetManager assets) {
        assets.load("graphics/background.png", Texture.class);
    }

    @Override
    public void loadAssets(AssetManager assets) {
        mTexture = assets.get("graphics/background.png");
        mColour = new Color(0, 10f / 256f, 68f / 256f, 1f);
    }

    @Override
    public void resize(SizeManager sizes) {
        mSize = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mHalfSize = mSize / 2f;
    }

    @Override
    public void render(Batch batch) {
        Gdx.gl.glClearColor(mColour.r, mColour.g, mColour.b, mColour.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(mTexture, -mHalfSize, -mHalfSize, mSize, mSize);
        batch.end();
    }

}
