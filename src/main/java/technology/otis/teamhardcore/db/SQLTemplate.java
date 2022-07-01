package technology.otis.teamhardcore.db;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class SQLTemplate {
    public Connection connection;
    public Boolean isConnected() {
        return (connection == null ? false : true);
    }
    public abstract void connect() throws ClassNotFoundException, SQLException;

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
