import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
    private final Client client;

    public Repl(String serverUrl) {
        client = new Client(serverUrl, this);
    }

    public void run() {
        System.out.println("\uD83D\uDC36 Welcome to chess. Sign in to start.");
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            //printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.println(result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.println(msg);
            }
        }
        System.out.println();
    }

//    public void notify(Notification notification) {
//        System.out.println(RED + notification.message());
//        printPrompt();
//    }
//
//    private void printPrompt() {
//        System.out.print("\n" + RESET + ">>> " + GREEN);
//    }

}
