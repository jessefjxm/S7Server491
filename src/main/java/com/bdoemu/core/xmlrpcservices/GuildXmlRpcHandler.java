package com.bdoemu.core.xmlrpcservices;

import com.bdoemu.commons.model.xmlrpc.BaseXmlRpcHandler;
import com.bdoemu.commons.model.xmlrpc.impl.XmlRpcGuild;
import com.bdoemu.commons.xmlrpc.XmlRpcHandler;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;

import java.util.List;
import java.util.stream.Collectors;

@XmlRpcHandler
public class GuildXmlRpcHandler extends BaseXmlRpcHandler {
    public String getGuilds() {
        final List<XmlRpcGuild> guilds = GuildService.getInstance().getGuilds().values().stream().map(guild -> guild.toXMLRpcObject("")).collect(Collectors.toList());
        return this.json(guilds);
    }
}
