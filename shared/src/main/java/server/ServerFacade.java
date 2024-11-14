package server;

import chess.ChessGame;
import requests.*;
import model.*;
import com.google.gson.Gson;
import results.*;

import java.io.*;
import java.net.*;

public class ServerFacade {
    private final String serverUrl;
    private AuthData authData;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public void clear() {
        var path = "/db";
        this.makeRequest("DELETE", path, null, null, null);
    }

    public AuthData register(String username, String password, String email) {
        var path = "/user";
        UserData req = new UserData(username, password, email);
        authData = this.makeRequest("POST", path, req, AuthData.class, null);
        return authData;
    }

    public AuthData login(String username, String password) throws ResponseException {
        var path = "/session";
        LoginRequest req = new LoginRequest(username, password);
        authData = this.makeRequest("POST", path, req, AuthData.class, null);
        return authData;
    }

    public ListGamesResult listGames() {
        var path = "/game";
        return this.makeRequest("GET", path, null, ListGamesResult.class, authData.authToken());
    }

    public void joinGame(int gameID, ChessGame.TeamColor color) {
        var path = "/game";
        JoinGameRequest req = new JoinGameRequest(color, gameID);
        this.makeRequest("PUT", path, req, null, authData.authToken());
    }

    public void logout() {
        var path = "/session";
        this.makeRequest("DELETE", path, null, null, authData.authToken());
    }

    public void createGame(String name) {
        var path = "/game";
        CreateGameRequest req = new CreateGameRequest(name);
        this.makeRequest("POST", path, req, null, authData.authToken());
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String header) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(request, http, header);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http, String header) throws IOException {
        if (header != null) {
            http.addRequestProperty("Authorization", header);
        }
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            if (status == 400) {
                throw new ResponseException(status, "failure: bad request");
            }
            else if (status == 401) {
                throw new ResponseException(status, "failure: unauthorized");
            }
            else if (status == 403) {
                throw new ResponseException(status, "failure: invalid");
            }
            else {
                throw new ResponseException(status, "failure");
            }
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status == 200;
    }
}
