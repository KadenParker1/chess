package server.handlers;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import server.handlers.exceptions.UnAuthorizedException;
import server.result.ErrorMessage;
import server.result.ListGamesResult;
import service.GameService;


public class ListGamesHandler {
    private final GameService service;
    private final Gson gson = new Gson();

    public ListGamesHandler(GameService service){
        this.service = service;
    }

    public void handle(Context ctx) {
        try {
            var result = service.listGames(ctx.header("authorization"));
            ctx.status(200);
            ctx.result(gson.toJson(result));

        }
        catch (UnAuthorizedException e){
            ctx.status(401);
            ctx.result(gson.toJson(new ErrorMessage(e.getMessage())));
        }
        catch (DataAccessException e) {
            ctx.status(500);
            ctx.result(gson.toJson(new ErrorMessage("Error: " + e.getMessage())));

        }
    }

}
