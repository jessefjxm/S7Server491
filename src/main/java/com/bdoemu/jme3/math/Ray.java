/*
 * Decompiled with CFR 0_121.
 */
package com.bdoemu.jme3.math;

import com.bdoemu.jme3.bounding.BoundingVolume;
import com.bdoemu.jme3.collision.Collidable;
import com.bdoemu.jme3.collision.CollisionResults;
import com.bdoemu.jme3.collision.UnsupportedCollisionException;
import com.bdoemu.jme3.math.Vector3f;
import com.bdoemu.jme3.util.TempVars;

public final class Ray
implements Cloneable,
Collidable {
    static final long serialVersionUID = 1;
    public Vector3f origin = new Vector3f();
    public Vector3f direction = new Vector3f(0.0f, 0.0f, 1.0f);
    public float limit = Float.POSITIVE_INFINITY;

    public Ray(Vector3f origin, Vector3f direction) {
        this.setOrigin(origin);
        this.setDirection(direction);
    }

    private boolean intersects(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f store, boolean doPlanar, boolean quad) {
        Vector3f edge2;
        float diffDotNorm;
        float sign;
        float dirDotEdge1xDiff;
        TempVars vars = TempVars.get();
        Vector3f tempVa = vars.vect1;
        Vector3f tempVb = vars.vect2;
        Vector3f tempVc = vars.vect3;
        Vector3f tempVd = vars.vect4;
        Vector3f diff = this.origin.subtract(v0, tempVa);
        Vector3f edge1 = v1.subtract(v0, tempVb);
        Vector3f norm = edge1.cross(edge2 = v2.subtract(v0, tempVc), tempVd);
        float dirDotNorm = this.direction.dot(norm);
        if (dirDotNorm > 1.1920929E-7f) {
            sign = 1.0f;
        } else if (dirDotNorm < -1.1920929E-7f) {
            sign = -1.0f;
            dirDotNorm = - dirDotNorm;
        } else {
            vars.release();
            return false;
        }
        float dirDotDiffxEdge2 = sign * this.direction.dot(diff.cross(edge2, edge2));
        if (dirDotDiffxEdge2 >= 0.0f && (dirDotEdge1xDiff = sign * this.direction.dot(edge1.crossLocal(diff))) >= 0.0f && (!quad ? dirDotDiffxEdge2 + dirDotEdge1xDiff <= dirDotNorm : dirDotEdge1xDiff <= dirDotNorm) && (diffDotNorm = (- sign) * diff.dot(norm)) >= 0.0f) {
            vars.release();
            if (store == null) {
                return true;
            }
            float inv = 1.0f / dirDotNorm;
            float t = diffDotNorm * inv;
            if (!doPlanar) {
                store.set(this.origin).addLocal(this.direction.x * t, this.direction.y * t, this.direction.z * t);
            } else {
                float w1 = dirDotDiffxEdge2 * inv;
                float w2 = dirDotEdge1xDiff * inv;
                store.set(t, w1, w2);
            }
            return true;
        }
        vars.release();
        return false;
    }

    public float intersects(Vector3f v0, Vector3f v1, Vector3f v2) {
        float dirDotEdge1xDiff;
        float sign;
        float diffDotNorm;
        float edge1X = v1.x - v0.x;
        float edge1Y = v1.y - v0.y;
        float edge1Z = v1.z - v0.z;
        float edge2X = v2.x - v0.x;
        float edge2Y = v2.y - v0.y;
        float edge2Z = v2.z - v0.z;
        float normX = edge1Y * edge2Z - edge1Z * edge2Y;
        float normY = edge1Z * edge2X - edge1X * edge2Z;
        float normZ = edge1X * edge2Y - edge1Y * edge2X;
        float dirDotNorm = this.direction.x * normX + this.direction.y * normY + this.direction.z * normZ;
        float diffX = this.origin.x - v0.x;
        float diffY = this.origin.y - v0.y;
        float diffZ = this.origin.z - v0.z;
        if (dirDotNorm > 1.1920929E-7f) {
            sign = 1.0f;
        } else if (dirDotNorm < -1.1920929E-7f) {
            sign = -1.0f;
            dirDotNorm = - dirDotNorm;
        } else {
            return Float.POSITIVE_INFINITY;
        }
        float diffEdge2X = diffY * edge2Z - diffZ * edge2Y;
        float diffEdge2Y = diffZ * edge2X - diffX * edge2Z;
        float diffEdge2Z = diffX * edge2Y - diffY * edge2X;
        float dirDotDiffxEdge2 = sign * (this.direction.x * diffEdge2X + this.direction.y * diffEdge2Y + this.direction.z * diffEdge2Z);
        if (dirDotDiffxEdge2 >= 0.0f && (dirDotEdge1xDiff = sign * (this.direction.x * (diffEdge2X = edge1Y * diffZ - edge1Z * diffY) + this.direction.y * (diffEdge2Y = edge1Z * diffX - edge1X * diffZ) + this.direction.z * (diffEdge2Z = edge1X * diffY - edge1Y * diffX))) >= 0.0f && dirDotDiffxEdge2 + dirDotEdge1xDiff <= dirDotNorm && (diffDotNorm = (- sign) * (diffX * normX + diffY * normY + diffZ * normZ)) >= 0.0f) {
            float inv = 1.0f / dirDotNorm;
            float t = diffDotNorm * inv;
            return t;
        }
        return Float.POSITIVE_INFINITY;
    }

    @Override
    public int collideWith(Collidable other, CollisionResults results) {
        if (other instanceof BoundingVolume) {
            BoundingVolume bv = (BoundingVolume)other;
            return bv.collideWith(this, results);
        }
        throw new UnsupportedCollisionException();
    }

    public Vector3f getOrigin() {
        return this.origin;
    }

    public void setOrigin(Vector3f origin) {
        this.origin.set(origin);
    }

    public float getLimit() {
        return this.limit;
    }

    public void setLimit(float limit) {
        this.limit = limit;
    }

    public Vector3f getDirection() {
        return this.direction;
    }

    public void setDirection(Vector3f direction) {
        assert (direction.isUnitVector());
        this.direction.set(direction);
    }

    public void set(Ray source) {
        this.origin.set(source.getOrigin());
        this.direction.set(source.getDirection());
    }

    public String toString() {
        return this.getClass().getSimpleName() + " [Origin: " + this.origin + ", Direction: " + this.direction + "]";
    }

    public Ray clone() {
        try {
            Ray r = (Ray)super.clone();
            r.direction = this.direction.clone();
            r.origin = this.origin.clone();
            return r;
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

