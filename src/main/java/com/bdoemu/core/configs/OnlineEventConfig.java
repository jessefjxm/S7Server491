package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;

@ConfigFile(name = "configs/onlineevent.properties")
public class OnlineEventConfig {
	// onlinegift 在线送礼活动
	@ConfigComments(comment = { "在线送礼：武器", "格式：物品ID;物品ID ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.weapon", value = "1001;1002;1003")
	public static String[] ONLINEGIFT_WEAPON;
	@ConfigComments(comment = { "附魔各是多少？", "格式：附魔;附魔 ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.weaponEnchant", value = "18;19;20")
	public static String[] ONLINEGIFT_WEAPON_ENCHANT;
	@ConfigComments(comment = { "抽取几个武器？" })
	@ConfigProperty(name = "onlineevent.onlinegift.weaponAmout", value = "2")
	public static int ONLINEGIFT_WEAPON_AMOUNT;
	@ConfigComments(comment = { "在线送礼：防具", "格式：物品ID;附魔;物品ID;附魔 ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.armor", value = "1001;1002;1003")
	public static String[] ONLINEGIFT_ARMOR;
	@ConfigComments(comment = { "附魔各是多少？", "格式：附魔;附魔 ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.armorEnchant", value = "18;19;20")
	public static String[] ONLINEGIFT_ARMOR_ENCHANT;
	@ConfigComments(comment = { "抽取几个防具？" })
	@ConfigProperty(name = "onlineevent.onlinegift.armorAmout", value = "1")
	public static int ONLINEGIFT_ARMOR_AMOUNT;
	@ConfigComments(comment = { "在线送礼：首饰", "格式：物品ID;附魔;物品ID;附魔 ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.accessory", value = "1001;1002;1003")
	public static String[] ONLINEGIFT_ACCESSORY;
	@ConfigComments(comment = { "附魔各是多少？", "格式：附魔;附魔 ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.accessoryEnchant", value = "18;19;20")
	public static String[] ONLINEGIFT_ACCESSORY_ENCHANT;
	@ConfigComments(comment = { "抽取几个首饰？" })
	@ConfigProperty(name = "onlineevent.onlinegift.accessoryAmout", value = "1")
	public static int ONLINEGIFT_ACCESSORY_AMOUNT;

	@ConfigComments(comment = { "在线送礼：炼金石", "格式：物品ID;物品ID ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.alchemistStone", value = "1001;1002;1003")
	public static String[] ONLINEGIFT_ALCHEMIST_STONE;
	@ConfigComments(comment = { "抽取几个炼金石？" })
	@ConfigProperty(name = "onlineevent.onlinegift.alchemistStoneAmout", value = "1")
	public static int ONLINEGIFT_ALCHEMIST_STONE_AMOUNT;

	@ConfigComments(comment = { "在线送礼：烹饪材料", "格式：物品ID;物品ID ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.ingredient", value = "1001;1002;1003")
	public static String[] ONLINEGIFT_INGREDIENT;
	@ConfigComments(comment = { "抽取几个材料？" })
	@ConfigProperty(name = "onlineevent.onlinegift.ingredientAmout", value = "10")
	public static int ONLINEGIFT_INGREDIENT_AMOUNT;
	@ConfigComments(comment = { "每种材料发几个？需要和物品数量一致", "格式：数量1;数量2 ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.ingredientCount", value = "300;400;500")
	public static String[] ONLINEGIFT_INGREDIENT_COUNT;
	@ConfigComments(comment = { "在线送礼：炼金材料", "格式：物品ID;物品ID ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.alchemyMaterial", value = "1001;1002;1003")
	public static String[] ONLINEGIFT_ALCHEMY_MATERIAL;
	@ConfigComments(comment = { "抽取几个材料？" })
	@ConfigProperty(name = "onlineevent.onlinegift.alchemyMaterialAmout", value = "10")
	public static int ONLINEGIFT_ALCHEMY_MATERIAL_AMOUNT;
	@ConfigComments(comment = { "每种材料发几个？需要和物品数量一致", "格式：数量1;数量2 ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.alchemyMaterialCount", value = "300;400;500")
	public static String[] ONLINEGIFT_ALCHEMY_MATERIAL_COUNT;

	@ConfigComments(comment = { "在线送礼：抽奖道具", "格式：物品ID;物品ID ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.lottery", value = "1001;1002;1003")
	public static String[] ONLINEGIFT_LOTTERY;
	@ConfigComments(comment = { "抽取几个道具？" })
	@ConfigProperty(name = "onlineevent.onlinegift.alchemyMaterialAmout", value = "10")
	public static int ONLINEGIFT_LOTTERY_AMOUNT;
	@ConfigComments(comment = { "每种材料发几个？需要和物品数量一致", "格式：数量1;数量2 ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.alchemyMaterialCount", value = "300;400;500")
	public static String[] ONLINEGIFT_LOTTERY_COUNT;

	@ConfigComments(comment = { "在线送礼：其他道具", "格式：物品ID;物品ID ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.others", value = "1001;1002;1003")
	public static String[] ONLINEGIFT_OTHERS;
	@ConfigComments(comment = { "抽取几个道具？" })
	@ConfigProperty(name = "onlineevent.onlinegift.othersAmout", value = "10")
	public static int ONLINEGIFT_OTHERS_AMOUNT;
	@ConfigComments(comment = { "每种材料发几个？需要和物品数量一致", "格式：数量1;数量2 ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.othersCount", value = "300;400;500")
	public static String[] ONLINEGIFT_OTHERS_COUNT;

	@ConfigComments(comment = { "在线送礼：100人额外奖励", "格式：物品ID;物品ID ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.100", value = "1001;1002;1003")
	public static String[] ONLINEGIFT_100;
	@ConfigComments(comment = { "每种材料发几个？需要和物品数量一致", "格式：数量1;数量2 ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.100Count", value = "1;1;1")
	public static String[] ONLINEGIFT_100_COUNT;
	@ConfigComments(comment = { "在线送礼：150人额外奖励", "格式：物品ID;物品ID ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.150", value = "1001;1002;1003")
	public static String[] ONLINEGIFT_150;
	@ConfigComments(comment = { "每种材料发几个？需要和物品数量一致", "格式：数量1;数量2 ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.150Count", value = "1;1;1")
	public static String[] ONLINEGIFT_150_COUNT;
	@ConfigComments(comment = { "在线送礼：200人额外奖励", "格式：物品ID;物品ID ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.200", value = "1001;1002;1003")
	public static String[] ONLINEGIFT_200;
	@ConfigComments(comment = { "每种材料发几个？需要和物品数量一致", "格式：数量1;数量2 ……以此类推" })
	@ConfigProperty(name = "onlineevent.onlinegift.200Count", value = "1;1;1")
	public static String[] ONLINEGIFT_200_COUNT;

	// marketshopping 扫荡拍卖行活动
	@ConfigComments(comment = { "需要扫荡的物品", "格式：物品ID;物品ID ……以此类推" })
	@ConfigProperty(name = "marketshopping.id", value = "1001;1002;1003")
	public static String[] MARKETSHOPPING_ID;
	@ConfigComments(comment = { "物品收购价最高多少？价格以万为单位", "需要和物品数量一致", "格式：价格1;价格2 ……以此类推" })
	@ConfigProperty(name = "marketshopping.price", value = "1000;1500;5000")
	public static String[] MARKETSHOPPING_PRICE;
}
