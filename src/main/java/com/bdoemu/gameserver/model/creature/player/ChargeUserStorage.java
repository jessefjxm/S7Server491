package com.bdoemu.gameserver.model.creature.player;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.network.sendable.SMChargeUser;
import com.bdoemu.gameserver.model.skills.packageeffects.AChargeUserEffect;
import com.bdoemu.gameserver.model.skills.packageeffects.enums.EChargeUserType;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class ChargeUserStorage extends JSONable {
    private final AChargeUserEffect[] packageEffects;

    public ChargeUserStorage() {
        this.packageEffects = new AChargeUserEffect[EChargeUserType.values().length];
    }

    public ChargeUserStorage(final BasicDBObject basicDBObject) {
        this.packageEffects = new AChargeUserEffect[EChargeUserType.values().length];
        if (basicDBObject != null && basicDBObject.containsField("chargeUserEffects")) {
            final BasicDBList dbList = (BasicDBList) basicDBObject.get("chargeUserEffects");
            for (final Object aDbList : dbList) {
                final BasicDBObject dbObject = (BasicDBObject) aDbList;
                final long endEffectTime = dbObject.getLong("effectEndTime");
                final EChargeUserType type = EChargeUserType.values()[dbObject.getInt("chargeUserType")];
                this.packageEffects[type.ordinal()] = type.newChargeUserEffectInstance(endEffectTime);
            }
        }
    }

    public void addChargeUserEffect(final AChargeUserEffect chargeUserEffect) {
        this.packageEffects[chargeUserEffect.getChargeUserType().ordinal()] = chargeUserEffect;
    }

    public AChargeUserEffect getChargeUserEffect(final EChargeUserType chargeUserType) {
        return this.packageEffects[chargeUserType.ordinal()];
    }

    public long getChargeUserEffectEndTime(final EChargeUserType chargeUserType) {
        final AChargeUserEffect chargeUserEffect = this.getChargeUserEffect(chargeUserType);
        return (chargeUserEffect == null) ? 0L : chargeUserEffect.getEndTimeSeconds();
    }

    public boolean isActiveChargeUserEffect(final EChargeUserType chargeUserType) {
        final AChargeUserEffect chargeUserEffect = this.getChargeUserEffect(chargeUserType);
        return chargeUserEffect != null && chargeUserEffect.isValid();
    }

    public void initPackageEffects(final Player player) {
        for (final AChargeUserEffect chargeUserEffect : this.packageEffects) {
            if (chargeUserEffect != null && chargeUserEffect.isValid()) {
                chargeUserEffect.initEffect(player);
            }
        }
        player.sendPacket(new SMChargeUser(player.getAccountData()));
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
        final BasicDBList dbList = new BasicDBList();
        for (final AChargeUserEffect chargeUserEffect : this.packageEffects) {
            if (chargeUserEffect != null && chargeUserEffect.isValid()) {
                dbList.add(chargeUserEffect.toDBObject());
            }
        }
        builder.append("chargeUserEffects", dbList);
        return builder.get();
    }
}
