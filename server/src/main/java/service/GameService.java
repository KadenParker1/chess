package service;

import chess.ChessGame;
import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import dataaccess.GameDao;
import dataaccess.UserDao;
import model.AuthData;
import model.GameData;
import server.handlers.exceptions.AlreadyTakenException;
import server.handlers.exceptions.BadRequestException;
import server.handlers.exceptions.UnAuthorizedException;
import server.request.CreateGameRequest;
import server.request.JoinGameRequest;
import server.result.CreateGameResult;

import java.util.Collection;

public class GameService {
    private final AuthDao authDao;
    private final GameDao gameDao;

    public GameService(AuthDao authDao, GameDao gameDao){
        this.authDao = authDao;
        this.gameDao = gameDao;
    }

    public Collection<GameData> listGames(String authToken) throws UnAuthorizedException, DataAccessException {
        if (authDao.getAuth(authToken) == null){
            throw new UnAuthorizedException(("Error: unauthorized"));
        }
        return gameDao.listGames();
    }

    public CreateGameResult createGame(String authToken, CreateGameRequest req) throws UnAuthorizedException,
            DataAccessException, BadRequestException {
        if (authDao.getAuth(authToken) == null){
            throw new UnAuthorizedException(("Error: unauthorized"));
        }

        if (req.gameName() == null){
            throw new BadRequestException(("Error: bad request"));
        }

        int gameID = gameDao.createGame(req.gameName());
        return new CreateGameResult(gameID);
    }

    public void joinGame(String authToken, JoinGameRequest req) throws UnAuthorizedException, DataAccessException,
            BadRequestException, AlreadyTakenException {
        if (authDao.getAuth(authToken) == null){
            throw new UnAuthorizedException(("Error: unauthorized"));
        }
        AuthData authData = authDao.getAuth(authToken);
        String username = authData.username();
        GameData game = gameDao.getGame(req.gameID());
        if (game == null){
            throw new BadRequestException(("Error: bad request"));
        }
        if (req.playerColor() == null){
            throw new BadRequestException(("Error: bad request"));
        }

        if (req.playerColor().equals("WHITE")) {
            if (game.whiteUsername() != null){
                throw new AlreadyTakenException(("Error: bad request"));
            }
            gameDao.updateGame(req.gameID(), "WHITE", username);
        }

        else if (req.playerColor().equals("BLACK")) {
            if (game.blackUsername() != null){
                throw new AlreadyTakenException(("Error: bad request"));
            }
            gameDao.updateGame(req.gameID(), "BLACK", username);
        }
        else {
            throw new BadRequestException(("Error: bad request"));
        }


    }

}
