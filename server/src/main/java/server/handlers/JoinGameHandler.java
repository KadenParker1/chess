package server.handlers;
import io.javalin.http.Context;
import com.google.gson.Gson;
import server.handlers.exceptions.AlreadyTakenException;
import server.handlers.exceptions.BadRequestException;
import server.handlers.exceptions.UnAuthorizedException;
import model.result.request.JoinGameRequest;
import model.result.ErrorMessage;
import model.result.JoinGameResult;
import service.GameService;

public class JoinGameHandler {
    private final GameService service;
    private final Gson gson = new Gson();

    public JoinGameHandler(GameService service){
        this.service = service;
    }

    public void handle(Context ctx){
        try {
            String auth = ctx.header("authorization");
            String jsonBody = ctx.body();
            JoinGameRequest req = new Gson().fromJson(jsonBody, JoinGameRequest.class);
            service.joinGame(auth, req);
            ctx.status(200);
            JoinGameResult result = new JoinGameResult();
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
        catch (AlreadyTakenException e) {
            ctx.status(403);
            ctx.result(gson.toJson(new ErrorMessage("Error: " + e.getMessage())));
        }
        catch (Exception e) {
            ctx.status(500);
            ctx.result(gson.toJson(new ErrorMessage("Error: " + e.getMessage())));
        }
    }

}
