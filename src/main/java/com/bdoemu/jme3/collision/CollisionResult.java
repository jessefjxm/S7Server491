/*
 * Decompiled with CFR 0_121.
 */
package com.bdoemu.jme3.collision;

import com.bdoemu.jme3.math.Vector3f;

public class CollisionResult
implements Comparable<CollisionResult> {
    private Vector3f contactPoint;
    private float distance;

    public CollisionResult(Vector3f contactPoint, float distance) {
        this.contactPoint = contactPoint;
        this.distance = distance;
    }

    public CollisionResult() {
    }

    @Override
    public int compareTo(CollisionResult other) {
        return Float.compare(this.distance, other.distance);
    }

    public boolean equals(Object obj) {
        if (obj instanceof CollisionResult) {
            return ((CollisionResult)obj).compareTo(this) == 0;
        }
        return super.equals(obj);
    }

    public int hashCode() {
        return Float.floatToIntBits(this.distance);
    }

    public Vector3f getContactPoint() {
        return this.contactPoint;
    }

    public float getDistance() {
        return this.distance;
    }
}

