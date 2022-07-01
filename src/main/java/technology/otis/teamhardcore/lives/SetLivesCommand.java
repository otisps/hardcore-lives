package technology.otis.teamhardcore.lives;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import technology.otis.teamhardcore.Teamhardcore;
import technology.otis.teamhardcore.db.SQLGetter;
import technology.otis.teamhardcore.general.ChatUtils;
import technology.otis.teamhardcore.general.UUIDFetcher;

public class SetLivesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ChatUtils chatUtils = new ChatUtils();
        boolean admin = sender.isOp() || !(sender instanceof Player);
        if(!admin) return true;
        int arglength = args.length;
        if(arglength == 2 && admin){
            String username = args[0];
            String playerId = String.valueOf(UUIDFetcher.getUUID(username));
            int lives = Integer.parseInt(args[1]);
            SQLGetter sqlGetter = Teamhardcore.getInstance().sqlGetter;
            sqlGetter.setLives(playerId, lives);
            String message = "Player updated with new total of lives!";
            message = chatUtils.hexFormat(message);
            sender.sendMessage(message);
            return true;
        }
        String message = Teamhardcore.getInstance().getConfig().getString("messages.usage");
        message = chatUtils.hexFormat(message);
        sender.sendMessage(message);
        return true;
    }
}
