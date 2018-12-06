package com.bdoemu.gameserver.dataholders.xml;

import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.commons.xml.XmlDataHolderRoot;
import com.bdoemu.commons.xml.factory.NodeForEach;
import com.bdoemu.commons.xml.models.XmlDataHolder;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.world.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

@XmlDataHolderRoot("ServantInitSpawns")
public class ServantSpawnInitData extends XmlDataHolder {
    private static final Logger log = LoggerFactory.getLogger(ServantSpawnInitData.class);
    private static Map<Integer, Map<Integer, Location>> table = new HashMap<>();

    public static ServantSpawnInitData getInstance() {
        return Holder.INSTANCE;
    }

    public void loadData(final NodeList nList) {
        ServerInfoUtils.printSection("ServantInitSpawns Loading");
        new NodeForEach(nList.item(0).getChildNodes()) {
            protected boolean read(final Node nNode) {
                if (!nNode.getNodeName().equalsIgnoreCase("ServantInitSpawn")) {
                    return true;
                }
                final int npcId = Integer.parseInt(nNode.getAttributes().getNamedItem("NpcId").getNodeValue());
                new NodeForEach(nNode.getChildNodes()) {
                    protected boolean read(final Node nNode) {
                        if (!nNode.getNodeName().equalsIgnoreCase("SpawnLocation")) {
                            return true;
                        }
                        final NamedNodeMap attrs = nNode.getAttributes();
                        final int typeId = Integer.parseInt(attrs.getNamedItem("Type").getNodeValue());
                        final double x = Double.parseDouble(attrs.getNamedItem("X").getNodeValue());
                        final double y = Double.parseDouble(attrs.getNamedItem("Y").getNodeValue());
                        final double z = Double.parseDouble(attrs.getNamedItem("Z").getNodeValue());
                        final double direction = Double.parseDouble(attrs.getNamedItem("Direction").getNodeValue());
                        final Location spawnLocation = new Location(x, y, z);
                        spawnLocation.setDirection(direction);
                        if (!ServantSpawnInitData.table.containsKey(npcId)) {
                            ServantSpawnInitData.table.put(npcId, new HashMap<>());
                        }
                        ServantSpawnInitData.table.get(npcId).put(typeId, spawnLocation);
                        return true;
                    }
                };
                return true;
            }
        };
    }

    public Location getSpawnLocation(final int npcId, final Servant servant) {
        if (ServantSpawnInitData.table.containsKey(npcId)) {
            final Map<Integer, Location> locations = ServantSpawnInitData.table.get(npcId);
            int typeIndex;
            switch (servant.getTemplate().getVehicleType()) {
                case Horse: {
                    typeIndex = 1;
                    break;
                }
                case Donkey:
                case SailingBoat: {
                    typeIndex = 2;
                    break;
                }
                case Camel: {
                    typeIndex = 3;
                    break;
                }
                case Carriage: {
                    typeIndex = 4;
                    break;
                }
                case Elephant:
                case RidableBabyElephant: {
                    typeIndex = 5;
                    break;
                }
                default: {
                    typeIndex = 1;
                    break;
                }
            }
            return locations.get(typeIndex);
        }
        ServantSpawnInitData.log.warn("Spawn placement for servants didn't exist in table (npcId={})!", npcId);
        return null;
    }

    private static class Holder {
        static final ServantSpawnInitData INSTANCE = new ServantSpawnInitData();
    }
}
