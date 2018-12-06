package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.enums.EClassType;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "classbalance", group = "sqlite")
@StartupComponent("Data")
public class ClassBalanceData implements IReloadable {
    private static class Holder {
        static final ClassBalanceData INSTANCE = new ClassBalanceData();
    }
    private static final Logger log = LoggerFactory.getLogger(ClassBalanceData.class);
    private EnumMap<EClassType, float[]> templates;

    private ClassBalanceData() {
        this.templates = new EnumMap<>(EClassType.class);
        this.load();
    }

    public static ClassBalanceData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("ClassBalanceData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'ClassBalance'")) {
            while (rs.next()) {
                final EClassType classKey = EClassType.valueOf(rs.getInt("ClassKey"));
                float[] classBalances = this.templates.computeIfAbsent(classKey, eClassType -> new float[EClassType.values().length]);
                for (final EClassType targetClassType : EClassType.values())
                    classBalances[targetClassType.ordinal()] = rs.getFloat("_" + targetClassType.ordinal());
                if (classKey != null)
                    this.templates.put(classKey, classBalances);
                else
                    log.info("Failed to load {} class balance data.", classKey);
            }
            ClassBalanceData.log.info("Loaded {} ClassBalanceTemplate's", this.templates.size());
        } catch (SQLException ex) {
            ClassBalanceData.log.error("Failed load ClassBalanceTemplate", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public float getClassBalance(final EClassType owner, final EClassType target) {
        return this.templates.get(owner)[target.ordinal()];
    }
}
