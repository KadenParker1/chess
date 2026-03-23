package server.handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import server.handlers.exceptions.BadRequestException;
import server.handlers.exceptions.UnAuthorizedException;
import model.result.request.LoginRequest;
import model.result.ErrorMessage;
import service.UserService;
import io.javalin.http.Context;

public class LoginHandler {
    private final UserService service;
    private final Gson gson = new Gson();

    public LoginHandler(UserService service){
        this.service = service;
    }

    public void handle(Context ctx) {
        try {
            String jsonBody = ctx.body();
            LoginRequest req = new Gson().fromJson(jsonBody, LoginRequest.class);
            var result = service.login(req);
            ctx.status(200);
            ctx.result(gson.toJson(result));

        }
        catch (BadRequestException e) {
            ctx.status(400);
            ctx.result(gson.toJson(new ErrorMessage("Error: " + e.getMessage())));

        }
        catch (UnAuthorizedException e){
            ctx.status(401);
            ctx.result(gson.toJson(new ErrorMessage("Error: " + e.getMessage())));
        }
        catch (DataAccessException e) {
            ctx.status(500);
            ctx.result(gson.toJson(new ErrorMessage("Error: " + e.getMessage())));

        }
    }
}
