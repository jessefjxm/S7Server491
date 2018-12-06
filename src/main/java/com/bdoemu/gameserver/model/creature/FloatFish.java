// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature;

import com.bdoemu.gameserver.model.fishing.templates.FloatFishingT;
import com.bdoemu.gameserver.model.world.Location;

public class FloatFish {
    private FloatFishingT template;
    private Location location;
    private long despawnTime;

    public FloatFish(final FloatFishingT template, final Location location) {
        this.template = template;
        this.location = location;
    }

    public FloatFishingT getTemplate() {
        return this.template;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public long getDespawnTime() {
        return this.despawnTime;
    }

    public void setDespawnTime(final long despawnTime) {
        this.despawnTime = despawnTime;
    }
}
