/*
 * Decompiled with CFR 0_121.
 */
package com.bdoemu.jme3.collision.bih;

import com.bdoemu.jme3.collision.CollisionResult;
import com.bdoemu.jme3.collision.CollisionResults;
import com.bdoemu.jme3.collision.bih.BIHTree;
import com.bdoemu.jme3.math.Matrix4f;
import com.bdoemu.jme3.math.Ray;
import com.bdoemu.jme3.math.Vector3f;
import com.bdoemu.jme3.util.TempVars;
import java.util.ArrayList;

public final class BIHNode {
    private int leftIndex;
    private int rightIndex;
    private BIHNode left;
    private BIHNode right;
    private float leftPlane;
    private float rightPlane;
    private int axis;
    private static Matrix4f worldMatrix = new Matrix4f();

    public BIHNode(int l, int r) {
        this.leftIndex = l;
        this.rightIndex = r;
        this.axis = 3;
    }

    public BIHNode(int axis) {
        this.axis = axis;
    }

    public BIHNode getLeftChild() {
        return this.left;
    }

    public void setLeftChild(BIHNode left) {
        this.left = left;
    }

    public float getLeftPlane() {
        return this.leftPlane;
    }

    public void setLeftPlane(float leftPlane) {
        this.leftPlane = leftPlane;
    }

    public BIHNode getRightChild() {
        return this.right;
    }

    public void setRightChild(BIHNode right) {
        this.right = right;
    }

    public float getRightPlane() {
        return this.rightPlane;
    }

    public void setRightPlane(float rightPlane) {
        this.rightPlane = rightPlane;
    }

    public final int intersectWhere(Ray r, BIHTree tree, float sceneMin, float sceneMax, CollisionResults results) {
        TempVars vars = TempVars.get();
        ArrayList<BIHStackData> stack = vars.bihStack;
        stack.clear();
        Vector3f o = vars.vect1.set(r.getOrigin());
        Vector3f d = vars.vect2.set(r.getDirection());
        Matrix4f inv = vars.tempMat4.set(worldMatrix).invertLocal();
        inv.mult(r.getOrigin(), r.getOrigin());
        inv.multNormal(r.getDirection(), r.getDirection());
        float[] origins = new float[]{r.getOrigin().x, r.getOrigin().y, r.getOrigin().z};
        float[] invDirections = new float[]{1.0f / r.getDirection().x, 1.0f / r.getDirection().y, 1.0f / r.getDirection().z};
        r.getDirection().normalizeLocal();
        Vector3f v1 = vars.vect3;
        Vector3f v2 = vars.vect4;
        Vector3f v3 = vars.vect5;
        int cols = 0;
        stack.add(new BIHStackData(this, sceneMin, sceneMax));
        block0 : while (stack.size() > 0) {
            BIHStackData data = stack.remove(stack.size() - 1);
            BIHNode node = data.node;
            float tMin = data.min;
            float tMax = data.max;
            if (tMax < tMin) continue;
            while (node.axis != 3) {
                int a = node.axis;
                float origin = origins[a];
                float invDirection = invDirections[a];
                float tNearSplit = (node.leftPlane - origin) * invDirection;
                float tFarSplit = (node.rightPlane - origin) * invDirection;
                BIHNode nearNode = node.left;
                BIHNode farNode = node.right;
                if (invDirection < 0.0f) {
                    float tmpSplit = tNearSplit;
                    tNearSplit = tFarSplit;
                    tFarSplit = tmpSplit;
                    BIHNode tmpNode = nearNode;
                    nearNode = farNode;
                    farNode = tmpNode;
                }
                if (tMin > tNearSplit && tMax < tFarSplit) continue block0;
                if (tMin > tNearSplit) {
                    tMin = Math.max(tMin, tFarSplit);
                    node = farNode;
                    continue;
                }
                if (tMax < tFarSplit) {
                    tMax = Math.min(tMax, tNearSplit);
                    node = nearNode;
                    continue;
                }
                stack.add(new BIHStackData(farNode, Math.max(tMin, tFarSplit), tMax));
                tMax = Math.min(tMax, tNearSplit);
                node = nearNode;
            }
            for (int i = node.leftIndex; i <= node.rightIndex; ++i) {
                tree.getTriangle(i, v1, v2, v3);
                float t = r.intersects(v1, v2, v3);
                if (Float.isInfinite(t)) continue;
                worldMatrix.mult(v1, v1);
                worldMatrix.mult(v2, v2);
                worldMatrix.mult(v3, v3);
                t = new Ray(o, d).intersects(v1, v2, v3);
                Vector3f contactPoint = new Vector3f(d).multLocal(t).addLocal(o);
                float worldSpaceDist = o.distance(contactPoint);
                CollisionResult cr = new CollisionResult(contactPoint, worldSpaceDist);
                results.addCollision(cr);
                ++cols;
            }
        }
        vars.release();
        r.setOrigin(o);
        r.setDirection(d);
        return cols;
    }

    public static final class BIHStackData {
        private final BIHNode node;
        private final float min;
        private final float max;

        BIHStackData(BIHNode node, float min, float max) {
            this.node = node;
            this.min = min;
            this.max = max;
        }
    }

}

