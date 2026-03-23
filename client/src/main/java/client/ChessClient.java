package client;
import java.util.Arrays;
import java.util.Scanner;
import static client.EscapeSequences.*;

public class ChessClient {
    private final ServerFacade server;
    private final String serverUrl;
    private State state = State.SIGNEDOUT;
    private String authToken = null;


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
        var tokens = input.toLowerCase().split(" ");
        var cmd  = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (state) {
            case SIGNEDOUT -> new PreLoginUI(server, this).eval(cmd, params);
            case SIGNEDIN -> new PostLoginUI(server, this).eval(cmd, params);
            case INGAME -> new GameUI(server, this).eval(cmd, params);
        };
    }
    private void printPrompt() {
        System.out.print("\n" + RESET + "[" + state + "] >>> " + GREEN);
    }



}
