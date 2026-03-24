package client;

import model.result.request.CreateGameRequest;
import model.result.request.JoinGameRequest;
import model.result.request.LoginRequest;
import model.result.request.RegisterRequest;
import org.junit.jupiter.api.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;

public class ServerFacadeTests {
    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    void clear() throws exception.ResponseException {
        facade.clear();
    }

    @Test
    @DisplayName("Register Success")
    public void registerSuccess() throws Exception {
        RegisterRequest request = new RegisterRequest("player1", "password", "p1@email.com");
        var authData = facade.register(request);
        assertTrue(authData.authToken().length() > 10);
    }

    @Test
    @DisplayName("Register Failure")
    public void registerFailure() throws Exception {
        facade.register(new RegisterRequest("player1", "pass", "email.com"));
        assertThrows(exception.ResponseException.class, () ->
                facade.register(new RegisterRequest("player1", "pass", "email.com"))
        );
    }


    @Test
    @DisplayName("Login Success")
    public void loginSuccess() throws Exception {
        facade.register(new RegisterRequest("player1", "pass", "p1@email.com"));
        var res = facade.login(new LoginRequest("player1", "pass"));
        assertNotNull(res.authToken());
    }

    @Test
    @DisplayName("Login Failure")
    public void loginFailure() throws Exception {
        assertThrows(exception.ResponseException.class, () ->
                facade.login(new LoginRequest("nonexistent", "wrong"))
        );
    }

    @Test
    @DisplayName("Logout Success")
    public void logoutSuccess() throws Exception {
        facade.register(new RegisterRequest("player1", "pass", "p1@email.com"));
        var res = facade.login(new LoginRequest("player1", "pass"));
        assertDoesNotThrow(() -> facade.logout(res.authToken()));
    }

    @Test
    @DisplayName("Logout Failure")
    public void logoutFailure() throws Exception {
        assertThrows(exception.ResponseException.class, () ->facade.logout(""));
    }

    @Test
    @DisplayName("Join Success")
    public void joinSuccess() throws Exception {
        facade.register(new RegisterRequest("player1", "pass", "p1@email.com"));
        var auth = facade.login(new LoginRequest("player1", "pass"));
        String authToken = auth.authToken();
        var gameResult = facade.createGame(new CreateGameRequest("CooolGame"), authToken);
        assertDoesNotThrow(() -> facade.join(new JoinGameRequest("WHITE", gameResult.gameID()), authToken));
    }

    @Test
    @DisplayName("Join Failure")
    public void joinFailure() throws Exception {
        var auth = facade.register(new RegisterRequest("player1", "pass", "p1@email.com"));
        assertThrows(exception.ResponseException.class, () ->
                facade.join(new JoinGameRequest("WHITE", 999), auth.authToken())
        );
    }

    @Test
    @DisplayName("CreateGame Success")
    public void createGameSuccess() throws Exception {
        facade.register(new RegisterRequest("player1", "pass", "p1@email.com"));
        var auth = facade.login(new LoginRequest("player1", "pass"));
        String authToken = auth.authToken();
        var id = facade.createGame(new CreateGameRequest("CooolGame"), authToken);
        assertNotNull(id);
    }

    @Test
    @DisplayName("CreateGame Failure")
    public void createGameFailure() throws Exception {
        assertThrows(exception.ResponseException.class, () -> facade.createGame(new CreateGameRequest("CooolGame"), "hghghghghghg"));
    }


    @Test
    @DisplayName("listGames Success")
    public void listGameSuccess() throws Exception {
        var auth = facade.register(new RegisterRequest("player1", "pass", "p1@email.com"));
        facade.createGame(new CreateGameRequest("Game1"), auth.authToken());
        facade.createGame(new CreateGameRequest("Game2"), auth.authToken());
        var res = facade.listGames(auth.authToken());
        assertEquals(2, res.games().size());
    }

    @Test
    @DisplayName("listGames Failure")
    public void listGameFailure() throws Exception {
        assertThrows(exception.ResponseException.class, () -> facade.listGames("hghghghghghg"));
    }


}
