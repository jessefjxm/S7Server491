// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature;

import com.bdoemu.gameserver.model.creature.monster.Monster;
import com.bdoemu.gameserver.model.creature.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class InstanceSummon {
    private final Monster summon;
    private final ConcurrentHashMap<Long, Player> owners;
    private Player owner;

    public InstanceSummon(final Monster summon, final Player owner) {
        this.owners = new ConcurrentHashMap<Long, Player>();
        this.summon = summon;
        this.owner = owner;
    }

    public Collection<Player> getOwners() {
        if (this.owners.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(this.owners.values());
    }

    public Player getOwner() {
        return this.owner;
    }

    public void setOwner(final Player owner) {
        this.owner = owner;
    }

    public Monster getSummon() {
        return this.summon;
    }

    public ConcurrentHashMap<Long, Player> getOwnerMap() {
        return this.owners;
    }

    public void putMember(final Player player) {
        this.owners.put(player.getAccountId(), player);
    }
}
