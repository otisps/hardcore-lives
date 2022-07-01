package technology.otis.teamhardcore.general;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import technology.otis.teamhardcore.Teamhardcore;

import java.util.UUID;

public class LeaveListener implements Listener {

    @EventHandler
    public void onLeaveEvent(PlayerQuitEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        Teamhardcore.teamMap.remove(uuid);
    }
}
