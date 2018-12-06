package com.bdoemu.gameserver.dataholders.xml;

import com.bdoemu.commons.xml.XmlDataHolderRoot;
import com.bdoemu.commons.xml.factory.NodeForEach;
import com.bdoemu.commons.xml.models.XmlDataHolder;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.services.SpawnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@XmlDataHolderRoot("SpawnPlacementList")
public class SpawnPlacementData extends XmlDataHolder {
    private static final Logger log = LoggerFactory.getLogger((Class) SpawnPlacementData.class);

    public static SpawnPlacementData getInstance() {
        return Holder.INSTANCE;
    }

    public void loadData(final NodeList nList) {
        new NodeForEach(nList.item(0).getChildNodes()) {
            protected boolean read(final Node nNode) {
                if (!nNode.getNodeName().equalsIgnoreCase("SpawnPlacement")) {
                    return true;
                }
                final SpawnPlacementT template = new SpawnPlacementT(nNode, false);
                final long key = template.getKey();
                if (key != 0L) {
                    SpawnService.getInstance().addSpawnStatic(key, template);
                } else {
                    SpawnService.getInstance().addSpawn(template);
                }
                return true;
            }
        };
    }

    private static class Holder {
        static final SpawnPlacementData INSTANCE = new SpawnPlacementData();
    }
}
