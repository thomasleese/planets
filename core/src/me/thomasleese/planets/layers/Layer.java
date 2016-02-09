package me.thomasleese.planets.layers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import me.thomasleese.planets.util.SizeManager;

public abstract class Layer {

    public abstract void queueAssets(AssetManager assets);
    public abstract void loadAssets(AssetManager assets);
    public abstract void resize(SizeManager sizes);
    public abstract void render(Batch batch);

}
