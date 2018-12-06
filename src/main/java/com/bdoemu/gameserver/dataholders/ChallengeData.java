package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.challenge.templates.ChallengeT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "challenge", group = "sqlite")
@StartupComponent("Data")
public class ChallengeData implements IReloadable {
    private static class Holder {
        static final ChallengeData INSTANCE = new ChallengeData();
    }
    private static final Logger log = LoggerFactory.getLogger(ChallengeData.class);
    private HashMap<Integer, ChallengeT> templates;

    private ChallengeData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static ChallengeData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("ChallengeData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Challenge_Table'")) {
            while (rs.next()) {
                final ChallengeT template = new ChallengeT(rs);
                this.templates.put(template.getChallengeId(), template);
            }
            ChallengeData.log.info("Loaded {} ChallengeTemplate's", this.templates.size());
        } catch (SQLException ex) {
            ChallengeData.log.error("Failed load ChallengeT", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public ChallengeT getTemplate(final int challengeId) {
        return this.templates.get(challengeId);
    }

    public Collection<ChallengeT> getTemplates() {
        return this.templates.values();
    }
}
