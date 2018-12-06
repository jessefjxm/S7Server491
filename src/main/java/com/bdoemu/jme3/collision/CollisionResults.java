/*
 * Decompiled with CFR 0_121.
 */
package com.bdoemu.jme3.collision;

import com.bdoemu.jme3.collision.CollisionResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CollisionResults
implements Iterable<CollisionResult> {
    private ArrayList<CollisionResult> results = null;
    private boolean sorted = true;

    public void clear() {
        if (this.results != null) {
            this.results.clear();
        }
    }

    @Override
    public Iterator<CollisionResult> iterator() {
        if (this.results == null) {
            List dumbCompiler = Collections.emptyList();
            return dumbCompiler.iterator();
        }
        if (!this.sorted) {
            Collections.sort(this.results);
            this.sorted = true;
        }
        return this.results.iterator();
    }

    public void addCollision(CollisionResult result) {
        if (this.results == null) {
            this.results = new ArrayList();
        }
        this.results.add(result);
        this.sorted = false;
    }

    public int size() {
        if (this.results == null) {
            return 0;
        }
        return this.results.size();
    }

    public CollisionResult getClosestCollision() {
        if (this.results == null || this.size() == 0) {
            return null;
        }
        if (!this.sorted) {
            Collections.sort(this.results);
            this.sorted = true;
        }
        return this.results.get(0);
    }

    public CollisionResult getFarthestCollision() {
        if (this.results == null || this.size() == 0) {
            return null;
        }
        if (!this.sorted) {
            Collections.sort(this.results);
            this.sorted = true;
        }
        return this.results.get(this.size() - 1);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CollisionResults[");
        if (this.results != null) {
            for (CollisionResult result : this.results) {
                sb.append(result).append(", ");
            }
            if (this.results.size() > 0) {
                sb.setLength(sb.length() - 2);
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

