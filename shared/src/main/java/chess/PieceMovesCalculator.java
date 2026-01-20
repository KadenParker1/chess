package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PieceMovesCalculator{
    private final ChessBoard board;
    private final ChessPosition myPosition;

    public PieceMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        this.board = board;
        this.myPosition = myPosition;
    }

    public Boolean inBounds(ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        return row >= 0 && row <= 7 && col >=0 && col <= 7; // check if within the chess board
    }

    public Boolean checkIfValidMove(ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
    }

    public Collection<ChessMove> calculateBishopMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
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