package dataaccess;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.GameDao;
import dataaccess.SQLGameDao;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class SQLGameTests {
    private SQLGameDao gameDao;
    private DatabaseManager manager;

    @BeforeEach
    public void setup() throws Exception{
        manager = new DatabaseManager();
        gameDao = new SQLGameDao(manager);
        gameDao.clearGames();
    }


    @Test
    @DisplayName("Update Game Success")
    public void updateGameSuccess() throws DataAccessException {
     int id = gameDao.createGame("CoolestGameEver");
        Assertions.assertDoesNotThrow(() -> gameDao.updateGame(id, "WHITE", "kaden"));
        GameData updated = gameDao.getGame(id);
        Assertions.assertEquals("kaden", updated.whiteUsername());
    }

    @Test
    @DisplayName("Get Game Bad Request")
    public void getGameBadRequest() throws DataAccessException {
        int id = gameDao.createGame("Gamer1");
        int wrongId = 5993;
        GameData game = gameDao.getGame(wrongId);
        Assertions.assertNull(game);
    }

    @Test
    @DisplayName("Update Game  Bad Request")
    public void updateGameBadRequest(){
        Assertions.assertThrows(DataAccessException.class, () ->
                gameDao.updateGame(12345, "BLACK", "ghost")
        );
    }

    @Test
    @DisplayName("Get Game Success")
    public void getGameSuccess() throws DataAccessException {
        int id = gameDao.createGame("Gamer1");
        GameData game = gameDao.getGame(id);
        Assertions.assertNotNull(game);
        Assertions.assertEquals("Gamer1", game.gameName());

    }

    @Test
    @DisplayName("Create Game Success")
    public void createGameSuccess() throws Exception{
        int gameID = gameDao.createGame("Cool Game");
        Assertions.assertTrue(gameID > 0);
    }

    @Test
    @DisplayName("Create Game Bad Request")
    public void createGameBadRequest() throws Exception{
        Assertions.assertThrows(DataAccessException.class, () -> gameDao.createGame(null));
    }

    @Test
    @DisplayName("List Game Bad Request")
    public void listGameBadRequest() {
        Assertions.assertDoesNotThrow(() -> {
            Collection<GameData> games = gameDao.listGames();
            Assertions.assertNotNull(games);
        });
    }

    @Test
    @DisplayName("List Game Success")
    public void listGameSuccess() throws DataAccessException {
        gameDao.createGame("Game 1");
        gameDao.createGame("Game 2");
        Collection<GameData> games = gameDao.listGames();
        Assertions.assertEquals(2, games.size());

    }


}
