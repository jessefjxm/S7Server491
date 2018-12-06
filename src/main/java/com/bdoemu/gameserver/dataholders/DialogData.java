package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.npc.dialogs.templates.DialogTemplate;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Reloadable(name = "dialog", group = "sqlite")
@StartupComponent("Data")
public class DialogData implements IReloadable {
    private static class Holder {
        static final DialogData INSTANCE = new DialogData();
    }
    private static final Logger log = LoggerFactory.getLogger(DialogData.class);
    private HashMap<Integer, HashMap<Integer, List<DialogTemplate>>> templates;

    public DialogData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static DialogData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("DialogData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Dialog_Table'")) {
            while (rs.next()) {
                final DialogTemplate template = new DialogTemplate(rs);
                final int npcId = template.getNpcId();
                final int dialogIndex = template.getDialogIndex();
                final String mainDialog = rs.getString("MainDialog");
                if (mainDialog == null || mainDialog.trim().isEmpty()) {
                    if (template.isDisabledByContentsGroup()) {
                        continue;
                    }
                    if (!this.templates.containsKey(npcId)) {
                        this.templates.put(npcId, new HashMap<>());
                    }
                    final HashMap<Integer, List<DialogTemplate>> dialogsByIndex = this.templates.get(npcId);
                    if (!dialogsByIndex.containsKey(dialogIndex)) {
                        dialogsByIndex.put(dialogIndex, new ArrayList<>());
                    }
                    dialogsByIndex.get(dialogIndex).add(template);
                }
            }
            DialogData.log.info("Loaded {} DialogTemplate's", this.templates.values().stream().mapToInt(item -> item.values().size()).sum());
        } catch (SQLException ex) {
            DialogData.log.error("Failed load DialogTemplate", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public DialogTemplate getTemplates(final int npcId, final int dialogIndex, final int index) {
        final HashMap<Integer, List<DialogTemplate>> npcDialogs = this.templates.get(npcId);
        if (npcDialogs == null) {
            DialogData.log.error("DialogTemplate npcId {} dialogIndex {} index {} bad npcId", npcId, dialogIndex, index);
            return null;
        }
        final List<DialogTemplate> dialogList = npcDialogs.get(dialogIndex);
        if (dialogList == null) {
            DialogData.log.error("DialogTemplate npcId {} dialogIndex {} index {} bad dialogIndex", npcId, dialogIndex, index);
            return null;
        }
        if (index >= dialogList.size()) {
            DialogData.log.error("DialogTemplate npcId {} dialogIndex {} index {} bad index", npcId, dialogIndex, index);
            return null;
        }
        return dialogList.get(index);
    }
}
