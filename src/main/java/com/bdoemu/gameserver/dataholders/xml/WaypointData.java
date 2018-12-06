package com.bdoemu.gameserver.dataholders.xml;

import com.bdoemu.commons.xml.XmlDataHolderRoot;
import com.bdoemu.commons.xml.models.XmlDataHolder;
import com.bdoemu.gameserver.model.waypoints.WaypointDataT;
import com.bdoemu.gameserver.model.waypoints.WaypointT;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlDataHolderRoot("WaypointData")
public class WaypointData extends XmlDataHolder {
    private static final Map<String, List<WaypointDataT>> waypoints = new HashMap<String, List<WaypointDataT>>();
    private static final Map<Integer, WaypointT> waypointsByKey = new HashMap<Integer, WaypointT>();

    public static WaypointData getInstance() {
        return Holder.INSTANCE;
    }

    public void loadData(final NodeList nList) {
        final WaypointDataT waypointTemplate = new WaypointDataT(nList.item(0));
        if (!WaypointData.waypoints.containsKey(waypointTemplate.getName())) {
            WaypointData.waypoints.put(waypointTemplate.getName(), new ArrayList<WaypointDataT>());
        }
        WaypointData.waypoints.get(waypointTemplate.getName()).add(waypointTemplate);
        WaypointData.waypointsByKey.putAll(waypointTemplate.getWaypointsByKey());
    }

    public WaypointT getWaypoint(final String fileName, final String waypointName) {
        if (WaypointData.waypoints.containsKey(fileName)) {
            for (final WaypointDataT waypointData : WaypointData.waypoints.get(fileName)) {
                final WaypointT waypointTemplate = waypointData.getWaypoint(waypointName);
                if (waypointTemplate != null) {
                    return waypointTemplate;
                }
            }
        }
        return null;
    }

    public WaypointT getWaypoint(Integer[] waypointData) {
        final int waypointKey = waypointData[1];
        final int waypointType = waypointData[0];
        return WaypointData.waypointsByKey.get(waypointKey);
    }

    public WaypointT getWaypoint(final int waypointKey) {
        return WaypointData.waypointsByKey.get(waypointKey);
    }

    public WaypointT getWaypoint(final String fileName, final int waypointKey) {
        for (final WaypointDataT waypointData : WaypointData.waypoints.get(fileName)) {
            final WaypointT waypointTemplate = waypointData.getWaypoint(waypointKey);
            if (waypointTemplate != null) {
                return waypointTemplate;
            }
        }
        return null;
    }

    private static class Holder {
        static final WaypointData INSTANCE = new WaypointData();
    }
}
