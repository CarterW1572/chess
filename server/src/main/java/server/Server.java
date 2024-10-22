package server;

import dataaccess.*;
import service.*;
import spark.*;

public class Server {

    private final Handler handler;

    public Server() {
        UserDAO userDAO = new MemoryUserDAO();
        GameDAO gameDAO = new MemoryGameDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        DataService dataService = new DataService(userDAO, gameDAO, authDAO);
        UserService userService = new UserService(userDAO, gameDAO, authDAO);
        GameService gameService = new GameService(userDAO, gameDAO, authDAO);
        handler = new Handler(dataService, userService, gameService);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", (req, res) -> {
            res.status(200);
            return handler.clear();
        });

        Spark.post("/user", (req, res) -> {
            try {
                res.status(200);
                return handler.register(req);
            }
            catch (BadRequestException e) {
                res.status(400);
                return e.getMessage();
            }
            catch (DataAccessException e) {
                res.status(403);
                return e.getMessage();
            }
        });

        Spark.post("/session", (req, res) -> {
            try {
                res.status(200);
                return handler.login(req);
            }
            catch (UnauthorizedException e) {
                res.status(401);
                return e.getMessage();
            }
        });

        Spark.delete("/session", handler::logout);
        Spark.get("/game", handler::listGames);
        Spark.post("/game", handler::createGame);
        Spark.put("/game", handler::joinGame);

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
