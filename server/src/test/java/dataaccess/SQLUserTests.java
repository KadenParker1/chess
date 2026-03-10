package dataaccess;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.SQLUserDao;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SQLUserTests {
    private SQLUserDao userDao;
    private DatabaseManager manager;

    @BeforeEach
    public void setup() throws Exception{
        manager = new DatabaseManager();
        userDao = new SQLUserDao(manager);
        userDao.clearUsers();
    }


    @Test
    @DisplayName("Create User - Positive")
    public void createUserPositive () throws DataAccessException{
        UserData user  = new UserData("kaden", "password", "coolguy@gmail.com");
        Assertions.assertDoesNotThrow(() -> userDao.createUser(user));
        UserData retrieved = userDao.getUser("kaden");
        Assertions.assertNotNull(retrieved);
        Assertions.assertEquals("kaden", retrieved.username());
    }

    @Test
    @DisplayName("Create User - Negative")
    public void createUserNegative () throws DataAccessException{
        UserData user  = new UserData("kaden", "password", "coolguy@gmail.com");
        userDao.createUser(user);
        Assertions.assertThrows(DataAccessException.class, () -> userDao.createUser(user));
    }

    @Test
    @DisplayName("Get User - Positive")
    public void getUserPositive() throws DataAccessException {
        userDao.createUser(new UserData("kaden", "brown", "brown@town.com"));
        UserData user = userDao.getUser("kaden");
        Assertions.assertNotNull(user);
    }

    @Test
    @DisplayName("Get User - Negative")
    public void getUserNegative() throws DataAccessException {
        UserData user = userDao.getUser("Random Guy");
        Assertions.assertNull(user, "Should be null, Random Guy is not in database");
    }

}
