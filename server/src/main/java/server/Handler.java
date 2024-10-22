package server;

import com.google.gson.Gson;
import dataaccess.*;
import model.*;
import requestObjects.*;
import resultObjects.*;
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
        var userData = serializer.fromJson(req.body(), UserData.class);
        AuthData authData = userService.register(userData);
        return serializer.toJson(authData);
    }

    public Object login(Request req) throws DataAccessException {
        var loginReq = serializer.fromJson(req.body(), LoginRequest.class);
        AuthData authData = userService.login(loginReq);
        return serializer.toJson(authData);
    }

    public Object logout(Request req) throws DataAccessException {
        var authToken = serializer.fromJson(req.headers("authorization"), String.class);
        userService.logout(authToken);
        return "{}";
    }

    public Object listGames(Request req) throws DataAccessException {

        return """
                { "games": [{"gameID": 1234, "whiteUsername":"", "blackUsername":"", "gameName:""} ]}
                """;
    }

    public Object createGame(Request req) throws DataAccessException {
        var authToken = serializer.fromJson(req.headers("authorization"), String.class);
        var createGameRequest = serializer.fromJson(req.body(), CreateGameRequest.class);
        CreateGameResult createGameResult = gameService.createGame(authToken, createGameRequest);
        return serializer.toJson(createGameResult);
    }

    public Object joinGame(Request req) throws DataAccessException {

        return "{}";
    }
}
