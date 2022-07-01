package technology.otis.teamhardcore.teams;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import technology.otis.teamhardcore.Teamhardcore;
import technology.otis.teamhardcore.general.ChatUtils;
import technology.otis.teamhardcore.general.UUIDFetcher;
import technology.otis.teamhardcore.scoreboard.ScoreboardFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ChatUtils chatUtils = new ChatUtils();
        boolean admin = sender.isOp() || !(sender instanceof Player);
        int argLength = args.length;
        String message = Teamhardcore.getInstance().getConfig().getString("messages.team-players");
        if(argLength == 0){
            if(!(sender instanceof Player)) return true;
            Player player = (Player) sender;
            String team = Teamhardcore.getInstance().sqlGetter.getTeamName(player.getUniqueId().toString());
            message = chatUtils.hexFormat(message).replace("%team%", team);
            if(team.equalsIgnoreCase("") || team.equalsIgnoreCase(" ")){
                return true;
            }
            sender.sendMessage(message);
            for (String teamMate:
                    Teamhardcore.getInstance().sqlGetter.getPlayersInTeam(team)) {
                sender.sendMessage(" - " + teamMate);
            }
            return true;
        }
        String subCommand = args[0];
        if (argLength == 2){
            if(!(sender instanceof Player)) return true;
            Player player = (Player) sender;
            if (subCommand.equalsIgnoreCase("get")){
                String team = args[1];
                message = chatUtils.hexFormat(message).replace("%team%", team);
                if(team.equalsIgnoreCase("") || team.equalsIgnoreCase(" ")){
                    return true;
                }
                sender.sendMessage(message);
                for (String teamMate:
                     Teamhardcore.getInstance().sqlGetter.getPlayersInTeam(team)) {
                    sender.sendMessage(" - " + teamMate);
                }
                return true;
            }
            if(subCommand.equalsIgnoreCase("exclude")){
                String playerId = UUIDFetcher.getUUID(player.getName()).toString();
                Teamhardcore.getInstance().sqlGetter.setTeamName(playerId, "");
                message = "Player has been stripped of a team!";
                message = chatUtils.hexFormat(message);
                Teamhardcore.teamMap.remove(UUID.fromString(playerId));
                sender.sendMessage(message);
                return true;
            }
        }
        if(argLength == 3 && admin){
            if (subCommand.equalsIgnoreCase("get")) {
                ArrayList<String> players = Teamhardcore.getInstance().sqlGetter.getPlayersInTeam(args[1]);
                message = Teamhardcore.getInstance().getConfig().getString("messages.team-players");
                message = chatUtils.hexFormat(message).replace("%team%", args[1]);
                sender.sendMessage(message);
                for (String name:
                     players) {
                    sender.sendMessage(" - " + name);
                }
                return true;

            }
            String username = args[1];
            String teamName = args[2];
            UUID uuid = UUIDFetcher.getUUID(username);
            String playerId = uuid.toString();
            if(subCommand.equalsIgnoreCase("invite")){
                Teamhardcore.getInstance().sqlGetter.setTeamName(playerId, teamName);
                Teamhardcore.teamMap.put(uuid, teamName);
                ScoreboardFactory scoreboardFactory = new ScoreboardFactory();
                Player player = Bukkit.getPlayer(uuid);
                if(player != null) {
                    player.setScoreboard(scoreboardFactory.getScoreboard(playerId));
                }
                message = "Player's team has been updated!";
                message = chatUtils.hexFormat(message);
                sender.sendMessage(message);
                return true;
            }
            return true;
        }
        message = Teamhardcore.getInstance().getConfig().getString("messages.usage");
        message = chatUtils.hexFormat(message).replace("%usage%", "/team {get}");
        sender.sendMessage(message);
        return true;
    }
}
