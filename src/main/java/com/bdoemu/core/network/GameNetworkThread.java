// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network;

import com.bdoemu.commons.network.IClientFactory;
import com.bdoemu.commons.network.NetworkThread;
import com.bdoemu.commons.network.handler.AbstractPacketHandlerFactory;
import com.bdoemu.core.startup.StartupComponent;
import jdk.net.ExtendedSocketOptions;
import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.StandardSocketOptions;
import java.util.concurrent.atomic.AtomicReference;

@StartupComponent("Network")
public class GameNetworkThread extends NetworkThread<GameClient> {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) GameNetworkThread.class);
        instance = new AtomicReference<Object>();
    }

    public static GameNetworkThread getInstance() {
        Object value = GameNetworkThread.instance.get();
        if (value == null) {
            synchronized (GameNetworkThread.instance) {
                value = GameNetworkThread.instance.get();
                if (value == null) {
                    final GameNetworkThread actualValue = new GameNetworkThread();
                    value = ((actualValue == null) ? GameNetworkThread.instance : actualValue);
                    GameNetworkThread.instance.set(value);
                }
            }
        }
        return (GameNetworkThread) ((value == GameNetworkThread.instance) ? null : value);
    }

    public IClientFactory<GameClient> getClientFactory() {
        return (IClientFactory<GameClient>) GameClientFactory.getInstance();
    }

    public AbstractPacketHandlerFactory<GameClient> getPacketHandler() {
        return (AbstractPacketHandlerFactory<GameClient>) GamePacketFactory.getInstance();
    }

    public String getSocketOptions() {
        final StrBuilder builder = new StrBuilder();
        try {
            builder.appendln("SO_KEEPALIVE: " + this.serverSocketChannel.getOption(StandardSocketOptions.SO_KEEPALIVE));
            builder.appendln("SO_RCVBUF: " + this.serverSocketChannel.getOption(StandardSocketOptions.SO_RCVBUF));
            builder.appendln("SO_SNDBUF: " + this.serverSocketChannel.getOption(StandardSocketOptions.SO_SNDBUF));
            builder.appendln("SO_REUSEADDR: " + this.serverSocketChannel.getOption(StandardSocketOptions.SO_REUSEADDR));
            builder.appendln("SO_LINGER: " + this.serverSocketChannel.getOption(StandardSocketOptions.SO_LINGER));
            builder.appendln("SO_BROADCAST: " + this.serverSocketChannel.getOption(StandardSocketOptions.SO_BROADCAST));
            builder.appendln("SO_FLOW_SLA: " + this.serverSocketChannel.getOption(ExtendedSocketOptions.SO_FLOW_SLA));
            builder.appendln("TCP_NODELAY: " + this.serverSocketChannel.getOption(StandardSocketOptions.TCP_NODELAY));
        } catch (Exception e) {
            builder.appendln("Error while getting server socket options.");
            GameNetworkThread.log.error("Error while getting server socket options.", (Throwable) e);
        }
        return builder.build();
    }
}
