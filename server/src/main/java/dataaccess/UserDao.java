package dataaccess;

import model.GameData;
import model.UserData;

import java.util.HashMap;

public interface UserDao {
    UserData getUser(String username) throws DataAccessException;
    void createUser(UserData userData) throws DataAccessException;
    void clearUsers() throws DataAccessException;

    public class MemoryGameDao implements GameDao {
        final private HashMap<Integer, GameData> games = new HashMap<>();

        public void clearGames() {
            Games.clear();
        }

        public void updateGame(int gameID, String playerColor, String username) {
        GameData currentGame = games.get(gameID);
        if (playerColor == "WHITE") {

        }
        }
        }
}
