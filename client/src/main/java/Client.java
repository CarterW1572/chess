import chess.ChessGame;
import com.google.gson.Gson;
import model.*;

import results.ListGameResult;
import server.ResponseException;
import server.ServerFacade;
import ui.DisplayBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Client {
    private Repl notificationHandler;
    private String serverUrl;
    private ServerFacade server;
    private State state;
    private final Gson serializer;
    private HashMap<Integer, Integer> currentGameNumbers;

    public Client(String serverUrl, Repl notificationHandler) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.notificationHandler = notificationHandler;
        serializer = new Gson();
        currentGameNumbers = new HashMap<>();
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
                case "create" -> createGame(params);
                case "list" -> listGames();
                case "join" -> joinGame(params);
                case "observe" -> observeGame(params);
                case "logout" -> logout();
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

    public String listGames() throws ResponseException {
        var list = server.listGames();
        StringBuilder gameList = new StringBuilder();
        ArrayList<ListGameResult> games = list.games();
        currentGameNumbers.clear();
        for (int i = 0; i < games.size(); i++) {
            String name = games.get(i).gameName();
            String white = games.get(i).whiteUsername();
            String black = games.get(i).blackUsername();
            int gameID = games.get(i).gameID();
            if (games.get(i).whiteUsername() == null) {
                white = "N/A";
            }
            if (games.get(i).blackUsername() == null) {
                black = "N/A";
            }
            gameList.append((i+1) + " - " + name + ": [white : " + white + "], [black : " + black + "]\n");
            currentGameNumbers.put(i+1, gameID);
        }
        if (gameList.charAt(gameList.length() - 1) == '\n') {
            gameList.deleteCharAt(gameList.length() - 1);
        }
        return gameList.toString();
    }

    public String joinGame(String... params) throws ResponseException {
        if (params.length < 2) {
            throw new ResponseException(400, "Expected: <ID> [WHITE | BLACK]");
        }
        int gameNum;
        ChessGame.TeamColor color;
        try {
            gameNum = Integer.valueOf(params[0]);
        }
        catch (NumberFormatException e) {
            return "Not a valid game ID";
        }
        if (params[1].equalsIgnoreCase("white")) {
            color = ChessGame.TeamColor.WHITE;
        }
        else if (params[1].equalsIgnoreCase("black")) {
            color = ChessGame.TeamColor.BLACK;
        }
        else {
            return "Invalid team color";
        }
        int gameID = currentGameNumbers.get(gameNum);
        server.joinGame(gameID, color);
        DisplayBoard.display();
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("You left the game") || !result.equals("You have resigned")) {
            String line = scanner.nextLine();

            try {
                result = playEval(line);
                return result;
            } catch (ResponseException e) {
                System.out.println(e.getMessage());
            }
        }
        return "Successfully joined game as " + color;
    }

    public String observeGame(String... params) throws ResponseException {
        if (params.length < 1) {
            throw new ResponseException(400, "Expected: <ID>");
        }
        int gameNum;
        try {
            gameNum = Integer.valueOf(params[0]);
        }
        catch (NumberFormatException e) {
            return "Not a valid game ID";
        }
        DisplayBoard.display();
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("You left the game")) {
            String line = scanner.nextLine();

            try {
                result = observeEval(line);
                return result;
            } catch (ResponseException e) {
                System.out.println(e.getMessage());
            }
        }
        return "You are observing game " + gameNum;
    }

    public String logout() throws ResponseException {
        server.logout();
        state = State.LOGGEDOUT;
        return "You are logged out";
    }

    public String createGame(String... params) throws ResponseException {
        if (params.length < 1) {
            throw new ResponseException(400, "Expected: <name>");
        }
        StringBuilder name = new StringBuilder();
        name.append(params[0]);
        for (int i = 1; i < params.length; i++) {
            name.append(" " + params[i]);
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

    private String playEval(String input) {
        if (input.equalsIgnoreCase("leave")) {
            return "You left the game";
        }
        else if (input.equalsIgnoreCase("resign")) {
            return "You have resigned";
        }
        else {
            throw new ResponseException(400, "Invalid input");
        }
    }

    private String observeEval(String input) {
        if (input.equalsIgnoreCase("leave")) {
            return "You left the game";
        }
        else {
            throw new ResponseException(400, "Invalid input");
        }
    }
}
