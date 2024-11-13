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
                case "register" -> register(params);
//                case "list" -> listGames();
                case "logout" -> logout();
                case "create" -> createGame(params);
//                case "observe" -> observeGame();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String register(String... params) throws ResponseException {
        if (params.length >= 3) {
            AuthData res = server.register(params[0], params[1], params[2]);
            state = State.LOGGEDIN;
            return "You are registered and logged in as " + res.username();
        }
        throw new ResponseException(400, "Expected: <username> <password> <email>");
    }

    public String login(String... params) throws ResponseException {
        if (params.length >= 2) {
            AuthData res = server.login(params[0], params[1]);
            state = State.LOGGEDIN;
            return "You are logged in as " + res.username();
        }
        throw new ResponseException(400, "Expected: <username> <password>");
    }

    public String logout() throws ResponseException {
        server.logout();
        state = State.LOGGEDOUT;
        return "You are logged out";
    }

    public String createGame(String... params) throws ResponseException {
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            name.append(params[i]);
        }
        server.createGame(name.toString());
        return "Game created";
    }

    public String help() {
        if (state == State.LOGGEDOUT) {
            return """
                    - register <username> <password> <email>
                    - login <username> <password>
                    - quit
                    - help""";
        }
        return """
                - create <name>
                - list
                - join <ID> [WHITE | BLACK]
                - observe <ID>
                - logout
                - quit
                - help""";
    }
}
