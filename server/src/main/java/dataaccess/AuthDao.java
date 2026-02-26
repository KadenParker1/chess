package dataaccess;

import model.AuthData;

public interface AuthDao {
    AuthData getAuth(String authToken) throws DataAccessException;

    AuthData createAuth(String username) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
    void clearAuths() throws DataAccessException;
}
