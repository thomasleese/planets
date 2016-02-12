package me.thomasleese.planets.util;

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

public class CircularOrbitSprite extends OrbitSprite {

    private TextureRegion mTextureRegion;
    private float mRadius;
    private RingShapeDrawer mDrawer;

    public CircularOrbitSprite(Texture texture, float radius) {
        super(texture);

        setScale(radius, radius);
        mTextureRegion = new TextureRegion(texture);

        mDrawer = new RingShapeDrawer();
    }

    @Override
    public void setScale(float x, float y) {
        mRadius = (x + y) / 2f;
    }

    @Override
    public void draw(Batch batch) {
        float width = 0.5f;
        mDrawer.draw((PolygonSpriteBatch) batch, mTextureRegion,
                getX(), getY(), mRadius - width, mRadius + width);
    }

}
