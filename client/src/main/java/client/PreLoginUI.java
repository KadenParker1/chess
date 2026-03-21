package client;

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

    public void login() {

    }
    public void register() {

    }
    public void help() {

    }
}