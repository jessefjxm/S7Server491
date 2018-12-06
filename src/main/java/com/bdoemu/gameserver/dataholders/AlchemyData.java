package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.alchemy.enums.EAlchemyType;
import com.bdoemu.gameserver.model.alchemy.templates.AlchemyT;
import com.bdoemu.gameserver.model.alchemy.templates.MaterialT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "alchemy", group = "sqlite")
@StartupComponent("Data")
public class AlchemyData implements IReloadable {
    private static class Holder {
        static final AlchemyData INSTANCE = new AlchemyData();
    }

    private static final Logger log = LoggerFactory.getLogger(AlchemyData.class);
    private final EnumMap<EAlchemyType, List<AlchemyT>> templates;
    private final HashMap<Integer, HashMap<Integer, MaterialT>> materialTemplates;

    private AlchemyData() {
        this.templates = new EnumMap<>(EAlchemyType.class);
        this.materialTemplates = new HashMap<>();
        this.load();
    }

    public static AlchemyData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        for (final EAlchemyType type : EAlchemyType.values()) {
            this.templates.put(type, new ArrayList<>());
        }
        ServerInfoUtils.printSection("AlchemyData Loading");
        HashMap<Integer, MaterialT> materialItemTemplates = new HashMap<>();
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Material_Table'")) {
            while (rs.next()) {
                final MaterialT template = new MaterialT(rs);
                if (!this.materialTemplates.containsKey(template.getMaterialNo())) {
                    this.materialTemplates.put(template.getMaterialNo(), new HashMap<>());
                }
                this.materialTemplates.get(template.getMaterialNo()).put(template.getItemKey(), template);
                materialItemTemplates.put(template.getItemKey(), template);
            }
            AlchemyData.log.info("Loaded {} MaterialTemplate's", this.materialTemplates.values().stream().mapToInt(item -> item.values().size()).sum());
        } catch (SQLException ex) {
            AlchemyData.log.error("Failed load MaterialT", ex);
        }
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Alchemy_Table'")) {
            while (rs.next()) {
                final AlchemyT template2 = new AlchemyT(rs, materialItemTemplates);
                this.templates.get(template2.getAlchemyType()).add(template2);
            }
            AlchemyData.log.info("Loaded {} AlchemyTemplate's", this.templates.size());
        } catch (SQLException ex) {
            AlchemyData.log.error("Failed load AlchemyT", ex);
        }
    }

    public void reload() {
        this.load();
    }

    private HashMap<Integer, MaterialT> getMaterialTemplates(final int materialNo) {
        return this.materialTemplates.get(materialNo);
    }

    public AlchemyT getAlchemyT(final EAlchemyType type, final int size, final long[][] sts) {
        for (final AlchemyT template : this.templates.get(type)) {
            // We need to check if we have same count of materials.
            // It is useless to check for material id, count if we dont even have the same count.
            if (template.getMaterials().size() != size)
                continue;

            // Validate the materials.
            boolean found = true;
            for (int i = 0; i < size; ++i) {
                final long[] material = sts[i];
                final int itemId = (int) material[0];
                final long count = material[1];

                // We have to make sure that all of our materials match.
                MaterialT foundMaterial = null;
                for (final Map.Entry<MaterialT, Long> materialEntry : template.getMaterials().entrySet()) {
                    // Find the material template.
                    Map<Integer, MaterialT> materialTemplate = this.getMaterialTemplates(materialEntry.getKey().getMaterialNo());
                    if (materialTemplate != null) {
                        foundMaterial = materialTemplate.get(itemId);
                        if (foundMaterial != null) {
                            // Check if we have enough materials to even use this material.
                            if (count < materialEntry.getValue())
                                foundMaterial = null;

                            // Since we found the material, we do not need to continue.
                            break;
                        }
                    }
                }

                // Since material is not found, we should exit.
                if (foundMaterial == null) {
                    found = false;
                    break;
                }
            }

            if (found) {
                return template;
            }
        }
        return null;
    }
}
