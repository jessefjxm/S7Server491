package com.bdoemu.gameserver.model.creature.player.services;

import com.bdoemu.commons.rmi.model.LoginAccountInfo;
import com.bdoemu.commons.thread.APeriodicTaskService;
import com.bdoemu.core.configs.LocalizingOptionConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMCreateCharacterToFieldNak;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.databaseCollections.AccountsDBCollection;
import com.bdoemu.gameserver.databaseCollections.PlayersDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.appearance.PlayerAppearanceStorage;
import com.bdoemu.gameserver.model.creature.player.enums.EZodiacType;
import com.bdoemu.gameserver.model.creature.player.templates.PCSetTemplate;
import com.bdoemu.gameserver.service.database.DatabaseLogFactory;
import com.bdoemu.gameserver.worldInstance.World;
import com.mongodb.BasicDBObject;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@StartupComponent("Service")
public class PlayerSaveService extends APeriodicTaskService {
    private static class Holder {
        static final PlayerSaveService INSTANCE = new PlayerSaveService();
    }

    private PlayerSaveService() {
        super(15L, TimeUnit.MINUTES, true);
    }

    public static PlayerSaveService getInstance() {
        return Holder.INSTANCE;
    }

    public void run() {
        this.saveAllPlayers(false);
        DatabaseLogFactory.getInstance().logOnline(World.getInstance().getPlayers().size());
    }

    public void saveAllPlayers(final boolean disconnect) {
        for (final Player player : World.getInstance().getPlayers()) {
            player.save();

            if (disconnect && player.getClient() != null)
                player.getClient().closeForce();
        }
    }

    public Player createPlayer(final String name, final int slot, final EZodiacType zodiacType, final PCSetTemplate playerTemplate, final PlayerAppearanceStorage playerAppearance, final GameClient client) {
        synchronized (Holder.INSTANCE) {
            if (PlayersDBCollection.getInstance().exists("name", name)) {
                client.sendPacket(SMCreateCharacterToFieldNak.NAME_ALREADY_EXIST);
                return null;
            }
            final LoginAccountInfo loginAccountInfo = client.getLoginAccountInfo();
            if (loginAccountInfo.getPlayers().size() >= LocalizingOptionConfig.DEFAULT_CHARACTER_SLOT + loginAccountInfo.getCharacterSlots())
                return null;

            final Player player = new Player(name, slot, zodiacType, playerTemplate, playerAppearance, client);
            for (int l = 2; l <= 6; ++l) {
                player.setLevel(l);
                player.setExp(300);
                player.onLevelChange(false);
            }
            PlayersDBCollection.getInstance().save(player);
            if (!AccountsDBCollection.getInstance().exists(player.getAccountId()))
                AccountsDBCollection.getInstance().save(player.getAccountData());
            loginAccountInfo.addPlayer((BasicDBObject) player.toDBObject());
            return player;
        }
    }

    public boolean updateName(final Player player, final String name) {
        synchronized (Holder.INSTANCE) {
            if (PlayersDBCollection.getInstance().exists("name", name)) {
                player.sendPacket(SMCreateCharacterToFieldNak.NAME_ALREADY_EXIST);
                return false;
            }

            PlayersDBCollection.getInstance().updateField(player.getObjectId(), "name", name);
            return true;
        }
    }
}
