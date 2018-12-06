package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.rmi.model.LoginAccountInfo;
import com.bdoemu.core.configs.NetworkConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.utils.WriteCharacterInfo;
import com.bdoemu.gameserver.dataholders.DyeingItemData;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.dataholders.PCSetData;
import com.bdoemu.gameserver.model.creature.player.templates.PCSetTemplate;
import com.bdoemu.gameserver.model.items.templates.DyeingItemT;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

import java.util.Collection;
import java.util.TreeMap;

public class SMLoginUserToFieldServer extends WriteCharacterInfo {
    private final GameClient client;
    private final LoginAccountInfo loginAccountInfo;
    private final Collection<BasicDBObject> players;
    private final int characterSlots;
    private final int packetNr;
    private final int characterSize;

    public SMLoginUserToFieldServer(final GameClient client, final LoginAccountInfo loginAccountInfo, final Collection<BasicDBObject> players, final int packetNr) {
        if (NetworkConfig.ENCRYPT_PACKETS) {
            this.setEncrypt(true);
        }
        this.characterSize = loginAccountInfo.getPlayers().size();
        this.client = client;
        this.loginAccountInfo = loginAccountInfo;
        this.players = players;
        this.characterSlots = loginAccountInfo.getCharacterSlots();
        this.packetNr = packetNr;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.packetNr);
        buffer.writeD(NetworkConfig.SERVER_VERSION);
        buffer.writeD(this.client.getObjectId());
        buffer.writeD(0);
        buffer.writeQ(this.loginAccountInfo.getAccountId());
        buffer.writeS(this.loginAccountInfo.getFamily(), 62);
        buffer.writeH(-1);
        buffer.writeH(this.characterSlots);
        buffer.writeC(this.characterSize);
        buffer.writeC(0);
        buffer.writeQ(-1L);
        buffer.writeQ(0L);
        buffer.writeC(this.players.size());
        for (final BasicDBObject player : this.players) {
            final BasicDBObject location = (BasicDBObject) player.get("location");
            final BasicDBObject equipment = (BasicDBObject) ((BasicDBObject) player.get("playerBag")).get("Equipments");
            final String name = player.getString("name");
            final PCSetTemplate pcSetTemplate = PCSetData.getInstance().getTemplate(player.getInt("classType"));
            buffer.writeH(pcSetTemplate.getCharacterKey());
            buffer.writeQ(player.getLong("_id"));
            buffer.writeC(player.getInt("slot"));
            buffer.writeS(name, 62);
            buffer.writeD(player.getInt("level"));
            buffer.writeD(player.getInt("equipSlotCacheCount"));
            final BasicDBList itemsDB = (BasicDBList) equipment.get("items");
            final TreeMap<Integer, BasicDBObject> equipmentMap = new TreeMap<Integer, BasicDBObject>();
            for (final Object anItemsDB : itemsDB) {
                final BasicDBObject itemDB = (BasicDBObject) anItemsDB;
                equipmentMap.put(itemDB.getInt("index"), itemDB);
            }
            for (int index = 0; index < 31; ++index) {
                final BasicDBObject item = equipmentMap.get(index);
                if (item != null) {
                    final int itemId = item.getInt("itemId");
                    final ItemTemplate template = ItemData.getInstance().getItemTemplate(itemId);
                    buffer.writeH(itemId);
                    buffer.writeH(item.getInt("enchantLevel"));
                    buffer.writeQ((long) item.getInt("expirationPeriod"));
                    buffer.writeH((template.getEndurance() > 0) ? item.getInt("endurance") : 32767);
                    final BasicDBList palettesDB = (BasicDBList) item.get("colorPalettes");
                    final TreeMap<Integer, DyeingItemT> colorPalettes = new TreeMap<Integer, DyeingItemT>();
                    for (final Object aPalettesDB : palettesDB) {
                        final BasicDBObject obj = (BasicDBObject) aPalettesDB;
                        colorPalettes.put(obj.getInt("index"), DyeingItemData.getInstance().getTemplate(obj.getInt("itemId")));
                    }
                    for (int paletteIndex = 0; paletteIndex < 12; ++paletteIndex) {
                        final DyeingItemT dyeingItemT = colorPalettes.get(paletteIndex);
                        if (dyeingItemT != null) {
                            buffer.writeC(dyeingItemT.getPaletteType());
                            buffer.writeC(dyeingItemT.getPaletteIndex());
                        } else {
                            buffer.writeC(-1);
                            buffer.writeC(-1);
                        }
                    }
                    buffer.writeC(item.getInt("colorPaletteType", 0));
                } else {
                    buffer.writeH(0);
                    buffer.writeH(0);
                    buffer.writeQ(-1L);
                    buffer.writeH(-2);
                    buffer.writeH(-1);
                    buffer.writeH(-1);
                    buffer.writeH(-1);
                    buffer.writeH(-1);
                    buffer.writeH(-1);
                    buffer.writeH(-1);
                    buffer.writeH(-1);
                    buffer.writeH(-1);
                    buffer.writeH(-1);
                    buffer.writeH(-1);
                    buffer.writeH(-1);
                    buffer.writeH(-1);
                    buffer.writeC(0);
                }
            }
            final BasicDBObject playerAppearance = (BasicDBObject) player.get("appearance");
            final BasicDBObject face = (BasicDBObject) playerAppearance.get("face");
            final BasicDBObject hairs = (BasicDBObject) playerAppearance.get("hairs");
            final BasicDBObject beard = (BasicDBObject) face.get("beard");
            final BasicDBObject mustache = (BasicDBObject) face.get("mustache");
            final BasicDBObject whiskers = (BasicDBObject) face.get("whiskers");
            final BasicDBObject eyebrows = (BasicDBObject) face.get("eyebrows");
            buffer.writeC(face.getInt("_id"));
            buffer.writeC(hairs.getInt("_id"));
            buffer.writeC(beard.getInt("_id"));
            buffer.writeC(mustache.getInt("_id"));
            buffer.writeC(whiskers.getInt("_id"));
            buffer.writeC(eyebrows.getInt("_id"));
            buffer.writeD(player.getInt("basicCacheCount"));
            buffer.writeC(player.getInt("zodiac"));
            this.writeAppearanceData(buffer, player, this.loginAccountInfo.getFamily());
            buffer.writeQ(player.getLong("deletionDate"));
            buffer.writeC(0);
            buffer.writeQ(player.getLong("lastLogin") / 1000L);
            buffer.writeQ(player.getLong("creationDate") / 1000L);
            buffer.writeF(location.getDouble("x"));
            buffer.writeF(location.getDouble("z"));
            buffer.writeF(location.getDouble("y"));
            buffer.writeQ(player.getLong("blockDate"));
            buffer.writeH(0);
            buffer.writeQ(player.getLong("lastLogin") / 1000L);
            buffer.writeB(new byte[58]);
            buffer.writeC(0);
        }
    }
}
