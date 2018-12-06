package com.bdoemu.gameserver.dataholders.xml;

import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.commons.xml.XmlDataHolderRoot;
import com.bdoemu.commons.xml.factory.NodeForEach;
import com.bdoemu.commons.xml.models.XmlDataHolder;
import com.bdoemu.gameserver.model.creature.player.enums.EClassType;
import com.bdoemu.gameserver.model.items.templates.OpenBox31T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

@XmlDataHolderRoot("boxes")
public class OpenBox31Data extends XmlDataHolder {
    private static final Logger log = LoggerFactory.getLogger(OpenBox31Data.class);
    private HashMap<Integer, EnumMap<EClassType, List<OpenBox31T>>> boxes;
    public OpenBox31Data() {
        this.boxes = new HashMap<>();
    }

    public static OpenBox31Data getInstance() {
        return Holder.INSTANCE;
    }

    public List<OpenBox31T> getTemplate(final int boxId, final EClassType classType) {
        if (!this.boxes.containsKey(boxId)) {
            OpenBox31Data.log.warn("OpenBox31Data missed box data for BoxId: " + boxId);
            return null;
        }
        return this.boxes.get(boxId).get(classType);
    }

    public void loadData(final NodeList nList) {
        ServerInfoUtils.printSection("OpenBox31Data Loading");
        new NodeForEach(nList.item(0).getChildNodes()) {
            protected boolean read(final Node nNode) {
                if (!nNode.getNodeName().equalsIgnoreCase("box")) {
                    return true;
                }
                final int boxitemKey = Integer.parseInt(nNode.getAttributes().getNamedItem("boxitemKey").getNodeValue());
                OpenBox31Data.this.boxes.put(boxitemKey, new EnumMap<>(EClassType.class));
                new NodeForEach(nNode.getChildNodes()) {
                    protected boolean read(final Node nNode) {
                        if (!nNode.getNodeName().equalsIgnoreCase("item")) {
                            return true;
                        }
                        final OpenBox31T openBox31T = new OpenBox31T(nNode);
                        if (!OpenBox31Data.this.boxes.get(boxitemKey).containsKey(openBox31T.getClassType())) {
                            OpenBox31Data.this.boxes.get(boxitemKey).put(openBox31T.getClassType(), new ArrayList<>());
                        }
                        OpenBox31Data.this.boxes.get(boxitemKey).get(openBox31T.getClassType()).add(openBox31T);
                        return true;
                    }
                };
                return true;
            }
        };
        OpenBox31Data.log.info("Loaded {} box entries.", this.boxes.size());
    }

    private static class Holder {
        static final OpenBox31Data INSTANCE = new OpenBox31Data();
    }
}
