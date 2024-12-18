package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

public class DisplayBoard {

    public DisplayBoard() {
    }

    public void display(ChessBoard board) {
        System.out.print(EscapeSequences.SET_TEXT_BOLD);
        StringBuilder sb = new StringBuilder();
        sb.append(this.topBorder());
        sb.append(this.middle(board));
        sb.append(this.bottomBorder());
        System.out.println(sb);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.topBorderRev());
        sb2.append(this.middleRev(board));
        sb2.append(this.bottomBorderRev());
        System.out.println(sb2);
        System.out.print(EscapeSequences.RESET_TEXT_BOLD_FAINT);
    }

    private String topBorder() {
        return EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.SET_TEXT_COLOR_BLACK +
                "    h  g  f  e  d  c  b  a    " + EscapeSequences.RESET_BG_COLOR + EscapeSequences.RESET_TEXT_COLOR +
                "\n";
    }

    private String topBorderRev() {
        return EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.SET_TEXT_COLOR_BLACK +
                "    a  b  c  d  e  f  g  h    " + EscapeSequences.RESET_BG_COLOR + EscapeSequences.RESET_TEXT_COLOR +
                "\n";
    }

    private String bottomBorder() {
        return EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.SET_TEXT_COLOR_BLACK +
                "    h  g  f  e  d  c  b  a    " + EscapeSequences.RESET_BG_COLOR + EscapeSequences.RESET_TEXT_COLOR +
                "\n";
    }

    private String bottomBorderRev() {
        return EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.SET_TEXT_COLOR_BLACK +
                "    a  b  c  d  e  f  g  h    " + EscapeSequences.RESET_BG_COLOR + EscapeSequences.RESET_TEXT_COLOR +
                "\n";
    }

    private String middle(ChessBoard board) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < 8; j++) {
            sb.append(EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.SET_TEXT_COLOR_BLACK + " " + (8-j) + " ");
            for (int i = 0; i < 8; i++) {
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
                    sb.append(whiteSquare(board, i, j));
                }
                else {
                    sb.append(blackSquare(board, i, j));
                }
            }
            sb.append(EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.SET_TEXT_COLOR_BLACK + " " + (8-j) + " " +
                    EscapeSequences.RESET_BG_COLOR + EscapeSequences.RESET_TEXT_COLOR + "\n");
        }
        return sb.toString();
    }

    private String middleRev(ChessBoard board) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < 8; j++) {
            sb.append(EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.SET_TEXT_COLOR_BLACK + " " + (j+1) + " ");
            for (int i = 0; i < 8; i++) {
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
                    sb.append(whiteSquare(board, 7 - i, 7 - j));
                }
                else {
                    sb.append(blackSquare(board, 7 - i, 7 - j));
                }
            }
            sb.append(EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.SET_TEXT_COLOR_BLACK + " " + (j+1) + " " +
                    EscapeSequences.RESET_BG_COLOR + EscapeSequences.RESET_TEXT_COLOR + "\n");
        }
        return sb.toString();
    }

    private String whiteSquare(ChessBoard board, int i, int j) {
        StringBuilder sb = new StringBuilder();
        if (board.getPiece(new ChessPosition(j + 1, i + 1)) != null) {
            if (board.getPiece(new ChessPosition(j + 1, i + 1)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                sb.append(EscapeSequences.SET_BG_COLOR_WHITE + EscapeSequences.SET_TEXT_COLOR_BLUE + " " +
                        convertPiece(board.getPiece(new ChessPosition(j + 1, i + 1)).getPieceType()) + " ");
            }
            else {
                sb.append(EscapeSequences.SET_BG_COLOR_WHITE + EscapeSequences.SET_TEXT_COLOR_RED + " " +
                        convertPiece(board.getPiece(new ChessPosition(j + 1, i + 1)).getPieceType()) + " ");
            }
        }
        else {
            sb.append(EscapeSequences.SET_BG_COLOR_WHITE + "   ");
        }
        return sb.toString();
    }

    private String blackSquare(ChessBoard board, int i, int j) {
        StringBuilder sb = new StringBuilder();
        if (board.getPiece(new ChessPosition(j + 1, i + 1)) != null) {
            if (board.getPiece(new ChessPosition(j + 1, i + 1)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                sb.append(EscapeSequences.SET_BG_COLOR_BLACK + EscapeSequences.SET_TEXT_COLOR_BLUE + " " +
                        convertPiece(board.getPiece(new ChessPosition(j + 1, i + 1)).getPieceType()) + " ");
            }
            else {
                sb.append(EscapeSequences.SET_BG_COLOR_BLACK + EscapeSequences.SET_TEXT_COLOR_RED + " " +
                        convertPiece(board.getPiece(new ChessPosition(j + 1, i + 1)).getPieceType()) + " ");
            }
        }
        else {
            sb.append(EscapeSequences.SET_BG_COLOR_BLACK + "   ");
        }
        return sb.toString();
    }

    private String convertPiece(ChessPiece.PieceType type) {
        return switch (type) {
            case KING -> "K";
            case QUEEN -> "Q";
            case KNIGHT -> "N";
            case BISHOP -> "B";
            case ROOK -> "R";
            case PAWN -> "P";
        };
    }
}
