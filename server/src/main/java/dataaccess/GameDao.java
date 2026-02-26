package dataaccess;

import model.AuthData;
import model.GameData;

import java.util.Collection;

public interface GameDao {
void clearGames() throws DataAccessException;
void updateGame(int gameID, String playerColor, String username) throws DataAccessException;
GameData getGame(int gameID) throws DataAccessException;
int createGame(GameData gameData) throws DataAccessException;
Collection<GameData> listGames() throws DataAccessException;
}