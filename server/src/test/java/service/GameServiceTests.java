package service;
import dataaccess.*;
import org.junit.jupiter.api.*;
import server.handlers.exceptions.*;
import server.request.*;
import model.*;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
public class GameServiceTests {private GameService gameService;
    private AuthDao authDao;
    private GameDao gameDao;
    private String validAuthToken;
    private DatabaseManager manager;



    @BeforeEach
    public void setup() throws DataAccessException {
        manager = new DatabaseManager();
        authDao = new SQLAuthDao(manager);
        gameDao = new SQLGameDao(manager);
        gameService = new GameService(authDao, gameDao);
        authDao.clearAuths();
        AuthData auth = authDao.createAuth("testUser");
        validAuthToken = auth.authToken();
        gameDao.clearGames();
    }

    @Test
    @DisplayName("Create Game - Success")
    public void createGameSuccess() throws Exception {
        CreateGameRequest req = new CreateGameRequest("Epic Chess");
        var result = gameService.createGame(validAuthToken, req);
        assertNotNull(result);
        assertTrue(result.gameID() > 0);
    }

    @Test
    @DisplayName("Create Game - Bad Request")
    public void createGameBadRequest() {
        CreateGameRequest req = new CreateGameRequest(null);
        assertThrows(BadRequestException.class, () -> gameService.createGame(validAuthToken, req));
    }

    @Test
    @DisplayName("Join Game - Success")
    public void joinGameSuccess() throws Exception{
        CreateGameRequest req = new CreateGameRequest("Epic Chess");
        var game = gameService.createGame(validAuthToken, req);
        int id = game.gameID();
        JoinGameRequest req2 = new JoinGameRequest("WHITE", id);
        assertDoesNotThrow(() -> gameService.joinGame(validAuthToken, req2));
    }

    @Test
    @DisplayName("Join Game - Bad Request")
    public void joinGameBadRequest() throws Exception{
        CreateGameRequest req = new CreateGameRequest("Epic Chess");
        var game = gameService.createGame(validAuthToken, req);
        int id = game.gameID();
        JoinGameRequest req2 = new JoinGameRequest("WHITE", id);
        gameService.joinGame(validAuthToken, req2);
        JoinGameRequest badRequest = new JoinGameRequest("WHITE", id);
        assertThrows(AlreadyTakenException.class, () -> gameService.joinGame(validAuthToken, badRequest));
    }

    @Test
    @DisplayName("List Games - Success")
    public void listGamesSuccess() throws Exception{
        gameService.createGame(validAuthToken, new CreateGameRequest("Game 1"));
        Collection<GameData> games = gameService.listGames(validAuthToken);
        assertEquals(1, games.size());

    }

    @Test
    @DisplayName("List Games - Bad Request")
    public void listGamesBadRequest() {
        assertThrows(UnAuthorizedException.class, () -> gameService.listGames("fake-token"));
    }

}
