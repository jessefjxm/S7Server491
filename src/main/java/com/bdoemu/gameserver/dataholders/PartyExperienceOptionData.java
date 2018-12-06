// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "partyexperienceoption", group = "sqlite")
@StartupComponent("Data")
public class PartyExperienceOptionData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;
    private static int[] table;

    static {
        log = LoggerFactory.getLogger((Class) PartyExperienceOptionData.class);
        instance = new AtomicReference<Object>();
        PartyExperienceOptionData.table = new int[4];
    }

    private PartyExperienceOptionData() {
        this.load();
    }

    public static PartyExperienceOptionData getInstance() {
        Object value = PartyExperienceOptionData.instance.get();
        if (value == null) {
            synchronized (PartyExperienceOptionData.instance) {
                value = PartyExperienceOptionData.instance.get();
                if (value == null) {
                    final PartyExperienceOptionData actualValue = new PartyExperienceOptionData();
                    value = ((actualValue == null) ? PartyExperienceOptionData.instance : actualValue);
                    PartyExperienceOptionData.instance.set(value);
                }
            }
        }
        return (PartyExperienceOptionData) ((value == PartyExperienceOptionData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("PartyExperienceOptionData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'PartyExperienceOption'")) {
            while (rs.next()) {
                final int memberCount = rs.getInt("MemberCount");
                final int allPartyExp = rs.getInt(String.valueOf(ServerConfig.GAME_SERVICE_TYPE.ordinal()));
                PartyExperienceOptionData.table[memberCount - 2] = allPartyExp / memberCount / 10000;
            }
            PartyExperienceOptionData.log.info("Loaded {} PartyExperienceOptionData's", (Object) PartyExperienceOptionData.table.length);
        } catch (SQLException ex) {
            PartyExperienceOptionData.log.error("Failed load PartyExperienceOptionData", (Throwable) ex);
        }
    }

    public double getExperience(final IParty<Player> party, final double exp) {
        if (party.getMembersCount() >= 2) {
            final int expModifier = PartyExperienceOptionData.table[party.getMembersCount() - 2];
            return exp * expModifier / 100.0;
        }
        return exp;
    }

    public void reload() {
        this.load();
    }
}
