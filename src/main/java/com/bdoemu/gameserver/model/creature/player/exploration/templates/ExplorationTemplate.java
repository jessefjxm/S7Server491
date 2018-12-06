package com.bdoemu.gameserver.model.creature.player.exploration.templates;

import com.bdoemu.commons.utils.XMLNode;
import com.bdoemu.gameserver.dataholders.WaypointDataOld;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ExplorationTemplate extends XMLNode {
    private WaypointTemplate waypointTemplate;
    private int explorationId;
    private float x;
    private float y;
    private float z;

    public ExplorationTemplate(final Node node) {
        final NamedNodeMap attributes = node.getAttributes();
        this.explorationId = this.readD(attributes, "Key");
        this.x = this.readF(attributes, "PosX");
        this.y = this.readF(attributes, "PosZ");
        this.z = this.readF(attributes, "PosY");
        this.waypointTemplate = WaypointDataOld.getInstance().getTemplate(this.explorationId);
    }

    public float getZ() {
        return this.z;
    }

    public float getY() {
        return this.y;
    }

    public float getX() {
        return this.x;
    }

    public int getExplorationId() {
        return this.explorationId;
    }

    public WaypointTemplate getWaypointTemplate() {
        return this.waypointTemplate;
    }
}
