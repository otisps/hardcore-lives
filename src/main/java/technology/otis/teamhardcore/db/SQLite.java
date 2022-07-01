package technology.otis.teamhardcore.db;

import technology.otis.teamhardcore.Teamhardcore;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite extends SQLTemplate{
    private String databaseName;

    public SQLite(Teamhardcore plugin){
        this.databaseName = plugin.getConfig().getString("sqlite.database");
    }
    @Override
    public void connect() throws ClassNotFoundException, SQLException {
        if (databaseName == null) {
            Teamhardcore.getInstance().getLogger().warning("No database connected!");
            return;
        }
        File dataFile = new File(Teamhardcore.getInstance().getDataFolder(), databaseName + ".db");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                Teamhardcore.getInstance().getLogger().warning("No database connected!");
            }
        }
        if (isConnected()) return;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + dataFile);
        return;
    }
}
