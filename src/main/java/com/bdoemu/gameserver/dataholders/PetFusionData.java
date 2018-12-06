// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.servant.templates.PetFusionTemplate;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "petfusion", group = "sqlite")
@StartupComponent("Data")
public class PetFusionData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;
    private static Set<PetFusionTemplate> templates;

    static {
        log = LoggerFactory.getLogger((Class) PetFusionData.class);
        instance = new AtomicReference<Object>();
        PetFusionData.templates = new HashSet<PetFusionTemplate>();
    }

    private PetFusionData() {
        this.load();
    }

    public static PetFusionData getInstance() {
        Object value = PetFusionData.instance.get();
        if (value == null) {
            synchronized (PetFusionData.instance) {
                value = PetFusionData.instance.get();
                if (value == null) {
                    final PetFusionData actualValue = new PetFusionData();
                    value = ((actualValue == null) ? PetFusionData.instance : actualValue);
                    PetFusionData.instance.set(value);
                }
            }
        }
        return (PetFusionData) ((value == PetFusionData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("PetFusionData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Pet_Fusion_Table'")) {
            while (rs.next()) {
                final PetFusionTemplate template = new PetFusionTemplate(rs);
                PetFusionData.templates.add(template);
            }
            PetFusionData.log.info("Loaded {} PetFusionTemplate's.", (Object) PetFusionData.templates.size());
        } catch (SQLException ex) {
            PetFusionData.log.error("Failed load PetFusionData", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public int getResultTierFor(final int race, final int kind, final int tier) {
        final Optional<PetFusionTemplate> maybeTemplate = PetFusionData.templates.stream().filter(template -> template.getTier() == tier && template.getRace() == race && template.getKind() == kind).findAny();
        if (maybeTemplate.isPresent()) {
            final PetFusionTemplate template2 = maybeTemplate.get();
            if (Rnd.getChance(template2.getSelectRate0() / 10000)) {
                return template2.getSelectTier0();
            }
            if (template2.getSelectRate1() > 0 && Rnd.getChance(template2.getSelectRate1() / 10000)) {
                return template2.getSelectTier1();
            }
        }
        return -1;
    }
}
