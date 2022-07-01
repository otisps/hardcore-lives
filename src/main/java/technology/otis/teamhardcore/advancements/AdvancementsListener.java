package technology.otis.teamhardcore.advancements;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import technology.otis.teamhardcore.Teamhardcore;
import technology.otis.teamhardcore.db.SQLGetter;

public class AdvancementsListener implements Listener {

    @EventHandler
    public void onAchievement(PlayerAdvancementDoneEvent event) {
        if (event.getAdvancement().getDisplay() == null) return;
        SQLGetter sqlGetter = Teamhardcore.getInstance().sqlGetter;
        String playerId = event.getPlayer().getUniqueId().toString();
        sqlGetter.setAdvancements(playerId, sqlGetter.getAdvancements(playerId) + 1);
        return;
    }
}
