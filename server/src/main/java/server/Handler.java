package server;

import com.google.gson.Gson;
import dataaccess.*;
import model.*;
import requests.*;
import results.*;
import service.*;
import spark.*;

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

    public Object register(Request req) throws BadRequestException, DataAccessException {
        var userData = serializer.fromJson(req.body(), UserData.class);
        AuthData authData = userService.register(userData);
        return serializer.toJson(authData);
    }

    public Object login(Request req) throws UnauthorizedException, DataAccessException {
        var loginReq = serializer.fromJson(req.body(), LoginRequest.class);
        AuthData authData = userService.login(loginReq);
        return serializer.toJson(authData);
    }

    public Object logout(Request req) throws UnauthorizedException, DataAccessException {
        var authToken = req.headers("authorization"); /*serializer.fromJson(req.headers("authorization"), String.class);*/
        userService.logout(authToken);
        return "{}";
    }

    public Object listGames(Request req) throws UnauthorizedException {
        var authToken = req.headers("authorization"); /*serializer.fromJson(req.headers("authorization"), String.class);*/
        ListGamesResult gameList = new ListGamesResult(gameService.listGames(authToken));
        return serializer.toJson(gameList);
    }

    public Object createGame(Request req) throws BadRequestException, UnauthorizedException {
        var authToken = req.headers("authorization"); /*serializer.fromJson(req.headers("authorization"), String.class);*/
        var createGameRequest = serializer.fromJson(req.body(), CreateGameRequest.class);
        CreateGameResult createGameResult = gameService.createGame(authToken, createGameRequest);
        return serializer.toJson(createGameResult);
    }

    public Object joinGame(Request req) throws UnauthorizedException, DataAccessException, BadRequestException {
        var authToken = req.headers("authorization");  /*serializer.fromJson(req.headers("authorization"), String.class);*/
        var joinGameRequest = serializer.fromJson(req.body(), JoinGameRequest.class);
        gameService.joinGame(authToken, joinGameRequest);
        return "{}";
    }
}
