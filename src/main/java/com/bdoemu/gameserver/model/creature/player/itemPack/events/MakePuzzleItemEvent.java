package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.gameserver.dataholders.ItemPuzzleData;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.ItemPuzzleT;

public class MakePuzzleItemEvent extends AItemEvent {
    private int puzzleKey;
    private int slotIndex;
    private EItemStorageLocation storageType;

    public MakePuzzleItemEvent(final Player player, final int puzzleKey, final int slotIndex, final EItemStorageLocation storageType) {
        super(player, player, player, EStringTable.eErrNoItemIsCreareToAlchemy, EStringTable.eErrNoItemIsRemovedToAlchemy, 0);
        this.puzzleKey = puzzleKey;
        this.slotIndex = slotIndex;
        this.storageType = storageType;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        if (!this.addTasks.isEmpty()) {
            for (final Item addItem : this.addTasks) {
                this.player.getObserveController().notifyObserver(EObserveType.gatherItem, addItem.getItemId(), addItem.getEnchantLevel(), addItem.getCount(), player.getObjectId());
            }
        }
    }

    @Override
    public boolean canAct() {
        final ItemPuzzleT template = ItemPuzzleData.getInstance().getTemplate(this.puzzleKey);
        if (template == null) {
            return false;
        }
        final ItemPack pack = this.storageType.isPlayerInventories() ? this.playerInventory : this.cashInventory;
        if (template.getMakeItemId() == 0) {
            return false;
        }
        final int mainRow = (int) Math.ceil((this.slotIndex - 1) / 8.0f);
        final int[][] puzzles = template.getPuzzles();
        Integer firstItemIndex = null;
        for (int index = 0; index < puzzles.length; ++index) {
            final int currentRow = mainRow + index;
            for (int i = 0; i < puzzles[index].length; ++i) {
                final int itemId = puzzles[index][i];
                if (itemId > 0) {
                    if (firstItemIndex == null) {
                        final int maxSlotInRow = mainRow * 8 + 2;
                        final int minSlotInRow = maxSlotInRow - 8;
                        firstItemIndex = this.slotIndex - minSlotInRow + 1 - i;
                        if (firstItemIndex < 0) {
                            return false;
                        }
                        final Item item = pack.getItem(this.slotIndex);
                        if (item == null || item.getItemId() != itemId) {
                            return false;
                        }
                        this.decreaseItem(this.slotIndex, 1L, pack.getLocationType());
                    } else {
                        final int maxSlotInRow = currentRow * 8 + 2;
                        final int minSlotInRow = maxSlotInRow - 8;
                        final int nextSlot = currentRow * 8 - 8 + 1 + (firstItemIndex + i);
                        if (nextSlot < minSlotInRow || nextSlot >= maxSlotInRow) {
                            return false;
                        }
                        final Item item2 = pack.getItem(nextSlot);
                        if (item2 == null || item2.getItemId() != itemId) {
                            return false;
                        }
                        this.decreaseItem(nextSlot, 1L, pack.getLocationType());
                    }
                }
            }
        }
        this.addItem(template.getMakeItemId(), 1L, template.getEnchantLevel());
        return super.canAct();
    }
}
