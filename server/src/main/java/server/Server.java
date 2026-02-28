package server;

import dataaccess.MemoryAuthDao;
import dataaccess.MemoryGameDao;
import dataaccess.MemoryUserDao;
import io.javalin.*;
import server.handlers.*;
import server.result.ErrorMessage;
import service.ClearService;
import service.GameService;
import service.UserService;

public class Server {

    private final Javalin javalin;
    private final UserService userService;
    private final GameService gameService;
    private final ClearService clearService;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        var authDao = new MemoryAuthDao();
        var userDao = new MemoryUserDao();
        var gameDao = new MemoryGameDao();

        this.userService = new UserService(authDao, userDao);
        this.gameService = new GameService(authDao, gameDao);
        this.clearService = new ClearService(authDao, userDao, gameDao);

        ClearApplicationHandler clearHandler = new ClearApplicationHandler(clearService);
        RegisterHandler registerHandler = new RegisterHandler(userService);
        LogoutHandler logoutHandler = new LogoutHandler(userService);
        LoginHandler loginHandler = new LoginHandler(userService);
        ListGamesHandler listGamesHandler = new ListGamesHandler(gameService);
        CreateGameHandler createGameHandler = new CreateGameHandler(gameService);
        JoinGameHandler joinGameHandler = new JoinGameHandler(gameService);
        // Register your endpoints and exception handlers here.

        javalin.delete("/db", clearHandler::handle);
        javalin.post("/user", registerHandler::handle);
        javalin.delete("/session", logoutHandler::handle);
        javalin.post("/session", loginHandler::handle);
        javalin.get("/game", listGamesHandler::handle);
        javalin.post("/game", createGameHandler::handle);
        javalin.put("/game", joinGameHandler::handle);
        javalin.exception(Exception.class, (e, ctx) -> {
            ctx.status(500);
            ctx.json(new ErrorMessage("Error: " + e.getMessage()));
        });

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
