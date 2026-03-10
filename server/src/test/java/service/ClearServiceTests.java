package service;
import dataaccess.*;
import org.junit.jupiter.api.*;
import server.handlers.exceptions.*;
import server.request.*;
import model.*;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ClearServiceTests {
    private ClearService service;
    private UserDao userDao;
    private AuthDao authDao;
    private GameDao gamDao;
    private DatabaseManager manager;


    @BeforeEach
    public void setup() throws DataAccessException {
        manager = new DatabaseManager();
        userDao = new SQLUserDao(manager);
        authDao = new SQLAuthDao(manager);
        gamDao = new SQLGameDao(manager);

        service = new ClearService(authDao, userDao, gamDao);
    }

    @Test
    @DisplayName("Clear Application - success")
    public void clearApplicationSuccess() throws DataAccessException{
        userDao.createUser(new model.UserData("Kaden Parker", "123", "kaden@gmail.com"));
        assertDoesNotThrow(() -> service.clearApplication());
        assertNull(userDao.getUser("Kaden Parker"), "UserDao should be empty after clear");

    }


}
