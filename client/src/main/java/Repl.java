import ui.EscapeSequences;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
    private final Client client;
    private State status;
    private String username;

    public Repl(String serverUrl) {
        client = new Client(serverUrl, this);
    }

    public void run() {
        System.out.println(EscapeSequences.WHITE_KING + " Welcome to chess. Sign in to start. " + EscapeSequences.WHITE_KING);
        System.out.println(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            status = client.getStatus();
            username = client.getUser();
            if (status == State.LOGGEDOUT) {
                System.out.print(SET_TEXT_BOLD + SET_TEXT_ITALIC + "[" + status + "] >>> " + RESET_TEXT_ITALIC +
                        RESET_TEXT_BOLD_FAINT);
            }
            else {
                System.out.print(SET_TEXT_BOLD + SET_TEXT_ITALIC + "[" + status + ": " + username + "] >>> " +
                        RESET_TEXT_ITALIC + RESET_TEXT_BOLD_FAINT);
            }
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.println(result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.println(msg);
            }
        }
    }
}
