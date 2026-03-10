package dataaccess;

import model.AuthData;
import dataaccess.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class SQLAuthDao implements AuthDao{
    final private dataaccess.DatabaseManager manager;
    public SQLAuthDao(DatabaseManager manager) throws DataAccessException {
        this.manager = manager;
        this.manager.initialize();
    }
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }


    public AuthData createAuth(String username) throws DataAccessException {
        try (var conn = manager.getConnection()){
            String authToken = generateToken();
            var statement = conn.prepareStatement("INSERT INTO auths VALUES (?, ?)");
            statement.setString(1, authToken);
            statement.setString(2, username);
            statement.executeUpdate();
            return new AuthData(authToken, username);
        }
        catch (SQLException ex){
            throw new DataAccessException("Error creating Auth", ex);
        }

    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        try (var conn = manager.getConnection()) {
            var statement = conn.prepareStatement("SELECT * FROM auths WHERE authToken = ?");
            statement.setString(1, authToken);
            try (var result = statement.executeQuery()){
                if (result.next()){
                    String username = result.getString("username");
                    return new AuthData(authToken, username);
                }
                return null;
            }

        }
        catch (SQLException ex) {
            throw new DataAccessException("Error Getting Auth", ex);
        }
    }
    public void deleteAuth(String authToken) throws DataAccessException {
        try (var conn = manager.getConnection()){
            var statement = conn.prepareStatement("DELETE from auths WHERE authToken = ?");
            statement.setString(1, authToken);
            statement.executeUpdate();
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error Getting Auth", ex);
        }

    }
    public void clearAuths() throws DataAccessException {

        try (var conn = manager.getConnection()) {
            var statement = conn.prepareStatement("DELETE FROM auths");
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Error Clearing Auth Database", ex);
        }
    }}
