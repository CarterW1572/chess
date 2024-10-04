package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor teamTurn;
    private ChessBoard board;

    public ChessGame() {
        teamTurn = TeamColor.WHITE;
        board = new ChessBoard();
        board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        if (board.getPiece(startPosition) == null) {
            return null;
        }
        if (board.getPiece(startPosition).getTeamColor() == TeamColor.WHITE) {
            setTeamTurn(TeamColor.WHITE);
        }
        else {
            setTeamTurn(TeamColor.BLACK);
        }
        Collection<ChessMove> moves = board.getPiece(startPosition).pieceMoves(board, startPosition);
        var removes = new ArrayList<ChessMove>();
        for (ChessMove move : moves) {
            ChessBoard oldBoard = (ChessBoard) board.clone();
            board.addPiece(move.getEndPosition(), board.getPiece(startPosition));
            board.removePiece(startPosition);
            if (isInCheck(teamTurn)) {
                removes.add(move);
            }
            board = oldBoard;
        }
        moves.removeAll(removes);
        return moves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition start = move.getStartPosition();
        if (board.getPiece(start) == null) {
            throw new InvalidMoveException("No piece in that position");
        }
        if (board.getPiece(start).getTeamColor() != teamTurn) {
            throw new InvalidMoveException("Out of turn");
        }
        var moves = validMoves(start);
        boolean goodMove = false;
        for (ChessMove valid : moves) {
            if (valid.equals(move)) {
                goodMove = true;
                break;
            }
        }
        if (goodMove) {
            if (move.getPromotionPiece() != null) {
                board.addPiece(move.getEndPosition(), new ChessPiece(teamTurn, move.getPromotionPiece()));
            }
            else {
                board.addPiece(move.getEndPosition(), board.getPiece(start));
            }
            board.removePiece(start);
            if (getTeamTurn() == TeamColor.WHITE) {
                setTeamTurn(TeamColor.BLACK);
            }
            else {
                setTeamTurn(TeamColor.WHITE);
            }
        }
        else {
            throw new InvalidMoveException("Not a valid move");
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        var kingPosition = new ChessPosition();
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                if (board.getPiece(new ChessPosition(row, col)) != null) {
                    if (board.getPiece(new ChessPosition(row, col)).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(new ChessPosition(row, col)).getTeamColor() == teamColor) {
                        kingPosition = new ChessPosition(row, col);
                    }
                }
            }
        }
        var enemyMoves = new ArrayList<ChessMove>();
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                if (board.getPiece(new ChessPosition(row, col)) != null) {
                    if (board.getPiece(new ChessPosition(row, col)).getTeamColor() != teamColor) {
                        enemyMoves.addAll(board.getPiece(new ChessPosition(row, col)).pieceMoves(board, new ChessPosition(row, col)));
                        for (ChessMove move : enemyMoves) {
                            if (move.equals(new ChessMove(new ChessPosition(row, col), kingPosition)) ||
                                move.equals(new ChessMove(new ChessPosition(row, col), kingPosition, ChessPiece.PieceType.QUEEN)) ||
                                move.equals(new ChessMove(new ChessPosition(row, col), kingPosition, ChessPiece.PieceType.BISHOP)) ||
                                move.equals(new ChessMove(new ChessPosition(row, col), kingPosition, ChessPiece.PieceType.KNIGHT)) ||
                                move.equals(new ChessMove(new ChessPosition(row, col), kingPosition, ChessPiece.PieceType.ROOK))) {
                                return true;
                            }
                        }
                        enemyMoves.clear();
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (isInCheck(teamColor)) {
            var moves = new ArrayList<ChessMove>();
            for (int row = 1; row <= 8; row++) {
                for (int col = 1; col <= 8; col++) {
                    if (board.getPiece(new ChessPosition(row, col)) != null && board.getPiece(new ChessPosition(row, col)).getTeamColor() == teamColor) {
                        moves.addAll(validMoves(new ChessPosition(row, col)));
                    }
                }
            }
            return moves.isEmpty();
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {
            var moves = new ArrayList<ChessMove>();
            for (int row = 1; row <= 8; row++) {
                for (int col = 1; col <= 8; col++) {
                    if (board.getPiece(new ChessPosition(row, col)) != null && board.getPiece(new ChessPosition(row, col)).getTeamColor() == teamColor) {
                        moves.addAll(validMoves(new ChessPosition(row, col)));
                    }
                }
            }
            return moves.isEmpty();
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
