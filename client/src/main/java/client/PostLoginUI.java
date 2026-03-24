package client;

import model.GameData;
import model.result.CreateGameResult;
import model.result.ListGamesResult;
import model.result.LogoutResult;
import model.result.request.CreateGameRequest;
import model.result.request.JoinGameRequest;

import java.util.ArrayList;
import java.util.List;
import static client.EscapeSequences.*;

public class PostLoginUI {
    private final ServerFacade server;
    private final ChessClient client;
    private List<GameData> gameList;
    public PostLoginUI(ServerFacade server, ChessClient client) {
        this.server = server;
        this.client = client;
    }
    public String eval(String cmd, String[] params) {
        return switch (cmd) {
            case "logout" -> logout();
            case "create" -> createGame(params);
            case "list" -> listGames();
            case "observe" -> observeGame(params);
            case "join" -> joinGame(params);
            case "quit" -> "quit";
            default ->  help();
        };
    }


    public String logout() {
        try {
            String token = client.getAuthToken();
            LogoutResult result = server.logout(token);
            client.setState(State.SIGNEDOUT);
            return BLUE + "Successfully logged out. Thanks for playing.";
        }
        catch (Exception e){
            return RED + "Error: " + e.getMessage();
        }

    }
    public String createGame(String[] params) {
        if (params.length != 1){
            return RED + "Please give just a game name to create!";
        }
        try {
            String token = client.getAuthToken();
            String gameName = params[0].toUpperCase();
            CreateGameResult result = server.createGame(new CreateGameRequest(gameName), token);
           return GREEN + "Game " + gameName + " has been created! Type 'list' to see its id number!";
        }
        catch (Exception e){
            return RED + "Error: " + e.getMessage();
        }
    }

    public String joinGame(String[] params) {
        if (params.length != 2){
            return RED + "Please give just a gameID and color to join!";
        }
        try {
            int listIndex = Integer.parseInt(params[0]) - 1;
            if (gameList == null || listIndex < 0 || listIndex >= gameList.size()) {
                return RED + "Invalid game number. Please run 'list' to see available games";
            }
            int realGameId = gameList.get(listIndex).gameID();
            server.join(new JoinGameRequest(params[1].toUpperCase(), realGameId), client.getAuthToken());
            client.setState(State.INGAME);
            return GREEN + "Joined " + gameList.get(listIndex).gameName() + " as " + params[1].toUpperCase();
        }
        catch (NumberFormatException e) {
            return RED + "ID must be a number to join.";
        }
        catch (Exception e){
            return RED + "Error: " + e.getMessage();
        }

    }


    public String listGames() {
        try {
            String token = client.getAuthToken();
            ListGamesResult result = server.listGames(token);
            this.gameList = new ArrayList<>(result.games());
            if (gameList.isEmpty()) {
                return BLUE + "No games found. Use 'create <NAME>' to start one!";
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < gameList.size(); i++){
                sb.append(String.format("%d: %s\n", i + 1, gameList.get(i).gameName()));
            }
            return sb.toString();
        }
        catch (Exception e){
            return RED + "Error: " + e.getMessage();
        }
    }

    public String observeGame(String[] params) {
        if (params.length != 1){
            return RED + "Please give just a game name to create!";
        }
        try {
            int listIndex = Integer.parseInt(params[0]) - 1;
            if (gameList == null || listIndex < 0 || listIndex >= gameList.size()) {
                return RED + "Invalid game number. Please run 'list' to see available games";
            }
            int realGameId = gameList.get(listIndex).gameID();
            server.join(new JoinGameRequest(null, realGameId), client.getAuthToken());
            client.setState(State.INGAME);
            return GREEN + "Observing!";
        }
        catch (NumberFormatException e) {
            return RED + "ID must be a number to Observe.";
        }
        catch (Exception e){
            return RED + "Error: " + e.getMessage();
        }

    }
    public String help() {
        return BLUE + """
             create <NAME>             - create a new ga,e
             list                      - list all active games
             join <ID> [WHITE|BLACK]   - join a game as a player
             observe <ID>              - watch a game in progress
             logout                    - logout of the server
             quit                      - exit the application
             help                      - help with possible commands
             """;

    }
}
