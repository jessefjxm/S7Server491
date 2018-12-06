package com.bdoemu.gameserver.model.creature.servant;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.dataholders.CreatureData;
import com.bdoemu.gameserver.model.auction.ServantItemMarket;
import com.bdoemu.gameserver.model.auction.services.AuctionGoodService;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantType;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AccountServantStorage extends JSONable {
    private static final Logger log = LoggerFactory.getLogger(AccountServantStorage.class);
    private Player player;
    private ConcurrentLinkedQueue<Servant> servants;

    public AccountServantStorage(final Player player) {
        this.servants = new ConcurrentLinkedQueue<>();
        this.player = player;
    }

    public AccountServantStorage(final BasicDBObject dbObject, final long accountId) {
        this.servants = new ConcurrentLinkedQueue<>();
        final HashMap<Long, ServantItemMarket> registredServants = AuctionGoodService.getInstance().getRegisteredServants(accountId);
        final BasicDBObject vehiclesDb = (BasicDBObject) dbObject.get(EServantType.Vehicle.toString());
        for (final String key : vehiclesDb.keySet()) {
            final int townId = Integer.parseInt(key);
            final BasicDBList vehiclesByTownDb = (BasicDBList) vehiclesDb.get(key);
            for (final Object vehicleDbObjects : vehiclesByTownDb) {
                final BasicDBObject vehicleDbObject = (BasicDBObject) vehicleDbObjects;
                if (CreatureData.getInstance().getTemplate(vehicleDbObject.getInt("id")) != null) {
                    Servant servant = new Servant(vehicleDbObject);
                    servant.setRegionId(townId);
                    servant.setAccountId(accountId);
                    final ServantItemMarket registredServant = registredServants.get(servant.getObjectId());
                    if ((servant.getServantState().isRegisterMarket() || servant.getServantState().isRegisterMating()) && registredServant == null) {
                        continue;
                    }
                    if (registredServant != null) {
                        if (registredServant.isSold() && registredServant.getAuctionRegisterType().isServantMarket()) {
                            continue;
                        }
                        servant = registredServant.getServant();
                    }
                    this.servants.add(servant);
                } else {
                    AccountServantStorage.log.warn("Servant loading ignored id: [{}] duo ContentGroupOption", (Object) vehicleDbObject.getInt("id"));
                }
            }
        }
        final BasicDBObject shipsDb = (BasicDBObject) dbObject.get(EServantType.Ship.toString());
        for (final String key2 : shipsDb.keySet()) {
            final int townId2 = Integer.parseInt(key2);
            final BasicDBList shipsByTownDb = (BasicDBList) shipsDb.get(key2);
            for (final Object shipDbObjects : shipsByTownDb) {
                final BasicDBObject shipDbObject = (BasicDBObject) shipDbObjects;
                if (CreatureData.getInstance().getTemplate(shipDbObject.getInt("id")) != null) {
                    final Servant servant2 = new Servant(shipDbObject);
                    servant2.setRegionId(townId2);
                    servant2.setAccountId(accountId);
                    this.servants.add(servant2);
                } else {
                    AccountServantStorage.log.warn("Servant loading ignored id: [{}] duo ContentGroupOption", (Object) shipDbObject.getInt("id"));
                }
            }
        }
        final BasicDBList petsDb = (BasicDBList) dbObject.get(EServantType.Pet.toString());
        for (final Object petDb : petsDb) {
            final BasicDBObject petObject = (BasicDBObject) petDb;
            final Servant servant3 = new Servant(petObject);
            servant3.setAccountId(accountId);
            this.servants.add(servant3);
        }
    }

    public ConcurrentLinkedQueue<Servant> getServants() {
        return this.servants;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        return builder.get();
    }
}
