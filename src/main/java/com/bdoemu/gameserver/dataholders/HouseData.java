// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.houses.templates.HouseInfoT;
import com.bdoemu.gameserver.model.houses.templates.HouseTransferT;
import com.bdoemu.gameserver.model.houses.templates.ReceipeForTownT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "house", group = "sqlite")
@StartupComponent("Data")
public class HouseData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) HouseData.class);
        instance = new AtomicReference<Object>();
    }

    private final HashMap<Integer, HashMap<Integer, HouseTransferT>> houseTransfers;
    private final HashMap<Integer, ReceipeForTownT> recipes;
    private final HashMap<Integer, HouseInfoT> houses;

    private HouseData() {
        this.houseTransfers = new HashMap<Integer, HashMap<Integer, HouseTransferT>>();
        this.recipes = new HashMap<Integer, ReceipeForTownT>();
        this.houses = new HashMap<Integer, HouseInfoT>();
        this.load();
    }

    public static HouseData getInstance() {
        Object value = HouseData.instance.get();
        if (value == null) {
            synchronized (HouseData.instance) {
                value = HouseData.instance.get();
                if (value == null) {
                    final HouseData actualValue = new HouseData();
                    value = ((actualValue == null) ? HouseData.instance : actualValue);
                    HouseData.instance.set(value);
                }
            }
        }
        return (HouseData) ((value == HouseData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("HouseData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'House_Transfer_New'")) {
            while (rs.next()) {
                final HouseTransferT template = new HouseTransferT(rs);
                final int transferKey = template.getTransferKey();
                if (!this.houseTransfers.containsKey(transferKey)) {
                    this.houseTransfers.put(transferKey, new HashMap<Integer, HouseTransferT>());
                }
                this.houseTransfers.get(transferKey).put(template.getLevel(), template);
            }
            HouseData.log.info("Loaded {} HouseTransferTemplate's", (Object) this.houseTransfers.values().stream().mapToInt(item -> item.values().size()).sum());
        } catch (SQLException ex) {
            HouseData.log.error("Failed load HouseTransferT", (Throwable) ex);
        }
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'ReceipeForTown_New'")) {
            while (rs.next()) {
                final ReceipeForTownT template2 = new ReceipeForTownT(rs);
                this.recipes.put(template2.getReceipeKey(), template2);
            }
            HouseData.log.info("Loaded {} ReceipeForTownTemplate's", (Object) this.recipes.size());
        } catch (SQLException ex) {
            HouseData.log.error("Failed load ReceipeForTownT", (Throwable) ex);
        }
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'HouseInfo_New'")) {
            while (rs.next()) {
                final HouseInfoT template3 = new HouseInfoT(rs);
                this.houses.put(template3.getHouseId(), template3);
            }
            HouseData.log.info("Loaded {} HouseInfoTemplate's", (Object) this.houses.size());
        } catch (SQLException ex) {
            HouseData.log.error("Failed load HouseInfoT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public Collection<HouseInfoT> getHouses() {
        return this.houses.values();
    }

    public HouseTransferT getHouseTransfer(final int transferKey, final int level) {
        return this.houseTransfers.get(transferKey).get(level);
    }

    public ReceipeForTownT getRecipe(final int recipeKey) {
        return this.recipes.get(recipeKey);
    }

    public HouseInfoT getHouse(final int houseId) {
        return this.houses.get(houseId);
    }
}
