package technology.otis.teamhardcore.general;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import technology.otis.teamhardcore.Teamhardcore;
import technology.otis.teamhardcore.db.SQLGetter;
import technology.otis.teamhardcore.scoreboard.ScoreboardFactory;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event){
        ChatUtils chatUtils = new ChatUtils();
        SQLGetter sqlGetter = Teamhardcore.getInstance().sqlGetter;
        Player player = event.getPlayer();
        String playerId = player.getUniqueId().toString();
        String team = "";
        boolean alreadyExists = sqlGetter.tableContains(playerId);
        if (alreadyExists) {
            ScoreboardFactory scoreboardFactory = new ScoreboardFactory();
            player.setScoreboard(scoreboardFactory.getScoreboard(playerId));
            int numberOfLives = sqlGetter.getLives(playerId);
            if (numberOfLives == 0){
                String message = Teamhardcore.getInstance().getConfig().getString("messages.outoflives");
                message = chatUtils.hexFormat(message);
                player.kickPlayer(message);
                return;
            }
            if (numberOfLives > 0) {
                team = sqlGetter.getTeamName(playerId);
            }
        }
        if (!alreadyExists ){
            sqlGetter.addUser(player.getName());
        }
        ScoreboardFactory scoreboardFactory = new ScoreboardFactory();
        player.setScoreboard(scoreboardFactory.getScoreboard(playerId));
        if(team.equalsIgnoreCase("")|| team.equalsIgnoreCase(" ")) return;
        Teamhardcore.teamMap.put(player.getUniqueId(), team);
    }
}
