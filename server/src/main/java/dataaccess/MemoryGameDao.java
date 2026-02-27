package dataaccess;
import model.GameData;
import java.util.HashMap;
import java.util.Collection;

public class MemoryGameDao implements GameDao {
    final private HashMap<java.lang.Integer, GameData> games = new HashMap<>();

    public void clearGames() {
        games.clear();
    }

    public void updateGame(int gameID, String playerColor, String username) throws DataAccessException{
        GameData currentGame = games.get(gameID);
        if (currentGame == null) {
            throw new DataAccessException("Error: bad request - game does not exist");
        }
        games.remove(gameID);
        if (playerColor.equals("WHITE")) {
            GameData newGame = new GameData(gameID, username, currentGame.blackUsername(), currentGame.gameName(), currentGame.game());
            games.put(gameID, newGame);
        }
        else {
            GameData newGame = new GameData(gameID, currentGame.whiteUsername(), username,currentGame.gameName(), currentGame.game());
            games.put(gameID, newGame);
        }
    }
public GameData getGame(int gameID) {
    return games.get(gameID);
}

public int createGame(GameData gameData)  {
    games.put(gameData.gameID(), gameData);
    return gameData.gameID();
}

public Collection<GameData> listGames() {
    return games.values();
}





}
