package client;
import com.google.gson.Gson;
import server.Server;
import server.request.CreateGameRequest;
import server.request.JoinGameRequest;
import server.request.LoginRequest;
import server.request.RegisterRequest;
import server.result.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;

    public ServerFacade(String url){
        serverUrl = url;
    }



    public RegisterResult register(RegisterRequest request) {
        var request = buildRequest(request);
        var response = sendRequest(request);
        return handleResponse(response);
    }

    public LoginResult login(LoginRequest request) {
        var request = buildRequest(request);
        var response = sendRequest(request);
        return handleResponse(response);
    }

    public JoinGameResult join(JoinGameRequest request) {
        var request = buildRequest(request);
        var response = sendRequest(request);
        return handleResponse(response);
    }

    public CreateGameResult createGame(CreateGameRequest request) {
        var request = buildRequest(request);
        var response = sendRequest(request);
        return handleResponse(response);
    }

    public LogoutResult logout(){

    }

    public CreateGameResult createGame(CreateGameRequest){

    }

    public ListGamesResult listGames(String authToken) throws exception.ResponseException {
        var request = buildRequest("GET", "/game", null, authToken);
        var response = sendRequest(request);
        return handleResponse(response, ListGamesResult.class);
    }

    private HttpRequest buildRequest(String method, String path, Object body, String authToken){
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.setHeader("Content-Type", "application/json");
        }
        if (authToken != null){
            request.header("Authorization", authToken);
        }
        return request.build();
    }

    private HttpRequest.BodyPublisher makeRequestBody(Object request){
        if (request != null) {
            return HttpRequest.BodyPublishers.ofString(new Gson().toJson(request));
        } else {
            return HttpRequest.BodyPublishers.noBody();
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws exception.ResponseException {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new exception.ResponseException(exception.ResponseException.Code.ServerError, ex.getMessage());
        }
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws exception.ResponseException {
        var status = response.statusCode();
        if (!isSuccessful(status)) {
            var body = response.body();
            if (body != null) {
                throw exception.ResponseException.fromJson(body);
            }

            throw new exception.ResponseException(exception.ResponseException.fromHttpStatusCode(status), "other failure: " + status);
        }

        if (responseClass != null) {
            return new Gson().fromJson(response.body(), responseClass);
        }

        return null;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }


}
