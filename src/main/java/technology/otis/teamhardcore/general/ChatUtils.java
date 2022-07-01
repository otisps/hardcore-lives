package technology.otis.teamhardcore.general;

import org.bukkit.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtils {
    private final Pattern hexPattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public String hexFormat(String message){ // Credit to CodedRed
        Matcher match = hexPattern.matcher(message);
        while (match.find()){
            String colour = message.substring(match.start(), match.end());
            message = message.replace(colour, ChatColor.valueOf(colour) + "");
            match = hexPattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
