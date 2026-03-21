package client;
import com.google.gson.Gson;
import server.Server;
import server.request.CreateGameRequest;
import server.request.JoinGameRequest;
import server.request.LoginRequest;
import server.request.RegisterRequest;
import server.result.CreateGameResult;
import server.result.JoinGameResult;
import server.result.LoginResult;
import server.result.RegisterResult;

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
        var request = buildRequest();
        var response = sendRequest(request);
        return handleResponse();
    }

    public LoginResult login(LoginRequest request) {

    }

    public JoinGameResult join(JoinGameRequest request) {

    }

    public CreateGameResult createGame(CreateGameRequest request) {

    }
    private HttpRequest buildRequest(String method, String path, Object body){
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.setHeader("Content-Type", "application/json");
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

    private HttpResponse<String> sendRequest(HttpRequest request){
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new exception.ResponseException(exception.ResponseException.Code.ServerError, ex.getMessage());
        }
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass){

    }



}
