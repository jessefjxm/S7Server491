package com.bdoemu.gameserver.service;

import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.commons.thread.APeriodicTaskService;
import com.bdoemu.core.network.sendable.SMTopContentsRanking;
import com.bdoemu.core.network.sendable.SMTopLifeRanking;
import com.bdoemu.core.network.sendable.SMTopMatchRanking;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.model.misc.enums.EContentsRankType;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@StartupComponent("Service")
public class LifeRankService extends APeriodicTaskService {
    private final EnumMap<ELifeExpType, List<Player>> lifeRanks;
    private final EnumMap<EContentsRankType, List<Player>> contentsRanks;
    private int membersRegistered;
    private LifeRankService() {
        super(15L, TimeUnit.MINUTES);
        this.lifeRanks = new EnumMap<>(ELifeExpType.class);
        this.contentsRanks = new EnumMap<>(EContentsRankType.class);
        for (final ELifeExpType lifeExpType : ELifeExpType.values()) {
            if (!lifeExpType.isNone()) {
                this.lifeRanks.put(lifeExpType, new ArrayList<>());
            }
        }
        for (final EContentsRankType contentsRankType : EContentsRankType.values()) {
            this.contentsRanks.put(contentsRankType, new ArrayList<>());
        }
    }

    public static LifeRankService getInstance() {
        return Holder.INSTANCE;
    }

    public int getMembersRegistered() {
        return this.membersRegistered;
    }

    public List<Player> getTopPlayerRanks(final ELifeExpType lifeExpType) {
        return this.lifeRanks.get(lifeExpType);
    }

    public List<Player> getTopPlayerRanks(final EContentsRankType contentsRankType) {
        return this.contentsRanks.get(contentsRankType);
    }

    public void run() {
        final Collection<Player> playersOnline = World.getInstance().getPlayers().stream().filter(x -> x.isPlayer() && (x.getAccessLevel() == EAccessLevel.USER || x.getAccessLevel() == EAccessLevel.TESTER)).collect(Collectors.toList());
        this.membersRegistered = playersOnline.size();
        final Player[] lifeRankPlayers = new Player[75];
        final Player[] contentsPlayers = new Player[15];
        final Player[] topMatchPlayers = new Player[10];
        int allRanksIndex = 0;
        for (final ELifeExpType lifeExpType : ELifeExpType.values()) {
            if (!lifeExpType.isNone()) {
                int rankLevel = 1;
                final List<Player> playersSorted = playersOnline.stream().sorted(Comparator.comparingInt(player -> ((Player) player).getLifeExperienceStorage().getLifeExperience(lifeExpType).getLevel()).reversed()).collect(Collectors.toList());
                for (final Player player2 : playersSorted) {
                    player2.getLifeExperienceStorage().getLifeExperience(lifeExpType).setRankLevel(rankLevel++);
                }
                final List<Player> topLifeRankPlayers = playersSorted.stream().limit(30L).collect(Collectors.toList());
                this.lifeRanks.put(lifeExpType, topLifeRankPlayers);
                for (int index = 0; index < 5; ++index) {
                    if (index < topLifeRankPlayers.size()) {
                        lifeRankPlayers[allRanksIndex] = topLifeRankPlayers.get(index);
                    }
                    ++allRanksIndex;
                }
            }
        }
        allRanksIndex = 0;
        for (final EContentsRankType contentsType : EContentsRankType.values()) {
            List<Player> playersSorted2 = null;
            switch (contentsType) {
                case MoneyRank: {
                    playersSorted2 = playersOnline
                            .stream()
                            .sorted(
                                    Comparator.comparingLong((Player player) -> player.getPlayerBag().getAllMoney()).reversed()
                            ).collect(Collectors.toList());
                    break;
                }
                case BattleRank: {
                    playersSorted2 = playersOnline
                            .stream()
                            .sorted(
                                    Comparator.comparingInt((ToIntFunction<Player>) Creature::getLevel).reversed()
                                            .thenComparing(Comparator.comparingLong(Player::getExp).reversed())
                            ).collect(Collectors.toList());
                    break;
                }
                case LocalWarRank: {
                    playersSorted2 = playersOnline
                            .stream()
                            .sorted(
                                    Comparator.comparingInt(Player::getLocalWarPoints).reversed()
                            ).collect(Collectors.toList());
                    break;
                }
            }
            final List<Player> topContentsRankPlayers = playersSorted2.stream().limit(30L).collect(Collectors.toList());
            this.contentsRanks.put(contentsType, topContentsRankPlayers);

            if (contentsType == EContentsRankType.LocalWarRank) {
                for (int index2 = 0; index2 < 10; ++index2) {
                    if (index2 < topContentsRankPlayers.size())
                        topMatchPlayers[index2] = topContentsRankPlayers.get(index2);
                }
            } else {
                for (int index2 = 0; index2 < 5; ++index2) {
                    if (index2 < topContentsRankPlayers.size()) {
                        contentsPlayers[allRanksIndex] = topContentsRankPlayers.get(index2);
                    }
                    ++allRanksIndex;
                }
            }

        }
        World.getInstance().broadcastWorldPacket(new SMTopLifeRanking(lifeRankPlayers));
        World.getInstance().broadcastWorldPacket(new SMTopContentsRanking(contentsPlayers));
        World.getInstance().broadcastWorldPacket(new SMTopMatchRanking(topMatchPlayers));
    }

    private static class Holder {
        static final LifeRankService INSTANCE = new LifeRankService();
    }
}
