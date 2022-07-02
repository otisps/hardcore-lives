package technology.otis.teamhardcore.advancements;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import technology.otis.teamhardcore.Teamhardcore;
import technology.otis.teamhardcore.db.SQLGetter;
import technology.otis.teamhardcore.general.ChatUtils;
import technology.otis.teamhardcore.general.UUIDFetcher;

import java.util.UUID;

public class ScoreCommand implements CommandExecutor {

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
                int score = sqlGetter.getAdvancements(playerId);
                String message = Teamhardcore.getInstance().getConfig().getString("messages.own-score");
                message = chatUtils.hexFormat(message).replace("%score%", score + "");
                sender.sendMessage(message);
                return true;
            }
        }
        if (argLength == 1) {
            String username = args[0];
            UUID uuid = null;
            uuid = UUIDFetcher.getUUID(username);
            String targetId = uuid.toString();
            int score = sqlGetter.getAdvancements(targetId);
            if(!(sqlGetter.tableContains(targetId))) {
                String message = Teamhardcore.getInstance().getConfig().getString("messages.unknown-target");
                message = chatUtils.hexFormat(message).replace("%player%", args[0]);
                sender.sendMessage(message);
                return true;
            }
            Player senderP = (Player) sender;
            if (admin || sqlGetter.getTeamName(senderP.getUniqueId().toString()).equalsIgnoreCase(sqlGetter.getTeamName(targetId))){
                sqlGetter.getAdvancements(targetId);
                String message = Teamhardcore.getInstance().getConfig().getString("messages.other-score");
                message = chatUtils.hexFormat(message).replace("%score%", score + "").replace("%player%", args[0]);
                sender.sendMessage(message);
            }

            return true;
        }
        String message = Teamhardcore.getInstance().getConfig().getString("messages.usage");
        message = chatUtils.hexFormat(message).replace("%usage%", "See your own score or a teammates: /score {Optional - teamMate}");
        sender.sendMessage(message);
        return true;
    }
}
