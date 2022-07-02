package technology.otis.teamhardcore.general;


import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class UUIDFetcher {

    public static String getName(UUID uuid){
        PlayerProfile profile = Bukkit.createProfile(uuid);
        profile.complete();
        return profile.getName();
    }

    public static UUID getUUID(String name){
        PlayerProfile profile = Bukkit.createProfile(name);
        profile.complete();
        return profile.getId() ;
    }
}
