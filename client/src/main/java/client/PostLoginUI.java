package client;

import model.GameData;
import model.result.CreateGameResult;
import model.result.ListGamesResult;
import model.result.LogoutResult;

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
            case "create game" -> createGame(params);
            case "list games" -> listGames();
            case "observe game" -> observeGame(params);
            case "join game" -> joinGame(params);
            default ->  "Help";
        };
    }


    public String logout() {
        try {
            String token = client.getAuthToken();
            LogoutResult result = server.logout(token);
            return BLUE + "Successfully logged out. Thanks for playing.";
        }
        catch (Exception e){
            return RED + "An unexpected error has occurred. Please try again.";
        }

    }
    public String createGame(String[] params) {
        try {
            String token = client.getAuthToken();
            CreateGameResult result = server.createGame(token);
            // do game result logic here
        }
        catch (Exception e){
            return RED + "An unexpected error has occurred. Please try again.";
        }
    }

    public String joinGame(String[] params) {
        try {
            int listIndex = Integer.parseInt(params[0]) - 1;

        }

        catch (Exception e){
            return RED + "An unexpected error has occurred. Please try again.";
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
            return RED + "An unexpected error has occurred. Please try again.";
        }
    }

    public String observeGame(String[] params) {

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
