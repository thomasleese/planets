package me.thomasleese.planets.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import me.thomasleese.planets.util.SizeManager;

import java.util.Calendar;

public class BackgroundLayer extends Layer {

    private Pixmap mColourPixmap;
    private Color mColour = new Color();

    public BackgroundLayer(AssetManager assets) {
        mColourPixmap = assets.get("graphics/background/colour-gradient.png");
    }

    public Color updateColour(Calendar now) {
        int progress = now.get(Calendar.HOUR) * 60 + now.get(Calendar.MINUTE);
        float proportion = progress / (24 * 60 * 60);
        int colour = mColourPixmap.getPixel((int) (proportion * mColourPixmap.getWidth()), 0);
        mColour.set(colour);
        return mColour;
    }

    @Override
    public void resize(SizeManager sizes) {

    }

    @Override
    public void render(Batch batch) {
        Calendar now = Calendar.getInstance();

        Color colour = updateColour(now);

        Gdx.gl.glClearColor(colour.r, colour.g, colour.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
