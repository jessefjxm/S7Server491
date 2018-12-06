// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.dataholders.MiniGameRewardData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.AddItemEvent;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.services.ItemMainGroupService;
import com.bdoemu.gameserver.model.items.templates.ItemSubGroupT;
import com.bdoemu.gameserver.model.minigame.template.MiniGameT;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CMMiniGameResult extends ReceivablePacket<GameClient> {
    private boolean result;
    private short gameId;

    public CMMiniGameResult(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.gameId = this.readH();
        this.result = this.readCB();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        final MiniGameT miniGameT = MiniGameRewardData.getInstance().getTemplate(this.gameId);
        if (player != null && miniGameT != null) {
            final Servant servant = player.getCurrentVehicle();
            if (servant != null && servant.getTemplate().getVehicleType().getId() == miniGameT.getCheckVehicle()) {
                if (this.result && player.addWp(-miniGameT.getSuccessWp())) {
                    final List<ItemSubGroupT> items = ItemMainGroupService.getItems(player, miniGameT.getDropGroup());
                    final ConcurrentLinkedQueue<Item> addItems = new ConcurrentLinkedQueue<Item>();
                    for (final ItemSubGroupT itemSubGroupT : items) {
                        addItems.add(new Item(itemSubGroupT.getItemId(), Rnd.get(itemSubGroupT.getMinCount(), itemSubGroupT.getMaxCount()), itemSubGroupT.getEnchantLevel()));
                    }
                    player.getPlayerBag().onEvent(new AddItemEvent(player, addItems, EStringTable.eErrNoItemIsCreareToAlchemy));
                } else {
                    player.addWp(-miniGameT.getFailWp());
                }
            }
        }
    }
}
