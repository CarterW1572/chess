import dataaccess.*;
import server.Server;
import service.*;

public class Main {
    public static void main(String[] args) {
        var server = new Server();
        server.run(8080);
    }
}