package ui;

public class DisplayBoard {

    public DisplayBoard() {}

    public static void display() {
        System.out.println(EscapeSequences.SET_BG_COLOR_MAGENTA + EscapeSequences.SET_TEXT_COLOR_BLACK +
                "    h  g  f  e  d  c  b  a    " + EscapeSequences.RESET_BG_COLOR + EscapeSequences.RESET_TEXT_COLOR);
    }
}
