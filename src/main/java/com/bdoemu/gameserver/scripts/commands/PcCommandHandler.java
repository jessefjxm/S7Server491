package com.bdoemu.gameserver.scripts.commands;

import com.bdoemu.MainServer;
import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.core.network.sendable.SMChat;
import com.bdoemu.core.network.sendable.SMClearPlayerSkillCoolTimeForManager;
import com.bdoemu.core.network.sendable.SMSetCharacterLevels;
import com.bdoemu.core.network.sendable.SMSetCharacterSpeeds;
import com.bdoemu.gameserver.dataholders.xml.ExploreData;
import com.bdoemu.gameserver.model.chat.CommandHandler;
import com.bdoemu.gameserver.model.chat.CommandHandlerMethod;
import com.bdoemu.gameserver.model.chat.enums.EChatNoticeType;
import com.bdoemu.gameserver.model.chat.enums.EChatType;
import com.bdoemu.gameserver.model.creature.npc.card.enums.ECardGradeType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.cooltimes.CoolTimeList;
import com.bdoemu.gameserver.model.creature.player.exploration.templates.ExplorationTemplate;
import com.bdoemu.gameserver.model.creature.player.fitness.enums.EFitnessType;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.AddItemEvent;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.stats.elements.BuffElement;
import com.bdoemu.gameserver.model.stats.enums.StatEnum;
import com.bdoemu.gameserver.worldInstance.World;
import org.apache.commons.lang3.text.StrBuilder;

@CommandHandler(prefix = "pc", accessLevel = EAccessLevel.MODERATOR)
public class PcCommandHandler extends AbstractCommandHandler {
	private static StrBuilder helpBuilder;

	static {
		(PcCommandHandler.helpBuilder = new StrBuilder()).appendln("Available commands:");
		PcCommandHandler.helpBuilder.appendln("pc announce [text]");
		PcCommandHandler.helpBuilder.appendln("pc kick [playerName]");
		PcCommandHandler.helpBuilder.appendln("pc giftonline [itemId] [count] (Optional: [enchantLevel])");
		PcCommandHandler.helpBuilder.appendln("pc setlevel [level]");
		PcCommandHandler.helpBuilder.appendln("pc addcash [cashCount]");
		PcCommandHandler.helpBuilder.appendln("pc addtendency [tendencyCount]");
		PcCommandHandler.helpBuilder.appendln("pc addwp [wpCount]");
		PcCommandHandler.helpBuilder.appendln("pc addexp [expCount]");
		PcCommandHandler.helpBuilder.appendln("pc addskillexp [expCount]");
		PcCommandHandler.helpBuilder.appendln("pc addskillpoints [pointsCount]");
		PcCommandHandler.helpBuilder.appendln("pc addlifeexp [lifeType] [expCount]");
		PcCommandHandler.helpBuilder.appendln("pc addfitnessexp [fitnessType] [expCount]");
		PcCommandHandler.helpBuilder.appendln("pc addexploreexp [nodeKey] [expCount]");
		PcCommandHandler.helpBuilder.appendln("pc addknowledge [cardId] [cardLevel]");
		PcCommandHandler.helpBuilder.appendln("pc addtitle [titleId]");
		PcCommandHandler.helpBuilder.appendln("pc clearskilllist");
		PcCommandHandler.helpBuilder.appendln("pc clearcooltime");
		PcCommandHandler.helpBuilder.appendln("pc openmap");
		PcCommandHandler.helpBuilder.appendln("pc info");
		PcCommandHandler.helpBuilder.appendln("pc gmspeed [level]");
		PcCommandHandler.helpBuilder.appendln("pc heal");
		PcCommandHandler.helpBuilder.appendln("pc debugdmg");
		PcCommandHandler.helpBuilder.appendln("pc kms");
		PcCommandHandler.helpBuilder.appendln("pc clearbuffs");
	}

	@CommandHandlerMethod
	public static Object[] index(final Player player, final String... params) {
		return AbstractCommandHandler.getAcceptResult(PcCommandHandler.helpBuilder.toString());
	}

	@CommandHandlerMethod
	public static Object[] openmap(final Player player, final String... params) {
		for (final ExplorationTemplate exploreTemplate : ExploreData.getInstance().getExplorations()) {
			player.getExploration().learnDiscovery(exploreTemplate.getExplorationId(), false);
		}
		return AbstractCommandHandler.getAcceptResult("");
	}

	@CommandHandlerMethod
	public static Object[] announce(final Player player, final String... params) {
		final StringBuilder announceText = new StringBuilder();
		for (final String param : params) {
			announceText.append(param).append(" ");
		}
		final SMChat announcePacket = new SMChat(player.getName(), player.getFamily(), player.getGameObjectId(),
				EChatType.Notice, EChatNoticeType.None, announceText.toString());
		World.getInstance().broadcastWorldPacket(announcePacket);
		return AbstractCommandHandler.getAcceptResult();
	}

	@CommandHandlerMethod
	public static Object[] kick(final Player player, final String... params) {
		final String kickName = params[0];
		final Player kickPlayer = World.getInstance().getPlayer(kickName);
		if (kickPlayer != null) {
			kickPlayer.getClient().closeForce();
			return AbstractCommandHandler.getAcceptResult();
		}
		return AbstractCommandHandler.getRejectResult("Player " + kickName + " doesn't exist in world.");
	}

	@CommandHandlerMethod
	public static Object[] clearbuffs(final Player player, final String... params) {
		for (ActiveBuff buff : player.getBuffList().getBuffs())
			buff.endEffect();
		return AbstractCommandHandler.getRejectResult("All buffs have been cleared.");
	}

	@CommandHandlerMethod
	public static Object[] giftonline(final Player player, final String... params) {
		int enchantLevel = 0;
		if (params.length >= 2) {
			Integer itemId;
			Long count;
			try {
				itemId = Integer.parseInt(params[0]);
				count = Long.parseLong(params[1]);
				if (params.length >= 3) {
					enchantLevel = Integer.parseInt(params[2]);
				}
			} catch (NumberFormatException ex) {
				return AbstractCommandHandler.getRejectResult("Number format error.");
			} catch (ArrayIndexOutOfBoundsException ex2) {
				return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
			}
			for (final Player _player : World.getInstance().getPlayers()) {
				_player.getPlayerBag().onEvent(new AddItemEvent(_player, itemId, enchantLevel, count));
			}
			return AbstractCommandHandler.getAcceptResult();
		}
		return AbstractCommandHandler.getRejectResult("Incorrect params count.");
	}

	@CommandHandlerMethod
	public static Object[] invis(final Player player, final String... params) {
		return AbstractCommandHandler.getAcceptResult("TODO: Implement me");
	}

	@CommandHandlerMethod
	public static Object[] setlevel(final Player player, final String... params) {
		Integer maxLevel;
		try {
			maxLevel = Integer.parseInt(params[0]);
		} catch (NumberFormatException ex) {
			return AbstractCommandHandler.getRejectResult("Number format error.");
		} catch (ArrayIndexOutOfBoundsException ex2) {
			return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
		}
		final int level = player.getLevel();
		if (maxLevel <= level) {
			return AbstractCommandHandler.getRejectResult("Delevel not implemented.");
		}
		for (int l = level + 1; l <= maxLevel; ++l) {
			player.setLevel(l);
			player.setExp(0L);
			player.onLevelChange(true);
			player.sendPacket(new SMSetCharacterLevels(player, true));
		}
		return AbstractCommandHandler.getAcceptResult("Level up successfully processed.");
	}

	@CommandHandlerMethod
	public static Object[] addtendency(final Player player, final String... params) {
		Integer tendency;
		try {
			tendency = Integer.parseInt(params[0]);
		} catch (NumberFormatException ex) {
			return AbstractCommandHandler.getRejectResult("Number format error.");
		} catch (ArrayIndexOutOfBoundsException ex2) {
			return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
		}
		player.addTendency(tendency);
		return AbstractCommandHandler.getAcceptResult("Tendency successfully added.");
	}

	@CommandHandlerMethod
	public static Object[] addknowledge(final Player player, final String... params) {
		Integer cardId;
		Integer cardLevel;
		try {
			cardId = Integer.parseInt(params[0]);
			cardLevel = Integer.parseInt(params[1]);
		} catch (NumberFormatException ex) {
			return AbstractCommandHandler.getRejectResult("Number format error.");
		} catch (ArrayIndexOutOfBoundsException ex2) {
			return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
		}
		final ECardGradeType cardGradeType = ECardGradeType.valueof(cardLevel);
		if (cardGradeType == null) {
			return AbstractCommandHandler
					.getRejectResult("ECardGradeType doesn't exist for " + cardLevel + " card level!");
		}
		player.getMentalCardHandler().updateMentalCard(cardId, cardGradeType);
		return AbstractCommandHandler.getAcceptResult("Knowledge successfully added.");
	}

	@CommandHandlerMethod
	public static Object[] addcash(final Player player, final String... params) {
		Long cash;
		try {
			cash = Long.parseLong(params[0]);
		} catch (NumberFormatException ex) {
			return AbstractCommandHandler.getRejectResult("Number format error.");
		} catch (ArrayIndexOutOfBoundsException ex2) {
			return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
		}
		MainServer.getRmi().addCash(player.getAccountId(), cash);
		return AbstractCommandHandler.getAcceptResult("Cash successfully added.");
	}

	@CommandHandlerMethod
	public static Object[] addwp(final Player player, final String... params) {
		Integer wp;
		try {
			wp = Integer.parseInt(params[0]);
		} catch (NumberFormatException ex) {
			return AbstractCommandHandler.getRejectResult("Number format error.");
		} catch (ArrayIndexOutOfBoundsException ex2) {
			return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
		}
		player.addWp(wp);
		return AbstractCommandHandler.getAcceptResult("WP successfully added.");
	}

	@CommandHandlerMethod
	public static Object[] addexp(final Player player, final String... params) {
		Integer exp;
		try {
			exp = Integer.parseInt(params[0]);
		} catch (NumberFormatException ex) {
			return AbstractCommandHandler.getRejectResult("Number format error.");
		} catch (ArrayIndexOutOfBoundsException ex2) {
			return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
		}
		player.addExp(exp);
		return AbstractCommandHandler.getAcceptResult("Experience successfully added.");
	}

	@CommandHandlerMethod
	public static Object[] addskillexp(final Player player, final String... params) {
		Integer skillExp;
		try {
			skillExp = Integer.parseInt(params[0]);
		} catch (NumberFormatException ex) {
			return AbstractCommandHandler.getRejectResult("Number format error.");
		} catch (ArrayIndexOutOfBoundsException ex2) {
			return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
		}
		player.getSkillList().addSkillExp(skillExp);
		return AbstractCommandHandler.getAcceptResult("Skill experience successfully added.");
	}

	@CommandHandlerMethod
	public static Object[] addlifeexp(final Player player, final String... params) {
		Integer type;
		Integer lifeExp;
		try {
			type = Integer.parseInt(params[0]);
			lifeExp = Integer.parseInt(params[1]);
		} catch (NumberFormatException ex) {
			return AbstractCommandHandler.getRejectResult("Number format error.");
		} catch (ArrayIndexOutOfBoundsException ex2) {
			return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
		}
		final ELifeExpType expType = ELifeExpType.valueOf(type);
		if (expType == null) {
			return AbstractCommandHandler.getRejectResult("Specified LifeExp doesn't exist.");
		}
		player.getLifeExperienceStorage().getLifeExperience(expType).addExp(player, lifeExp);
		return AbstractCommandHandler.getAcceptResult("Life experience successfully added.");
	}

	@CommandHandlerMethod
	public static Object[] addfitnessexp(final Player player, final String... params) {
		Integer fitnessExp;
		EFitnessType efitnessType;
		try {
			final Integer fitnessType = Integer.parseInt(params[0]);
			fitnessExp = Integer.parseInt(params[1]);
			efitnessType = EFitnessType.valueOf(fitnessType);
		} catch (IllegalArgumentException ex) {
			return AbstractCommandHandler.getRejectResult("Illegal command format.");
		} catch (ArrayIndexOutOfBoundsException ex2) {
			return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
		}
		player.getFitnessHandler().addExp(player, efitnessType, fitnessExp);
		return AbstractCommandHandler.getAcceptResult("Fitness experience successfully added.");
	}

	@CommandHandlerMethod
	public static Object[] addexploreexp(final Player player, final String... params) {
		Integer territoryKey;
		Integer exploreExp;
		try {
			territoryKey = Integer.parseInt(params[0]);
			exploreExp = Integer.parseInt(params[1]);
		} catch (IllegalArgumentException ex) {
			return AbstractCommandHandler.getRejectResult("Illegal command format.");
		} catch (ArrayIndexOutOfBoundsException ex2) {
			return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
		}
		player.getExplorePointHandler().addExp(territoryKey, exploreExp);
		return AbstractCommandHandler.getAcceptResult("Explore experience successfully added.");
	}

	@CommandHandlerMethod
	public static Object[] addskillpoints(final Player player, final String... params) {
		Integer skillpoints;
		try {
			skillpoints = Integer.parseInt(params[0]);
		} catch (NumberFormatException ex) {
			return AbstractCommandHandler.getRejectResult("Number format error.");
		} catch (ArrayIndexOutOfBoundsException ex2) {
			return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
		}
		player.getSkillList().addSkillPoints(skillpoints);
		return AbstractCommandHandler.getAcceptResult("Skill points successfully added.");
	}

	@CommandHandlerMethod
	public static Object[] addtitle(final Player player, final String... params) {
		Integer titleId;
		try {
			titleId = Integer.parseInt(params[0]);
		} catch (NumberFormatException ex) {
			return AbstractCommandHandler.getRejectResult("Number format error.");
		} catch (ArrayIndexOutOfBoundsException ex2) {
			return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
		}
		if (!player.getTitleHandler().addTitle(titleId)) {
			return AbstractCommandHandler.getRejectResult("Specified title doesn't exist.");
		}
		return AbstractCommandHandler.getAcceptResult("Title successfully added.");
	}

	@CommandHandlerMethod
	public static Object[] clearskilllist(final Player player, final String... params) {
		player.getSkillList().clearLearnedSkills();
		return AbstractCommandHandler.getAcceptResult("Skill list successfully cleared.");
	}

	@CommandHandlerMethod
	public static Object[] clearcooltime(final Player player, final String... params) {
		final CoolTimeList coolTimeList = player.getCoolTimeList();
		coolTimeList.clearCoolTimes();
		player.sendPacket(new SMClearPlayerSkillCoolTimeForManager());
		return AbstractCommandHandler.getAcceptResult("Cool time successfully cleared.");
	}

	@CommandHandlerMethod
	public static Object[] heal(final Player player, final String... params) {
		player.getGameStats().getHp().updateHp(9999999999.0, null);
		player.getGameStats().getMp().updateMp(9999999999.0);
		if (player.getCurrentVehicle() != null && player.getCurrentVehicle().isOwnerMounted()) {
			player.getCurrentVehicle().getGameStats().getHp().updateHp(999999999.9, null);
			player.getCurrentVehicle().setHunger(9999);
		}
		return AbstractCommandHandler.getAcceptResult("Healed.");
	}

	@CommandHandlerMethod
	public static Object[] debugdmg(final Player player, final String... params) {
		player.setDamageDebug();
		return AbstractCommandHandler.getAcceptResult("Damage debug toggled.");
	}

	@CommandHandlerMethod
	public static Object[] kms(final Player player, final String... params) {
		player.getGameStats().getHp().updateHp(-9999999999.0, null);
		return AbstractCommandHandler.getAcceptResult("Died kek.");
	}

	@CommandHandlerMethod
	public static Object[] info(final Player player, final String... params) {

		// Player Stats:
		player.sendMessage("HP: " + player.getGameStats().getHp().getMaxValue(), true);
		player.sendMessage("MP: " + player.getGameStats().getHp().getMaxValue(), true);
		player.sendMessage("HP Regen: " + player.getGameStats().getHPRegen().getMaxValue(), true);
		player.sendMessage("HP Regen: " + player.getGameStats().getHPRegen().getMaxValue(), true);

		// Player background stats:
		player.sendMessage("DHIT: " + player.getGameStats().getDHIT().getMaxValue(), true);
		player.sendMessage("DDV: " + player.getGameStats().getDDV().getMaxValue(), true);
		player.sendMessage("DPV: " + player.getGameStats().getDPV().getIntMaxValue(), true);
		player.sendMessage("HDDV: " + player.getGameStats().getStat(StatEnum.HDDV).getIntMaxValue(), true);
		player.sendMessage("HDPV: " + player.getGameStats().getStat(StatEnum.HDPV).getIntMaxValue(), true);

		player.sendMessage(
				"KnockBack: " + (player.getGameStats().getResistKnockBack().getIntMaxValue() / 10_000.0) + "%", true);
		player.sendMessage(
				"KnockDown: " + (player.getGameStats().getResistKnockDown().getIntMaxValue() / 10_000.0) + "%", true);
		player.sendMessage("Capture: " + (player.getGameStats().getResistCapture().getIntMaxValue() / 10_000.0) + "%",
				true);
		player.sendMessage("Stun: " + (player.getGameStats().getResistStun().getIntMaxValue() / 10_000.0) + "%", true);
		player.sendMessage("Rigid: " + (player.getGameStats().getResistRigid().getIntMaxValue() / 10_000.0) + "%",
				true);
		player.sendMessage("Bound " + (player.getGameStats().getResistBound().getIntMaxValue() / 10_000.0) + "%", true);
		player.sendMessage("Horror: " + (player.getGameStats().getResistHorror().getIntMaxValue() / 10_000.0) + "%",
				true);

		return AbstractCommandHandler.getAcceptResult();
	}

	@CommandHandlerMethod
	public static Object[] gmspeed(final Player player, final String... params) {
		Long gmSpeedLevel;
		try {
			gmSpeedLevel = Long.parseLong(params[0]);
		} catch (IllegalArgumentException ex) {
			return AbstractCommandHandler.getRejectResult("Illegal command format.");
		} catch (ArrayIndexOutOfBoundsException ex2) {
			return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
		}
		player.getGameStats().getMoveSpeedRate().addRateElement(new BuffElement(gmSpeedLevel * 100000.0f));
		player.getGameStats().getAttackSpeedRate().addRateElement(new BuffElement(gmSpeedLevel * 100000.0f));
		player.getGameStats().getCastingSpeedRate().addRateElement(new BuffElement(gmSpeedLevel * 100000.0f));
		player.sendPacket(new SMSetCharacterSpeeds(player.getGameStats()));
		return AbstractCommandHandler.getAcceptResult();
	}
}
