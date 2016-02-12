package me.thomasleese.planets.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import me.thomasleese.planets.util.RingShapeDrawer;

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
