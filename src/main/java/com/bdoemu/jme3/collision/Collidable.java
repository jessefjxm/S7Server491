/*
 * Decompiled with CFR 0_121.
 */
package com.bdoemu.jme3.collision;

import com.bdoemu.jme3.collision.CollisionResults;
import com.bdoemu.jme3.collision.UnsupportedCollisionException;

public interface Collidable {
    public int collideWith(Collidable var1, CollisionResults var2) throws UnsupportedCollisionException;
}

