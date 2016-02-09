package me.thomasleese.planets.layers;

import com.badlogic.gdx.Gdx;
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

    private static final Texture TEXTURE = new Texture(Gdx.files.internal("graphics/orbit.png"));

    private TextureRegion mTexture;

    @Override
    public void resize(SizeManager sizes) {
        float halfSize = sizes.getOrbitLength(8);
        int size = (int) (halfSize * 2f);

        FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGB565, size, size, false);

        mTexture = new TextureRegion(fbo.getColorBufferTexture());
        mTexture.flip(false, true);

        fbo.begin();

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        PolygonSpriteBatch batch = new PolygonSpriteBatch();

        Matrix4 matrix = new Matrix4();
        matrix.setToOrtho2D(-halfSize, -halfSize, size, size);
        batch.setProjectionMatrix(matrix);

        TextureRegion texture = new TextureRegion(TEXTURE);

        RingShapeDrawer drawer = new RingShapeDrawer();

        batch.begin();

        float w = 1.5f;

        for (int i = 0; i < 8; i++) {
            float radius = sizes.getOrbitLength(i);
            drawer.draw(batch, texture, 0, 0, radius - w, radius + w);
        }

        batch.end();

        fbo.end();

        batch.dispose();

        HdpiUtils.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void render(Batch batch) {
        batch.begin();
        batch.draw(mTexture, -mTexture.getRegionWidth() / 2, -mTexture.getRegionHeight() / 2);
        batch.end();
    }

}
