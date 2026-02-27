package server;

import dataaccess.MemoryAuthDao;
import dataaccess.MemoryGameDao;
import dataaccess.MemoryUserDao;
import io.javalin.*;
import server.handlers.ClearApplicationHandler;
import server.handlers.LoginHandler;
import server.handlers.LogoutHandler;
import server.handlers.RegisterHandler;
import server.result.ErrorMessage;
import service.ClearService;
import service.GameService;
import service.UserService;

public class Server {

    private final Javalin javalin;
    private final UserService userService;
//    private final GameService gameService;
    private final ClearService clearService;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        var authDao = new MemoryAuthDao();
        var userDao = new MemoryUserDao();
        var gameDao = new MemoryGameDao();

        this.userService = new UserService(authDao, userDao);
//        this.gameService = new GameService(gameDao, authDao);
        this.clearService = new ClearService(authDao, userDao, gameDao);

        ClearApplicationHandler clearHandler = new ClearApplicationHandler(clearService);
        RegisterHandler registerHandler = new RegisterHandler(userService);
        LogoutHandler logoutHandler = new LogoutHandler(userService);
        LoginHandler loginHandler = new LoginHandler(userService);
        // Register your endpoints and exception handlers here.

        javalin.delete("/db", clearHandler::handle);
        javalin.post("/user", registerHandler::handle);
        javalin.delete("/session", logoutHandler::handle);
        javalin.post("/session", loginHandler::handle);
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
