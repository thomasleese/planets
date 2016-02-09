package me.thomasleese.planets.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import me.thomasleese.planets.util.SizeManager;

public class HighlightShadowLayer extends Layer {

    private static final Texture TEXTURE =
        new Texture(Gdx.files.internal("graphics/shadow.png"));

    private float mSize = 0;

    @Override
    public void resize(SizeManager sizes) {
        mSize = sizes.getScreenSize();
    }

    @Override
    public void render(Batch batch) {
        batch.begin();
        batch.draw(TEXTURE, -mSize, -mSize, mSize * 2f, mSize * 2f);
        batch.end();
    }

}
