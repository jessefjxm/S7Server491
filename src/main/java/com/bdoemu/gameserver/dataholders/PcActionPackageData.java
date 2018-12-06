package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.DatabaseUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.enums.EVehicleType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.enums.EClassType;
import com.bdoemu.gameserver.model.creature.player.enums.EEquipSlot;
import com.bdoemu.gameserver.model.creature.player.enums.EquipType;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.EnumMap;

@Reloadable(name = "pcactionpackage", group = "sqlite")
@StartupComponent("Data")
public class PcActionPackageData implements IReloadable {
    private static class Holder {
        static final PcActionPackageData INSTANCE = new PcActionPackageData();
    }

    private static final Logger log = LoggerFactory.getLogger(PcActionPackageData.class);
    private final EnumMap<EClassType, EnumMap<EquipType, String>> equipMap;
    private final EnumMap<EClassType, EnumMap<EVehicleType, String>> vehicleMap;

    private PcActionPackageData() {
        this.equipMap = new EnumMap<>(EClassType.class);
        this.vehicleMap = new EnumMap<>(EClassType.class);
        this.load();
    }

    public static PcActionPackageData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'PcActionPackage_Table'")) {
            final ResultSetMetaData rsMeta = rs.getMetaData();
            while (rs.next()) {
                final EClassType classType = EClassType.valueOf(rs.getInt("Index"));
                if (classType != null) {
                    for (final EquipType equipType : EquipType.values()) {
                        if (equipType.getDefaultSlot() == EEquipSlot.rightHand || equipType == EquipType.None) {
                            if (DatabaseUtils.hasColumn(rsMeta, equipType.toString())) {
                                final String equipPackage = rs.getString(equipType.toString());
                                if (equipPackage != null) {
                                    this.equipMap.computeIfAbsent(classType, k -> new EnumMap<>(EquipType.class)).put(equipType, equipPackage.toLowerCase());
                                }
                            } else {
                                PcActionPackageData.log.warn("PcActionPackage table didn't contain column for EquipType.{}", equipType.toString());
                            }
                        }
                    }
                    for (final EVehicleType vehicleType : EVehicleType.values()) {
                        if (DatabaseUtils.hasColumn(rsMeta, vehicleType.toString())) {
                            final String vehiclePackage = rs.getString(vehicleType.toString());
                            if (vehiclePackage != null) {
                                this.vehicleMap.computeIfAbsent(classType, k -> new EnumMap<>(EVehicleType.class)).put(vehicleType, vehiclePackage.toLowerCase());
                            }
                        }
                    }
                }
            }
            PcActionPackageData.log.info("Loaded {} equip stance and {} ride stance action packages", this.equipMap.size(), this.vehicleMap.size());
        } catch (SQLException ex) {
            PcActionPackageData.log.error("Failed load PcActionPackageData", ex);
        }
    }

    private String getDefaultActionPackageName(final Player player) {
        return this.equipMap.get(player.getClassType()).get(EquipType.None);
    }

    public String getActionPackageName(final Player player) {
        final Servant servant = player.getCurrentVehicle();
        if (player.getCurrentVehicle() != null) {
            return this.vehicleMap.get(player.getClassType()).get(servant.getTemplate().getVehicleType());
        }
        final Item item = player.getEquipments().getItem(EEquipSlot.rightHand);
        if (item != null) {
            return this.equipMap.get(player.getClassType()).get(item.getTemplate().getEquipType());
        }
        return this.getDefaultActionPackageName(player);
    }

    public void reload() {
        this.load();
    }
}
