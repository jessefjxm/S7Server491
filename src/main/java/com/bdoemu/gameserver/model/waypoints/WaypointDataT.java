package com.bdoemu.gameserver.model.waypoints;

import com.bdoemu.commons.utils.XMLNode;
import com.bdoemu.commons.utils.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaypointDataT
        extends XMLNode {
    private static final Logger log = LoggerFactory.getLogger(WaypointDataT.class);
    private String name;
    private String subName;
    private String groupName;
    private String groupKey;
    private Map<String, WaypointT> waypoints = new HashMap<>();
    private Map<Integer, WaypointT> waypointsByKey = new HashMap<>();
    private Map<Integer, List<Integer>> links = new HashMap<>();
    private Map<String, WaypointRouteT> routes = new HashMap<>();

    public WaypointDataT(Node node) {
        NamedNodeMap attr = node.getAttributes();
        this.name = attr.getNamedItem("name").getNodeValue().toLowerCase();
        if (attr.getNamedItem("subName") != null) {
            this.subName = attr.getNamedItem("subName").getNodeName().toLowerCase();
        }
        for (Node docNodes : XmlUtils.asList(node.getChildNodes())) {
            switch (docNodes.getNodeName()) {
                case "WaypointList": {
                    List<Node> waypointNodeList = XmlUtils.asList(docNodes.getChildNodes());
                    waypointNodeList.stream().filter(waypointNode -> waypointNode.getNodeName().equalsIgnoreCase("waypoint")).forEach(waypointNode -> {
                                WaypointT waypoint = new WaypointT(waypointNode, this);
                                this.waypoints.put(waypoint.getName(), waypoint);
                                this.waypointsByKey.put(waypoint.getKey(), waypoint);
                            }
                    );
                    break;
                }
                case "LinkList": {
                    List<Node> linkList = XmlUtils.asList(docNodes.getChildNodes());
                    linkList.stream().filter(waypointNode -> waypointNode.getNodeName().equalsIgnoreCase("link")).forEach(linkNode -> {
                                NamedNodeMap linkAttr = linkNode.getAttributes();
                                Integer sourceWaypoint = this.readD(linkAttr, "SourceWaypoint");
                                Integer targetWaypoint = this.readD(linkAttr, "TargetWaypoint");
                                if (!this.links.containsKey(sourceWaypoint)) {
                                    this.links.put(sourceWaypoint, new ArrayList<>());
                                }
                                this.links.get(sourceWaypoint).add(targetWaypoint);
                            }
                    );
                    break;
                }
                case "GroupList": {
                    List<Node> groupList = XmlUtils.asList(docNodes.getChildNodes());
                    groupList.stream().filter(groupNode -> groupNode.getNodeName().equalsIgnoreCase("group")).forEach(groupNode -> {
                                if (this.groupName == null) {
                                    NamedNodeMap linkAttr = groupNode.getAttributes();
                                    this.groupName = this.readS(linkAttr, "Name").toLowerCase();
                                    this.groupKey = this.readS(linkAttr, "Key");
                                } else {
                                    log.warn("Found > 1 Groups in waypoint definitions! GroupName=[{}]", this.groupName);
                                }
                            }
                    );
                    break;
                }
                case "RouteList": {
                    List<Node> routeList = XmlUtils.asList(docNodes.getChildNodes());
                    routeList.stream().filter(routeNode -> routeNode.getNodeName().equalsIgnoreCase("route")).forEach(routeNode -> {
                                WaypointRouteT waypointRoute = new WaypointRouteT(routeNode);
                                this.routes.put(waypointRoute.getName(), waypointRoute);
                            }
                    );
                }
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public String getSubName() {
        return this.subName;
    }

    public Map<Integer, WaypointT> getWaypointsByKey() {
        return this.waypointsByKey;
    }

    public WaypointT getWaypoint(int waypointKey) {
        return this.waypointsByKey.get(waypointKey);
    }

    public WaypointT getWaypoint(String waypointName) {
        return this.waypoints.get(waypointName);
    }

    public Map<String, WaypointRouteT> getRoutes() {
        return this.routes;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public String getGroupKey() {
        return this.groupKey;
    }
}