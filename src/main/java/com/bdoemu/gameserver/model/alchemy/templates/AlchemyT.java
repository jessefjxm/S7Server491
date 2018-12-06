package com.bdoemu.gameserver.model.alchemy.templates;

import com.bdoemu.gameserver.model.alchemy.enums.EAlchemyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AlchemyT {
    private static final Logger log = LoggerFactory.getLogger(AlchemyT.class);
    private final HashMap<MaterialT, Long> materials;
    private EAlchemyType alchemyType;
    private int resultDropGroup;

    public AlchemyT(final ResultSet rs, final HashMap<Integer, MaterialT> materialItemTemplates) throws SQLException {
        this.materials = new HashMap<>();
        this.alchemyType = EAlchemyType.valueOf(rs.getInt("AlchemyType"));
        this.resultDropGroup = rs.getInt("ResultDropGroup");
        for (int i = 1; i <= 8; ++i) {
            if (rs.getString("MaterialItem" + i) != null) {
                final int materialItemId = rs.getInt("MaterialItem" + i);
                final MaterialT materialT = materialItemTemplates.get(materialItemId);
                if (materialT != null) {
                    this.materials.put(materialT, rs.getLong("MaterialCount" + i));
                } else {
                    AlchemyT.log.error("Material template with id=[{}] for AlchemyType=[[]] in column=[{}] didn't exist in database!", new Object[]{materialItemId, this.alchemyType.toString(), "MaterialItem" + i});
                }
            }
        }
    }

    public EAlchemyType getAlchemyType() {
        return this.alchemyType;
    }

    public int getResultDropGroup() {
        return this.resultDropGroup;
    }

    public HashMap<MaterialT, Long> getMaterials() {
        return this.materials;
    }
}
