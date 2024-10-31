import dataaccess.*;
import server.Server;
import service.*;

import javax.xml.crypto.Data;

public class Main {
    public static void main(String[] args) {
        var server = new Server();
        server.run(8080);
    }
}