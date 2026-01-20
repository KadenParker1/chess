package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PieceMovesCalculator{
    private final ChessBoard board;
    private final ChessPosition myPosition;
    private final ChessGame.TeamColor myTeamColor;
    public PieceMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        this.board = board;
        this.myPosition = myPosition;
        ChessPiece piece = board.getPiece(myPosition);
        if (piece != null) {
            this.myTeamColor = piece.getTeamColor();
        }
        else {
            myTeamColor = null;
        }
    }

    public Boolean inBounds(ChessPosition myAttemptedPosition) {
        int row = myAttemptedPosition.getRow();
        int col = myAttemptedPosition.getColumn();
        return row >= 1 && row <= 8 && col >=1 && col <= 8; // check if within the chess board
    }

    public Boolean checkIfEmpty(ChessPosition myAttemptedPosition) {
        ChessPiece piece = board.getPiece(myAttemptedPosition);
        if (piece == null) {
            return true;
        }
        else {
            return false;
        }
        }

    public Boolean checkIfCapturable(ChessPosition myAttemptedPosition) {
        ChessPiece piece = board.getPiece(myAttemptedPosition);
        if (piece == null) {
            return false;
        }
        return myTeamColor != piece.getTeamColor();
    }

    public Collection<ChessMove> calculateBishopMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();

        ChessPosition up_Right_Attemped_Move = new ChessPosition((myPosition.getRow() + 1), (myPosition.getColumn() + 1));
        while (inBounds(up_Right_Attemped_Move)) {
            if (checkIfEmpty(up_Right_Attemped_Move)) {
                moves.add(new ChessMove(myPosition, up_Right_Attemped_Move, null));
            }
            else {
                if (checkIfCapturable(up_Right_Attemped_Move)) {
                    moves.add(new ChessMove(myPosition, up_Right_Attemped_Move, null));
                }
                    break;
            }
            up_Right_Attemped_Move = new ChessPosition((up_Right_Attemped_Move.getRow()+1), (up_Right_Attemped_Move.getColumn()+1));
        }

        ChessPosition down_Left_Attemped_Move = new ChessPosition((myPosition.getRow()-1), (myPosition.getColumn()-1));
        while (inBounds(down_Left_Attemped_Move)) {
            if (checkIfEmpty(down_Left_Attemped_Move)) {
                moves.add(new ChessMove(myPosition, down_Left_Attemped_Move, null));
            }
            else {
                if (checkIfCapturable(down_Left_Attemped_Move)) {
                    moves.add(new ChessMove(myPosition, down_Left_Attemped_Move, null));
                }
                    break;
            }
            down_Left_Attemped_Move = new ChessPosition((down_Left_Attemped_Move.getRow()-1), (down_Left_Attemped_Move.getColumn()-1));

        }

        ChessPosition up_Left_Attemped_Move = new ChessPosition((myPosition.getRow()-1), (myPosition.getColumn()+1));
        while (inBounds(up_Left_Attemped_Move)) {
            if (checkIfEmpty(up_Left_Attemped_Move)) {
                moves.add(new ChessMove(myPosition, up_Left_Attemped_Move, null));
            }
            else {
                if (checkIfCapturable(up_Left_Attemped_Move)) {
                    moves.add(new ChessMove(myPosition, up_Left_Attemped_Move, null));
                }
                    break;
            }
            up_Left_Attemped_Move = new ChessPosition((up_Left_Attemped_Move.getRow()-1), (up_Left_Attemped_Move.getColumn()+1));
        }

        ChessPosition down_Right_Attemped_Move = new ChessPosition((myPosition.getRow()+1), (myPosition.getColumn()-1));
        while (inBounds(down_Right_Attemped_Move)) {
            if (checkIfEmpty(down_Right_Attemped_Move)) {
                moves.add(new ChessMove(myPosition, down_Right_Attemped_Move, null));
            }
            else {
                if (checkIfCapturable(down_Right_Attemped_Move)) {
                    moves.add(new ChessMove(myPosition, down_Right_Attemped_Move, null));
                }
                    break;
            }
            down_Right_Attemped_Move = new ChessPosition((down_Right_Attemped_Move.getRow()+1), (down_Right_Attemped_Move.getColumn()-1));
        }


        return moves;
    }

    public Collection<ChessMove> calculatePawnMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        return moves;
    }

    public Collection<ChessMove> calculateRookMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        return moves;

    }
    public Collection<ChessMove> calculateKingMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        return moves;
    }
    public Collection<ChessMove> calculateQueenMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        return moves;
    }
    public Collection<ChessMove> calculateKnightMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        return moves;
    }

    public Collection<ChessMove> pieceMoves() {
        ChessPiece piece = board.getPiece(myPosition);
        if (piece == null) {
            return List.of();
        }
        if (piece.getPieceType() == ChessPiece.PieceType.ROOK) {
            return calculateRookMoves(board, myPosition);
        }

        else if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            return calculatePawnMoves(board, myPosition);
        }

        else if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
            return calculateKnightMoves(board, myPosition);
        }

        else if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            return calculateKingMoves(board, myPosition);

        }

        else if (piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
            return calculateQueenMoves(board, myPosition);
        }

        else if (piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
            return calculateBishopMoves(board, myPosition);
        }
        return List.of(); // return empty list

    }


}