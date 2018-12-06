// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.commons.utils.ServerInfoUtils;
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

@Reloadable(name = "petchangelook", group = "sqlite")
@StartupComponent("Data")
public class PetChangeLookTable implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) PetChangeLookTable.class);
        instance = new AtomicReference<Object>();
    }

    private Map<Integer, List<Integer>> table;

    private PetChangeLookTable() {
        this.table = new HashMap<Integer, List<Integer>>();
        this.load();
    }

    public static PetChangeLookTable getInstance() {
        Object value = PetChangeLookTable.instance.get();
        if (value == null) {
            synchronized (PetChangeLookTable.instance) {
                value = PetChangeLookTable.instance.get();
                if (value == null) {
                    final PetChangeLookTable actualValue = new PetChangeLookTable();
                    value = ((actualValue == null) ? PetChangeLookTable.instance : actualValue);
                    PetChangeLookTable.instance.set(value);
                }
            }
        }
        return (PetChangeLookTable) ((value == PetChangeLookTable.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("PetChangeLookData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Pet_ChangeLook_Table'")) {
            while (rs.next()) {
                final int petChangeLookKey = rs.getInt("PetChangeLookKey");
                final int actionIndex = rs.getInt("ActionIndex");
                if (!this.table.containsKey(petChangeLookKey)) {
                    this.table.put(petChangeLookKey, new ArrayList<Integer>());
                }
                this.table.get(petChangeLookKey).add(actionIndex);
            }
            PetChangeLookTable.log.info("Loaded {} PetChangeLookData's.", (Object) this.table.values().size());
        } catch (SQLException ex) {
            PetChangeLookTable.log.error("Failed load PetChangeLookData", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public int getRandomActionIndex(final int changeLookKey) {
        final List<Integer> list = this.table.get(changeLookKey);
        if (list != null) {
            return (int) Rnd.get((List) list);
        }
        return 0;
    }

    public boolean isPetActionIndexValid(final int changeLookKey, final int actionIndex) {
        return this.table.containsKey(changeLookKey) && this.table.get(changeLookKey).contains(actionIndex);
    }
}
