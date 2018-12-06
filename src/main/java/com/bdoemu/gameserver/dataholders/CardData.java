package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.npc.card.templates.CardTemplate;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "card", group = "sqlite")
@StartupComponent("Data")
public class CardData implements IReloadable {
    private static class Holder {
        static final CardData INSTANCE = new CardData();
    }
    private static final Logger log = LoggerFactory.getLogger(CardData.class);
    private HashMap<Integer, CardTemplate> templates;

    private CardData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static CardData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("CardData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Card_Table'")) {
            int counter = 0;
            while (rs.next()) {
                ++counter;
                final CardTemplate template = new CardTemplate(rs);
                if (ContentsGroupOptionData.getInstance().isContentsGroupOpen(template.getContentsGroupKey())) {
                    this.templates.put(template.getCardId(), template);
                }
            }
            CardData.log.info("Loaded {} CardData's. ({} disabled by ContentsGroupOptionData)", this.templates.size(), (Object) (counter - this.templates.size()));
        } catch (SQLException ex) {
            CardData.log.error("Failed load CardTemplate", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public CardTemplate getTemplate(final int cardId) {
        return this.templates.get(cardId);
    }
}
