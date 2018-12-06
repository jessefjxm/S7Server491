package com.bdoemu.gameserver.model.creature.npc.templates;

import com.bdoemu.commons.utils.XMLNode;
import com.bdoemu.commons.xml.factory.NodeForEach;
import com.bdoemu.gameserver.model.world.Location;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class SpawnPlacementT extends XMLNode {
    private int regionId;
    private int creatureId;
    private int spawnQuantity;
    private boolean isSpawnOnBoot = true;
    private int dialogIndex;
    private long key;
    private Location location;
    private int actionIndex;
    private int function = -1;
    private boolean useCustomSpawnTimeData = false;
    private int spawnStartHourFromSpawn = -1;
    private int spawnEndHourFromSpawn = -1;
    private int sectorX;
    private int sectorY;
    private int sectorZ;
    private int staticId;
    private int index;

    public SpawnPlacementT(Location location) {
        this.location = location;
    }

    public SpawnPlacementT(int creatureId, Location loc, int staticId, int index, int sectorX, int sectorY, int sectorZ) {
        this.creatureId = creatureId;
        this.location = loc;
        this.staticId = staticId;
        this.index = index;
        this.sectorX = sectorX;
        this.sectorY = sectorY;
        this.sectorZ = sectorZ;
    }

    public SpawnPlacementT(int creatureId, int formIndex, Location loc) {
        this.creatureId = creatureId;
        this.actionIndex = formIndex;
        this.location = new Location(loc);
    }

    public SpawnPlacementT(Node node, boolean isRegionClientData) {
        this.location = new Location();
        if (isRegionClientData) {
            NamedNodeMap _attributes = node.getAttributes();
            if (_attributes.getNamedItem("key") != null) {
                this.creatureId = this.readD(_attributes, "key");
            }
            if (_attributes.getNamedItem("dialogIndex") != null) {
                this.dialogIndex = this.readD(_attributes, "dialogIndex");
            }
            if (_attributes.getNamedItem("actionIndex") != null) {
                this.actionIndex = this.readD(_attributes, "actionIndex");
            }
            if (_attributes.getNamedItem("function") != null) {
                this.function = this.readD(_attributes, "function");
            }
            if (_attributes.getNamedItem("direction") != null) {
                this.location.setDirection((double) this.readF(_attributes, "direction"));
            }
            if (_attributes.getNamedItem("position") != null) {
                String position = this.readS(_attributes, "position");
                position = position.replaceAll("\\{", "");
                position = position.replaceAll("\\}", "");
                String[] coordinates = position.split(",");
                this.location.setXYZ((double) Float.parseFloat(coordinates[0]), (double) Float.parseFloat(coordinates[2]), (double) Float.parseFloat(coordinates[1]));
            }
            if (_attributes.getNamedItem("isUseCustomSpawnTimeData") != null) {
                this.useCustomSpawnTimeData = this.readBoolean(_attributes, "isUseCustomSpawnTimeData");
                if (this.useCustomSpawnTimeData) {
                    this.spawnStartHourFromSpawn = Integer.parseInt(_attributes.getNamedItem("spawnStartHourFromSpawn").getNodeValue());
                    this.spawnEndHourFromSpawn = Integer.parseInt(_attributes.getNamedItem("spawnEndHourFromSpawn").getNodeValue());
                }
            }
        } else {
            NamedNodeMap _attributes = node.getAttributes();
            if (_attributes.getNamedItem("Key") != null) {
                this.key = this.readQ(_attributes, "Key");
            }
            new NodeForEach(node.getChildNodes()) {

                protected boolean read(Node nNode) {
                    switch (nNode.getNodeName()) {
                        case "Position": {
                            NamedNodeMap attributes = nNode.getAttributes();
                            location.setXYZ((double) readF(attributes, "X"), (double) readF(attributes, "Y"), (double) readF(attributes, "Z"));
                            break;
                        }
                        case "UseSpawnGroup": {
                            NamedNodeMap attributes = nNode.getAttributes();
                            creatureId = readD(attributes, "SpawnCharacterKey");
                            spawnQuantity = readD(attributes, "SpawnQuantity");
                            if (attributes.getNamedItem("IsSpawnOnBoot") != null)
                                isSpawnOnBoot = readBoolean(attributes, "IsSpawnOnBoot");
                            else
                                isSpawnOnBoot = true;
                            break;
                        }
                        case "ActionIndex": {
                            actionIndex = Integer.parseInt(nNode.getFirstChild().getNodeValue());
                            break;
                        }
                        case "DialogIndex": {
                            dialogIndex = Integer.parseInt(nNode.getFirstChild().getNodeValue());
                            break;
                        }
                        case "Function": {
                            function = Integer.parseInt(nNode.getFirstChild().getNodeValue());
                            break;
                        }
                        case "Direction": {
                            location.setDirection((double) Float.parseFloat(nNode.getFirstChild().getNodeValue()));
                        }
                    }
                    return true;
                }
            };
        }
    }

    public int getRegionId() {
        return this.regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getCreatureId() {
        return this.creatureId;
    }

    public void setCreatureId(int creatureId) {
        this.creatureId = creatureId;
    }

    public int getSpawnQuantity() {
        return this.spawnQuantity;
    }

    public void setSpawnQuantity(int spawnQuantity) {
        this.spawnQuantity = spawnQuantity;
    }

    public int getDialogIndex() {
        return this.dialogIndex;
    }

    public void setDialogIndex(int dialogIndex) {
        this.dialogIndex = dialogIndex;
    }

    public long getKey() {
        return this.key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getActionIndex() {
        return this.actionIndex;
    }

    public void setActionIndex(int actionIndex) {
        this.actionIndex = actionIndex;
    }

    public int getFunction() {
        return this.function;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    public boolean doSpawnOnBoot() {
        return this.isSpawnOnBoot;
    }

    public boolean isUseCustomSpawnTimeData() {
        return this.useCustomSpawnTimeData;
    }

    public void setUseCustomSpawnTimeData(boolean useCustomSpawnTimeData) {
        this.useCustomSpawnTimeData = useCustomSpawnTimeData;
    }

    public int getSpawnStartHourFromSpawn() {
        return this.spawnStartHourFromSpawn;
    }

    public void setSpawnStartHourFromSpawn(int spawnStartHourFromSpawn) {
        this.spawnStartHourFromSpawn = spawnStartHourFromSpawn;
    }

    public int getSpawnEndHourFromSpawn() {
        return this.spawnEndHourFromSpawn;
    }

    public void setSpawnEndHourFromSpawn(int spawnEndHourFromSpawn) {
        this.spawnEndHourFromSpawn = spawnEndHourFromSpawn;
    }

    public int getSectorX() {
        return this.sectorX;
    }

    public void setSectorX(int sectorX) {
        this.sectorX = sectorX;
    }

    public int getSectorY() {
        return this.sectorY;
    }

    public void setSectorY(int sectorY) {
        this.sectorY = sectorY;
    }

    public int getSectorZ() {
        return this.sectorZ;
    }

    public void setSectorZ(int sectorZ) {
        this.sectorZ = sectorZ;
    }

    public int getStaticId() {
        return this.staticId;
    }

    public void setStaticId(int staticId) {
        this.staticId = staticId;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String toString() {
        return "SpawnPlacementT(regionId=" + this.getRegionId() + ", creatureId=" + this.getCreatureId() + ", spawnQuantity=" + this.getSpawnQuantity() + ", dialogIndex=" + this.getDialogIndex() + ", key=" + this.getKey() + ", location=" + (Object) this.getLocation() + ", actionIndex=" + this.getActionIndex() + ", function=" + this.getFunction() + ", useCustomSpawnTimeData=" + this.isUseCustomSpawnTimeData() + ", spawnStartHourFromSpawn=" + this.getSpawnStartHourFromSpawn() + ", spawnEndHourFromSpawn=" + this.getSpawnEndHourFromSpawn() + ", sectorX=" + this.getSectorX() + ", sectorY=" + this.getSectorY() + ", sectorZ=" + this.getSectorZ() + ", staticId=" + this.getStaticId() + ", index=" + this.getIndex() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SpawnPlacementT)) {
            return false;
        }
        SpawnPlacementT other = (SpawnPlacementT) ((Object) o);
        if (!other.canEqual((Object) this)) {
            return false;
        }
        if (this.getRegionId() != other.getRegionId()) {
            return false;
        }
        if (this.getCreatureId() != other.getCreatureId()) {
            return false;
        }
        if (this.getSpawnQuantity() != other.getSpawnQuantity()) {
            return false;
        }
        if (this.getDialogIndex() != other.getDialogIndex()) {
            return false;
        }
        if (this.getKey() != other.getKey()) {
            return false;
        }
        Location this$location = this.getLocation();
        Location other$location = other.getLocation();
        if (this$location == null ? other$location != null : !this$location.equals((Object) other$location)) {
            return false;
        }
        if (this.getActionIndex() != other.getActionIndex()) {
            return false;
        }
        if (this.getFunction() != other.getFunction()) {
            return false;
        }
        if (this.isUseCustomSpawnTimeData() != other.isUseCustomSpawnTimeData()) {
            return false;
        }
        if (this.getSpawnStartHourFromSpawn() != other.getSpawnStartHourFromSpawn()) {
            return false;
        }
        if (this.getSpawnEndHourFromSpawn() != other.getSpawnEndHourFromSpawn()) {
            return false;
        }
        if (this.getSectorX() != other.getSectorX()) {
            return false;
        }
        if (this.getSectorY() != other.getSectorY()) {
            return false;
        }
        if (this.getSectorZ() != other.getSectorZ()) {
            return false;
        }
        if (this.getStaticId() != other.getStaticId()) {
            return false;
        }
        if (this.getIndex() != other.getIndex()) {
            return false;
        }
        return true;
    }

    protected boolean canEqual(Object other) {
        return other instanceof SpawnPlacementT;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getRegionId();
        result = result * 59 + this.getCreatureId();
        result = result * 59 + this.getSpawnQuantity();
        result = result * 59 + this.getDialogIndex();
        long $key = this.getKey();
        result = result * 59 + (int) ($key >>> 32 ^ $key);
        Location $location = this.getLocation();
        result = result * 59 + ($location == null ? 43 : $location.hashCode());
        result = result * 59 + this.getActionIndex();
        result = result * 59 + this.getFunction();
        result = result * 59 + (this.isUseCustomSpawnTimeData() ? 79 : 97);
        result = result * 59 + this.getSpawnStartHourFromSpawn();
        result = result * 59 + this.getSpawnEndHourFromSpawn();
        result = result * 59 + this.getSectorX();
        result = result * 59 + this.getSectorY();
        result = result * 59 + this.getSectorZ();
        result = result * 59 + this.getStaticId();
        result = result * 59 + this.getIndex();
        return result;
    }

}
