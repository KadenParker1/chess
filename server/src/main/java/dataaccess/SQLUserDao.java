package dataaccess;
import model.AuthData;
import model.UserData;

import java.sql.SQLException;

public class SQLUserDao implements UserDao{
    final private dataaccess.DatabaseManager manager;
    public SQLUserDao(DatabaseManager manager) throws DataAccessException {
        this.manager = manager;
        this.manager.initialize();
    }

    public UserData getUser(String username) throws DataAccessException {
        try (var conn = manager.getConnection()) {
            var statement = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1, username);
            try (var result = statement.executeQuery()){
                if (result.next()){
                    String password = result.getString("password");
                    String email = result.getString("email");
                    return new UserData(username, password, email);
                }
                return null;
            }

        }
        catch (SQLException ex) {
            throw new DataAccessException("Error Getting Auth", ex);
        }
    }

    public void createUser(UserData userData) throws DataAccessException {
        String username = userData.username();
        String password = userData.password();
        String email = userData.email();
        try (var conn = manager.getConnection()) {
            var statement = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1, username);
            try (var result = statement.executeQuery()) {
                if (result.next()) {
                    throw new DataAccessException("Username already exists");
                }
                else {
                    var statementTwo = conn.prepareStatement("INSERT INTO users VALUES(?, ?, ?)");
                    statementTwo.setString(1, username);
                    statementTwo.setString(2, password);
                    statementTwo.setString(3, email);
                    statement.executeUpdate();
                }
                }
            }
        catch (SQLException ex) {
            throw new DataAccessException("Error Getting Auth", ex);
        }
    }

    public void clearUsers() throws DataAccessException{
        try (var conn = manager.getConnection()) {
            var statement = conn.prepareStatement("DELETE FROM users");
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Error Getting Auth", ex);
        }
    }
}
