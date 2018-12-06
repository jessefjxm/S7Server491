// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.collection.BitMask;
import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "petequipskillaquire", group = "sqlite")
@StartupComponent("Data")
public class PetEquipSkillAquireData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) PetEquipSkillAquireData.class);
        instance = new AtomicReference<Object>();
    }

    private final Map<Integer, List<Integer>> aquireTable;
    private final Map<Integer, Integer> skillTable;

    private PetEquipSkillAquireData() {
        this.aquireTable = new HashMap<Integer, List<Integer>>();
        this.skillTable = new HashMap<Integer, Integer>();
        this.load();
    }

    public static PetEquipSkillAquireData getInstance() {
        Object value = PetEquipSkillAquireData.instance.get();
        if (value == null) {
            synchronized (PetEquipSkillAquireData.instance) {
                value = PetEquipSkillAquireData.instance.get();
                if (value == null) {
                    final PetEquipSkillAquireData actualValue = new PetEquipSkillAquireData();
                    value = ((actualValue == null) ? PetEquipSkillAquireData.instance : actualValue);
                    PetEquipSkillAquireData.instance.set(value);
                }
            }
        }
        return (PetEquipSkillAquireData) ((value == PetEquipSkillAquireData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("PetEquipSkillAquireData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Pet_EquipSkill_Aquire_Table' WHERE Service_Type == " + ServerConfig.GAME_SERVICE_TYPE.ordinal())) {
            while (rs.next()) {
                final int key = rs.getInt("Key");
                final List<Integer> aquireTableList = new ArrayList<Integer>();
                for (int index = 0; index <= 37; ++index) {
                    final int aquireRate = rs.getInt("AquireRate_" + index);
                    aquireTableList.add(aquireRate);
                }
                this.aquireTable.put(key, aquireTableList);
            }
            PetEquipSkillAquireData.log.info("Loaded {} PetEquipSkillAquireData key's.", (Object) this.aquireTable.size());
        } catch (SQLException ex) {
            PetEquipSkillAquireData.log.error("Failed load PetEquipSkillAquireData", (Throwable) ex);
        }
        ServerInfoUtils.printSection("PetEquipSkillData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Pet_EquipSkill_Table'")) {
            while (rs.next()) {
                final int index2 = rs.getInt("Index");
                final int groupNo = rs.getInt("GroupNo");
                final int skillNo = rs.getInt("SkillNo");
                this.skillTable.put(index2, skillNo);
            }
            PetEquipSkillAquireData.log.info("Loaded {} PetEquipSkillData key's.", (Object) this.skillTable.size());
        } catch (SQLException ex) {
            PetEquipSkillAquireData.log.error("Failed load PetEquipSkillAquireData", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public int getSkillNoByIndex(final int skillIndex) {
        return this.skillTable.get(skillIndex);
    }

    public int getSkillsCount() {
        return this.skillTable.values().size();
    }

    public int getRandomSkillIndex(final int acquireKey, final BitMask currentMask) {
        List<Integer> chanceList;
        if (currentMask != null) {
            chanceList = new ArrayList<Integer>();
            for (int index = 0; index < chanceList.size(); ++index) {
                if (!currentMask.get(index)) {
                    chanceList.add(chanceList.get(index));
                }
            }
        } else {
            chanceList = this.aquireTable.get(acquireKey);
        }
        if (chanceList != null) {
            final double random = 1000000.0 * Rnd.nextDouble();
            double chanceFrom = 0.0;
            for (int index2 = 0; index2 < chanceList.size(); ++index2) {
                if (chanceFrom >= 1000000.0) {
                    chanceFrom = 0.0;
                }
                final double chance = chanceList.get(index2);
                if (random >= chanceFrom && random <= chance + chanceFrom) {
                    return index2;
                }
                chanceFrom += chance;
            }
        }
        return -1;
    }
}
