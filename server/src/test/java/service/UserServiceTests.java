package service;
import dataaccess.*;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import model.UserData;
import server.handlers.exceptions.AlreadyTakenException;
import server.handlers.exceptions.BadRequestException;
import server.handlers.exceptions.UnAuthorizedException;
import server.request.LoginRequest;
import server.request.RegisterRequest;
import server.result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTests {
    private UserService service;
    private UserDao userDao;
    private AuthDao authDao;
    private String validAuthToken;
    private DatabaseManager manager;

    @BeforeEach
    public void setup() throws DataAccessException {
        manager = new DatabaseManager();
        userDao = new SQLUserDao(manager);
        authDao = new SQLAuthDao(manager);
        service = new UserService(authDao, userDao);
        userDao.clearUsers();
        authDao.clearAuths();

        AuthData auth = authDao.createAuth("testUser");
        validAuthToken = auth.authToken();
    }


    @Test
    @DisplayName("Register - Success")
    public void registerSuccess() throws Exception{
        String user = "kaden";
        RegisterRequest req = new RegisterRequest(user, "password", "kaden@gmail.com");
        RegisterResult result = service.register(req);
        assertNotNull(result);
        assertTrue(result.username().equals(user));
    }

    @Test
    @DisplayName("Register - Already Taken")
    public void registerAlreadyTaken() throws Exception{
        String user = "kaden";
        RegisterRequest req = new RegisterRequest(user, "password", "kaden@gmail.com");
        RegisterResult result = service.register(req);
        assertThrows(AlreadyTakenException.class, () -> service.register(req));
    }

    @Test
    @DisplayName("Login - Success")
    public void loginSuccess() throws Exception{
        String user = "kaden";
        String password = "password";
        RegisterRequest regRequest = new RegisterRequest(user, password, "gmail.com");
        RegisterResult res = service.register(regRequest);
        LoginRequest req = new LoginRequest(user, password);
        var result = service.login(req);
        assertNotNull(result.authToken());
        assertEquals(user, result.username());
    }

    @Test
    @DisplayName("Login - bad request")
    public void loginBadRequest() throws BadRequestException, AlreadyTakenException, DataAccessException, UnAuthorizedException{
        String user = "kaden";
        String password = "password";
        RegisterRequest regRequest = new RegisterRequest(user, password, "gmail.com");
        RegisterResult res = service.register(regRequest);
        LoginRequest req = new LoginRequest(user, "BAD PASSWORd");
        assertThrows(Exception.class, () -> service.login(req));


    }

    @Test
    @DisplayName("Logout - Success")
    public void logoutSuccess() throws Exception{
        var reg = service.register(new RegisterRequest("kaden", "password", "gmail.com"));
        String token = reg.authToken();
        assertDoesNotThrow(() -> service.logout(token));
        assertNull(authDao.getAuth(token), "Auth token should be deleted from DAO");
    }

    @Test
    @DisplayName("Logout - unauthorized")
    public void logoutUnauthorized() {
        assertThrows(UnAuthorizedException.class, () -> service.logout("fake_token"));
    }
}
