// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.templates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EquipSetOptionT {
    private List<Integer> setItems;
    private HashMap<Integer, Integer> buffs;
    private byte setType;
    private int index;

    public EquipSetOptionT(final ResultSet rs) throws SQLException {
        this.setItems = new ArrayList<Integer>();
        this.buffs = new HashMap<Integer, Integer>();
        this.index = rs.getInt("Index");
        this.setType = rs.getByte("SetType");
        for (int i = 1; i <= 14; ++i) {
            if (rs.getString("Set_" + i + "_Option") != null) {
                this.buffs.put(i, rs.getInt("Set_" + i + "_Option"));
            }
        }
        for (int i = 1; i <= 40; ++i) {
            if (rs.getString("Equip_" + i) != null) {
                this.setItems.add(rs.getInt("Equip_" + i));
            }
        }
    }

    public int getIndex() {
        return this.index;
    }

    public HashMap<Integer, Integer> getBuffs() {
        return this.buffs;
    }

    public List<Integer> getSetItems() {
        return this.setItems;
    }
}
