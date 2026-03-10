package passoff.server.dataaccess;

import dataaccess.DatabaseManager;
import dataaccess.SQLAuthDao;
import dataaccess.SQLGameDao;
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
    public void createAuthSuccess() {

    }

    @Test
    @DisplayName("Create Auth Bad Request")
    public void createAuthBadRequest() {

    }

    @Test
    @DisplayName("Get Auth Success")
    public void getAuthSuccess() {

    }

    @Test
    @DisplayName("Get Auth Bad Request")
    public void getAuthBadRequest() {

    }

    @Test
    @DisplayName("Delete Auth Success")
    public void deleteAuthSuccess() {

    }

    @Test
    @DisplayName("Delete Auth Bad Request")
    public void deleteAuthBadRequest() {

    }


}
