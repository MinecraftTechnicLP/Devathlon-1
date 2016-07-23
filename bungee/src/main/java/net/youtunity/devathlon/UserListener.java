package net.youtunity.devathlon;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.youtunity.devathlon.server.ServerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by thecrealm on 23.07.16.
 */
public class UserListener implements Listener {

    private DevathlonPlugin plugin;
    private List<UUID> join = new ArrayList<>();

    public UserListener(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(LoginEvent event) {
        join.add(event.getConnection().getUniqueId());
    }

    @EventHandler
    public void onHandshake(ProxyPingEvent event) {

        String host = event.getConnection().getVirtualHost().getHostString();

        ServerContext context = plugin.getServerRegistry().lookupContext(host);
        System.out.println(context.getMotd());

        event.getResponse().setDescriptionComponent(new TextComponent(context.getMotd()));
    }

    @EventHandler
    public void onConnect(ServerConnectEvent event) {

        if(!join.contains(event.getPlayer().getPendingConnection().getUniqueId())) {
            return;
        }

        join.remove(event.getPlayer().getPendingConnection().getUniqueId());
        String host = event.getPlayer().getPendingConnection().getVirtualHost().getHostString();

        if(ProxyServer.getInstance().getServers().containsKey(host)) {
            event.setTarget(ProxyServer.getInstance().getServerInfo(host));
            event.getPlayer().sendMessage(TextComponent.fromLegacyText("The requested server is online, Welcome!"));
        } else {
            event.getPlayer().sendMessage(TextComponent.fromLegacyText("The requested server is offline, starting up for you.."));


        }
    }
}