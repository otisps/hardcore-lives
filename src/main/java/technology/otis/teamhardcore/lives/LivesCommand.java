package technology.otis.teamhardcore.lives;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import technology.otis.teamhardcore.Teamhardcore;
import technology.otis.teamhardcore.db.SQLGetter;
import technology.otis.teamhardcore.general.ChatUtils;
import technology.otis.teamhardcore.general.UUIDFetcher;

public class LivesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ChatUtils chatUtils = new ChatUtils();
        boolean admin = sender.isOp() || !(sender instanceof Player);
        SQLGetter sqlGetter = Teamhardcore.getInstance().sqlGetter;
        int argLength = args.length;
        if (argLength == 0){
            if (sender instanceof  Player){
                Player player = (Player) sender;
                String playerId = player.getUniqueId().toString();
                sqlGetter.getLives(playerId);
                String message = Teamhardcore.getInstance().getConfig().getString("messages.own-lives");
                message = chatUtils.hexFormat(message);
                sender.sendMessage(message);
                return true;
            }
        }
        if (argLength == 1) {
            String username = args[0];
            String targetId = UUIDFetcher.getUUID(username).toString();
            if(!sqlGetter.tableContains(targetId)){
                String message = Teamhardcore.getInstance().getConfig().getString("messages.not-found");
                message = chatUtils.hexFormat(message);
                sender.sendMessage(message);
                return true;
            }
            if (admin) {
                sqlGetter.getLives(targetId);
                String message = Teamhardcore.getInstance().getConfig().getString("messages.other-lives");
                message = chatUtils.hexFormat(message);
                sender.sendMessage(message);
                return true;
            }

            Player senderP = (Player) sender;
            if (sqlGetter.getTeamName(senderP.getUniqueId().toString()).equalsIgnoreCase(sqlGetter.getTeamName(targetId))){
                sqlGetter.getLives(targetId);
                String message = Teamhardcore.getInstance().getConfig().getString("messages.other-lives");
                message = chatUtils.hexFormat(message);
                sender.sendMessage(message);
                return true;
            }
        }
        String message = Teamhardcore.getInstance().getConfig().getString("messages.usage");
        message = chatUtils.hexFormat(message);
        sender.sendMessage(message);
        return true;
    }
}
