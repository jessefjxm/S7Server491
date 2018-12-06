package com.bdoemu.gameserver.dataholders.binary;

import com.bdoemu.commons.io.FileBinaryReader;
import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.actions.templates.ActionScriptT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Reloadable(name = "actionchart", group = "binary")
@StartupComponent("Data")
public class BinaryActionChartData implements IReloadable {
    private static final Logger log = LoggerFactory.getLogger((Class) BinaryActionChartData.class);
    private final Map<String, ActionScriptT> actionScripts;
    private BinaryActionChartData() {
        this.actionScripts = new HashMap<>();
        this.load();
    }

    public static BinaryActionChartData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        try (final FileBinaryReader reader = new FileBinaryReader("./data/static_data/binary/binaryactionchart.bin")) {
            for (int actionScriptCount = reader.readD(), actionScriptIndex = 0; actionScriptIndex < actionScriptCount; ++actionScriptIndex) {
                final ActionScriptT actionScript = new ActionScriptT(reader);
                this.actionScripts.put(actionScript.getName(), actionScript);
            }
            BinaryActionChartData.log.info("Loaded {} ActionScript packages.", this.actionScripts.size());
        } catch (Exception e) {
            BinaryActionChartData.log.error("Error while reading BinaryActionChart", e);
        }
    }

    public void reload() {
        this.load();
    }

    public ActionScriptT getActionScript(final String actionScriptName) {
        return this.actionScripts.get(actionScriptName);
    }

    private static class Holder {
        static final BinaryActionChartData INSTANCE = new BinaryActionChartData();
    }
}
