// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.sendable.SMMakeThemeBook;
import com.bdoemu.gameserver.dataholders.ThemeData;
import com.bdoemu.gameserver.model.creature.npc.card.Card;
import com.bdoemu.gameserver.model.creature.npc.card.enums.ECardGradeType;
import com.bdoemu.gameserver.model.creature.npc.theme.Theme;
import com.bdoemu.gameserver.model.creature.npc.theme.templates.ThemeTemplate;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.misc.enums.EGamePointType;

import java.util.ArrayList;
import java.util.Collection;

public class MakeThemeBookEvent extends AItemEvent {
    private int themeId;
    private Collection<Card> mentalCards;

    public MakeThemeBookEvent(final Player player, final int themeId) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.themeId = themeId;
    }

    public static int getMoney(final int cardLevelSumma, final int cardMaxLevelSumma) {
        final int v3 = 100 * cardLevelSumma / cardMaxLevelSumma;
        int result;
        if (v3 < 50) {
            result = 100000;
        } else if (v3 >= 80) {
            if (v3 >= 100) {
                result = 1000;
            } else {
                result = 10000;
            }
        } else {
            result = 50000;
        }
        return result;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendPacket(new SMMakeThemeBook(this.mentalCards));
    }

    @Override
    public boolean canAct() {
        final ThemeTemplate themeTemplate = ThemeData.getInstance().getTemplate(this.themeId);
        if (themeTemplate == null) {
            return false;
        }
        final Integer bookCaseId = themeTemplate.getBookCase();
        if (bookCaseId == null) {
            return false;
        }
        this.addItem(bookCaseId, 1L, 0);
        final Theme theme = this.player.getMentalCardHandler().getTheme(this.themeId);
        if (theme == null || !theme.isFull()) {
            return false;
        }
        int cardLevelSumma = 0;
        this.mentalCards = new ArrayList<Card>();
        int count = Rnd.get(1, theme.getTemplate().getNeedCount2());
        for (final Card card : theme.getCards().values()) {
            cardLevelSumma += card.getCardGradeType().getLevel();
            if (count > 0) {
                this.mentalCards.add(card);
                theme.getCards().remove(card.getCardId());
            }
            --count;
        }
        this.player.getObserveController().notifyObserver(EObserveType.acquirePoint, EGamePointType.WP.ordinal(), this.player.getMaxWp());
        this.decreaseItem(0, getMoney(cardLevelSumma, theme.getTemplate().getNeedCount2() * ECardGradeType.S.getLevel()), EItemStorageLocation.Inventory);
        return super.canAct();
    }
}
