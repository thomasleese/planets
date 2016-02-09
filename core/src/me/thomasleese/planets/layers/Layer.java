package me.thomasleese.planets.layers;

import com.badlogic.gdx.graphics.g2d.Batch;
import me.thomasleese.planets.util.SizeManager;

public abstract class Layer {

    public abstract void resize(SizeManager sizes);
    public abstract void render(Batch batch);

}
