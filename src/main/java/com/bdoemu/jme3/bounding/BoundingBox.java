/*
 * Decompiled with CFR 0_121.
 */
package com.bdoemu.jme3.bounding;

import com.bdoemu.jme3.bounding.BoundingVolume;
import com.bdoemu.jme3.collision.Collidable;
import com.bdoemu.jme3.collision.CollisionResult;
import com.bdoemu.jme3.collision.CollisionResults;
import com.bdoemu.jme3.collision.UnsupportedCollisionException;
import com.bdoemu.jme3.math.FastMath;
import com.bdoemu.jme3.math.Matrix3f;
import com.bdoemu.jme3.math.Matrix4f;
import com.bdoemu.jme3.math.Plane;
import com.bdoemu.jme3.math.Quaternion;
import com.bdoemu.jme3.math.Ray;
import com.bdoemu.jme3.math.Transform;
import com.bdoemu.jme3.math.Vector3f;
import com.bdoemu.jme3.util.TempVars;

public class BoundingBox
extends BoundingVolume {
    float xExtent;
    float yExtent;
    float zExtent;

    public BoundingBox() {
    }

    public BoundingBox(Vector3f c, float x, float y, float z) {
        this.center.set(c);
        this.xExtent = x;
        this.yExtent = y;
        this.zExtent = z;
    }

    public BoundingBox(BoundingBox source) {
        this.center.set(source.center);
        this.xExtent = source.xExtent;
        this.yExtent = source.yExtent;
        this.zExtent = source.zExtent;
    }

    public BoundingBox(Vector3f min, Vector3f max) {
        this.setMinMax(min, max);
    }

    @Override
    public BoundingVolume.Type getType() {
        return BoundingVolume.Type.AABB;
    }

    @Override
    public void computeFromPoints(float[] points) {
        this.containAABB(points);
    }

    public static void checkMinMax(Vector3f min, Vector3f max, Vector3f point) {
        if (point.x < min.x) {
            min.x = point.x;
        }
        if (point.x > max.x) {
            max.x = point.x;
        }
        if (point.y < min.y) {
            min.y = point.y;
        }
        if (point.y > max.y) {
            max.y = point.y;
        }
        if (point.z < min.z) {
            min.z = point.z;
        }
        if (point.z > max.z) {
            max.z = point.z;
        }
    }

    public void containAABB(float[] points) {
        if (points == null) {
            return;
        }
        float minX = Float.POSITIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY;
        float minZ = Float.POSITIVE_INFINITY;
        float maxX = Float.NEGATIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY;
        float maxZ = Float.NEGATIVE_INFINITY;
        for (int i = 0; i < points.length; i += 3) {
            float x = points[i];
            float y = points[i + 1];
            float z = points[i + 2];
            if (x < minX) {
                minX = x;
            }
            if (x > maxX) {
                maxX = x;
            }
            if (y < minY) {
                minY = y;
            }
            if (y > maxY) {
                maxY = y;
            }
            if (z < minZ) {
                minZ = z;
            }
            if (z <= maxZ) continue;
            maxZ = z;
        }
        this.center.set(minX + maxX, minY + maxY, minZ + maxZ);
        this.center.multLocal(0.5f);
        this.xExtent = maxX - this.center.x;
        this.yExtent = maxY - this.center.y;
        this.zExtent = maxZ - this.center.z;
    }

    @Override
    public BoundingVolume transform(Transform trans, BoundingVolume store) {
        BoundingBox box = store == null || store.getType() != BoundingVolume.Type.AABB ? new BoundingBox() : (BoundingBox)store;
        this.center.mult(trans.getScale(), box.center);
        trans.getRotation().mult(box.center, box.center);
        box.center.addLocal(trans.getTranslation());
        TempVars vars = TempVars.get();
        Matrix3f transMatrix = vars.tempMat3;
        transMatrix.set(trans.getRotation());
        transMatrix.absoluteLocal();
        Vector3f scale = trans.getScale();
        vars.vect1.set(this.xExtent * FastMath.abs(scale.x), this.yExtent * FastMath.abs(scale.y), this.zExtent * FastMath.abs(scale.z));
        transMatrix.mult(vars.vect1, vars.vect2);
        box.xExtent = FastMath.abs(vars.vect2.getX());
        box.yExtent = FastMath.abs(vars.vect2.getY());
        box.zExtent = FastMath.abs(vars.vect2.getZ());
        vars.release();
        return box;
    }

    @Override
    public BoundingVolume transform(Matrix4f trans, BoundingVolume store) {
        BoundingBox box = store == null || store.getType() != BoundingVolume.Type.AABB ? new BoundingBox() : (BoundingBox)store;
        TempVars vars = TempVars.get();
        float w = trans.multProj(this.center, box.center);
        box.center.divideLocal(w);
        Matrix3f transMatrix = vars.tempMat3;
        trans.toRotationMatrix(transMatrix);
        transMatrix.absoluteLocal();
        vars.vect1.set(this.xExtent, this.yExtent, this.zExtent);
        transMatrix.mult(vars.vect1, vars.vect1);
        box.xExtent = FastMath.abs(vars.vect1.getX());
        box.yExtent = FastMath.abs(vars.vect1.getY());
        box.zExtent = FastMath.abs(vars.vect1.getZ());
        vars.release();
        return box;
    }

    @Override
    public Plane.Side whichSide(Plane plane) {
        float radius = FastMath.abs(this.xExtent * plane.getNormal().getX()) + FastMath.abs(this.yExtent * plane.getNormal().getY()) + FastMath.abs(this.zExtent * plane.getNormal().getZ());
        float distance = plane.pseudoDistance(this.center);
        if (distance < - radius) {
            return Plane.Side.Negative;
        }
        if (distance > radius) {
            return Plane.Side.Positive;
        }
        return Plane.Side.None;
    }

    @Override
    public BoundingVolume merge(BoundingVolume volume) {
        return this.mergeLocal(volume);
    }

    @Override
    public BoundingVolume mergeLocal(BoundingVolume volume) {
        if (volume == null) {
            return this;
        }
        switch (volume.getType()) {
            case AABB: {
                BoundingBox vBox = (BoundingBox)volume;
                return this.mergeLocal(vBox.center, vBox.xExtent, vBox.yExtent, vBox.zExtent);
            }
        }
        return null;
    }

    private BoundingBox mergeLocal(Vector3f c, float x, float y, float z) {
        float low;
        float high;
        if (this.xExtent == Float.POSITIVE_INFINITY || x == Float.POSITIVE_INFINITY) {
            this.center.x = 0.0f;
            this.xExtent = Float.POSITIVE_INFINITY;
        } else {
            low = this.center.x - this.xExtent;
            if (low > c.x - x) {
                low = c.x - x;
            }
            if ((high = this.center.x + this.xExtent) < c.x + x) {
                high = c.x + x;
            }
            this.center.x = (low + high) / 2.0f;
            this.xExtent = high - this.center.x;
        }
        if (this.yExtent == Float.POSITIVE_INFINITY || y == Float.POSITIVE_INFINITY) {
            this.center.y = 0.0f;
            this.yExtent = Float.POSITIVE_INFINITY;
        } else {
            low = this.center.y - this.yExtent;
            if (low > c.y - y) {
                low = c.y - y;
            }
            if ((high = this.center.y + this.yExtent) < c.y + y) {
                high = c.y + y;
            }
            this.center.y = (low + high) / 2.0f;
            this.yExtent = high - this.center.y;
        }
        if (this.zExtent == Float.POSITIVE_INFINITY || z == Float.POSITIVE_INFINITY) {
            this.center.z = 0.0f;
            this.zExtent = Float.POSITIVE_INFINITY;
        } else {
            low = this.center.z - this.zExtent;
            if (low > c.z - z) {
                low = c.z - z;
            }
            if ((high = this.center.z + this.zExtent) < c.z + z) {
                high = c.z + z;
            }
            this.center.z = (low + high) / 2.0f;
            this.zExtent = high - this.center.z;
        }
        return this;
    }

    @Override
    public BoundingVolume clone(BoundingVolume store) {
        if (store != null && store.getType() == BoundingVolume.Type.AABB) {
            BoundingBox rVal = (BoundingBox)store;
            rVal.center.set(this.center);
            rVal.xExtent = this.xExtent;
            rVal.yExtent = this.yExtent;
            rVal.zExtent = this.zExtent;
            rVal.checkPlane = this.checkPlane;
            return rVal;
        }
        BoundingBox rVal = new BoundingBox(this.center.clone(), this.xExtent, this.yExtent, this.zExtent);
        return rVal;
    }

    public String toString() {
        return this.getClass().getSimpleName() + " [Center: " + this.center + "  xExtent: " + this.xExtent + "  yExtent: " + this.yExtent + "  zExtent: " + this.zExtent + "]";
    }

    @Override
    public boolean intersects(BoundingVolume bv) {
        return bv.intersectsBoundingBox(this);
    }

    @Override
    public boolean intersectsBoundingBox(BoundingBox bb) {
        assert (Vector3f.isValidVector(this.center) && Vector3f.isValidVector(bb.center));
        if (this.center.x + this.xExtent < bb.center.x - bb.xExtent || this.center.x - this.xExtent > bb.center.x + bb.xExtent) {
            return false;
        }
        if (this.center.y + this.yExtent < bb.center.y - bb.yExtent || this.center.y - this.yExtent > bb.center.y + bb.yExtent) {
            return false;
        }
        if (this.center.z + this.zExtent < bb.center.z - bb.zExtent || this.center.z - this.zExtent > bb.center.z + bb.zExtent) {
            return false;
        }
        return true;
    }

    @Override
    public boolean intersects(Ray ray) {
        assert (Vector3f.isValidVector(this.center));
        TempVars vars = TempVars.get();
        Vector3f diff = ray.origin.subtract(this.getCenter(vars.vect2), vars.vect1);
        float[] fWdU = vars.fWdU;
        float[] fAWdU = vars.fAWdU;
        float[] fDdU = vars.fDdU;
        float[] fADdU = vars.fADdU;
        float[] fAWxDdU = vars.fAWxDdU;
        fWdU[0] = ray.getDirection().dot(Vector3f.UNIT_X);
        fAWdU[0] = FastMath.abs(fWdU[0]);
        fDdU[0] = diff.dot(Vector3f.UNIT_X);
        fADdU[0] = FastMath.abs(fDdU[0]);
        if (fADdU[0] > this.xExtent && (double)(fDdU[0] * fWdU[0]) >= 0.0) {
            vars.release();
            return false;
        }
        fWdU[1] = ray.getDirection().dot(Vector3f.UNIT_Y);
        fAWdU[1] = FastMath.abs(fWdU[1]);
        fDdU[1] = diff.dot(Vector3f.UNIT_Y);
        fADdU[1] = FastMath.abs(fDdU[1]);
        if (fADdU[1] > this.yExtent && (double)(fDdU[1] * fWdU[1]) >= 0.0) {
            vars.release();
            return false;
        }
        fWdU[2] = ray.getDirection().dot(Vector3f.UNIT_Z);
        fAWdU[2] = FastMath.abs(fWdU[2]);
        fDdU[2] = diff.dot(Vector3f.UNIT_Z);
        fADdU[2] = FastMath.abs(fDdU[2]);
        if (fADdU[2] > this.zExtent && (double)(fDdU[2] * fWdU[2]) >= 0.0) {
            vars.release();
            return false;
        }
        Vector3f wCrossD = ray.getDirection().cross(diff, vars.vect2);
        fAWxDdU[0] = FastMath.abs(wCrossD.dot(Vector3f.UNIT_X));
        float rhs = this.yExtent * fAWdU[2] + this.zExtent * fAWdU[1];
        if (fAWxDdU[0] > rhs) {
            vars.release();
            return false;
        }
        fAWxDdU[1] = FastMath.abs(wCrossD.dot(Vector3f.UNIT_Y));
        rhs = this.xExtent * fAWdU[2] + this.zExtent * fAWdU[0];
        if (fAWxDdU[1] > rhs) {
            vars.release();
            return false;
        }
        fAWxDdU[2] = FastMath.abs(wCrossD.dot(Vector3f.UNIT_Z));
        rhs = this.xExtent * fAWdU[1] + this.yExtent * fAWdU[0];
        if (fAWxDdU[2] > rhs) {
            vars.release();
            return false;
        }
        vars.release();
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int collideWithRay(Ray ray, CollisionResults results) {
        TempVars vars = TempVars.get();
        try {
            boolean notEntirelyClipped;
            Vector3f diff = vars.vect1.set(ray.origin).subtractLocal(this.center);
            Vector3f direction = vars.vect2.set(ray.direction);
            float[] t = vars.fWdU;
            t[0] = 0.0f;
            t[1] = Float.POSITIVE_INFINITY;
            float saveT0 = t[0];
            float saveT1 = t[1];
            boolean bl = notEntirelyClipped = this.clip(direction.x, - diff.x - this.xExtent, t) && this.clip(- direction.x, diff.x - this.xExtent, t) && this.clip(direction.y, - diff.y - this.yExtent, t) && this.clip(- direction.y, diff.y - this.yExtent, t) && this.clip(direction.z, - diff.z - this.zExtent, t) && this.clip(- direction.z, diff.z - this.zExtent, t);
            if (notEntirelyClipped && (t[0] != saveT0 || t[1] != saveT1)) {
                if (t[1] > t[0]) {
                    float[] distances = t;
                    Vector3f point0 = new Vector3f(ray.direction).multLocal(distances[0]).addLocal(ray.origin);
                    Vector3f point1 = new Vector3f(ray.direction).multLocal(distances[1]).addLocal(ray.origin);
                    CollisionResult result = new CollisionResult(point0, distances[0]);
                    results.addCollision(result);
                    result = new CollisionResult(point1, distances[1]);
                    results.addCollision(result);
                    int n = 2;
                    return n;
                }
                Vector3f point = new Vector3f(ray.direction).multLocal(t[0]).addLocal(ray.origin);
                CollisionResult result = new CollisionResult(point, t[0]);
                results.addCollision(result);
                int point1 = 1;
                return point1;
            }
            int point = 0;
            return point;
        }
        finally {
            vars.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int collideWithRay(Ray ray) {
        TempVars vars = TempVars.get();
        try {
            boolean notEntirelyClipped;
            Vector3f diff = vars.vect1.set(ray.origin).subtractLocal(this.center);
            Vector3f direction = vars.vect2.set(ray.direction);
            float[] t = vars.fWdU;
            t[0] = 0.0f;
            t[1] = Float.POSITIVE_INFINITY;
            float saveT0 = t[0];
            float saveT1 = t[1];
            boolean bl = notEntirelyClipped = this.clip(direction.x, - diff.x - this.xExtent, t) && this.clip(- direction.x, diff.x - this.xExtent, t) && this.clip(direction.y, - diff.y - this.yExtent, t) && this.clip(- direction.y, diff.y - this.yExtent, t) && this.clip(direction.z, - diff.z - this.zExtent, t) && this.clip(- direction.z, diff.z - this.zExtent, t);
            if (notEntirelyClipped && (t[0] != saveT0 || t[1] != saveT1)) {
                if (t[1] > t[0]) {
                    int n = 2;
                    return n;
                }
                int n = 1;
                return n;
            }
            int n = 0;
            return n;
        }
        finally {
            vars.release();
        }
    }

    @Override
    public int collideWith(Collidable other, CollisionResults results) {
        if (other instanceof Ray) {
            Ray ray = (Ray)other;
            return this.collideWithRay(ray, results);
        }
        if (other instanceof BoundingVolume) {
            if (this.intersects((BoundingVolume)other)) {
                CollisionResult r = new CollisionResult();
                results.addCollision(r);
                return 1;
            }
            return 0;
        }
        throw new UnsupportedCollisionException("With: " + other.getClass().getSimpleName());
    }

    @Override
    public int collideWith(Collidable other) {
        if (other instanceof Ray) {
            Ray ray = (Ray)other;
            return this.collideWithRay(ray);
        }
        if (other instanceof BoundingVolume) {
            return this.intersects((BoundingVolume)other) ? 1 : 0;
        }
        throw new UnsupportedCollisionException("With: " + other.getClass().getSimpleName());
    }

    @Override
    public boolean contains(Vector3f point) {
        return FastMath.abs(this.center.x - point.x) < this.xExtent && FastMath.abs(this.center.y - point.y) < this.yExtent && FastMath.abs(this.center.z - point.z) < this.zExtent;
    }

    @Override
    public boolean intersects(Vector3f point) {
        return FastMath.abs(this.center.x - point.x) <= this.xExtent && FastMath.abs(this.center.y - point.y) <= this.yExtent && FastMath.abs(this.center.z - point.z) <= this.zExtent;
    }

    @Override
    public float distanceToEdge(Vector3f point) {
        float delta;
        TempVars vars = TempVars.get();
        Vector3f closest = vars.vect1;
        point.subtract(this.center, closest);
        float sqrDistance = 0.0f;
        if (closest.x < - this.xExtent) {
            delta = closest.x + this.xExtent;
            sqrDistance += delta * delta;
            closest.x = - this.xExtent;
        } else if (closest.x > this.xExtent) {
            delta = closest.x - this.xExtent;
            sqrDistance += delta * delta;
            closest.x = this.xExtent;
        }
        if (closest.y < - this.yExtent) {
            delta = closest.y + this.yExtent;
            sqrDistance += delta * delta;
            closest.y = - this.yExtent;
        } else if (closest.y > this.yExtent) {
            delta = closest.y - this.yExtent;
            sqrDistance += delta * delta;
            closest.y = this.yExtent;
        }
        if (closest.z < - this.zExtent) {
            delta = closest.z + this.zExtent;
            sqrDistance += delta * delta;
            closest.z = - this.zExtent;
        } else if (closest.z > this.zExtent) {
            delta = closest.z - this.zExtent;
            sqrDistance += delta * delta;
            closest.z = this.zExtent;
        }
        vars.release();
        return FastMath.sqrt(sqrDistance);
    }

    private boolean clip(float denom, float numer, float[] t) {
        if (denom > 0.0f) {
            float newT = numer / denom;
            if (newT > t[1]) {
                return false;
            }
            if (newT > t[0]) {
                t[0] = newT;
            }
            return true;
        }
        if (denom < 0.0f) {
            float newT = numer / denom;
            if (newT < t[0]) {
                return false;
            }
            if (newT < t[1]) {
                t[1] = newT;
            }
            return true;
        }
        return (double)numer <= 0.0;
    }

    public Vector3f getExtent(Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        store.set(this.xExtent, this.yExtent, this.zExtent);
        return store;
    }

    public float getXExtent() {
        return this.xExtent;
    }

    public float getYExtent() {
        return this.yExtent;
    }

    public float getZExtent() {
        return this.zExtent;
    }

    public void setXExtent(float xExtent) {
        if (xExtent < 0.0f) {
            throw new IllegalArgumentException();
        }
        this.xExtent = xExtent;
    }

    public Vector3f getMin(Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        store.set(this.center).subtractLocal(this.xExtent, this.yExtent, this.zExtent);
        return store;
    }

    public Vector3f getMax(Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        store.set(this.center).addLocal(this.xExtent, this.yExtent, this.zExtent);
        return store;
    }

    public void setMinMax(Vector3f min, Vector3f max) {
        this.center.set(max).addLocal(min).multLocal(0.5f);
        this.xExtent = FastMath.abs(max.x - this.center.x);
        this.yExtent = FastMath.abs(max.y - this.center.y);
        this.zExtent = FastMath.abs(max.z - this.center.z);
    }

    @Override
    public float getVolume() {
        return 8.0f * this.xExtent * this.yExtent * this.zExtent;
    }

}

