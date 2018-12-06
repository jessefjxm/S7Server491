/*
 * Decompiled with CFR 0_121.
 */
package com.bdoemu.jme3.math;

import com.bdoemu.jme3.math.Matrix4f;
import com.bdoemu.jme3.math.Quaternion;
import com.bdoemu.jme3.math.Vector3f;

public final class Transform
implements Cloneable {
    static final long serialVersionUID = 1;
    public static final Transform IDENTITY = new Transform();
    private Quaternion rot = new Quaternion();
    private Vector3f translation = new Vector3f();
    private Vector3f scale = new Vector3f(1.0f, 1.0f, 1.0f);

    public Transform(Vector3f translation, Quaternion rot) {
        this.translation.set(translation);
        this.rot.set(rot);
    }

    public Transform(Vector3f translation, Quaternion rot, Vector3f scale) {
        this(translation, rot);
        this.scale.set(scale);
    }

    public Transform(Vector3f translation) {
        this(translation, Quaternion.IDENTITY);
    }

    public Transform(Quaternion rot) {
        this(Vector3f.ZERO, rot);
    }

    public Transform() {
        this(Vector3f.ZERO, Quaternion.IDENTITY);
    }

    public Transform setRotation(Quaternion rot) {
        this.rot.set(rot);
        return this;
    }

    public Transform setTranslation(Vector3f trans) {
        this.translation.set(trans);
        return this;
    }

    public Vector3f getTranslation() {
        return this.translation;
    }

    public Transform setScale(Vector3f scale) {
        this.scale.set(scale);
        return this;
    }

    public Transform setScale(float scale) {
        this.scale.set(scale, scale, scale);
        return this;
    }

    public Vector3f getScale() {
        return this.scale;
    }

    public Vector3f getTranslation(Vector3f trans) {
        if (trans == null) {
            trans = new Vector3f();
        }
        trans.set(this.translation);
        return trans;
    }

    public Quaternion getRotation(Quaternion quat) {
        if (quat == null) {
            quat = new Quaternion();
        }
        quat.set(this.rot);
        return quat;
    }

    public Quaternion getRotation() {
        return this.rot;
    }

    public Vector3f getScale(Vector3f scale) {
        if (scale == null) {
            scale = new Vector3f();
        }
        scale.set(this.scale);
        return scale;
    }

    public void interpolateTransforms(Transform t1, Transform t2, float delta) {
        this.rot.slerp(t1.rot, t2.rot, delta);
        this.translation.interpolateLocal(t1.translation, t2.translation, delta);
        this.scale.interpolateLocal(t1.scale, t2.scale, delta);
    }

    public Transform combineWithParent(Transform parent) {
        this.scale.multLocal(parent.scale);
        parent.rot.mult(this.rot, this.rot);
        this.translation.multLocal(parent.scale);
        parent.rot.multLocal(this.translation).addLocal(parent.translation);
        return this;
    }

    public Transform setTranslation(float x, float y, float z) {
        this.translation.set(x, y, z);
        return this;
    }

    public Transform setScale(float x, float y, float z) {
        this.scale.set(x, y, z);
        return this;
    }

    public Vector3f transformVector(Vector3f in, Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        return this.rot.mult(store.set(in).multLocal(this.scale), store).addLocal(this.translation);
    }

    public Vector3f transformInverseVector(Vector3f in, Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        in.subtract(this.translation, store);
        this.rot.inverse().mult(store, store);
        store.divideLocal(this.scale);
        return store;
    }

    public Matrix4f toTransformMatrix() {
        Matrix4f trans = new Matrix4f();
        trans.setTranslation(this.translation);
        trans.setRotationQuaternion(this.rot);
        trans.setScale(this.scale);
        return trans;
    }

    public void fromTransformMatrix(Matrix4f mat) {
        this.translation.set(mat.toTranslationVector());
        this.rot.set(mat.toRotationQuat());
        this.scale.set(mat.toScaleVector());
    }

    public Transform invert() {
        Transform t = new Transform();
        t.fromTransformMatrix(this.toTransformMatrix().invertLocal());
        return t;
    }

    public void loadIdentity() {
        this.translation.set(0.0f, 0.0f, 0.0f);
        this.scale.set(1.0f, 1.0f, 1.0f);
        this.rot.set(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.rot.hashCode();
        hash = 89 * hash + this.translation.hashCode();
        hash = 89 * hash + this.scale.hashCode();
        return hash;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Transform other = (Transform)obj;
        return this.translation.equals(other.translation) && this.scale.equals(other.scale) && this.rot.equals(other.rot);
    }

    public String toString() {
        return this.getClass().getSimpleName() + "[ " + this.translation.x + ", " + this.translation.y + ", " + this.translation.z + "]\n[ " + this.rot.x + ", " + this.rot.y + ", " + this.rot.z + ", " + this.rot.w + "]\n[ " + this.scale.x + " , " + this.scale.y + ", " + this.scale.z + "]";
    }

    public Transform set(Transform matrixQuat) {
        this.translation.set(matrixQuat.translation);
        this.rot.set(matrixQuat.rot);
        this.scale.set(matrixQuat.scale);
        return this;
    }

    public Transform clone() {
        try {
            Transform tq = (Transform)super.clone();
            tq.rot = this.rot.clone();
            tq.scale = this.scale.clone();
            tq.translation = this.translation.clone();
            return tq;
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

