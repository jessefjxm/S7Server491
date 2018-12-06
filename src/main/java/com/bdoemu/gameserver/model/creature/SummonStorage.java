// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature;

import com.bdoemu.core.network.sendable.SMPlayerAddSummonList;
import com.bdoemu.core.network.sendable.SMPlayerDelSummonList;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SummonStorage {
    private final Creature owner;
    private ConcurrentHashMap<Integer, Creature> summons;

    public SummonStorage(final Creature owner) {
        this.owner = owner;
    }

    public boolean addSummon(final Creature summon) {
        if (this.summons == null) {
            this.summons = new ConcurrentHashMap<Integer, Creature>();
        }
        if (this.summons.putIfAbsent(summon.getGameObjectId(), summon) == null) {
            if (this.owner.isPlayer()) {
                this.owner.sendPacket(new SMPlayerAddSummonList(summon));
            }
            return true;
        }
        return false;
    }

    public boolean containSummon(final Creature summon) {
        return this.summons != null && this.summons.contains(summon);
    }

    public boolean removeSummon(final Creature summon) {
        if (this.summons != null && this.summons.remove(summon.getGameObjectId()) != null) {
            if (this.owner.isPlayer()) {
                this.owner.sendPacket(new SMPlayerDelSummonList(summon));
            }
            return true;
        }
        return false;
    }

    public Creature getHaetae() {
        if (this.summons == null) {
            return null;
        }
        final Optional<Creature> haetae = this.summons.values().stream().filter(summon -> summon.getTemplate().getVehicleType().isHaetae()).findFirst();
        return haetae.isPresent() ? haetae.get() : null;
    }

    public Collection<Creature> getSummons() {
        return (Collection<Creature>) ((this.summons == null) ? Collections.emptyList() : this.summons.values());
    }
}
