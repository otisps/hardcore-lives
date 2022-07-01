package technology.otis.teamhardcore.teams;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import technology.otis.teamhardcore.Teamhardcore;
import technology.otis.teamhardcore.general.ChatUtils;

import java.util.ArrayList;

public class TeamsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ChatUtils chatUtils = new ChatUtils();
        ArrayList<String> teamNames = Teamhardcore.getInstance().sqlGetter.getAllTeams();
        String message = Teamhardcore.getInstance().getConfig().getString("messages.get-teams");
        message = chatUtils.hexFormat(message);
        sender.sendMessage(message);
        for (String team:
             teamNames) {
            if((team.equalsIgnoreCase("") || team.equalsIgnoreCase(" "))){
            } else {
                sender.sendMessage(" - " + team);
            }
        }
        return true;
    }
}
