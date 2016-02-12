package me.thomasleese.planets.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.HdpiUtils;
import com.badlogic.gdx.math.Matrix4;

public class GlassBall {

    private Sprite mBallSprite;
    private Sprite mShadowSprite;
    private float mSize;

    public GlassBall(Color colour, AssetManager assets) {
        mBallSprite = new Sprite(makeTexture(colour, assets));
        mBallSprite.setOriginCenter();
        mBallSprite.setAlpha(1.0f);

        Texture shadowTexture = assets.get("graphics/glass-ball/shadow.png");
        mShadowSprite = new Sprite(shadowTexture);
        mShadowSprite.setOriginCenter();
        mShadowSprite.setColor(colour);
        mShadowSprite.setAlpha(0.8f);

        setSize(100);
        setCenter(0, 0);
    }

    public void setSize(float size) {
        mSize = size;

        float scale = size / mBallSprite.getWidth();
        mBallSprite.setScale(scale, scale);

        float shadowScale = size / (mShadowSprite.getWidth() / 2f);
        mShadowSprite.setScale(shadowScale, shadowScale);
    }

    public float getSize() {
        return mSize;
    }

    public void setCenter(float x, float y) {
        mBallSprite.setCenter(x, y);
        mShadowSprite.setCenter(x, y);
    }

    public void setRotation(float rotation) {
        mBallSprite.setRotation(rotation);
        mShadowSprite.setRotation(rotation);
    }

    public void draw(Batch batch) {
        mShadowSprite.draw(batch);
        mBallSprite.draw(batch);
    }

    private TextureRegion makeTexture(Color colour, AssetManager assets) {
        int size = 100;

        FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGBA8888, size, size, false);

        TextureRegion texture = new TextureRegion(fbo.getColorBufferTexture());
        texture.flip(false, true);

        fbo.begin();

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpriteBatch batch = new SpriteBatch();

        Matrix4 matrix = new Matrix4();
        matrix.setToOrtho2D(0, 0, size, size);
        batch.setProjectionMatrix(matrix);

        batch.begin();

        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_DST_ALPHA);

        Texture backgroundTexture = assets.get("graphics/glass-ball/background.png");

        batch.setColor(colour.r, colour.g, colour.b, 0.75f);
        batch.draw(backgroundTexture, 0, 0, size, size);

        Texture ballTexture = assets.get("graphics/glass-ball/ball.png");

        batch.setColor(Color.WHITE);
        batch.draw(ballTexture, 0, 0, size, size);

        batch.end();

        batch.dispose();

        fbo.end();

        HdpiUtils.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        return texture;
    }

    public static void loadAssets(AssetManager assets) {
        assets.load("graphics/glass-ball/background.png", Texture.class);
        assets.load("graphics/glass-ball/shadow.png", Texture.class);
        assets.load("graphics/glass-ball/ball.png", Texture.class);
    }

}
