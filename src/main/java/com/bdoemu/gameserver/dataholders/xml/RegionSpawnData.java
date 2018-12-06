package com.bdoemu.gameserver.dataholders.xml;

import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.commons.xml.XmlDataHolderRoot;
import com.bdoemu.commons.xml.factory.NodeForEach;
import com.bdoemu.commons.xml.models.XmlDataHolder;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.services.SpawnService;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@XmlDataHolderRoot("RegionSpawnInfo")
public class RegionSpawnData extends XmlDataHolder {
    public static RegionSpawnData getInstance() {
        return Holder.INSTANCE;
    }

    public void loadData(final NodeList nList) {
        ServerInfoUtils.printSection("RegionSpawnData Loading");
        new NodeForEach(nList.item(0).getChildNodes()) {
            protected boolean read(final Node nNode) {
                if (!nNode.getNodeName().equalsIgnoreCase("RegionInfo")) {
                    return true;
                }
                final int regionId = Integer.parseInt(nNode.getAttributes().getNamedItem("Key").getNodeValue());
                new NodeForEach(nNode.getChildNodes()) {
                    protected boolean read(final Node nNode) {
                        if (!nNode.getNodeName().equalsIgnoreCase("SpawnInfo")) {
                            return true;
                        }
                        final SpawnPlacementT template = new SpawnPlacementT(nNode, true);
                        template.setRegionId(regionId);
                        SpawnService.getInstance().addSpawn(template);
                        return true;
                    }
                };
                return true;
            }
        };
    }

    private static class Holder {
        static final RegionSpawnData INSTANCE = new RegionSpawnData();
    }
}
