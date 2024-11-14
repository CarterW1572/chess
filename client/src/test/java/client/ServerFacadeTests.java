package client;

import chess.ChessGame;
import org.junit.jupiter.api.*;
import results.ListGameResult;
import results.ListGamesResult;
import server.ResponseException;
import server.Server;
import server.ServerFacade;

import java.util.ArrayList;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;
    private int gameID;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:8080");
    }

    @BeforeEach
    public void init2() {
        facade.clear();
        facade.register("username", "password", "email");
        facade.createGame("chess");
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void register() {
        var authData = facade.register("player1", "password", "p1@email.com");
        Assertions.assertNotNull(authData.authToken());
    }

    @Test
    public void registerFail() {
        try {
            facade.register("username", "password", "email");
        }
        catch (ResponseException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void login() {
        var authData = facade.login("username", "password");
        Assertions.assertNotNull(authData);
    }

    @Test
    public void loginFail() {
        try {
            facade.login("user", "pass");
        }
        catch (ResponseException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void list() {
        ListGamesResult gameList = facade.listGames();
        Assertions.assertNotNull(gameList);
    }

    @Test
    public void listFail() {
        facade.clear();
        facade.register("username", "password", "email");
        ListGamesResult gameList = facade.listGames();
        ListGamesResult expected = new ListGamesResult(new ArrayList<>());
        Assertions.assertEquals(expected, gameList);
    }

    @Test
    public void join() {
        ListGamesResult gameList = facade.listGames();
        gameID = gameList.games().getFirst().gameID();
        facade.joinGame(gameID, ChessGame.TeamColor.WHITE);
        gameList = facade.listGames();
        Assertions.assertEquals("username", gameList.games().getFirst().whiteUsername());
    }

    @Test
    public void joinFail() {
        try {
            ListGamesResult gameList = facade.listGames();
            gameID = gameList.games().getFirst().gameID();
            facade.joinGame(gameID, ChessGame.TeamColor.WHITE);
            gameList = facade.listGames();
            facade.joinGame(gameID, ChessGame.TeamColor.BLACK);
            Assertions.assertEquals("username", gameList.games().getFirst().whiteUsername());
        }
        catch (ResponseException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void logout() {
        try {
            facade.logout();
            Assertions.assertTrue(true);
        }
        catch (ResponseException e) {
            Assertions.fail();
        }
    }

    @Test
    public void logoutFail() {
        try {
            facade.logout();
            facade.logout();
            Assertions.fail();
        }
        catch (ResponseException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void create() {
        facade.createGame("name");
        Assertions.assertEquals("name", facade.listGames().games().getLast().gameName());
    }

    @Test
    public void createFail() {
        try {
            facade.logout();
            facade.createGame("name");
            Assertions.fail();
        }
        catch (ResponseException e) {
            Assertions.assertTrue(true);
        }
    }
}
