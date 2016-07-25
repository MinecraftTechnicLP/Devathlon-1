package net.youtunity.devathlon.server;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.youtunity.devathlon.Servers;
import net.youtunity.devathlon.api.ServerStatus;

import java.net.InetSocketAddress;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ServerContext {

    private String server;
    private String host;
    private int port;
    private String motd;
    private ServerStatus serverStatus = ServerStatus.OFFLINE;

    public ServerContext(String server) {
        this.server = server;
        this.host = "default";
        this.port = 1;
        this.motd = server + "'s Server";
    }

    public String getServer() {
        return server;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getMotd() {
        return motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    public ServerStatus getServerStatus() {
        return serverStatus;
    }

    public void doStatusChange(ServerStatus newStatus) {
        if (newStatus == serverStatus) {
            return;
        }

        this.serverStatus = newStatus;

        if(newStatus == ServerStatus.RUNNING) {
            Servers.addServer(getServer(), getHost(), getPort());
        }

        if(newStatus == ServerStatus.STOPPING) {
            Servers.removeServer(getServer());
        }

        ProxyServer.getInstance().getPluginManager().callEvent(new ServerStatusChangedEvent(newStatus, this));
    }
}
