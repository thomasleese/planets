package me.thomasleese.planets.layers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import me.thomasleese.planets.util.SizeManager;

public class HighlightShadowLayer extends Layer {

    private Texture mTexture;
    private float mSize = 0;

    @Override
    public void queueAssets(AssetManager assets) {
        assets.load("graphics/highlight-shadow/shadow.png", Texture.class);
    }

    @Override
    public void loadAssets(AssetManager assets) {
        mTexture = assets.get("graphics/highlight-shadow/shadow.png");
    }

    @Override
    public void resize(SizeManager sizes) {
        mSize = sizes.getScreenSize();
    }

    @Override
    public void render(Batch batch) {
        batch.begin();
        batch.draw(mTexture, -mSize, -mSize, mSize * 2f, mSize * 2f);
        batch.end();
    }

}
