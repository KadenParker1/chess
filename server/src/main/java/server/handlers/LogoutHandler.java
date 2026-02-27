package server.handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import org.eclipse.jetty.util.log.Log;
import server.handlers.exceptions.UnAuthorizedException;
import server.result.ErrorMessage;
import server.result.LogoutResult;
import service.UserService;
import io.javalin.http.Context;

public class LogoutHandler {private final UserService service;
    private final Gson gson = new Gson();

    public LogoutHandler(UserService service){
        this.service = service;
    }

    public void handle(Context ctx) {
        try {
            service.logout(ctx.header("authorization"));
            ctx.status(200);
            LogoutResult result = new LogoutResult();
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
