package client;

import model.GameData;
import server.result.CreateGameResult;
import server.result.ListGamesResult;

import java.util.ArrayList;
import java.util.Collection;
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
            case "logout" -> logout(params);
            case "create game" -> createGame(params);
            case "list games" -> listGames();
            case "play game" -> playGame(params);
            case "observe game" -> observeGame();
            default ->  "Help";
        };
    }


    public void logout() {

    }
    public void createGame() {
        String token = client.getAuthToken();
        CreateGameResult result = server.createGame(token);


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

    public void observeGame() {

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
