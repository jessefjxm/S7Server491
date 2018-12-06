/*
 * Decompiled with CFR 0_121.
 */
package com.bdoemu.jme3.bounding;

import com.bdoemu.jme3.bounding.BoundingBox;
import com.bdoemu.jme3.collision.Collidable;
import com.bdoemu.jme3.collision.CollisionResults;
import com.bdoemu.jme3.math.Matrix4f;
import com.bdoemu.jme3.math.Plane;
import com.bdoemu.jme3.math.Ray;
import com.bdoemu.jme3.math.Transform;
import com.bdoemu.jme3.math.Vector3f;
import com.bdoemu.jme3.util.TempVars;

public abstract class BoundingVolume
implements Cloneable,
Collidable {
    protected int checkPlane = 0;
    protected Vector3f center = new Vector3f();

    public BoundingVolume() {
    }

    public BoundingVolume(Vector3f center) {
        this.center.set(center);
    }

    public int getCheckPlane() {
        return this.checkPlane;
    }

    public final void setCheckPlane(int value) {
        this.checkPlane = value;
    }

    public abstract Type getType();

    public final BoundingVolume transform(Transform trans) {
        return this.transform(trans, null);
    }

    public abstract BoundingVolume transform(Transform var1, BoundingVolume var2);

    public abstract BoundingVolume transform(Matrix4f var1, BoundingVolume var2);

    public abstract Plane.Side whichSide(Plane var1);

    public abstract void computeFromPoints(float[] var1);

    public abstract BoundingVolume merge(BoundingVolume var1);

    public abstract BoundingVolume mergeLocal(BoundingVolume var1);

    public abstract BoundingVolume clone(BoundingVolume var1);

    public final Vector3f getCenter() {
        return this.center;
    }

    public final Vector3f getCenter(Vector3f store) {
        store.set(this.center);
        return store;
    }

    public final void setCenter(Vector3f newCenter) {
        this.center.set(newCenter);
    }

    public final void setCenter(float x, float y, float z) {
        this.center.set(x, y, z);
    }

    public final float distanceTo(Vector3f point) {
        return this.center.distance(point);
    }

    public final float distanceSquaredTo(Vector3f point) {
        return this.center.distanceSquared(point);
    }

    public abstract float distanceToEdge(Vector3f var1);

    public abstract boolean intersects(BoundingVolume var1);

    public abstract boolean intersects(Ray var1);

    public abstract boolean intersectsBoundingBox(BoundingBox var1);

    public abstract boolean contains(Vector3f var1);

    public abstract boolean intersects(Vector3f var1);

    public abstract float getVolume();

    public BoundingVolume clone() {
        try {
            BoundingVolume clone = (BoundingVolume)super.clone();
            clone.center = this.center.clone();
            return clone;
        }
        catch (CloneNotSupportedException ex) {
            throw new AssertionError();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int collideWith(Collidable other) {
        TempVars tempVars = TempVars.get();
        try {
            CollisionResults tempResults = tempVars.collisionResults;
            tempResults.clear();
            int n = this.collideWith(other, tempResults);
            return n;
        }
        finally {
            tempVars.release();
        }
    }

    public static enum Type {
        Sphere,
        AABB,
        Capsule;
        

        private Type() {
        }
    }

}

