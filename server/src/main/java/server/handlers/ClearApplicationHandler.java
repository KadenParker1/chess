package server.handlers;


import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.result.ClearResult;
import model.result.ErrorMessage;
import service.ClearService;
import io.javalin.http.Context;

public class ClearApplicationHandler {
    private final ClearService service;
    private final Gson gson = new Gson();

    public ClearApplicationHandler(ClearService service){
        this.service = service;
    }


    public void handle(Context ctx) {
    try {
        service.clearApplication();
        ctx.status(200);
        ClearResult result = new ClearResult();
        ctx.result(gson.toJson(result));

    }
    catch (DataAccessException e) {
        ctx.status(500);
        ctx.result(gson.toJson(new ErrorMessage("Error: " + e.getMessage())));

    }

    }




}
