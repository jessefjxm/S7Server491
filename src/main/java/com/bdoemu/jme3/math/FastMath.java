/*
 * Decompiled with CFR 0_121.
 */
package com.bdoemu.jme3.math;

import com.bdoemu.jme3.math.Vector2f;
import com.bdoemu.jme3.math.Vector3f;
import java.util.Random;

public final class FastMath {
    public static final double DBL_EPSILON = 2.220446049250313E-16;
    public static final float FLT_EPSILON = 1.1920929E-7f;
    public static final float ZERO_TOLERANCE = 1.0E-4f;
    public static final float ONE_THIRD = 0.33333334f;
    public static final float PI = 3.1415927f;
    public static final float TWO_PI = 6.2831855f;
    public static final float HALF_PI = 1.5707964f;
    public static final float QUARTER_PI = 0.7853982f;
    public static final float INV_PI = 0.31830987f;
    public static final float INV_TWO_PI = 0.15915494f;
    public static final float DEG_TO_RAD = 0.017453292f;
    public static final float RAD_TO_DEG = 57.295776f;
    public static final Random rand = new Random(System.currentTimeMillis());

    private FastMath() {
    }

    public static boolean isPowerOfTwo(int number) {
        return number > 0 && (number & number - 1) == 0;
    }

    public static int nearestPowerOfTwo(int number) {
        --number;
        number |= number >> 1;
        number |= number >> 2;
        number |= number >> 4;
        number |= number >> 8;
        number |= number >> 16;
        return number += ++number == 0 ? 1 : 0;
    }

    public static float interpolateLinear(float scale, float startValue, float endValue) {
        if (startValue == endValue) {
            return startValue;
        }
        if (scale <= 0.0f) {
            return startValue;
        }
        if (scale >= 1.0f) {
            return endValue;
        }
        return (1.0f - scale) * startValue + scale * endValue;
    }

    public static Vector3f interpolateLinear(float scale, Vector3f startValue, Vector3f endValue, Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        store.x = FastMath.interpolateLinear(scale, startValue.x, endValue.x);
        store.y = FastMath.interpolateLinear(scale, startValue.y, endValue.y);
        store.z = FastMath.interpolateLinear(scale, startValue.z, endValue.z);
        return store;
    }

    public static Vector3f interpolateLinear(float scale, Vector3f startValue, Vector3f endValue) {
        return FastMath.interpolateLinear(scale, startValue, endValue, null);
    }

    public static float extrapolateLinear(float scale, float startValue, float endValue) {
        return (1.0f - scale) * startValue + scale * endValue;
    }

    public static Vector3f extrapolateLinear(float scale, Vector3f startValue, Vector3f endValue, Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        store.x = FastMath.extrapolateLinear(scale, startValue.x, endValue.x);
        store.y = FastMath.extrapolateLinear(scale, startValue.y, endValue.y);
        store.z = FastMath.extrapolateLinear(scale, startValue.z, endValue.z);
        return store;
    }

    public static Vector3f extrapolateLinear(float scale, Vector3f startValue, Vector3f endValue) {
        return FastMath.extrapolateLinear(scale, startValue, endValue, null);
    }

    public static float interpolateCatmullRom(float u, float T, float p0, float p1, float p2, float p3) {
        float c1 = p1;
        float c2 = -1.0f * T * p0 + T * p2;
        float c3 = 2.0f * T * p0 + (T - 3.0f) * p1 + (3.0f - 2.0f * T) * p2 + (- T) * p3;
        float c4 = (- T) * p0 + (2.0f - T) * p1 + (T - 2.0f) * p2 + T * p3;
        return ((c4 * u + c3) * u + c2) * u + c1;
    }

    public static Vector3f interpolateCatmullRom(float u, float T, Vector3f p0, Vector3f p1, Vector3f p2, Vector3f p3, Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        store.x = FastMath.interpolateCatmullRom(u, T, p0.x, p1.x, p2.x, p3.x);
        store.y = FastMath.interpolateCatmullRom(u, T, p0.y, p1.y, p2.y, p3.y);
        store.z = FastMath.interpolateCatmullRom(u, T, p0.z, p1.z, p2.z, p3.z);
        return store;
    }

    public static Vector3f interpolateCatmullRom(float u, float T, Vector3f p0, Vector3f p1, Vector3f p2, Vector3f p3) {
        return FastMath.interpolateCatmullRom(u, T, p0, p1, p2, p3, null);
    }

    public static float interpolateBezier(float u, float p0, float p1, float p2, float p3) {
        float oneMinusU = 1.0f - u;
        float oneMinusU2 = oneMinusU * oneMinusU;
        float u2 = u * u;
        return p0 * oneMinusU2 * oneMinusU + 3.0f * p1 * u * oneMinusU2 + 3.0f * p2 * u2 * oneMinusU + p3 * u2 * u;
    }

    public static Vector3f interpolateBezier(float u, Vector3f p0, Vector3f p1, Vector3f p2, Vector3f p3, Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        store.x = FastMath.interpolateBezier(u, p0.x, p1.x, p2.x, p3.x);
        store.y = FastMath.interpolateBezier(u, p0.y, p1.y, p2.y, p3.y);
        store.z = FastMath.interpolateBezier(u, p0.z, p1.z, p2.z, p3.z);
        return store;
    }

    public static Vector3f interpolateBezier(float u, Vector3f p0, Vector3f p1, Vector3f p2, Vector3f p3) {
        return FastMath.interpolateBezier(u, p0, p1, p2, p3, null);
    }

    public static float getCatmullRomP1toP2Length(Vector3f p0, Vector3f p1, Vector3f p2, Vector3f p3, float startRange, float endRange, float curveTension) {
        float len;
        float l2;
        float l1;
        float epsilon = 0.001f;
        float middleValue = (startRange + endRange) * 0.5f;
        Vector3f start = p1.clone();
        if (startRange != 0.0f) {
            FastMath.interpolateCatmullRom(startRange, curveTension, p0, p1, p2, p3, start);
        }
        Vector3f end = p2.clone();
        if (endRange != 1.0f) {
            FastMath.interpolateCatmullRom(endRange, curveTension, p0, p1, p2, p3, end);
        }
        Vector3f middle = FastMath.interpolateCatmullRom(middleValue, curveTension, p0, p1, p2, p3);
        float l = end.subtract(start).length();
        if (l + epsilon < (len = (l1 = middle.subtract(start).length()) + (l2 = end.subtract(middle).length()))) {
            l1 = FastMath.getCatmullRomP1toP2Length(p0, p1, p2, p3, startRange, middleValue, curveTension);
            l2 = FastMath.getCatmullRomP1toP2Length(p0, p1, p2, p3, middleValue, endRange, curveTension);
        }
        l = l1 + l2;
        return l;
    }

    public static float getBezierP1toP2Length(Vector3f p0, Vector3f p1, Vector3f p2, Vector3f p3) {
        float delta = 0.02f;
        float result = 0.0f;
        Vector3f v1 = p0.clone();
        Vector3f v2 = new Vector3f();
        for (float t = 0.0f; t <= 1.0f; t += delta) {
            FastMath.interpolateBezier(t, p0, p1, p2, p3, v2);
            result += v1.subtractLocal(v2).length();
            v1.set(v2);
        }
        return result;
    }

    public static float acos(float fValue) {
        if (-1.0f < fValue) {
            if (fValue < 1.0f) {
                return (float)Math.acos(fValue);
            }
            return 0.0f;
        }
        return 3.1415927f;
    }

    public static float asin(float fValue) {
        if (-1.0f < fValue) {
            if (fValue < 1.0f) {
                return (float)Math.asin(fValue);
            }
            return 1.5707964f;
        }
        return -1.5707964f;
    }

    public static float atan(float fValue) {
        return (float)Math.atan(fValue);
    }

    public static float atan2(float fY, float fX) {
        return (float)Math.atan2(fY, fX);
    }

    public static float ceil(float fValue) {
        return (float)Math.ceil(fValue);
    }

    public static float cos(float v) {
        return (float)Math.cos(v);
    }

    public static float sin(float v) {
        return (float)Math.sin(v);
    }

    public static float exp(float fValue) {
        return (float)Math.exp(fValue);
    }

    public static float abs(float fValue) {
        if (fValue < 0.0f) {
            return - fValue;
        }
        return fValue;
    }

    public static float floor(float fValue) {
        return (float)Math.floor(fValue);
    }

    public static float invSqrt(float fValue) {
        return (float)(1.0 / Math.sqrt(fValue));
    }

    public static float fastInvSqrt(float x) {
        float xhalf = 0.5f * x;
        int i = Float.floatToIntBits(x);
        i = 1597463174 - (i >> 1);
        x = Float.intBitsToFloat(i);
        x *= 1.5f - xhalf * x * x;
        return x;
    }

    public static float log(float fValue) {
        return (float)Math.log(fValue);
    }

    public static float log(float value, float base) {
        return (float)(Math.log(value) / Math.log(base));
    }

    public static float pow(float fBase, float fExponent) {
        return (float)Math.pow(fBase, fExponent);
    }

    public static float sqr(float fValue) {
        return fValue * fValue;
    }

    public static float sqrt(float fValue) {
        return (float)Math.sqrt(fValue);
    }

    public static float tan(float fValue) {
        return (float)Math.tan(fValue);
    }

    public static int sign(int iValue) {
        if (iValue > 0) {
            return 1;
        }
        if (iValue < 0) {
            return -1;
        }
        return 0;
    }

    public static float sign(float fValue) {
        return Math.signum(fValue);
    }

    public static int counterClockwise(Vector2f p0, Vector2f p1, Vector2f p2) {
        float dx1 = p1.x - p0.x;
        float dy2 = p2.y - p0.y;
        float dy1 = p1.y - p0.y;
        float dx2 = p2.x - p0.x;
        if (dx1 * dy2 > dy1 * dx2) {
            return 1;
        }
        if (dx1 * dy2 < dy1 * dx2) {
            return -1;
        }
        if (dx1 * dx2 < 0.0f || dy1 * dy2 < 0.0f) {
            return -1;
        }
        if (dx1 * dx1 + dy1 * dy1 < dx2 * dx2 + dy2 * dy2) {
            return 1;
        }
        return 0;
    }

    public static int pointInsideTriangle(Vector2f t0, Vector2f t1, Vector2f t2, Vector2f p) {
        int val1 = FastMath.counterClockwise(t0, t1, p);
        if (val1 == 0) {
            return 1;
        }
        int val2 = FastMath.counterClockwise(t1, t2, p);
        if (val2 == 0) {
            return 1;
        }
        if (val2 != val1) {
            return 0;
        }
        int val3 = FastMath.counterClockwise(t2, t0, p);
        if (val3 == 0) {
            return 1;
        }
        if (val3 != val1) {
            return 0;
        }
        return val3;
    }

    public static Vector3f computeNormal(Vector3f v1, Vector3f v2, Vector3f v3) {
        Vector3f a1 = v1.subtract(v2);
        Vector3f a2 = v3.subtract(v2);
        return a2.crossLocal(a1).normalizeLocal();
    }

    public static float determinant(double m00, double m01, double m02, double m03, double m10, double m11, double m12, double m13, double m20, double m21, double m22, double m23, double m30, double m31, double m32, double m33) {
        double det01 = m20 * m31 - m21 * m30;
        double det02 = m20 * m32 - m22 * m30;
        double det03 = m20 * m33 - m23 * m30;
        double det12 = m21 * m32 - m22 * m31;
        double det13 = m21 * m33 - m23 * m31;
        double det23 = m22 * m33 - m23 * m32;
        return (float)(m00 * (m11 * det23 - m12 * det13 + m13 * det12) - m01 * (m10 * det23 - m12 * det03 + m13 * det02) + m02 * (m10 * det13 - m11 * det03 + m13 * det01) - m03 * (m10 * det12 - m11 * det02 + m12 * det01));
    }

    public static float nextRandomFloat() {
        return rand.nextFloat();
    }

    public static int nextRandomInt(int min, int max) {
        return (int)(FastMath.nextRandomFloat() * (float)(max - min + 1)) + min;
    }

    public static int nextRandomInt() {
        return rand.nextInt();
    }

    public static Vector3f sphericalToCartesian(Vector3f sphereCoords, Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        store.y = sphereCoords.x * FastMath.sin(sphereCoords.z);
        float a = sphereCoords.x * FastMath.cos(sphereCoords.z);
        store.x = a * FastMath.cos(sphereCoords.y);
        store.z = a * FastMath.sin(sphereCoords.y);
        return store;
    }

    public static Vector3f cartesianToSpherical(Vector3f cartCoords, Vector3f store) {
        float x;
        if (store == null) {
            store = new Vector3f();
        }
        if ((x = cartCoords.x) == 0.0f) {
            x = 1.1920929E-7f;
        }
        store.x = FastMath.sqrt(x * x + cartCoords.y * cartCoords.y + cartCoords.z * cartCoords.z);
        store.y = FastMath.atan(cartCoords.z / x);
        if (x < 0.0f) {
            store.y += 3.1415927f;
        }
        store.z = FastMath.asin(cartCoords.y / store.x);
        return store;
    }

    public static Vector3f sphericalToCartesianZ(Vector3f sphereCoords, Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        store.z = sphereCoords.x * FastMath.sin(sphereCoords.z);
        float a = sphereCoords.x * FastMath.cos(sphereCoords.z);
        store.x = a * FastMath.cos(sphereCoords.y);
        store.y = a * FastMath.sin(sphereCoords.y);
        return store;
    }

    public static Vector3f cartesianZToSpherical(Vector3f cartCoords, Vector3f store) {
        float x;
        if (store == null) {
            store = new Vector3f();
        }
        if ((x = cartCoords.x) == 0.0f) {
            x = 1.1920929E-7f;
        }
        store.x = FastMath.sqrt(x * x + cartCoords.y * cartCoords.y + cartCoords.z * cartCoords.z);
        store.z = FastMath.atan(cartCoords.z / x);
        if (x < 0.0f) {
            store.z += 3.1415927f;
        }
        store.y = FastMath.asin(cartCoords.y / store.x);
        return store;
    }

    public static float normalize(float val, float min, float max) {
        if (Float.isInfinite(val) || Float.isNaN(val)) {
            return 0.0f;
        }
        float range = max - min;
        while (val > max) {
            val -= range;
        }
        while (val < min) {
            val += range;
        }
        return val;
    }

    public static float copysign(float x, float y) {
        if (y >= 0.0f && x <= 0.0f) {
            return - x;
        }
        if (y < 0.0f && x >= 0.0f) {
            return - x;
        }
        return x;
    }

    public static float clamp(float input, float min, float max) {
        return input < min ? min : (input > max ? max : input);
    }

    public static float saturate(float input) {
        return FastMath.clamp(input, 0.0f, 1.0f);
    }

    public static boolean approximateEquals(float a, float b) {
        if (a == b) {
            return true;
        }
        return FastMath.abs(a - b) / Math.max(FastMath.abs(a), FastMath.abs(b)) <= 1.0E-5f;
    }

    public static float convertHalfToFloat(short half) {
        switch (half) {
            case 0: {
                return 0.0f;
            }
            case (short) 32768: {
                return -0.0f;
            }
            case 31744: {
                return Float.POSITIVE_INFINITY;
            }
            case (short) 64512: {
                return Float.NEGATIVE_INFINITY;
            }
        }
        return Float.intBitsToFloat((half & 32768) << 16 | (half & 31744) + 114688 << 13 | (half & 1023) << 13);
    }

    public static short convertFloatToHalf(float flt) {
        if (Float.isNaN(flt)) {
            throw new UnsupportedOperationException("NaN to half conversion not supported!");
        }
        if (flt == Float.POSITIVE_INFINITY) {
            return 31744;
        }
        if (flt == Float.NEGATIVE_INFINITY) {
            return -1024;
        }
        if (flt == 0.0f) {
            return 0;
        }
        if (flt == -0.0f) {
            return -32768;
        }
        if (flt > 65504.0f) {
            return 31743;
        }
        if (flt < -65504.0f) {
            return -1025;
        }
        if (flt > 0.0f && flt < 3.054738E-5f) {
            return 1;
        }
        if (flt < 0.0f && flt > -3.054738E-5f) {
            return -32767;
        }
        int f = Float.floatToIntBits(flt);
        return (short)(f >> 16 & 32768 | (f & 2139095040) - 939524096 >> 13 & 31744 | f >> 13 & 1023);
    }
}

