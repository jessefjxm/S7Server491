/*
 * Decompiled with CFR 0_121.
 */
package com.bdoemu.jme3.collision.bih;

import com.bdoemu.jme3.bounding.BoundingBox;
import com.bdoemu.jme3.bounding.BoundingVolume;
import com.bdoemu.jme3.collision.Collidable;
import com.bdoemu.jme3.collision.CollisionResult;
import com.bdoemu.jme3.collision.CollisionResults;
import com.bdoemu.jme3.collision.UnsupportedCollisionException;
import com.bdoemu.jme3.collision.bih.BIHNode;
import com.bdoemu.jme3.math.Ray;
import com.bdoemu.jme3.math.Vector3f;
import com.bdoemu.jme3.scene.CollisionData;
import com.bdoemu.jme3.util.TempVars;

public class BIHTree
implements CollisionData {
    private BIHNode root;
    private float[] pointData;
    private transient float[] bihSwapTmp = new float[9];

    public BIHTree(float[] vertices, int[] indexes) {
        int pointSize = indexes.length / 3 * 3;
        this.pointData = new float[pointSize * 3];
        int p = 0;
        for (int i = 0; i < pointSize; i += 3) {
            int vert = indexes[i] * 3;
            this.pointData[p++] = vertices[vert++];
            this.pointData[p++] = vertices[vert++];
            this.pointData[p++] = vertices[vert];
            vert = indexes[i + 1] * 3;
            this.pointData[p++] = vertices[vert++];
            this.pointData[p++] = vertices[vert++];
            this.pointData[p++] = vertices[vert];
            vert = indexes[i + 2] * 3;
            this.pointData[p++] = vertices[vert++];
            this.pointData[p++] = vertices[vert++];
            this.pointData[p++] = vertices[vert];
        }
        BoundingBox sceneBbox = this.createBox(0, indexes.length / 3 - 1);
        this.root = this.createNode(0, indexes.length / 3 - 1, sceneBbox, 0);
    }

    private BoundingBox createBox(int l, int r) {
        TempVars vars = TempVars.get();
        Vector3f min = vars.vect1.set(new Vector3f(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY));
        Vector3f max = vars.vect2.set(new Vector3f(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY));
        Vector3f v1 = vars.vect3;
        Vector3f v2 = vars.vect4;
        Vector3f v3 = vars.vect5;
        for (int i = l; i <= r; ++i) {
            this.getTriangle(i, v1, v2, v3);
            BoundingBox.checkMinMax(min, max, v1);
            BoundingBox.checkMinMax(min, max, v2);
            BoundingBox.checkMinMax(min, max, v3);
        }
        BoundingBox bbox = new BoundingBox(min, max);
        vars.release();
        return bbox;
    }

    private int sortTriangles(int l, int r, float split, int axis) {
        int pivot = l;
        int j = r;
        TempVars vars = TempVars.get();
        Vector3f v1 = vars.vect1;
        Vector3f v2 = vars.vect2;
        Vector3f v3 = vars.vect3;
        while (pivot <= j) {
            this.getTriangle(pivot, v1, v2, v3);
            v1.addLocal(v2).addLocal(v3).multLocal(0.33333334f);
            if (v1.get(axis) > split) {
                this.swapTriangles(pivot, j);
                --j;
                continue;
            }
            ++pivot;
        }
        vars.release();
        pivot = pivot == l && j < pivot ? j : pivot;
        return pivot;
    }

    private void setMinMax(BoundingBox bbox, boolean doMin, int axis, float value) {
        Vector3f min = bbox.getMin(null);
        Vector3f max = bbox.getMax(null);
        if (doMin) {
            min.set(axis, value);
        } else {
            max.set(axis, value);
        }
        bbox.setMinMax(min, max);
    }

    private float getMinMax(BoundingBox bbox, boolean doMin, int axis) {
        if (doMin) {
            return bbox.getMin(null).get(axis);
        }
        return bbox.getMax(null).get(axis);
    }

    private BIHNode createNode(int l, int r, BoundingBox nodeBbox, int depth) {
        int pivot;
        float split;
        if (r - l < 21 || depth > 100) {
            return new BIHNode(l, r);
        }
        BoundingBox currentBox = this.createBox(l, r);
        Vector3f exteriorExt = nodeBbox.getExtent(null);
        Vector3f interiorExt = currentBox.getExtent(null);
        exteriorExt.subtractLocal(interiorExt);
        int axis = 0;
        axis = exteriorExt.x > exteriorExt.y ? (exteriorExt.x > exteriorExt.z ? 0 : 2) : (exteriorExt.y > exteriorExt.z ? 1 : 2);
        if (exteriorExt.equals(Vector3f.ZERO)) {
            axis = 0;
        }
        if ((pivot = this.sortTriangles(l, r, split = currentBox.getCenter().get(axis), axis)) == l || pivot == r) {
            pivot = (r + l) / 2;
        }
        if (pivot < l) {
            BoundingBox rbbox = new BoundingBox(currentBox);
            this.setMinMax(rbbox, true, axis, split);
            return this.createNode(l, r, rbbox, depth + 1);
        }
        if (pivot > r) {
            BoundingBox lbbox = new BoundingBox(currentBox);
            this.setMinMax(lbbox, false, axis, split);
            return this.createNode(l, r, lbbox, depth + 1);
        }
        BIHNode node = new BIHNode(axis);
        BoundingBox lbbox = new BoundingBox(currentBox);
        this.setMinMax(lbbox, false, axis, split);
        node.setLeftPlane(this.getMinMax(this.createBox(l, Math.max(l, pivot - 1)), false, axis));
        node.setLeftChild(this.createNode(l, Math.max(l, pivot - 1), lbbox, depth + 1));
        BoundingBox rbbox = new BoundingBox(currentBox);
        this.setMinMax(rbbox, true, axis, split);
        node.setRightPlane(this.getMinMax(this.createBox(pivot, r), true, axis));
        node.setRightChild(this.createNode(pivot, r, rbbox, depth + 1));
        return node;
    }

    public void getTriangle(int index, Vector3f v1, Vector3f v2, Vector3f v3) {
        int pointIndex = index * 9;
        v1.x = this.pointData[pointIndex++];
        v1.y = this.pointData[pointIndex++];
        v1.z = this.pointData[pointIndex++];
        v2.x = this.pointData[pointIndex++];
        v2.y = this.pointData[pointIndex++];
        v2.z = this.pointData[pointIndex++];
        v3.x = this.pointData[pointIndex++];
        v3.y = this.pointData[pointIndex++];
        v3.z = this.pointData[pointIndex++];
    }

    public void swapTriangles(int index1, int index2) {
        int p1 = index1 * 9;
        int p2 = index2 * 9;
        System.arraycopy(this.pointData, p1, this.bihSwapTmp, 0, 9);
        System.arraycopy(this.pointData, p2, this.pointData, p1, 9);
        System.arraycopy(this.bihSwapTmp, 0, this.pointData, p2, 9);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int collideWithRay(Ray r, BoundingVolume worldBound, CollisionResults results) {
        TempVars vars = TempVars.get();
        try {
            CollisionResults boundResults = vars.collisionResults;
            boundResults.clear();
            worldBound.collideWith(r, boundResults);
            if (boundResults.size() > 0) {
                float tMin = boundResults.getClosestCollision().getDistance();
                float tMax = boundResults.getFarthestCollision().getDistance();
                if (tMax <= 0.0f) {
                    tMax = Float.POSITIVE_INFINITY;
                } else if (tMin == tMax) {
                    tMin = 0.0f;
                }
                if (tMin <= 0.0f) {
                    tMin = 0.0f;
                }
                if (r.getLimit() < Float.POSITIVE_INFINITY && tMin > (tMax = Math.min(tMax, r.getLimit()))) {
                    int n = 0;
                    return n;
                }
                int n = this.root.intersectWhere(r, this, tMin, tMax, results);
                return n;
            }
            int tMin = 0;
            return tMin;
        }
        finally {
            vars.release();
        }
    }

    @Override
    public int collideWith(Collidable other, BoundingVolume worldBound, CollisionResults results) {
        if (other instanceof Ray) {
            Ray ray = (Ray)other;
            return this.collideWithRay(ray, worldBound, results);
        }
        throw new UnsupportedCollisionException("Collidable:" + other);
    }
}

