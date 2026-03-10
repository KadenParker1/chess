package dataaccess;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.SQLAuthDao;
import dataaccess.SQLGameDao;
import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SQLAuthTests {
    private SQLAuthDao authDao;
    private DatabaseManager manager;

    @BeforeEach
    public void setup() throws Exception{
        manager = new DatabaseManager();
        authDao = new SQLAuthDao(manager);
        authDao.clearAuths();
    }

    @Test
    @DisplayName("Create Auth Success")
    public void createAuthSuccess() throws DataAccessException {
        AuthData auth = authDao.createAuth("kaden");
        Assertions.assertNotNull(auth.authToken());
        Assertions.assertEquals("kaden", auth.username());
    }

    @Test
    @DisplayName("Create Auth Bad Request")
    public void createAuthBadRequest() {
        Assertions.assertThrows(DataAccessException.class,
                () -> authDao.createAuth(null));
    }

    @Test
    @DisplayName("Get Auth Success")
    public void getAuthSuccess() throws DataAccessException {
        AuthData created = authDao.createAuth("kaden");
        AuthData retrieved = authDao.getAuth(created.authToken());
        Assertions.assertNotNull(retrieved);
        Assertions.assertEquals(created.username(), retrieved.username());
        Assertions.assertEquals(created.authToken(), retrieved.authToken());
    }

    @Test
    @DisplayName("Get Auth Bad Request")
    public void getAuthBadRequest() throws DataAccessException {
        AuthData retrieved = authDao.getAuth("fake-token-1eueiuieiuei23");
        Assertions.assertNull(retrieved, "Should return null for non-existent token");
    }

    @Test
    @DisplayName("Delete Auth Success")
    public void deleteAuthSuccess() throws DataAccessException {
        AuthData auth = authDao.createAuth("kaden");
        authDao.deleteAuth(auth.authToken());
        AuthData retrieved = authDao.getAuth(auth.authToken());
        Assertions.assertNull(retrieved, "Token should be null after deletion");
    }

    @Test
    @DisplayName("Delete Auth Bad Request")
    public void deleteAuthBadRequest() {
        Assertions.assertDoesNotThrow(() -> authDao.deleteAuth("non-existent-token"));
    }


}
