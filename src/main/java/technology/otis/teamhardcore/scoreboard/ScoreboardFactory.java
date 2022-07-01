package technology.otis.teamhardcore.scoreboard;

import com.google.j2objc.annotations.ObjectiveCName;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;
import technology.otis.teamhardcore.Teamhardcore;
import technology.otis.teamhardcore.general.ChatUtils;

public class ScoreboardFactory {
    public Scoreboard getScoreboard(String playerId) {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        ChatUtils chatUtils = new ChatUtils();
        String title = Teamhardcore.getInstance().getConfig().getString("setting.scoreboard-title");
        Objective objective = scoreboard.registerNewObjective("TeamBoard", "dummy", chatUtils.hexFormat(title));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        int scoreInt = Teamhardcore.getInstance().sqlGetter.getAdvancements(playerId);
        Score score = objective.getScore(chatUtils.hexFormat("&lScore: "));
        score.setScore(scoreInt);
        Score lives = objective.getScore(chatUtils.hexFormat("&lLives: "));
        int livesInt = Teamhardcore.getInstance().sqlGetter.getLives(playerId);
        lives.setScore(livesInt);
        String teamString = Teamhardcore.getInstance().sqlGetter.getTeamName(playerId);
        Score team = objective.getScore(chatUtils.hexFormat("&lTeam is " + teamString));
        team.setScore(0);
        return scoreboard;
    }


}
