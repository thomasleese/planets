package me.thomasleese.planets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

import java.util.Calendar;

public class Background {

    private Pixmap mColourPixmap;
    private Color mColour = new Color();

    public Background(AssetManager assets) {
        mColourPixmap = assets.get("graphics/background/colour-gradient.png");
    }

    public Color updateColour(Calendar now) {
        int progress = now.get(Calendar.HOUR) * 60 + now.get(Calendar.MINUTE);
        float proportion = progress / (24 * 60 * 60);
        int colour = mColourPixmap.getPixel((int) (proportion * mColourPixmap.getWidth()), 0);
        mColour.set(colour);
        return mColour;
    }

}
