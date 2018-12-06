/*
 * Decompiled with CFR 0_121.
 */
package com.bdoemu.jme3.math;

import com.bdoemu.jme3.math.Vector3f;

public class Plane
implements Cloneable {
    protected Vector3f normal = new Vector3f();
    protected float constant;

    public Plane() {
    }

    public Plane(Vector3f normal, float constant) {
        if (normal == null) {
            throw new IllegalArgumentException("normal cannot be null");
        }
        this.normal.set(normal);
        this.constant = constant;
    }

    public void setNormal(Vector3f normal) {
        if (normal == null) {
            throw new IllegalArgumentException("normal cannot be null");
        }
        this.normal.set(normal);
    }

    public Vector3f getNormal() {
        return this.normal;
    }

    public float pseudoDistance(Vector3f point) {
        return this.normal.dot(point) - this.constant;
    }

    public void setPlanePoints(Vector3f v1, Vector3f v2, Vector3f v3) {
        this.normal.set(v2).subtractLocal(v1);
        this.normal.crossLocal(v3.x - v1.x, v3.y - v1.y, v3.z - v1.z).normalizeLocal();
        this.constant = this.normal.dot(v1);
    }

    public String toString() {
        return this.getClass().getSimpleName() + " [Normal: " + this.normal + " - Constant: " + this.constant + "]";
    }

    public Plane clone() {
        try {
            Plane p = (Plane)super.clone();
            p.normal = this.normal.clone();
            return p;
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public static enum Side {
        None,
        Positive,
        Negative;
        

        private Side() {
        }
    }

}

