package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.servant.model.ServantFormT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "form", group = "sqlite")
@StartupComponent("Data")
public class FormData implements IReloadable {
    private static class Holder {
        static final FormData INSTANCE = new FormData();
    }
    private static final Logger log = LoggerFactory.getLogger(FormData.class);
    private Map<Integer, ServantFormT> templates;

    private FormData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static FormData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("FormData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Form_Table'")) {
            while (rs.next()) {
                final ServantFormT template = new ServantFormT(rs);
                final int index = template.getIndex();
                this.templates.put(index, template);
            }
            FormData.log.info("Loaded {} ServantFormTemplate's", this.templates.size());
        } catch (SQLException ex) {
            FormData.log.error("Failed load ServantFormT", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public ServantFormT getForm(final int formId) {
        return this.templates.get(formId);
    }

    public int getActionIndexByFormId(final int formId) {
        if (this.templates.containsKey(formId)) {
            return this.templates.get(formId).getActionIndex();
        }
        return 0;
    }
}
