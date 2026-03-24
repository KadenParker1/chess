package client;

public class GameUI {
    private final ServerFacade server;
    private final ChessClient client;

    public GameUI(ServerFacade server, ChessClient client){
        this.server = server;
        this.client = client;
    }

    public String eval(String cmd, String[] params) {
        return switch (cmd) {
            case "quit" -> "quit";
            default ->  help();
        };
        }

        public String help() {
        return """
                quit                                     -   leave the application
                help                                     -   help with possible commands
                """;
        }
}
