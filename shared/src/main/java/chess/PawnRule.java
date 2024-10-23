package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnRule extends PieceRule {

    private final ChessGame.TeamColor teamColor;

    public PawnRule(final ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
    }

    @Override
    public Collection<ChessMove> getPossibleMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        if (teamColor == ChessGame.TeamColor.BLACK) {
            if (position.getRow() == 6 &&
                    board.getPiece(new ChessPosition(position.getRow(), position.getColumn()+1)) == null &&
                    board.getPiece(new ChessPosition(position.getRow()-1, position.getColumn()+1)) == null) {
                moves.add(new ChessMove(position, new ChessPosition(position.getRow()-1,
                        position.getColumn()+1)));
            }
            if (position.getRow() == 1) {
                if (board.getPiece(new ChessPosition(position.getRow(), position.getColumn()+1)) == null) {
                    moves.add(new ChessMove(position, new ChessPosition(position.getRow(),
                            position.getColumn() + 1), ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(position, new ChessPosition(position.getRow(),
                            position.getColumn() + 1), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(position, new ChessPosition(position.getRow(),
                            position.getColumn() + 1), ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(position, new ChessPosition(position.getRow(),
                            position.getColumn() + 1), ChessPiece.PieceType.KNIGHT));
                }
                if (board.getPiece(new ChessPosition(position.getRow(), position.getColumn())) != null &&
                        position.getColumn() > 0) {
                    if (board.getPiece(new ChessPosition(position.getRow(), position.getColumn())).getTeamColor()
                            != teamColor) {
                        moves.add(new ChessMove(position, new ChessPosition(position.getRow(),
                                position.getColumn()), ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(position, new ChessPosition(position.getRow(),
                                position.getColumn()), ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(position, new ChessPosition(position.getRow(),
                                position.getColumn()), ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(position, new ChessPosition(position.getRow(),
                                position.getColumn()), ChessPiece.PieceType.KNIGHT));
                    }
                }
                if (board.getPiece(new ChessPosition(position.getRow(), position.getColumn()+2)) != null &&
                        position.getColumn() < 7) {
                    if (board.getPiece(new ChessPosition(position.getRow(),
                            position.getColumn()+2)).getTeamColor() != teamColor) {
                        moves.add(new ChessMove(position, new ChessPosition(position.getRow(),
                                position.getColumn() + 2), ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(position, new ChessPosition(position.getRow(),
                                position.getColumn() + 2), ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(position, new ChessPosition(position.getRow(),
                                position.getColumn() + 2), ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(position, new ChessPosition(position.getRow(),
                                position.getColumn() + 2), ChessPiece.PieceType.KNIGHT));
                    }
                }
            }
            if (position.getRow() != 1 && position.getColumn() > 0 &&
                    board.getPiece(new ChessPosition(position.getRow(), position.getColumn())) != null) {
                if (board.getPiece(new ChessPosition(position.getRow(),
                        position.getColumn())).getTeamColor() != teamColor) {
                    moves.add(new ChessMove(position, new ChessPosition(position.getRow(), position.getColumn())));
                }
            }
            if (position.getRow() != 1 && position.getColumn() < 7 &&
                    board.getPiece(new ChessPosition(position.getRow(), position.getColumn()+2)) != null) {
                if (board.getPiece(new ChessPosition(position.getRow(),
                        position.getColumn()+2)).getTeamColor() != teamColor) {
                    moves.add(new ChessMove(position, new ChessPosition(position.getRow(),
                            position.getColumn() + 2)));
                }
            }
            if (position.getRow() != 1 && board.getPiece(new ChessPosition(position.getRow(),
                    position.getColumn()+1)) == null) {
                moves.add(new ChessMove(position, new ChessPosition(position.getRow(), position.getColumn()+1)));
            }
        }
        else {
            if (position.getRow() == 1 && board.getPiece(new ChessPosition(position.getRow()+2,
                    position.getColumn()+1)) == null && board.getPiece(new ChessPosition(position.getRow()+3,
                    position.getColumn()+1)) == null) {
                moves.add(new ChessMove(position,
                        new ChessPosition(position.getRow()+3, position.getColumn()+1)));
            }
            if (position.getRow() == 6) {
                if (board.getPiece(new ChessPosition(position.getRow()+2, position.getColumn()+1)) == null) {
                    moves.add(new ChessMove(position, new ChessPosition(position.getRow()+2,
                            position.getColumn() + 1), ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(position, new ChessPosition(position.getRow()+2,
                            position.getColumn() + 1), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(position, new ChessPosition(position.getRow()+2,
                            position.getColumn() + 1), ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(position, new ChessPosition(position.getRow()+2,
                            position.getColumn() + 1), ChessPiece.PieceType.KNIGHT));
                }
                if (board.getPiece(new ChessPosition(position.getRow()+2, position.getColumn())) != null &&
                        position.getColumn() > 0) {
                    if (board.getPiece(new ChessPosition(position.getRow()+2, position.getColumn())).getTeamColor()
                            != teamColor) {
                        moves.add(new ChessMove(position, new ChessPosition(position.getRow()+2,
                                position.getColumn()), ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(position, new ChessPosition(position.getRow()+2,
                                position.getColumn()), ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(position, new ChessPosition(position.getRow()+2,
                                position.getColumn()), ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(position, new ChessPosition(position.getRow()+2,
                                position.getColumn()), ChessPiece.PieceType.KNIGHT));
                    }
                }
                if (board.getPiece(new ChessPosition(position.getRow()+2, position.getColumn()+2)) != null &&
                        position.getColumn() < 7) {
                    if (board.getPiece(new ChessPosition(position.getRow()+2,
                            position.getColumn()+2)).getTeamColor() != teamColor) {
                        moves.add(new ChessMove(position, new ChessPosition(position.getRow()+2,
                                position.getColumn() + 2), ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(position, new ChessPosition(position.getRow()+2,
                                position.getColumn() + 2), ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(position, new ChessPosition(position.getRow()+2,
                                position.getColumn() + 2), ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(position, new ChessPosition(position.getRow()+2,
                                position.getColumn() + 2), ChessPiece.PieceType.KNIGHT));
                    }
                }
            }
            if (position.getRow() != 6 && position.getColumn() > 0 &&
                    board.getPiece(new ChessPosition(position.getRow()+2, position.getColumn())) != null) {
                if (board.getPiece(new ChessPosition(position.getRow()+2, position.getColumn())).getTeamColor()
                        != teamColor) {
                    moves.add(new ChessMove(position,
                            new ChessPosition(position.getRow()+2, position.getColumn())));
                }
            }
            if (position.getRow() != 6 && position.getColumn() < 7 &&
                    board.getPiece(new ChessPosition(position.getRow()+2, position.getColumn()+2)) != null) {
                if (board.getPiece(new ChessPosition(position.getRow()+2,
                        position.getColumn()+2)).getTeamColor() != teamColor) {
                    moves.add(new ChessMove(position,
                            new ChessPosition(position.getRow()+2, position.getColumn() + 2)));
                }
            }
            if (position.getRow() != 6 &&
                    board.getPiece(new ChessPosition(position.getRow()+2, position.getColumn()+1)) == null) {
                moves.add(new ChessMove(position,
                        new ChessPosition(position.getRow()+2, position.getColumn()+1)));
            }
        }
        return moves;
    }
}
