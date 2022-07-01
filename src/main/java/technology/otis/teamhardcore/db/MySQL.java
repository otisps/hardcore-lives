package technology.otis.teamhardcore.db;

import technology.otis.teamhardcore.Teamhardcore;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL extends SQLTemplate {
    private String host;
    private String port;
    private String database;
    private String username;
    private String password;

    public MySQL() {
        this.host = Teamhardcore.getInstance().getConfig().getString("mysql.host");
        this.port = Teamhardcore.getInstance().getConfig().getString("mysql.port");;
        this.database = Teamhardcore.getInstance().getConfig().getString("mysql.database");;
        this.username = Teamhardcore.getInstance().getConfig().getString("mysql.username");;
        this.password = Teamhardcore.getInstance().getConfig().getString("mysql.password");;
    }

    @Override
    public void connect() throws ClassNotFoundException, SQLException {
        if(isConnected()) return;
        connection = DriverManager.getConnection(
                "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false",
                username, password);
    }

}
