package net.youtunity.devathlon.user;

import net.youtunity.devathlon.team.Team;
import org.bukkit.entity.Player;

/**
 * Created by thecrealm on 30.07.16.
 */
public class User {

    private UUID uuid;
    private Team team;

    public User(Player player) {
        this.uuid = player.getUniqueID();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }
}
