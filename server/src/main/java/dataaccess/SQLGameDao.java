package dataaccess;
import chess.ChessGame;
import model.GameData;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import com.google.gson.Gson;

public class SQLGameDao implements GameDao{
    final private dataaccess.DatabaseManager manager;
    public SQLGameDao(DatabaseManager manager) throws DataAccessException {
        this.manager = manager;
        this.manager.initialize();
    }

    public void updateGame(int gameID, String playerColor, String username) throws DataAccessException {
        try (var conn = manager.getConnection()) {
            var statement = conn.prepareStatement("SELECT * FROM games WHERE gameID = ?");
            statement.setInt(1, gameID);
            String color = null;
            if (playerColor.equals("WHITE")) {
                color = "whiteUsername";
            }
            if (playerColor.equals("BLACK")) {
                color = "whiteUsername";
            }
            try (var result = statement.executeQuery()) {
                if (result.next()) {
                        try (var statementTwo = conn.prepareStatement("UPDATE games SET ? = ? WHERE gameID = ?")) {
                            statementTwo.setString(1, color);
                            statementTwo.setString(2, username);
                            statementTwo.setInt(3, gameID);
                            statementTwo.executeUpdate();
                        }
                } else {
                    throw new DataAccessException("Error: Game ID not in database");
                }

            }
        } catch (SQLException ex) {
            throw new DataAccessException("Error Clearing Game Database", ex);
        }
    }


    public GameData getGame(int gameID) throws DataAccessException {
        try (var conn = manager.getConnection()) {
            var statement = conn.prepareStatement("SELECT * FROM games WHERE gameID = ?");
            statement.setInt(1, gameID);
            try (var result = statement.executeQuery()) {
                if (result.next()) {
                    String gameJson = result.getString("game");
                    Gson gson = new Gson();
                    ChessGame game = gson.fromJson(gameJson, ChessGame.class);

                    return new GameData(result.getInt("gameID"), result.getString("whiteUsername"),
                            result.getString("blackUsername"), result.getString("gameName"),
                            game);
                }
            }

        } catch (SQLException ex) {
                throw new DataAccessException("Error Accessing Game Data", ex);
            }

        return null;
    }

public void clearGames() throws DataAccessException{
        try (var conn = manager.getConnection()) {
        var statement = conn.prepareStatement("DELETE FROM games");
        statement.executeUpdate();
    } catch (SQLException ex) {
        throw new DataAccessException("Error Clearing Game Database", ex);
    }

    }

    public int createGame(String gameName) throws DataAccessException {
        try (var conn = manager.getConnection()) {
            var statement = conn.prepareStatement("INSERT INTO games (gameName, game) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ChessGame game = new ChessGame();
            Gson gson = new Gson();
            String gameJson = gson.toJson(game);
            statement.setString(1, gameName);
            statement.setString(2, gameJson);
            statement.executeUpdate();
            try (var gameId = statement.getGeneratedKeys()) {
                if (gameId.next()) {
                    int newGameID = gameId.getInt(1);
                    return newGameID;
                } else {
                    throw new DataAccessException("Error: Could not retrieve gameID");
                }
            }

        } catch (SQLException ex) {
                throw new DataAccessException("Error Creating Game", ex);
            }
    }

    public Collection<GameData> listGames() throws DataAccessException {
        var games = new java.util.ArrayList<GameData>();
        Gson gson = new Gson();
        try (var conn = manager.getConnection()) {
            var statement = conn.prepareStatement("SELECT * FROM games");
            try (var result = statement.executeQuery()) {
                while (result.next()) {
                    String gameJson = result.getString("game");
                    ChessGame game = gson.fromJson(gameJson, ChessGame.class);
                    GameData data = new GameData(result.getInt("gameID"), result.getString("whiteUsername"),
                            result.getString("blackUsername"), result.getString("gameName"),
                            game);
                    games.add(data);

                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Error Clearing Game Database", ex);
        }
        return games;
    }
    }
