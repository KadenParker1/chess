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
            default ->  "Help";
        };
        }

        public String help() {
        return """
                - quit
                """;
        }
}
