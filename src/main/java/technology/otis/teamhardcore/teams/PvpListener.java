package technology.otis.teamhardcore.teams;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import technology.otis.teamhardcore.Teamhardcore;

public class PvpListener implements Listener {

    @EventHandler
    public void onPvpEvent(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)){
            return;
        }
        // So pvp event ....
        Player player = (Player) event.getEntity();
        Player attacker = (Player) event.getDamager();
        String playerTeam = Teamhardcore.teamMap.get(player.getUniqueId());
        String attackerTeam = Teamhardcore.teamMap.get(attacker.getUniqueId());
        if(playerTeam.equalsIgnoreCase(attackerTeam)){
            event.setCancelled(true);
        }
    }
}
