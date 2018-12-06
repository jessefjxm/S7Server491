// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemPuzzleT {
    private int[][] puzzles;
    private int makeItemId;
    private int enchantLevel;

    public ItemPuzzleT(final ResultSet rs, final int index) throws SQLException {
        this.puzzles = new int[5][5];
        for (int i = 0; i < 5; ++i) {
            this.puzzles[index][i] = rs.getInt("Puzzle" + i);
        }
        this.makeItemId = rs.getInt("MakeItemKey");
        this.enchantLevel = rs.getInt("MakeItemEnchantLevel");
    }

    public void update(final ResultSet rs, final int index) throws SQLException {
        for (int i = 0; i < 5; ++i) {
            this.puzzles[index][i] = rs.getInt("Puzzle" + i);
        }
    }

    public int getMakeItemId() {
        return this.makeItemId;
    }

    public int getEnchantLevel() {
        return this.enchantLevel;
    }

    public int[][] getPuzzles() {
        return this.puzzles;
    }
}
