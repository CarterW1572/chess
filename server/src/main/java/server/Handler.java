package server;

import com.google.gson.Gson;
import dataaccess.*;
import service.*;
import spark.*;

import javax.xml.crypto.Data;

public class Handler {
    private final DataService dataService;
    private final Gson serializer = new Gson();

    public Handler(DataService dataService) {
        this.dataService = dataService;
    }

    public Object clear(Request req, Response res) throws DataAccessException {
        dataService.clear();
        res.status(200);
        return "{}";
    }

    public Object register(Request req, Response res) throws DataAccessException {
        return """
                {"username": "", "authToken": ""}
                """;
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
