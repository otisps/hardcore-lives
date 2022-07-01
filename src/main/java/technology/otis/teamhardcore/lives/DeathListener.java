package technology.otis.teamhardcore.lives;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import technology.otis.teamhardcore.Teamhardcore;
import technology.otis.teamhardcore.db.SQLGetter;
import technology.otis.teamhardcore.general.ChatUtils;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeathEvent(PlayerDeathEvent event){
        ChatUtils chatUtils = new ChatUtils();
        Player player = event.getEntity();
        String playerId = player.getUniqueId().toString();
        SQLGetter sqlGetter = Teamhardcore.getInstance().sqlGetter;
        sqlGetter.setLives(playerId, sqlGetter.getLives(playerId) - 1);
        if (sqlGetter.getLives(playerId) <= 0){
            String message = Teamhardcore.getInstance().getConfig().getString("messages.outoflives");
            message = chatUtils.hexFormat(message);
            player.kickPlayer(message);
        }
        String message = Teamhardcore.getInstance().getConfig().getString("messages.death");
        message = chatUtils.hexFormat(message);
        player.sendMessage(message);

    }
}
