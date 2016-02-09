package me.thomasleese.planets.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.HdpiUtils;
import com.badlogic.gdx.math.Matrix4;

public class CircularOrbitSprite extends OrbitSprite {

    public CircularOrbitSprite(Texture texture, float radius) {
        super(makeTexture(new TextureRegion(texture), radius * 2f));
        setScale(0.5f, 0.5f);
    }

    private static TextureRegion makeTexture(TextureRegion texture, float radius) {
        float halfSize = radius + 10f;
        int size = (int) (halfSize * 2f);

        FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGBA8888, size, size, false);

        TextureRegion resultTexture = new TextureRegion(fbo.getColorBufferTexture());
        resultTexture.flip(false, true);

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
        drawer.draw(batch, texture, 0, 0, radius - w, radius + w);

        batch.end();

        fbo.end();

        batch.dispose();

        HdpiUtils.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        return resultTexture;
    }

}
