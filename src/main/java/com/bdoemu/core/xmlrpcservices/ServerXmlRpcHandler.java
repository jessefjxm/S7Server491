package com.bdoemu.core.xmlrpcservices;

import com.bdoemu.commons.model.xmlrpc.BaseXmlRpcHandler;
import com.bdoemu.commons.model.xmlrpc.impl.XmlRpcOnline;
import com.bdoemu.commons.xmlrpc.XmlRpcHandler;
import com.bdoemu.gameserver.worldInstance.World;

@XmlRpcHandler
public class ServerXmlRpcHandler extends BaseXmlRpcHandler {
    public String getOnline() {
        final XmlRpcOnline result = new XmlRpcOnline();
        result.setLivePlayers(World.getInstance().getPlayers().size());
        result.setTwinksPlayers(0);
        return this.json(result);
    }
}
