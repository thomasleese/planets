package me.thomasleese.planets.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.HdpiUtils;
import com.badlogic.gdx.math.Matrix4;
import me.thomasleese.planets.util.RingShapeDrawer;
import me.thomasleese.planets.util.SizeManager;

public class OrbitsLayer extends Layer {

    private TextureRegion mRingTexture;
    private TextureRegion mRenderedTexture;

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
        float halfSize = sizes.getOrbitLength(8);
        int size = (int) (halfSize * 2f);

        FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGBA8888, size, size, false);

        mRenderedTexture = new TextureRegion(fbo.getColorBufferTexture());
        mRenderedTexture.flip(false, true);

        fbo.begin();

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        PolygonSpriteBatch batch = new PolygonSpriteBatch();

        Matrix4 matrix = new Matrix4();
        matrix.setToOrtho2D(-halfSize, -halfSize, size, size);
        batch.setProjectionMatrix(matrix);

        RingShapeDrawer drawer = new RingShapeDrawer();

        batch.begin();

        float w = 1f;

        for (int i = 0; i < 8; i++) {
            float radius = sizes.getOrbitLength(i);
            drawer.draw(batch, mRingTexture, 0, 0, radius - w, radius + w);
        }

        batch.end();

        fbo.end();

        batch.dispose();

        HdpiUtils.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void render(Batch batch) {
        batch.begin();
        batch.draw(mRenderedTexture, -mRenderedTexture.getRegionWidth() / 2, -mRenderedTexture.getRegionHeight() / 2);
        batch.end();
    }

}
