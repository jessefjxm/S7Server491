// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.waypoints;

import com.bdoemu.commons.utils.XMLNode;
import com.bdoemu.commons.utils.XmlUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class WaypointRouteT extends XMLNode {
    private String name;
    private Integer key;
    private List<Integer> routeWaypointList;

    public WaypointRouteT(final Node waypointRouteNode) {
        this.routeWaypointList = new ArrayList<Integer>();
        final NamedNodeMap attr = waypointRouteNode.getAttributes();
        this.name = this.readS(attr, "Name").toLowerCase();
        this.key = this.readD(attr, "Key");
        final List<Node> waypointNodeList = (List<Node>) XmlUtils.asList(waypointRouteNode.getChildNodes());
        waypointNodeList.stream().filter(waypointNode -> waypointNode.getNodeName().equalsIgnoreCase("waypoint")).forEach(routeNode -> this.routeWaypointList.add(this.readD(attr, "Key")));
    }

    public String getName() {
        return this.name;
    }

    public Integer getKey() {
        return this.key;
    }

    public List<Integer> getRouteWaypointList() {
        return this.routeWaypointList;
    }
}
