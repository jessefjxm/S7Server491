/*
 * Decompiled with CFR 0_121.
 * 
 * Could not load the following classes:
 *  com.bdoemu.commons.utils.XMLNode
 */
package com.bdoemu.gameserver.model.waypoints;

import com.bdoemu.commons.utils.XMLNode;
import com.bdoemu.gameserver.model.actions.enums.ENaviType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class WaypointT
        extends XMLNode {
    private String name;
    private Integer key;
    private int consumeExplorePoint;
    private boolean isEscape;
    private boolean isSubWaypoint;
    private double posX;
    private double posY;
    private double posZ;
    private ENaviType property;
    private WaypointDataT waypointDataTemplate;

    public WaypointT(Node node, WaypointDataT waypointDataTemplate) {
        this.waypointDataTemplate = waypointDataTemplate;
        NamedNodeMap attr = node.getAttributes();
        this.name = this.readS(attr, "Name").toLowerCase();
        this.key = this.readD(attr, "Key");
        this.isEscape = this.readBoolean(attr, "IsEscape");
        this.isSubWaypoint = this.readBoolean(attr, "IsSubWaypoint");
        this.posX = this.readF(attr, "PosX");
        this.posY = this.readF(attr, "PosZ");
        this.posZ = this.readF(attr, "PosY");
        if (attr.getNamedItem("Property") != null) {
            this.property = ENaviType.valueOf(this.readS(attr, "Property"));
        }
        if (attr.getNamedItem("ConsumeExplorePoint") != null) {
            this.consumeExplorePoint = this.readD(attr, "ConsumeExplorePoint");
        }
    }

    public String getName() {
        return this.name;
    }

    public Integer getKey() {
        return this.key;
    }

    public boolean isEscape() {
        return this.isEscape;
    }

    public boolean isSubWaypoint() {
        return this.isSubWaypoint;
    }

    public double getPosX() {
        return this.posX;
    }

    public double getPosY() {
        return this.posY;
    }

    public double getPosZ() {
        return this.posZ;
    }

    public ENaviType getProperty() {
        return this.property;
    }

    public int getConsumeExplorePoint() {
        return this.consumeExplorePoint;
    }

    public WaypointDataT getWaypointDataTemplate() {
        return this.waypointDataTemplate;
    }
}

