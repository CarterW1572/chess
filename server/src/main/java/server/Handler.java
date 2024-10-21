package server;

import com.google.gson.Gson;
import dataaccess.*;
import model.*;
import service.*;
import spark.*;

import javax.xml.crypto.Data;

public class Handler {
    private final DataService dataService;
    private final UserService userService;
    private final GameService gameService;
    private final Gson serializer = new Gson();

    public Handler(DataService dataService, UserService userService, GameService gameService) {
        this.dataService = dataService;
        this.userService = userService;
        this.gameService = gameService;
    }

    public Object clear() throws DataAccessException {
        dataService.clear();
        return "{}";
    }

    public Object register(Request req) throws DataAccessException {
        var userData = new Gson().fromJson(req.body(), UserData.class);
        AuthData authData;
        try {
            authData = userService.register(userData);
        }
        catch (DataAccessException e) {
            return new Gson().toJson(e);
        }
        return new Gson().toJson(authData);
    }

    public Object login(Request req, Response res) throws DataAccessException {
        return """
                {"username": "", "authToken": ""}
                """;
    }

    public Object logout(Request req, Response res) throws DataAccessException {
        return "{}";
    }

    public Object listGames(Request req, Response res) throws DataAccessException {
        return """
                { "games": [{"gameID": 1234, "whiteUsername":"", "blackUsername":"", "gameName:""} ]}
                """;
    }

    public Object createGame(Request req, Response res) throws DataAccessException {
        return """
                { "gameID": 1234 }
                """;
    }

    public Object joinGame(Request req, Response res) throws DataAccessException {
        return "{}";
    }
}