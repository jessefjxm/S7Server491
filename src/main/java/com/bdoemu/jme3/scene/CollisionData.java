/*
 * Decompiled with CFR 0_121.
 */
package com.bdoemu.jme3.scene;

import com.bdoemu.jme3.bounding.BoundingVolume;
import com.bdoemu.jme3.collision.Collidable;
import com.bdoemu.jme3.collision.CollisionResults;

public interface CollisionData
extends Cloneable {
    public int collideWith(Collidable var1, BoundingVolume var2, CollisionResults var3);
}

