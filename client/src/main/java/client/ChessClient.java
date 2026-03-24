package client;
import java.util.Arrays;
import java.util.Scanner;
import static client.EscapeSequences.*;

public class ChessClient {
    private final ServerFacade server;
    private final String serverUrl;
    private State state = State.SIGNEDOUT;
    private String authToken = null;
    private final PostLoginUI postLoginUI;
    private final PreLoginUI preLoginUI;
    private final GameUI gameUI;


    public void setAuthToken(String token){
        this.authToken = token;
    }
    public String getAuthToken() {
       return authToken;
    }
    public void setState(State state){
        this.state = state;
    }

    public ChessClient(String serverUrl) throws exception.ResponseException {
        this.serverUrl = serverUrl;
        server = new ServerFacade(serverUrl);
        this.postLoginUI = new PostLoginUI(server, this);
        this.preLoginUI = new PreLoginUI(server, this);
        this.gameUI = new GameUI(server, this);
    }

    public void run() {
        System.out.println("Welcome to Kaden's 240 chess. Type Help to get started.");
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")){
            printPrompt();
            String line = scanner.nextLine();
            try {
                result = eval(line);
                System.out.print(BLUE + result);

            }
            catch (Throwable e) {
                System.out.print(RED + "An unexpected error has occurred. Please try again.");
            }
        }

    }

    public String eval(String input){
        if (input == null || input.trim().isEmpty()) {
            return "";
        }
        var tokens = input.trim().split("\\s+");
        var cmd  = tokens[0].toLowerCase();
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (state) {
            case SIGNEDOUT -> preLoginUI.eval(cmd, params);
            case SIGNEDIN -> postLoginUI.eval(cmd, params);
            case INGAME -> gameUI.eval(cmd, params);
        };
    }
    private void printPrompt() {
        System.out.print("\n" + RESET + "[" + state + "] >>> " + GREEN);
    }

}
