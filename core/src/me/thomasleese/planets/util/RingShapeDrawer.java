package me.thomasleese.planets.util;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class RingShapeDrawer {

    private class Drawer extends MeshBuilder {

        private Texture texture;

        public Drawer() {
            super();
            super.begin(
                new VertexAttributes(new VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE), VertexAttribute
                    .ColorPacked(), VertexAttribute.TexCoords(1)), GL20.GL_TRIANGLES);
        }

        public void setTextureRegion(TextureRegion region) {
            texture = region.getTexture();
            setUVRange(region);
        }

        public void draw(PolygonSpriteBatch batch) {
            batch.draw(texture, getVertices(), 0, getNumVertices() * getFloatsPerVertex(), getIndices(), 0, getNumIndices());
            clear();
        }

        private final Vector3 tempV1 = new Vector3();
        private final Vector3 tempV2 = new Vector3();
        private final Vector3 tempV3 = new Vector3();
        private final Vector3 tempV4 = new Vector3();
        private final VertexInfo vertTmp1 = new VertexInfo();
        private final VertexInfo vertTmp2 = new VertexInfo();
        private final VertexInfo vertTmp3 = new VertexInfo();
        private final VertexInfo vertTmp4 = new VertexInfo();

        @Override
        public void ellipse(float width, float height, float innerWidth, float innerHeight, int divisions, float centerX,
            float centerY, float centerZ, float normalX, float normalY, float normalZ, float tangentX, float tangentY, float tangentZ,
            float binormalX, float binormalY, float binormalZ, float angleFrom, float angleTo) {
            if (innerWidth <= 0 || innerHeight <= 0) {
                ensureTriangles(divisions + 2, divisions);
            } else if (innerWidth == width && innerHeight == height) {
                ensureVertices(divisions + 1);
                ensureIndices(divisions + 1);
            } else {
                ensureRectangles((divisions + 1) * 2, divisions + 1);
            }

            final float ao = MathUtils.degreesToRadians * angleFrom;
            final float step = (MathUtils.degreesToRadians * (angleTo - angleFrom)) / divisions;
            final Vector3 sxEx = tempV1.set(tangentX, tangentY, tangentZ).scl(width * 0.5f);
            final Vector3 syEx = tempV2.set(binormalX, binormalY, binormalZ).scl(height * 0.5f);
            final Vector3 sxIn = tempV3.set(tangentX, tangentY, tangentZ).scl(innerWidth * 0.5f);
            final Vector3 syIn = tempV4.set(binormalX, binormalY, binormalZ).scl(innerHeight * 0.5f);
            VertexInfo currIn = vertTmp3.set(null, null, null, null);
            currIn.hasUV = currIn.hasPosition = currIn.hasNormal = true;
            currIn.uv.set(.5f, .5f);
            currIn.position.set(centerX, centerY, centerZ);
            currIn.normal.set(normalX, normalY, normalZ);
            VertexInfo currEx = vertTmp4.set(null, null, null, null);
            currEx.hasUV = currEx.hasPosition = currEx.hasNormal = true;
            currEx.uv.set(.5f, .5f);
            currEx.position.set(centerX, centerY, centerZ);
            currEx.normal.set(normalX, normalY, normalZ);
            final short center = vertex(currEx);
            float angle = 0f;
            final float us = 0.5f * (innerWidth / width);
            final float vs = 0.5f * (innerHeight / height);
            short i1, i2 = 0, i3 = 0, i4 = 0;
            for (int i = 0; i <= divisions; i++) {
                angle = ao + step * i;
                final float x = MathUtils.cos(angle);
                final float y = MathUtils.sin(angle);
                currEx.position.set(centerX, centerY, centerZ).add(sxEx.x * x + syEx.x * y, sxEx.y * x + syEx.y * y,
                    sxEx.z * x + syEx.z * y);
                currEx.uv.set(.5f + .5f * x, .5f + .5f * y);
                i1 = vertex(currEx);

                if (innerWidth <= 0f || innerHeight <= 0f) {
                    if (i != 0) triangle(i1, i2, center);
                    i2 = i1;
                } else if (innerWidth == width && innerHeight == height) {
                    if (i != 0) line(i1, i2);
                    i2 = i1;
                } else {
                    currIn.position.set(centerX, centerY, centerZ).add(sxIn.x * x + syIn.x * y, sxIn.y * x + syIn.y * y,
                        sxIn.z * x + syIn.z * y);
                    currIn.uv.set(0.5f, 0.5f);
                    i2 = i1;
                    i1 = vertex(currIn);

                    if (i != 0) rect(i1, i2, i4, i3);
                    i4 = i2;
                    i3 = i1;
                }
            }
        }

    }

    public void draw(PolygonSpriteBatch batch, TextureRegion texture, float cx, float cy, float innerRadius, float outerRadius) {
        Drawer drawer = new Drawer();
        drawer.setTextureRegion(texture);

        int segments = (int) (outerRadius * 2f);
        float outerDiameter = outerRadius * 2f;
        float innterDiameter = innerRadius * 2f;

        drawer.ellipse(outerDiameter, outerDiameter, innterDiameter, innterDiameter, segments, cx, cy, 0, 0, 0, -1);
        drawer.draw(batch);
    }

}
