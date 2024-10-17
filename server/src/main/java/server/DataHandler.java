package server;

import com.google.gson.Gson;
import dataaccess.*;
import service.*;
import spark.*;

public class DataHandler {
    private final DataService dataService;
    private final Gson serializer = new Gson();

    public DataHandler(DataService dataService) {
        this.dataService = dataService;
    }

    public Object clear(Request req, Response res) throws DataAccessException {
        dataService.clear();
        res.status(200);
        return "{}";
    }
}
