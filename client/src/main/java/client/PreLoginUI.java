package client;

import model.result.LoginResult;
import model.result.RegisterResult;
import model.result.request.LoginRequest;
import model.result.request.RegisterRequest;

import static client.EscapeSequences.GREEN;
import static client.EscapeSequences.RED;

public class PreLoginUI {
    private final ServerFacade server;
    private final ChessClient client;

    public PreLoginUI(ServerFacade server, ChessClient client) {
        this.server = server;
        this.client = client;
    }

    public String eval(String cmd, String[] params) {
        return switch (cmd) {
            case "login" -> login(params);
            case "register" -> register(params);
            case "quit" -> "quit";
            default -> help();
        };
    }

    public String login(String [] params) {
        if (params.length != 2){
            return RED + "PLease give both a username and password to login. Try again!";
        }
        try {
            String username = params[0];
            String password = params[1];
            LoginRequest request = new LoginRequest(username, password);
            LoginResult result = server.login(request);
            client.setAuthToken(result.authToken());
            client.setState(State.SIGNEDIN);
            return GREEN + "You are logged in! Type help to see more command options.";
        }

        catch (Exception e){
            return RED + "Error: " + e.getMessage();
        }

    }
    public String register(String [] params) {
        if (params.length != 3){
            return RED + "Please give both a username, password, and email to register. Try again!";
        }
        try {
            String username = params[0];
            String password = params[1];
            String email = params[2];
            RegisterRequest request = new RegisterRequest(username, password, email);
            server.register(request);
            String[] loginparams = {username, password};
            login(loginparams);
            return GREEN + "You have successfully registered!";
        }
        catch (Exception e){
            return RED + "Error: " + e.getMessage();
        }
    }

    public String help() {
        return """
                register <USERNAME> <PASSWORD> <EMAIL>   -   register an account
                login <USERNAME> <PASSWORD>              -   login to an account to play
                quit                                     -   leave the application
                help                                     -   help with possible commands
                """;

    }
}