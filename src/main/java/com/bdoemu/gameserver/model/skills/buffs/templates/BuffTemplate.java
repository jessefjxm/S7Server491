package com.bdoemu.gameserver.model.skills.buffs.templates;

import com.bdoemu.gameserver.model.skills.buffs.ModuleBuffType;
import com.bdoemu.gameserver.model.skills.buffs.enums.BuffType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BuffTemplate {
    private int buffId;
    private int group;
    private int level;
    private BuffType buffType;
    private ModuleBuffType moduleBuffType;
    private long repeatTime;
    private long validityTime;
    private int applyRate;
    private boolean removeOnDead;
    private boolean display;
    private boolean applyToGroup;
    private boolean onlyApplyToCharacter;
    private boolean isAbsolute;
    private Integer[] params;

    public BuffTemplate(final ResultSet rs) throws SQLException {
        this.params = new Integer[10];
        this.buffId = rs.getInt("Index");
        this.group = rs.getInt("Group");
        this.level = rs.getInt("Level");
        this.buffType = BuffType.values()[rs.getInt("BuffType")];
        this.moduleBuffType = ModuleBuffType.valueOf(rs.getInt("ModuleType"));
        this.validityTime = rs.getLong("ValidityTime");
        this.repeatTime = rs.getLong("RepeatTime");
        this.applyRate = rs.getInt("ApplyRate");
        this.removeOnDead = (rs.getInt("RemoveOnDead") == 1);
        this.display = (rs.getInt("IsDisplay") == 1);
        this.onlyApplyToCharacter = (rs.getInt("OnlyApplyToCharacter") == 1);
        this.applyToGroup = (rs.getInt("ApplyToGroup") == 1);
        for (int i = 0; i < 10; ++i) {
            final String param = rs.getString("Param" + i);
            if (param != null) {
                this.params[i] = rs.getInt("Param" + i);
            }
        }
    }

    public int getGroup() {
        return this.group;
    }

    public int getLevel() {
        return this.level;
    }

    public boolean isDisplay() {
        return this.display;
    }

    public boolean isOnlyApplyToCharacter() {
        return this.onlyApplyToCharacter;
    }

    public boolean isApplyToGroup() {
        return this.applyToGroup;
    }

    public long getValidityTime() {
        return this.validityTime;
    }

    public int getApplyRate() {
        return this.applyRate;
    }

    public long getRepeatTime() {
        return this.repeatTime;
    }

    public boolean isRemoveOnDead() {
        return this.removeOnDead;
    }

    public ModuleBuffType getModuleType() {
        return this.moduleBuffType;
    }

    public Integer[] getParams() {
        return this.params;
    }

    public boolean isParamsEquals(final Integer[] params) {
        if (this.params.length > 0 && params.length > 0) {
            for (int paramIndex = 0; paramIndex < params.length; ++paramIndex) {
                if (paramIndex + 1 <= params.length) {
                    final Integer paramToCheck = this.params[paramIndex];
                    if (paramToCheck == null || !paramToCheck.equals(params[paramIndex])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public int getBuffId() {
        return this.buffId;
    }

    public BuffType getBuffType() {
        return this.buffType;
    }
}
