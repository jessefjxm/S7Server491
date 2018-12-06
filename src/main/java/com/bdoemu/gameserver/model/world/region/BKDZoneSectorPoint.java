package com.bdoemu.gameserver.model.world.region;

import java.awt.*;

public class BKDZoneSectorPoint {
    private int offsetX;
    private int offsetY;
    private Color color;

    public BKDZoneSectorPoint(final int offsetX, final int offsetY, final Color color) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public int getOffsetX() {
        return this.offsetX;
    }

    public int getOffsetY() {
        return this.offsetY;
    }
}
