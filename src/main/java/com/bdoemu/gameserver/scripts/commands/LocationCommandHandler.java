package com.bdoemu.gameserver.scripts.commands;

import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.gameserver.dataholders.RegionData;
import com.bdoemu.gameserver.model.chat.CommandHandler;
import com.bdoemu.gameserver.model.chat.CommandHandlerMethod;
import com.bdoemu.gameserver.model.chat.enums.EChatResponseType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.world.region.enums.ERegionImgType;

import java.awt.*;

@CommandHandler(prefix = "loc", accessLevel = EAccessLevel.USER)
public class LocationCommandHandler extends AbstractCommandHandler {
    @CommandHandlerMethod
    public static Object[] index(final Player player, final String... params) {
        return new Object[]{EChatResponseType.Accepted, player.getLocation().toString()};
    }

    @CommandHandlerMethod(accessLevel = EAccessLevel.MODERATOR)
    public static Object[] rgb(final Player player, final String... params) {
        final double x = player.getLocation().getX();
        final double y = player.getLocation().getY();
        final StringBuilder builder = new StringBuilder();
        final double diffX = x - RegionData.MAP_MIN_X;
        final double diffY = RegionData.MAP_MAX_Y - y;
        final int pixelX = (int) (diffX / RegionData.MAP_IMAGE_SCALE);
        final int pixelY = (int) (diffY / RegionData.MAP_IMAGE_SCALE);
        builder.append("pixelX:").append(pixelX).append(" pixelY:").append(pixelY).append("\n");
        final Color region = new Color(RegionData.getInstance().getRegionMask(ERegionImgType.Region, x, y));
        builder.append("Region R:").append(region.getRed()).append(" G:").append(region.getGreen()).append(" B:").append(region.getBlue()).append("\n");
        final Color fishing = new Color(RegionData.getInstance().getRegionMask(ERegionImgType.Fishing, x, y));
        builder.append("Fishing R:").append(fishing.getRed()).append(" G:").append(fishing.getGreen()).append(" B:").append(fishing.getBlue()).append("\n");
        final Color water = new Color(RegionData.getInstance().getRegionMask(ERegionImgType.Water, x, y));
        builder.append("Water R:").append(water.getRed()).append(" G:").append(water.getGreen()).append(" B:").append(water.getBlue()).append("\n");
        final Color underground = new Color(RegionData.getInstance().getRegionMask(ERegionImgType.Underground, x, y));
        builder.append("Underground R:").append(underground.getRed()).append(" G:").append(underground.getGreen()).append(" B:").append(underground.getBlue()).append("\n");
        final Color villageSiege = new Color(RegionData.getInstance().getRegionMask(ERegionImgType.VillageSiege, x, y));
        builder.append("VillageSiege R:").append(villageSiege.getRed()).append(" G:").append(villageSiege.getGreen()).append(" B:").append(villageSiege.getBlue()).append("\n");
        final Color siege = new Color(RegionData.getInstance().getRegionMask(ERegionImgType.Siege, x, y));
        builder.append("Siege R:").append(siege.getRed()).append(" G:").append(siege.getGreen()).append(" B:").append(siege.getBlue()).append("\n");
        return new Object[]{EChatResponseType.Accepted, builder.toString()};
    }
}
