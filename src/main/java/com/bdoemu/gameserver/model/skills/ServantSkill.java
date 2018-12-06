// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.network.sendable.SMSetServantSkillExp;
import com.bdoemu.gameserver.dataholders.VehicleSkillData;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.skills.templates.VehicleSkillOwnerT;
import com.bdoemu.gameserver.model.skills.templates.VehicleSkillT;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class ServantSkill extends JSONable {
    private final VehicleSkillT vehicleSkillT;
    private final VehicleSkillOwnerT vehicleSkillOwnerT;
    private int exp;
    private int baseExp;
    private boolean isCannotChange;

    public ServantSkill(final VehicleSkillOwnerT vehicleSkillOwnerT, final int skillId, final int exp) {
        this.isCannotChange = false;
        this.vehicleSkillOwnerT = vehicleSkillOwnerT;
        this.vehicleSkillT = VehicleSkillData.getInstance().getSkillTemplate(skillId);
        this.exp = exp;
        this.baseExp = this.vehicleSkillT.getAddExpForCheck() + vehicleSkillOwnerT.getAddExp()[this.vehicleSkillT.getSkillId() - 1];
    }

    public ServantSkill(final VehicleSkillOwnerT vehicleSkillOwnerT, final BasicDBObject basicDBObject) {
        this.isCannotChange = false;
        this.vehicleSkillOwnerT = vehicleSkillOwnerT;
        this.vehicleSkillT = VehicleSkillData.getInstance().getSkillTemplate(basicDBObject.getInt("skillId"));
        this.exp = basicDBObject.getInt("exp");
        if (this.exp > this.vehicleSkillT.getMaxExp() - this.baseExp) {
            this.exp = this.vehicleSkillT.getMaxExp() - this.baseExp;
        }
        this.isCannotChange = basicDBObject.getBoolean("isCannotChange", false);
        this.baseExp = this.vehicleSkillT.getAddExpForCheck() + vehicleSkillOwnerT.getAddExp()[this.vehicleSkillT.getSkillId() - 1];
    }

    public boolean isCannotChange() {
        return this.isCannotChange;
    }

    public VehicleSkillT getVehicleSkillT() {
        return this.vehicleSkillT;
    }

    public int getSkillId() {
        return this.vehicleSkillT.getSkillId();
    }

    public int getExpPercentage() {
        return (this.baseExp + this.exp) * 100 / this.vehicleSkillT.getMaxExp();
    }

    public int getExp() {
        return this.exp;
    }

    public synchronized void addExp(final Servant servant, final int exp) {
        final int totalCurrentExp = this.baseExp + this.exp;
        if (totalCurrentExp >= this.vehicleSkillT.getMaxExp()) {
            return;
        }
        if (totalCurrentExp + exp <= this.vehicleSkillT.getMaxExp()) {
            this.exp += exp;
        } else {
            this.exp = this.vehicleSkillT.getMaxExp() - this.baseExp;
        }
        if (!servant.getServantState().skillTraining()) {
            servant.getOwner().sendPacket(new SMSetServantSkillExp(servant.getObjectId(), this));
        }
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.add("skillId", (Object) this.vehicleSkillT.getSkillId());
        builder.add("exp", (Object) this.exp);
        builder.add("isCannotChange", (Object) this.isCannotChange);
        return builder.get();
    }
}
