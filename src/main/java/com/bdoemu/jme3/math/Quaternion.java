/*
 * Decompiled with CFR 0_121.
 */
package com.bdoemu.jme3.math;

import com.bdoemu.jme3.math.FastMath;
import com.bdoemu.jme3.math.Matrix3f;
import com.bdoemu.jme3.math.Matrix4f;
import com.bdoemu.jme3.math.Vector3f;
import com.bdoemu.jme3.util.TempVars;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.logging.Logger;

public final class Quaternion
implements Cloneable {
    static final long serialVersionUID = 1;
    private static final Logger logger = Logger.getLogger(Quaternion.class.getName());
    public static final Quaternion IDENTITY = new Quaternion();
    public static final Quaternion DIRECTION_Z = new Quaternion();
    public static final Quaternion ZERO = new Quaternion(0.0f, 0.0f, 0.0f, 0.0f);
    protected float x;
    protected float y;
    protected float z;
    protected float w;

    public Quaternion() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 1.0f;
    }

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }

    public float getW() {
        return this.w;
    }

    public Quaternion set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Quaternion set(Quaternion q) {
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
        this.w = q.w;
        return this;
    }

    public Quaternion(float[] angles) {
        this.fromAngles(angles);
    }

    public Quaternion(Quaternion q1, Quaternion q2, float interp) {
        this.slerp(q1, q2, interp);
    }

    public Quaternion(Quaternion q) {
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
        this.w = q.w;
    }

    public void loadIdentity() {
        this.z = 0.0f;
        this.y = 0.0f;
        this.x = 0.0f;
        this.w = 1.0f;
    }

    public boolean isIdentity() {
        if (this.x == 0.0f && this.y == 0.0f && this.z == 0.0f && this.w == 1.0f) {
            return true;
        }
        return false;
    }

    public Quaternion fromAngles(float[] angles) {
        if (angles.length != 3) {
            throw new IllegalArgumentException("Angles array must have three elements");
        }
        return this.fromAngles(angles[0], angles[1], angles[2]);
    }

    public Quaternion fromAngles(float xAngle, float yAngle, float zAngle) {
        float angle = zAngle * 0.5f;
        float sinZ = FastMath.sin(angle);
        float cosZ = FastMath.cos(angle);
        angle = yAngle * 0.5f;
        float sinY = FastMath.sin(angle);
        float cosY = FastMath.cos(angle);
        angle = xAngle * 0.5f;
        float sinX = FastMath.sin(angle);
        float cosX = FastMath.cos(angle);
        float cosYXcosZ = cosY * cosZ;
        float sinYXsinZ = sinY * sinZ;
        float cosYXsinZ = cosY * sinZ;
        float sinYXcosZ = sinY * cosZ;
        this.w = cosYXcosZ * cosX - sinYXsinZ * sinX;
        this.x = cosYXcosZ * sinX + sinYXsinZ * cosX;
        this.y = sinYXcosZ * cosX + cosYXsinZ * sinX;
        this.z = cosYXsinZ * cosX - sinYXcosZ * sinX;
        this.normalizeLocal();
        return this;
    }

    public float[] toAngles(float[] angles) {
        if (angles == null) {
            angles = new float[3];
        } else if (angles.length != 3) {
            throw new IllegalArgumentException("Angles array must have three elements");
        }
        float sqw = this.w * this.w;
        float sqx = this.x * this.x;
        float sqy = this.y * this.y;
        float sqz = this.z * this.z;
        float unit = sqx + sqy + sqz + sqw;
        float test = this.x * this.y + this.z * this.w;
        if ((double)test > 0.499 * (double)unit) {
            angles[1] = 2.0f * FastMath.atan2(this.x, this.w);
            angles[2] = 1.5707964f;
            angles[0] = 0.0f;
        } else if ((double)test < -0.499 * (double)unit) {
            angles[1] = -2.0f * FastMath.atan2(this.x, this.w);
            angles[2] = -1.5707964f;
            angles[0] = 0.0f;
        } else {
            angles[1] = FastMath.atan2(2.0f * this.y * this.w - 2.0f * this.x * this.z, sqx - sqy - sqz + sqw);
            angles[2] = FastMath.asin(2.0f * test / unit);
            angles[0] = FastMath.atan2(2.0f * this.x * this.w - 2.0f * this.y * this.z, - sqx + sqy - sqz + sqw);
        }
        return angles;
    }

    public Quaternion fromRotationMatrix(Matrix3f matrix) {
        return this.fromRotationMatrix(matrix.m00, matrix.m01, matrix.m02, matrix.m10, matrix.m11, matrix.m12, matrix.m20, matrix.m21, matrix.m22);
    }

    public Quaternion fromRotationMatrix(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
        float t;
        float lengthSquared = m00 * m00 + m10 * m10 + m20 * m20;
        if (lengthSquared != 1.0f && lengthSquared != 0.0f) {
            lengthSquared = 1.0f / FastMath.sqrt(lengthSquared);
            m00 *= lengthSquared;
            m10 *= lengthSquared;
            m20 *= lengthSquared;
        }
        if ((lengthSquared = m01 * m01 + m11 * m11 + m21 * m21) != 1.0f && lengthSquared != 0.0f) {
            lengthSquared = 1.0f / FastMath.sqrt(lengthSquared);
            m01 *= lengthSquared;
            m11 *= lengthSquared;
            m21 *= lengthSquared;
        }
        if ((lengthSquared = m02 * m02 + m12 * m12 + m22 * m22) != 1.0f && lengthSquared != 0.0f) {
            lengthSquared = 1.0f / FastMath.sqrt(lengthSquared);
            m02 *= lengthSquared;
            m12 *= lengthSquared;
            m22 *= lengthSquared;
        }
        if ((t = m00 + m11 + m22) >= 0.0f) {
            float s = FastMath.sqrt(t + 1.0f);
            this.w = 0.5f * s;
            s = 0.5f / s;
            this.x = (m21 - m12) * s;
            this.y = (m02 - m20) * s;
            this.z = (m10 - m01) * s;
        } else if (m00 > m11 && m00 > m22) {
            float s = FastMath.sqrt(1.0f + m00 - m11 - m22);
            this.x = s * 0.5f;
            s = 0.5f / s;
            this.y = (m10 + m01) * s;
            this.z = (m02 + m20) * s;
            this.w = (m21 - m12) * s;
        } else if (m11 > m22) {
            float s = FastMath.sqrt(1.0f + m11 - m00 - m22);
            this.y = s * 0.5f;
            s = 0.5f / s;
            this.x = (m10 + m01) * s;
            this.z = (m21 + m12) * s;
            this.w = (m02 - m20) * s;
        } else {
            float s = FastMath.sqrt(1.0f + m22 - m00 - m11);
            this.z = s * 0.5f;
            s = 0.5f / s;
            this.x = (m02 + m20) * s;
            this.y = (m21 + m12) * s;
            this.w = (m10 - m01) * s;
        }
        return this;
    }

    public Matrix3f toRotationMatrix() {
        Matrix3f matrix = new Matrix3f();
        return this.toRotationMatrix(matrix);
    }

    public Matrix3f toRotationMatrix(Matrix3f result) {
        float norm = this.norm();
        float s = norm == 1.0f ? 2.0f : (norm > 0.0f ? 2.0f / norm : 0.0f);
        float xs = this.x * s;
        float ys = this.y * s;
        float zs = this.z * s;
        float xx = this.x * xs;
        float xy = this.x * ys;
        float xz = this.x * zs;
        float xw = this.w * xs;
        float yy = this.y * ys;
        float yz = this.y * zs;
        float yw = this.w * ys;
        float zz = this.z * zs;
        float zw = this.w * zs;
        result.m00 = 1.0f - (yy + zz);
        result.m01 = xy - zw;
        result.m02 = xz + yw;
        result.m10 = xy + zw;
        result.m11 = 1.0f - (xx + zz);
        result.m12 = yz - xw;
        result.m20 = xz - yw;
        result.m21 = yz + xw;
        result.m22 = 1.0f - (xx + yy);
        return result;
    }

    public Matrix4f toRotationMatrix(Matrix4f result) {
        TempVars tempv = TempVars.get();
        Vector3f originalScale = tempv.vect1;
        result.toScaleVector(originalScale);
        result.setScale(1.0f, 1.0f, 1.0f);
        float norm = this.norm();
        float s = norm == 1.0f ? 2.0f : (norm > 0.0f ? 2.0f / norm : 0.0f);
        float xs = this.x * s;
        float ys = this.y * s;
        float zs = this.z * s;
        float xx = this.x * xs;
        float xy = this.x * ys;
        float xz = this.x * zs;
        float xw = this.w * xs;
        float yy = this.y * ys;
        float yz = this.y * zs;
        float yw = this.w * ys;
        float zz = this.z * zs;
        float zw = this.w * zs;
        result.m00 = 1.0f - (yy + zz);
        result.m01 = xy - zw;
        result.m02 = xz + yw;
        result.m10 = xy + zw;
        result.m11 = 1.0f - (xx + zz);
        result.m12 = yz - xw;
        result.m20 = xz - yw;
        result.m21 = yz + xw;
        result.m22 = 1.0f - (xx + yy);
        result.setScale(originalScale);
        tempv.release();
        return result;
    }

    public Vector3f getRotationColumn(int i) {
        return this.getRotationColumn(i, null);
    }

    public Vector3f getRotationColumn(int i, Vector3f store) {
        float norm;
        if (store == null) {
            store = new Vector3f();
        }
        if ((norm = this.norm()) != 1.0f) {
            norm = FastMath.invSqrt(norm);
        }
        float xx = this.x * this.x * norm;
        float xy = this.x * this.y * norm;
        float xz = this.x * this.z * norm;
        float xw = this.x * this.w * norm;
        float yy = this.y * this.y * norm;
        float yz = this.y * this.z * norm;
        float yw = this.y * this.w * norm;
        float zz = this.z * this.z * norm;
        float zw = this.z * this.w * norm;
        switch (i) {
            case 0: {
                store.x = 1.0f - 2.0f * (yy + zz);
                store.y = 2.0f * (xy + zw);
                store.z = 2.0f * (xz - yw);
                break;
            }
            case 1: {
                store.x = 2.0f * (xy - zw);
                store.y = 1.0f - 2.0f * (xx + zz);
                store.z = 2.0f * (yz + xw);
                break;
            }
            case 2: {
                store.x = 2.0f * (xz + yw);
                store.y = 2.0f * (yz - xw);
                store.z = 1.0f - 2.0f * (xx + yy);
                break;
            }
            default: {
                logger.warning("Invalid column index.");
                throw new IllegalArgumentException("Invalid column index. " + i);
            }
        }
        return store;
    }

    public Quaternion fromAngleAxis(float angle, Vector3f axis) {
        Vector3f normAxis = axis.normalize();
        this.fromAngleNormalAxis(angle, normAxis);
        return this;
    }

    public Quaternion fromAngleNormalAxis(float angle, Vector3f axis) {
        if (axis.x == 0.0f && axis.y == 0.0f && axis.z == 0.0f) {
            this.loadIdentity();
        } else {
            float halfAngle = 0.5f * angle;
            float sin = FastMath.sin(halfAngle);
            this.w = FastMath.cos(halfAngle);
            this.x = sin * axis.x;
            this.y = sin * axis.y;
            this.z = sin * axis.z;
        }
        return this;
    }

    public float toAngleAxis(Vector3f axisStore) {
        float angle;
        float sqrLength = this.x * this.x + this.y * this.y + this.z * this.z;
        if (sqrLength == 0.0f) {
            angle = 0.0f;
            if (axisStore != null) {
                axisStore.x = 1.0f;
                axisStore.y = 0.0f;
                axisStore.z = 0.0f;
            }
        } else {
            angle = 2.0f * FastMath.acos(this.w);
            if (axisStore != null) {
                float invLength = 1.0f / FastMath.sqrt(sqrLength);
                axisStore.x = this.x * invLength;
                axisStore.y = this.y * invLength;
                axisStore.z = this.z * invLength;
            }
        }
        return angle;
    }

    public Quaternion slerp(Quaternion q1, Quaternion q2, float t) {
        if (q1.x == q2.x && q1.y == q2.y && q1.z == q2.z && q1.w == q2.w) {
            this.set(q1);
            return this;
        }
        float result = q1.x * q2.x + q1.y * q2.y + q1.z * q2.z + q1.w * q2.w;
        if (result < 0.0f) {
            q2.x = - q2.x;
            q2.y = - q2.y;
            q2.z = - q2.z;
            q2.w = - q2.w;
            result = - result;
        }
        float scale0 = 1.0f - t;
        float scale1 = t;
        if (1.0f - result > 0.1f) {
            float theta = FastMath.acos(result);
            float invSinTheta = 1.0f / FastMath.sin(theta);
            scale0 = FastMath.sin((1.0f - t) * theta) * invSinTheta;
            scale1 = FastMath.sin(t * theta) * invSinTheta;
        }
        this.x = scale0 * q1.x + scale1 * q2.x;
        this.y = scale0 * q1.y + scale1 * q2.y;
        this.z = scale0 * q1.z + scale1 * q2.z;
        this.w = scale0 * q1.w + scale1 * q2.w;
        return this;
    }

    public void slerp(Quaternion q2, float changeAmnt) {
        if (this.x == q2.x && this.y == q2.y && this.z == q2.z && this.w == q2.w) {
            return;
        }
        float result = this.x * q2.x + this.y * q2.y + this.z * q2.z + this.w * q2.w;
        if (result < 0.0f) {
            q2.x = - q2.x;
            q2.y = - q2.y;
            q2.z = - q2.z;
            q2.w = - q2.w;
            result = - result;
        }
        float scale0 = 1.0f - changeAmnt;
        float scale1 = changeAmnt;
        if (1.0f - result > 0.1f) {
            float theta = FastMath.acos(result);
            float invSinTheta = 1.0f / FastMath.sin(theta);
            scale0 = FastMath.sin((1.0f - changeAmnt) * theta) * invSinTheta;
            scale1 = FastMath.sin(changeAmnt * theta) * invSinTheta;
        }
        this.x = scale0 * this.x + scale1 * q2.x;
        this.y = scale0 * this.y + scale1 * q2.y;
        this.z = scale0 * this.z + scale1 * q2.z;
        this.w = scale0 * this.w + scale1 * q2.w;
    }

    public void nlerp(Quaternion q2, float blend) {
        float dot = this.dot(q2);
        float blendI = 1.0f - blend;
        if (dot < 0.0f) {
            this.x = blendI * this.x - blend * q2.x;
            this.y = blendI * this.y - blend * q2.y;
            this.z = blendI * this.z - blend * q2.z;
            this.w = blendI * this.w - blend * q2.w;
        } else {
            this.x = blendI * this.x + blend * q2.x;
            this.y = blendI * this.y + blend * q2.y;
            this.z = blendI * this.z + blend * q2.z;
            this.w = blendI * this.w + blend * q2.w;
        }
        this.normalizeLocal();
    }

    public Quaternion add(Quaternion q) {
        return new Quaternion(this.x + q.x, this.y + q.y, this.z + q.z, this.w + q.w);
    }

    public Quaternion addLocal(Quaternion q) {
        this.x += q.x;
        this.y += q.y;
        this.z += q.z;
        this.w += q.w;
        return this;
    }

    public Quaternion subtract(Quaternion q) {
        return new Quaternion(this.x - q.x, this.y - q.y, this.z - q.z, this.w - q.w);
    }

    public Quaternion subtractLocal(Quaternion q) {
        this.x -= q.x;
        this.y -= q.y;
        this.z -= q.z;
        this.w -= q.w;
        return this;
    }

    public Quaternion mult(Quaternion q) {
        return this.mult(q, null);
    }

    public Quaternion mult(Quaternion q, Quaternion res) {
        if (res == null) {
            res = new Quaternion();
        }
        float qw = q.w;
        float qx = q.x;
        float qy = q.y;
        float qz = q.z;
        res.x = this.x * qw + this.y * qz - this.z * qy + this.w * qx;
        res.y = (- this.x) * qz + this.y * qw + this.z * qx + this.w * qy;
        res.z = this.x * qy - this.y * qx + this.z * qw + this.w * qz;
        res.w = (- this.x) * qx - this.y * qy - this.z * qz + this.w * qw;
        return res;
    }

    public void apply(Matrix3f matrix) {
        float oldX = this.x;
        float oldY = this.y;
        float oldZ = this.z;
        float oldW = this.w;
        this.fromRotationMatrix(matrix);
        float tempX = this.x;
        float tempY = this.y;
        float tempZ = this.z;
        float tempW = this.w;
        this.x = oldX * tempW + oldY * tempZ - oldZ * tempY + oldW * tempX;
        this.y = (- oldX) * tempZ + oldY * tempW + oldZ * tempX + oldW * tempY;
        this.z = oldX * tempY - oldY * tempX + oldZ * tempW + oldW * tempZ;
        this.w = (- oldX) * tempX - oldY * tempY - oldZ * tempZ + oldW * tempW;
    }

    public Quaternion fromAxes(Vector3f[] axis) {
        if (axis.length != 3) {
            throw new IllegalArgumentException("Axis array must have three elements");
        }
        return this.fromAxes(axis[0], axis[1], axis[2]);
    }

    public Quaternion fromAxes(Vector3f xAxis, Vector3f yAxis, Vector3f zAxis) {
        return this.fromRotationMatrix(xAxis.x, yAxis.x, zAxis.x, xAxis.y, yAxis.y, zAxis.y, xAxis.z, yAxis.z, zAxis.z);
    }

    public void toAxes(Vector3f[] axis) {
        Matrix3f tempMat = this.toRotationMatrix();
        axis[0] = tempMat.getColumn(0, axis[0]);
        axis[1] = tempMat.getColumn(1, axis[1]);
        axis[2] = tempMat.getColumn(2, axis[2]);
    }

    public Vector3f mult(Vector3f v) {
        return this.mult(v, null);
    }

    public Vector3f multLocal(Vector3f v) {
        float tempX = this.w * this.w * v.x + 2.0f * this.y * this.w * v.z - 2.0f * this.z * this.w * v.y + this.x * this.x * v.x + 2.0f * this.y * this.x * v.y + 2.0f * this.z * this.x * v.z - this.z * this.z * v.x - this.y * this.y * v.x;
        float tempY = 2.0f * this.x * this.y * v.x + this.y * this.y * v.y + 2.0f * this.z * this.y * v.z + 2.0f * this.w * this.z * v.x - this.z * this.z * v.y + this.w * this.w * v.y - 2.0f * this.x * this.w * v.z - this.x * this.x * v.y;
        v.z = 2.0f * this.x * this.z * v.x + 2.0f * this.y * this.z * v.y + this.z * this.z * v.z - 2.0f * this.w * this.y * v.x - this.y * this.y * v.z + 2.0f * this.w * this.x * v.y - this.x * this.x * v.z + this.w * this.w * v.z;
        v.x = tempX;
        v.y = tempY;
        return v;
    }

    public Quaternion multLocal(Quaternion q) {
        float x1 = this.x * q.w + this.y * q.z - this.z * q.y + this.w * q.x;
        float y1 = (- this.x) * q.z + this.y * q.w + this.z * q.x + this.w * q.y;
        float z1 = this.x * q.y - this.y * q.x + this.z * q.w + this.w * q.z;
        this.w = (- this.x) * q.x - this.y * q.y - this.z * q.z + this.w * q.w;
        this.x = x1;
        this.y = y1;
        this.z = z1;
        return this;
    }

    public Quaternion multLocal(float qx, float qy, float qz, float qw) {
        float x1 = this.x * qw + this.y * qz - this.z * qy + this.w * qx;
        float y1 = (- this.x) * qz + this.y * qw + this.z * qx + this.w * qy;
        float z1 = this.x * qy - this.y * qx + this.z * qw + this.w * qz;
        this.w = (- this.x) * qx - this.y * qy - this.z * qz + this.w * qw;
        this.x = x1;
        this.y = y1;
        this.z = z1;
        return this;
    }

    public Vector3f mult(Vector3f v, Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        if (v.x == 0.0f && v.y == 0.0f && v.z == 0.0f) {
            store.set(0.0f, 0.0f, 0.0f);
        } else {
            float vx = v.x;
            float vy = v.y;
            float vz = v.z;
            store.x = this.w * this.w * vx + 2.0f * this.y * this.w * vz - 2.0f * this.z * this.w * vy + this.x * this.x * vx + 2.0f * this.y * this.x * vy + 2.0f * this.z * this.x * vz - this.z * this.z * vx - this.y * this.y * vx;
            store.y = 2.0f * this.x * this.y * vx + this.y * this.y * vy + 2.0f * this.z * this.y * vz + 2.0f * this.w * this.z * vx - this.z * this.z * vy + this.w * this.w * vy - 2.0f * this.x * this.w * vz - this.x * this.x * vy;
            store.z = 2.0f * this.x * this.z * vx + 2.0f * this.y * this.z * vy + this.z * this.z * vz - 2.0f * this.w * this.y * vx - this.y * this.y * vz + 2.0f * this.w * this.x * vy - this.x * this.x * vz + this.w * this.w * vz;
        }
        return store;
    }

    public Quaternion mult(float scalar) {
        return new Quaternion(scalar * this.x, scalar * this.y, scalar * this.z, scalar * this.w);
    }

    public Quaternion multLocal(float scalar) {
        this.w *= scalar;
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        return this;
    }

    public float dot(Quaternion q) {
        return this.w * q.w + this.x * q.x + this.y * q.y + this.z * q.z;
    }

    public float norm() {
        return this.w * this.w + this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public Quaternion normalizeLocal() {
        float n = FastMath.invSqrt(this.norm());
        this.x *= n;
        this.y *= n;
        this.z *= n;
        this.w *= n;
        return this;
    }

    public Quaternion inverse() {
        float norm = this.norm();
        if ((double)norm > 0.0) {
            float invNorm = 1.0f / norm;
            return new Quaternion((- this.x) * invNorm, (- this.y) * invNorm, (- this.z) * invNorm, this.w * invNorm);
        }
        return null;
    }

    public Quaternion inverseLocal() {
        float norm = this.norm();
        if ((double)norm > 0.0) {
            float invNorm = 1.0f / norm;
            this.x *= - invNorm;
            this.y *= - invNorm;
            this.z *= - invNorm;
            this.w *= invNorm;
        }
        return this;
    }

    public void negate() {
        this.x *= -1.0f;
        this.y *= -1.0f;
        this.z *= -1.0f;
        this.w *= -1.0f;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }

    public boolean equals(Object o) {
        if (!(o instanceof Quaternion)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        Quaternion comp = (Quaternion)o;
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
        hash = 37 * hash + Float.floatToIntBits(this.x);
        hash = 37 * hash + Float.floatToIntBits(this.y);
        hash = 37 * hash + Float.floatToIntBits(this.z);
        hash = 37 * hash + Float.floatToIntBits(this.w);
        return hash;
    }

    public void readExternal(ObjectInput in) throws IOException {
        this.x = in.readFloat();
        this.y = in.readFloat();
        this.z = in.readFloat();
        this.w = in.readFloat();
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(this.x);
        out.writeFloat(this.y);
        out.writeFloat(this.z);
        out.writeFloat(this.w);
    }

    public void lookAt(Vector3f direction, Vector3f up) {
        TempVars vars = TempVars.get();
        vars.vect3.set(direction).normalizeLocal();
        vars.vect1.set(up).crossLocal(direction).normalizeLocal();
        vars.vect2.set(direction).crossLocal(vars.vect1).normalizeLocal();
        this.fromAxes(vars.vect1, vars.vect2, vars.vect3);
        vars.release();
    }

    public Quaternion opposite() {
        return this.opposite(null);
    }

    public Quaternion opposite(Quaternion store) {
        if (store == null) {
            store = new Quaternion();
        }
        Vector3f axis = new Vector3f();
        float angle = this.toAngleAxis(axis);
        store.fromAngleAxis(3.1415927f + angle, axis);
        return store;
    }

    public Quaternion oppositeLocal() {
        return this.opposite(this);
    }

    public Quaternion clone() {
        try {
            return (Quaternion)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    static {
        DIRECTION_Z.fromAxes(Vector3f.UNIT_X, Vector3f.UNIT_Y, Vector3f.UNIT_Z);
    }
}

