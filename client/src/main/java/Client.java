import model.*;

import server.ResponseException;
import server.ServerFacade;

import java.util.Arrays;

public class Client {
    private Repl notificationHandler;
    private String serverUrl;
    private ServerFacade server;
    private State state;

    public Client(String serverUrl, Repl notificationHandler) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.notificationHandler = notificationHandler;
        state = State.LOGGEDOUT;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> login(params);
//                case "register" -> register(params);
//                case "list" -> listGames();
//                case "logout" -> logout();
//                case "create" -> createGame(params);
//                case "observe" -> observeGame();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String login(String... params) throws ResponseException {
        if (params.length >= 2) {
            state = State.LOGGEDIN;
            AuthData res = server.login(params[0], params[1]);
            return "You are logged in as " + res.username();
        }
        throw new ResponseException(400, "Expected: <username> <password>");
    }

    public String help() {
        if (state == State.LOGGEDOUT) {
            return """
                    - login <username> <password>
                    - register <username> <password> <email>
                    - quit
                    - help
                    """;
        }
        return """
                - list
                - create <name>
                - join <ID> [WHITE | BLACK]
                - observe <ID>
                - logout
                - quit
                - help
                """;
    }
}
