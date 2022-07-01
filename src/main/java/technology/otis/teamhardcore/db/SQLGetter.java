package technology.otis.teamhardcore.db;

import technology.otis.teamhardcore.Teamhardcore;
import technology.otis.teamhardcore.general.UUIDFetcher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class SQLGetter {

    /**
     * If table doesn't exist, create it!
     */
    public void createTable () {
        PreparedStatement statement;
        try {
            statement = Teamhardcore.getInstance().sql.getConnection()
                    .prepareStatement("CREATE TABLE IF NOT EXISTS playerData " +
                            "(name VARCHAR (64), playerID VARCHAR (64)," +
                            " teamName VARCHAR (64), advancements int, " +
                            "lives int, PRIMARY KEY (playerID))");

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to see if a target player contains in the player data table
     * @param playerId target player's uuid
     * @return true or false
     */
    public boolean tableContains(String playerId){
        try {
            PreparedStatement statement = Teamhardcore.getInstance().sql.getConnection()
                    .prepareStatement("SELECT * FROM playerData WHERE playerID=?");
            statement.setString(1, playerId);
            ResultSet results = statement.executeQuery();
            if(results.next()){
                return true;
            }
            return false;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Add a player to the table
     * @param username target username
     */
    public void addUser (String username) {
        try {
            String uuid = UUIDFetcher.getUUID(username).toString();
            boolean exist = tableContains(uuid);
            if(!exist){
                PreparedStatement statement = Teamhardcore.getInstance().sql.getConnection().prepareStatement("INSERT INTO playerData VALUES (?, ?, ?, ?, ?)");
                statement.setString(1, username);
                statement.setString(2, uuid);
                statement.setString(3,"");
                statement.setInt(4, 0);
                statement.setInt(5,0);
                statement.executeUpdate();
                return;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * Function which queries the MySQL database for a players advancements
     * @param playerId target player's uuid
     * @return The target player's advancements
     */
    public int getAdvancements(String playerId){
        try {
            PreparedStatement statement = Teamhardcore.getInstance().sql.getConnection()
                    .prepareStatement("SELECT advancements FROM playerData WHERE playerID=?");
            statement.setString(1, playerId);
            ResultSet results = statement.executeQuery();
            if(results.next()){
                return results.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Function which queries the MySQL database for a players team name
     * @param playerId target player's uuid
     * @return The target player's team name
     */
    public String getTeamName(String playerId){
        try {
            PreparedStatement statement = Teamhardcore.getInstance().sql.getConnection()
                    .prepareStatement("SELECT teamName FROM playerData WHERE playerID=?");
            statement.setString(1, playerId);
            ResultSet results = statement.executeQuery();
            if(results.next()){
                return results.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * Function which queries the MySQL database for a players lives
     * @param playerId target player's uuid
     * @return The target player's lives
     */
    public int getLives(String playerId){
        try {
            PreparedStatement statement = Teamhardcore.getInstance().sql.getConnection()
                    .prepareStatement("SELECT lives FROM playerData WHERE playerID=?");
            statement.setString(1, playerId);
            ResultSet results = statement.executeQuery();
            if(results.next()){
                return results.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Procedure to allow the bruteforce setting of advancements
     * @param playerId target player's uuid
     * @param advancements the new integer value for target player's advancements
     */
    public void setAdvancements(String playerId, int advancements) {
        if (!tableContains(playerId)){
            addUser(UUIDFetcher.getName(UUID.fromString(playerId)));
        }
        try {
            PreparedStatement statement = Teamhardcore.getInstance().sql.getConnection()
                    .prepareStatement("UPDATE playerData SET advancements=? WHERE playerID=?");
            statement.setInt(1, advancements);
            statement.setString(2, playerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Procedure to allow the bruteforce setting of teamName
     * @param playerId target player's uuid
     * @param teamName the new integer value for target player's teamName
     */
    public void setTeamName(String playerId, String teamName) {
        if (!tableContains(playerId)){
            addUser(UUIDFetcher.getName(UUID.fromString(playerId)));
        }
        try {
            PreparedStatement statement = Teamhardcore.getInstance().sql.getConnection()
                    .prepareStatement("UPDATE playerData SET teamName=? WHERE playerID=?");
            statement.setString(1, teamName);
            statement.setString(2, playerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Procedure to allow the bruteforce setting of lives
     * @param playerId target player's uuid
     * @param lives the new integer value for target player's lives
     */
    public void setLives (String playerId, int lives) {
        if (!tableContains(playerId)){
            addUser(UUIDFetcher.getName(UUID.fromString(playerId)));
        }
        try {
            PreparedStatement statement = Teamhardcore.getInstance().sql.getConnection()
                    .prepareStatement("UPDATE playerData SET lives=? WHERE playerID=?");
            statement.setInt(1, lives);
            statement.setString(2, playerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all active teams!
     * @return arraylist of team names
     */
    public ArrayList<String> getAllTeams(){
        ArrayList<String> teams = new ArrayList<>();
        try {
            PreparedStatement statement = Teamhardcore.getInstance().sql.getConnection()
                    .prepareStatement("SELECT teamName FROM playerData");
            ResultSet results = statement.executeQuery();
            while(results.next()){
                String teamName = results.getString(1);
                boolean contains = teams.contains(teamName);
                if (contains){
                    continue;
                }
                if (!contains){
                    teams.add(teamName);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return teams;
    }

    /**
     * Gets all the players in a team
     * @param teamName the target team
     * @return the players in that team!
     */
    public ArrayList<String> getPlayersInTeam (String teamName) {
        ArrayList<String> players = new ArrayList<>();
        try {
            PreparedStatement statement = Teamhardcore.getInstance().sql.getConnection()
                    .prepareStatement("SELECT playerID FROM playerData WHERE teamName=?");
            statement.setString(1,teamName);
            ResultSet results = statement.executeQuery();
            while(results.next()){
                String playerId = results.getString(1);
                UUID uuid = UUID.fromString(playerId);
                String username = UUIDFetcher.getName(uuid);
                players.add(username);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return players;
    }

}
