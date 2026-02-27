package server;

import dataaccess.MemoryAuthDao;
import dataaccess.MemoryGameDao;
import dataaccess.MemoryUserDao;
import io.javalin.*;
import service.ClearService;
import service.GameService;
import service.UserService;

public class Server {

    private final Javalin javalin;
    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        var authDao = new MemoryAuthDao();
        var userDao = new MemoryUserDao();
        var gameDao = new MemoryGameDao();

        this.userService = new UserService(userDao, authDao);
        this.gameService = new GameService(gameDao, authDao);
        this.clearService = new ClearService(authDao, userDao, gameDao);


        // Register your endpoints and exception handlers here.

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
