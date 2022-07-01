package technology.otis.teamhardcore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import technology.otis.teamhardcore.advancements.AdvancementsListener;
import technology.otis.teamhardcore.advancements.ScoreCommand;
import technology.otis.teamhardcore.db.MySQL;
import technology.otis.teamhardcore.db.SQLGetter;
import technology.otis.teamhardcore.db.SQLTemplate;
import technology.otis.teamhardcore.db.SQLite;
import technology.otis.teamhardcore.general.JoinListener;
import technology.otis.teamhardcore.general.LeaveListener;
import technology.otis.teamhardcore.lives.DeathListener;
import technology.otis.teamhardcore.lives.LivesCommand;
import technology.otis.teamhardcore.lives.SetLivesCommand;
import technology.otis.teamhardcore.teams.PvpListener;
import technology.otis.teamhardcore.teams.TeamCommand;
import technology.otis.teamhardcore.teams.TeamsCommand;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public final class Teamhardcore extends JavaPlugin {
    public static HashMap<UUID, String> teamMap;
    public SQLTemplate sql;
    public SQLGetter sqlGetter;
    private static Teamhardcore instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        instance = this;
        this.teamMap = new HashMap<>();
        this.sql = new MySQL();
        this.sqlGetter = new SQLGetter();

        try {
            sql.connect();
        } catch (ClassNotFoundException | SQLException e ) {
            this.sql = new SQLite(this);
            try {
                sql.connect();
            } catch (ClassNotFoundException | SQLException ex) {
                Bukkit.getLogger().info("Database is not connected, please update config.yml, check your connection and then try again.");
                getServer().getPluginManager().disablePlugin(this);
                getPluginLoader().disablePlugin(this);
                return;
            }
        }

        if (sql.isConnected()){
            getServer().getLogger().info("Database and Example Plugin are connected!");
            sqlGetter.createTable();
        }

        getCommand("teams").setExecutor(new TeamsCommand());
        getCommand("team").setTabCompleter(new TeamCommand());
        getCommand("setlives").setExecutor(new SetLivesCommand());
        getCommand("score").setExecutor(new ScoreCommand());
        getCommand("lives").setExecutor(new LivesCommand());

        getServer().getPluginManager().registerEvents(new AdvancementsListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new PvpListener(), this);


        getServer().getLogger().info("Plugin TeamHardcore Enabled");
    }

    public static Teamhardcore getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        sql.disconnect();
    }
}
