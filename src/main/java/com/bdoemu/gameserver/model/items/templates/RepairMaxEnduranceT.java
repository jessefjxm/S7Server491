package com.bdoemu.gameserver.model.items.templates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class RepairMaxEnduranceT {
    private final HashMap<Integer, RepairResourceMaxEnduranceT> _templates;
    private int _baseItemId;

    public RepairMaxEnduranceT(final ResultSet rs) throws SQLException {
        _templates = new HashMap<>();
        _baseItemId = rs.getInt("BaseItem");
        for (int index = 1; index <= 10; ++index) { // 12 TODO
            if (rs.getString("Item" + index) != null) {
                final RepairResourceMaxEnduranceT template = new RepairResourceMaxEnduranceT(index, rs);
                _templates.put(template.getItemId(), template);
            }
        }
    }

    public RepairResourceMaxEnduranceT getTemplate(int resourceItemId) {
        return _templates.get(resourceItemId);
    }

    public int getBaseItemId() {
        return _baseItemId;
    }
}
