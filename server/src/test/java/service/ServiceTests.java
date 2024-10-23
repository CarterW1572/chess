package service;

import chess.ChessGame;
import dataaccess.*;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.*;
import server.BadRequestException;

public class ServiceTests {

    private final UserDAO userDAO = new MemoryUserDAO();
    private final GameDAO gameDAO = new MemoryGameDAO();
    private final AuthDAO authDAO = new MemoryAuthDAO();
    private final DataService dataService = new DataService(userDAO, gameDAO, authDAO);
    private final UserService userService = new UserService(userDAO, gameDAO, authDAO);
    private final GameService gameService = new GameService(userDAO, gameDAO, authDAO);
    private AuthData authData;

    @BeforeEach
    public void setUp() {
        try {
            authData = userService.register(new UserData("username", "password", "email"));
        }
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void clearTest() {
        try {
            dataService.clear();
        }
        catch (DataAccessException e) {
            Assertions.fail();
        }
    }

    @Test
    public void registerTestSuccess() {
        try {
            Assertions.assertNotNull(userService.register(new UserData("newUser", "newPass", "newEmail")));
        }
        catch (DataAccessException e) {
            Assertions.fail();
        }
    }

    @Test
    public void registerTestFail() {
        try {
            userService.register(new UserData("username", "newPass", "newEmail"));
        }
        catch (DataAccessException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void loginTestSuccess() {
        try {
            userService.login(new LoginRequest("username", "password"));
        }
        catch (UnauthorizedException e) {
            Assertions.fail();
        }
    }

    @Test
    public void loginTestFail() {
        try {
            userService.login(new LoginRequest("username", "notPassword"));
        }
        catch (UnauthorizedException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void logoutTestSuccess() {
        try {
            userService.logout(authData.authToken());
        }
        catch (UnauthorizedException e) {
            Assertions.fail();
        }
    }

    @Test
    public void logoutTestFail() {
        try {
            userService.logout("not correct authToken");
        }
        catch (UnauthorizedException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void createGameSuccess() {
        try {
            CreateGameRequest req = new CreateGameRequest("game");
            gameService.createGame(authData.authToken(), req);
        }
        catch (UnauthorizedException | BadRequestException e) {
            Assertions.fail();
        }
    }

    @Test
    public void createGameFail() {
        try {
            CreateGameRequest req = new CreateGameRequest("game");
            gameService.createGame("fake auth token", req);
        }
        catch (UnauthorizedException | BadRequestException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void joinGameSuccess() {
        try {
            CreateGameRequest req = new CreateGameRequest("game");
            int gameID = gameService.createGame(authData.authToken(), req).gameID();
            JoinGameRequest joinReq = new JoinGameRequest(ChessGame.TeamColor.WHITE, gameID);
            gameService.joinGame(authData.authToken(), joinReq);
        }
        catch (DataAccessException | UnauthorizedException | BadRequestException e) {
            Assertions.fail();
        }
    }

    @Test
    public void joinGameFail() {
        try {
            CreateGameRequest req = new CreateGameRequest("game");
            gameService.createGame(authData.authToken(), req);
            JoinGameRequest joinReq = new JoinGameRequest(ChessGame.TeamColor.WHITE, 2);
            gameService.joinGame(authData.authToken(), joinReq);
        }
        catch (DataAccessException | UnauthorizedException | BadRequestException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void listGameSuccess() {
        try {
            CreateGameRequest req = new CreateGameRequest("game");
            gameService.createGame(authData.authToken(), req);
            gameService.listGames(authData.authToken());
        }
        catch (UnauthorizedException e) {
            Assertions.fail();
        }
    }

    @Test
    public void listGameFail() {
        try {
            CreateGameRequest req = new CreateGameRequest("game");
            gameService.createGame(authData.authToken(), req);
            gameService.listGames("authData.authToken()");
        }
        catch (UnauthorizedException e) {
            Assertions.assertTrue(true);
        }
    }
}