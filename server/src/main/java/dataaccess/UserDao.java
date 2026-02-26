package dataaccess;

import model.UserData;

public interface UserDao {
    UserData getUser(String username) throws DataAccessException;
    void createUser(String username) throws DataAccessException;
    void clearUsers() throws DataAccessException;
}
