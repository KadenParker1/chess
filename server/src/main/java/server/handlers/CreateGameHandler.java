package server.handlers;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import com.google.gson.Gson;
import server.handlers.exceptions.BadRequestException;
import server.handlers.exceptions.UnAuthorizedException;
import server.request.CreateGameRequest;
import server.request.RegisterRequest;
import server.result.ErrorMessage;
import service.GameService;

public class CreateGameHandler {
    private final GameService service;
    private final Gson gson = new Gson();
    public CreateGameHandler(GameService service){
        this.service = service;
    }

    public void handle(Context ctx){
        try {
            String auth = ctx.header("authorization");
            String jsonBody = ctx.body();
            CreateGameRequest req = new Gson().fromJson(jsonBody, CreateGameRequest.class);
            var result = service.createGame(auth, req);
            ctx.status(200);
            ctx.result(gson.toJson(result));
        }
        catch (BadRequestException e) {
            ctx.status(400);
            ctx.result(gson.toJson(new ErrorMessage("Error: " + e.getMessage())));
        }
        catch (UnAuthorizedException e) {
            ctx.status(401);
            ctx.result(gson.toJson(new ErrorMessage("Error: " + e.getMessage())));


        }
        catch (DataAccessException e) {
            ctx.status(500);
            ctx.result(gson.toJson(new ErrorMessage("Error: " + e.getMessage())));

        }
    }




}
