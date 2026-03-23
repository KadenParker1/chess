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
            default ->  "Help";
        };
    }

    public String login(String [] params) {
        if (params.length != 2){
            return RED + "PLease give both a username and password to login. Try again!";
        }
        try {
            String username = params[0].toUpperCase();
            String password = params[1].toUpperCase();
            LoginRequest request = new LoginRequest(username, password);
            LoginResult result = server.login(request);
            client.setAuthToken(result.authToken());
            client.setState(State.SIGNEDIN);
            return GREEN + "You are logged in! Type help to see more command options.";
        }

        catch (Exception e){
            return RED + "An unexpected error has occurred. Please try again!";
        }

    }
    public String register(String [] params) {
        if (params.length != 3){
            return RED + "PLease give both a username and password to login. Try again!";
        }
        try {
            String username = params[0].toUpperCase();
            String password = params[1].toUpperCase();
            String email = params[2].toUpperCase();
            RegisterRequest request = new RegisterRequest(username, password, email);
            server.register(request);
            return GREEN + "You have succesfully registered! Please login to continue";
        }
        catch (Exception e){
            return RED + "An unexpected error has occurred. Please try again!";
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