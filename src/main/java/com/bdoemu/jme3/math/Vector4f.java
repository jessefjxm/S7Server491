/*
 * Decompiled with CFR 0_121.
 */
package com.bdoemu.jme3.math;

import com.bdoemu.jme3.math.FastMath;
import java.util.logging.Logger;

public final class Vector4f
implements Cloneable {
    static final long serialVersionUID = 1;
    private static final Logger logger = Logger.getLogger(Vector4f.class.getName());
    public static final Vector4f ZERO = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
    public static final Vector4f NAN = new Vector4f(Float.NaN, Float.NaN, Float.NaN, Float.NaN);
    public static final Vector4f UNIT_X = new Vector4f(1.0f, 0.0f, 0.0f, 0.0f);
    public static final Vector4f UNIT_Y = new Vector4f(0.0f, 1.0f, 0.0f, 0.0f);
    public static final Vector4f UNIT_Z = new Vector4f(0.0f, 0.0f, 1.0f, 0.0f);
    public static final Vector4f UNIT_W = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
    public static final Vector4f UNIT_XYZW = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Vector4f POSITIVE_INFINITY = new Vector4f(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    public static final Vector4f NEGATIVE_INFINITY = new Vector4f(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
    public float x;
    public float y;
    public float z;
    public float w;

    public Vector4f() {
        this.w = 0.0f;
        this.z = 0.0f;
        this.y = 0.0f;
        this.x = 0.0f;
    }

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4f(Vector4f copy) {
        this.set(copy);
    }

    public Vector4f set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Vector4f set(Vector4f vect) {
        this.x = vect.x;
        this.y = vect.y;
        this.z = vect.z;
        this.w = vect.w;
        return this;
    }

    public Vector4f add(Vector4f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        return new Vector4f(this.x + vec.x, this.y + vec.y, this.z + vec.z, this.w + vec.w);
    }

    public Vector4f add(Vector4f vec, Vector4f result) {
        result.x = this.x + vec.x;
        result.y = this.y + vec.y;
        result.z = this.z + vec.z;
        result.w = this.w + vec.w;
        return result;
    }

    public Vector4f addLocal(Vector4f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
        this.w += vec.w;
        return this;
    }

    public Vector4f add(float addX, float addY, float addZ, float addW) {
        return new Vector4f(this.x + addX, this.y + addY, this.z + addZ, this.w + addW);
    }

    public Vector4f addLocal(float addX, float addY, float addZ, float addW) {
        this.x += addX;
        this.y += addY;
        this.z += addZ;
        this.w += addW;
        return this;
    }

    public Vector4f scaleAdd(float scalar, Vector4f add) {
        this.x = this.x * scalar + add.x;
        this.y = this.y * scalar + add.y;
        this.z = this.z * scalar + add.z;
        this.w = this.w * scalar + add.w;
        return this;
    }

    public Vector4f scaleAdd(float scalar, Vector4f mult, Vector4f add) {
        this.x = mult.x * scalar + add.x;
        this.y = mult.y * scalar + add.y;
        this.z = mult.z * scalar + add.z;
        this.w = mult.w * scalar + add.w;
        return this;
    }

    public float dot(Vector4f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, 0 returned.");
            return 0.0f;
        }
        return this.x * vec.x + this.y * vec.y + this.z * vec.z + this.w * vec.w;
    }

    public Vector4f project(Vector4f other) {
        float n = this.dot(other);
        float d = other.lengthSquared();
        return new Vector4f(other).normalizeLocal().multLocal(n / d);
    }

    public boolean isUnitVector() {
        float len = this.length();
        return 0.99f < len && len < 1.01f;
    }

    public float length() {
        return FastMath.sqrt(this.lengthSquared());
    }

    public float lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }

    public float distanceSquared(Vector4f v) {
        double dx = this.x - v.x;
        double dy = this.y - v.y;
        double dz = this.z - v.z;
        double dw = this.w - v.w;
        return (float)(dx * dx + dy * dy + dz * dz + dw * dw);
    }

    public float distance(Vector4f v) {
        return FastMath.sqrt(this.distanceSquared(v));
    }

    public Vector4f mult(float scalar) {
        return new Vector4f(this.x * scalar, this.y * scalar, this.z * scalar, this.w * scalar);
    }

    public Vector4f mult(float scalar, Vector4f product) {
        if (null == product) {
            product = new Vector4f();
        }
        product.x = this.x * scalar;
        product.y = this.y * scalar;
        product.z = this.z * scalar;
        product.w = this.w * scalar;
        return product;
    }

    public Vector4f multLocal(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        this.w *= scalar;
        return this;
    }

    public Vector4f multLocal(Vector4f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        this.x *= vec.x;
        this.y *= vec.y;
        this.z *= vec.z;
        this.w *= vec.w;
        return this;
    }

    public Vector4f multLocal(float x, float y, float z, float w) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        this.w *= w;
        return this;
    }

    public Vector4f mult(Vector4f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        return this.mult(vec, null);
    }

    public Vector4f mult(Vector4f vec, Vector4f store) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        if (store == null) {
            store = new Vector4f();
        }
        return store.set(this.x * vec.x, this.y * vec.y, this.z * vec.z, this.w * vec.w);
    }

    public Vector4f divide(float scalar) {
        scalar = 1.0f / scalar;
        return new Vector4f(this.x * scalar, this.y * scalar, this.z * scalar, this.w * scalar);
    }

    public Vector4f divideLocal(float scalar) {
        scalar = 1.0f / scalar;
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        this.w *= scalar;
        return this;
    }

    public Vector4f divide(Vector4f scalar) {
        return new Vector4f(this.x / scalar.x, this.y / scalar.y, this.z / scalar.z, this.w / scalar.w);
    }

    public Vector4f divideLocal(Vector4f scalar) {
        this.x /= scalar.x;
        this.y /= scalar.y;
        this.z /= scalar.z;
        this.w /= scalar.w;
        return this;
    }

    public Vector4f negate() {
        return new Vector4f(- this.x, - this.y, - this.z, - this.w);
    }

    public Vector4f negateLocal() {
        this.x = - this.x;
        this.y = - this.y;
        this.z = - this.z;
        this.w = - this.w;
        return this;
    }

    public Vector4f subtract(Vector4f vec) {
        return new Vector4f(this.x - vec.x, this.y - vec.y, this.z - vec.z, this.w - vec.w);
    }

    public Vector4f subtractLocal(Vector4f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        this.x -= vec.x;
        this.y -= vec.y;
        this.z -= vec.z;
        this.w -= vec.w;
        return this;
    }

    public Vector4f subtract(Vector4f vec, Vector4f result) {
        if (result == null) {
            result = new Vector4f();
        }
        result.x = this.x - vec.x;
        result.y = this.y - vec.y;
        result.z = this.z - vec.z;
        result.w = this.w - vec.w;
        return result;
    }

    public Vector4f subtract(float subtractX, float subtractY, float subtractZ, float subtractW) {
        return new Vector4f(this.x - subtractX, this.y - subtractY, this.z - subtractZ, this.w - subtractW);
    }

    public Vector4f subtractLocal(float subtractX, float subtractY, float subtractZ, float subtractW) {
        this.x -= subtractX;
        this.y -= subtractY;
        this.z -= subtractZ;
        this.w -= subtractW;
        return this;
    }

    public Vector4f normalize() {
        float length = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
        if (length != 1.0f && length != 0.0f) {
            length = 1.0f / FastMath.sqrt(length);
            return new Vector4f(this.x * length, this.y * length, this.z * length, this.w * length);
        }
        return this.clone();
    }

    public Vector4f normalizeLocal() {
        float length = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
        if (length != 1.0f && length != 0.0f) {
            length = 1.0f / FastMath.sqrt(length);
            this.x *= length;
            this.y *= length;
            this.z *= length;
            this.w *= length;
        }
        return this;
    }

    public Vector4f maxLocal(Vector4f other) {
        this.x = other.x > this.x ? other.x : this.x;
        this.y = other.y > this.y ? other.y : this.y;
        this.z = other.z > this.z ? other.z : this.z;
        this.w = other.w > this.w ? other.w : this.w;
        return this;
    }

    public Vector4f minLocal(Vector4f other) {
        this.x = other.x < this.x ? other.x : this.x;
        this.y = other.y < this.y ? other.y : this.y;
        this.z = other.z < this.z ? other.z : this.z;
        this.w = other.w < this.w ? other.w : this.w;
        return this;
    }

    public Vector4f zero() {
        this.w = 0.0f;
        this.z = 0.0f;
        this.y = 0.0f;
        this.x = 0.0f;
        return this;
    }

    public float angleBetween(Vector4f otherVector) {
        float dotProduct = this.dot(otherVector);
        float angle = FastMath.acos(dotProduct);
        return angle;
    }

    public Vector4f interpolateLocal(Vector4f finalVec, float changeAmnt) {
        this.x = (1.0f - changeAmnt) * this.x + changeAmnt * finalVec.x;
        this.y = (1.0f - changeAmnt) * this.y + changeAmnt * finalVec.y;
        this.z = (1.0f - changeAmnt) * this.z + changeAmnt * finalVec.z;
        this.w = (1.0f - changeAmnt) * this.w + changeAmnt * finalVec.w;
        return this;
    }

    public Vector4f interpolateLocal(Vector4f beginVec, Vector4f finalVec, float changeAmnt) {
        this.x = (1.0f - changeAmnt) * beginVec.x + changeAmnt * finalVec.x;
        this.y = (1.0f - changeAmnt) * beginVec.y + changeAmnt * finalVec.y;
        this.z = (1.0f - changeAmnt) * beginVec.z + changeAmnt * finalVec.z;
        this.w = (1.0f - changeAmnt) * beginVec.w + changeAmnt * finalVec.w;
        return this;
    }

    public static boolean isValidVector(Vector4f vector) {
        if (vector == null) {
            return false;
        }
        if (Float.isNaN(vector.x) || Float.isNaN(vector.y) || Float.isNaN(vector.z) || Float.isNaN(vector.w)) {
            return false;
        }
        if (Float.isInfinite(vector.x) || Float.isInfinite(vector.y) || Float.isInfinite(vector.z) || Float.isInfinite(vector.w)) {
            return false;
        }
        return true;
    }

    public Vector4f clone() {
        try {
            return (Vector4f)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public float[] toArray(float[] floats) {
        if (floats == null) {
            floats = new float[]{this.x, this.y, this.z, this.w};
        }
        return floats;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Vector4f)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        Vector4f comp = (Vector4f)o;
        if (Float.compare(this.x, comp.x) != 0) {
            return false;
        }
        if (Float.compare(this.y, comp.y) != 0) {
            return false;
        }
        if (Float.compare(this.z, comp.z) != 0) {
            return false;
        }
        if (Float.compare(this.w, comp.w) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int hash = 37;
        hash += 37 * hash + Float.floatToIntBits(this.x);
        hash += 37 * hash + Float.floatToIntBits(this.y);
        hash += 37 * hash + Float.floatToIntBits(this.z);
        hash += 37 * hash + Float.floatToIntBits(this.w);
        return hash;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }

    public float getX() {
        return this.x;
    }

    public Vector4f setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return this.y;
    }

    public Vector4f setY(float y) {
        this.y = y;
        return this;
    }

    public float getZ() {
        return this.z;
    }

    public Vector4f setZ(float z) {
        this.z = z;
        return this;
    }

    public float getW() {
        return this.w;
    }

    public Vector4f setW(float w) {
        this.w = w;
        return this;
    }

    public float get(int index) {
        switch (index) {
            case 0: {
                return this.x;
            }
            case 1: {
                return this.y;
            }
            case 2: {
                return this.z;
            }
            case 3: {
                return this.w;
            }
        }
        throw new IllegalArgumentException("index must be either 0, 1, 2 or 3");
    }

    public void set(int index, float value) {
        switch (index) {
            case 0: {
                this.x = value;
                return;
            }
            case 1: {
                this.y = value;
                return;
            }
            case 2: {
                this.z = value;
                return;
            }
            case 3: {
                this.w = value;
                return;
            }
        }
        throw new IllegalArgumentException("index must be either 0, 1, 2 or 3");
    }
}

