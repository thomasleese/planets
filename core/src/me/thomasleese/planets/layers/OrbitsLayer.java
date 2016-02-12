package me.thomasleese.planets.layers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import me.thomasleese.planets.util.RingShapeDrawer;
import me.thomasleese.planets.util.SizeManager;

public class OrbitsLayer extends Layer {

    private SizeManager mSizes;
    private TextureRegion mRingTexture;
    private RingShapeDrawer mDrawer;

    public OrbitsLayer() {
        mDrawer = new RingShapeDrawer();
    }

    @Override
    public void queueAssets(AssetManager assets) {
        assets.load("graphics/orbits/orbit.png", Texture.class);
    }

    @Override
    public void loadAssets(AssetManager assets) {
        Texture texture = assets.get("graphics/orbits/orbit.png");
        mRingTexture = new TextureRegion(texture);
    }

    @Override
    public void resize(SizeManager sizes) {
        mSizes = sizes;
    }

    @Override
    public void render(Batch batch) {
        batch.begin();

        float w = 1f;

        for (int i = 0; i < 8; i++) {
            float radius = mSizes.getOrbitLength(i);
            mDrawer.draw((PolygonSpriteBatch) batch, mRingTexture, 0, 0, radius - w, radius + w);
        }

        batch.end();
    }

}
