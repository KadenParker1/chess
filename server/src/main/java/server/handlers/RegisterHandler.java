package server.handlers;

import com.google.gson.Gson;
import io.javalin.http.BadRequestResponse;
import server.handlers.exceptions.AlreadyTakenException;
import server.handlers.exceptions.BadRequestException;
import server.request.RegisterRequest;
import server.result.ErrorMessage;
import server.result.RegisterResult;
import service.UserService;
import io.javalin.http.Context;

public class RegisterHandler {
    private final UserService service;
    private final Gson gson = new Gson();

    public RegisterHandler(UserService service){
        this.service = service;
    }

    public void handle(Context ctx) {
        try {
            String jsonBody = ctx.body();
            RegisterRequest req = new Gson().fromJson(jsonBody, RegisterRequest.class);
            var result = service.register(req);
            ctx.status(200);
            ctx.json(result);
        }

        catch (BadRequestException e) {
            ctx.status(400);
            ctx.json(new ErrorMessage(e.getMessage()));
        }
        catch (AlreadyTakenException e) {
            ctx.status(403);
            ctx.json(new ErrorMessage(e.getMessage()));
        }
        catch (Exception e) {
            ctx.status(500);
            ctx.json(new ErrorMessage(e.getMessage()));
        }
    }
}
