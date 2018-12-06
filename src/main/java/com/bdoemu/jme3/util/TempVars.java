/*
 * Decompiled with CFR 0_121.
 */
package com.bdoemu.jme3.util;

import com.bdoemu.jme3.collision.CollisionResults;
import com.bdoemu.jme3.collision.bih.BIHNode;
import com.bdoemu.jme3.math.Matrix3f;
import com.bdoemu.jme3.math.Matrix4f;
import com.bdoemu.jme3.math.Vector3f;
import java.util.ArrayList;

public class TempVars {
    private static final int STACK_SIZE = 5;
    private static final ThreadLocal<TempVarsStack> varsLocal = ThreadLocal.withInitial(() -> new TempVarsStack());
    private boolean isUsed = false;
    public final Vector3f vect1 = new Vector3f();
    public final Vector3f vect2 = new Vector3f();
    public final Vector3f vect3 = new Vector3f();
    public final Vector3f vect4 = new Vector3f();
    public final Vector3f vect5 = new Vector3f();
    public final Matrix3f tempMat3 = new Matrix3f();
    public final Matrix4f tempMat4 = new Matrix4f();
    public final float[] fWdU = new float[3];
    public final float[] fAWdU = new float[3];
    public final float[] fDdU = new float[3];
    public final float[] fADdU = new float[3];
    public final float[] fAWxDdU = new float[3];
    public final float[] matrixWrite = new float[16];
    public final CollisionResults collisionResults = new CollisionResults();
    public final ArrayList<BIHNode.BIHStackData> bihStack = new ArrayList();

    private TempVars() {
    }

    public static TempVars get() {
        TempVarsStack stack = varsLocal.get();
        TempVars instance = stack.tempVars[stack.index];
        if (instance == null) {
            stack.tempVars[stack.index] = instance = new TempVars();
        }
        ++stack.index;
        instance.isUsed = true;
        return instance;
    }

    public void release() {
        if (!this.isUsed) {
            throw new IllegalStateException("This instance of TempVars was already released!");
        }
        this.isUsed = false;
        TempVarsStack stack = varsLocal.get();
        --stack.index;
        if (stack.tempVars[stack.index] != this) {
            throw new IllegalStateException("An instance of TempVars has not been released in a called method!");
        }
    }

    private static class TempVarsStack {
        int index = 0;
        TempVars[] tempVars = new TempVars[5];

        private TempVarsStack() {
        }
    }

}

