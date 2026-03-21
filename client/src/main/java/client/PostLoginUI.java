package client;

public class PostLoginUI {
    private final ServerFacade server;
    private final ChessClient client;
    public PostLoginUI(ServerFacade server, ChessClient client) {
        this.server = server;
        this.client = client;
    }
    public String eval(String cmd, String[] params) {
        return switch (cmd) {
            case "logout" -> logout(params);
            case "create game" -> createGame(params);
            case "list games" -> listGames(params);
            case "play game" -> playGame(params);
            case "observe game" -> observeGame(params);
            default ->  "Help";
        };
    }


    public void logout() {

    }
    public void createGame() {

    }
    public void listGames() {

    }
    public void playGame() {

    }
    public void observeGame() {

    }
    public void help() {

    }
}
