package technology.otis.teamhardcore.advancements;

import io.papermc.paper.advancement.AdvancementDisplay;
import org.bukkit.advancement.Advancement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import technology.otis.teamhardcore.Teamhardcore;
import technology.otis.teamhardcore.db.SQLGetter;
import technology.otis.teamhardcore.scoreboard.ScoreboardFactory;

public class AdvancementsListener implements Listener {

    @EventHandler
    public void onAchievement(PlayerAdvancementDoneEvent event) {
        Advancement advancement = event.getAdvancement();
        AdvancementDisplay display = advancement.getDisplay();
        if(display == null) return;
        SQLGetter sqlGetter = Teamhardcore.getInstance().sqlGetter;
        String playerId = event.getPlayer().getUniqueId().toString();
        sqlGetter.setAdvancements(playerId, sqlGetter.getAdvancements(playerId) + 1);
        ScoreboardFactory scoreboardFactory = new ScoreboardFactory();
        event.getPlayer().setScoreboard(scoreboardFactory.getScoreboard(playerId));
        return;
    }
}
