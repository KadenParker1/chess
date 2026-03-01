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

        ChessPosition upRightAttempedMove = new ChessPosition((myPosition.getRow() + 1), (myPosition.getColumn() + 1));
        while (inBounds(upRightAttempedMove)) {
            if (checkIfEmpty(upRightAttempedMove)) {
                moves.add(new ChessMove(myPosition, upRightAttempedMove, null));
            }
            else {
                if (checkIfCapturable(upRightAttempedMove)) {
                    moves.add(new ChessMove(myPosition, upRightAttempedMove, null));
                }
                    break;
            }
            upRightAttempedMove = new ChessPosition((upRightAttempedMove.getRow()+1), (upRightAttempedMove.getColumn()+1));
        }

        ChessPosition downLeftAttempedMove = new ChessPosition((myPosition.getRow()-1), (myPosition.getColumn()-1));
        while (inBounds(downLeftAttempedMove)) {
            if (checkIfEmpty(downLeftAttempedMove)) {
                moves.add(new ChessMove(myPosition, downLeftAttempedMove, null));
            }
            else {
                if (checkIfCapturable(downLeftAttempedMove)) {
                    moves.add(new ChessMove(myPosition, downLeftAttempedMove, null));
                }
                    break;
            }
            downLeftAttempedMove = new ChessPosition((downLeftAttempedMove.getRow()-1), (downLeftAttempedMove.getColumn()-1));

        }

        ChessPosition upLeftAttempedMove = new ChessPosition((myPosition.getRow()-1), (myPosition.getColumn()+1));
        while (inBounds(upLeftAttempedMove)) {
            if (checkIfEmpty(upLeftAttempedMove)) {
                moves.add(new ChessMove(myPosition, upLeftAttempedMove, null));
            }
            else {
                if (checkIfCapturable(upLeftAttempedMove)) {
                    moves.add(new ChessMove(myPosition, upLeftAttempedMove, null));
                }
                    break;
            }
            upLeftAttempedMove = new ChessPosition((upLeftAttempedMove.getRow()-1), (upLeftAttempedMove.getColumn()+1));
        }

        ChessPosition downRightAttempedMove = new ChessPosition((myPosition.getRow()+1), (myPosition.getColumn()-1));
        while (inBounds(downRightAttempedMove)) {
            if (checkIfEmpty(downRightAttempedMove)) {
                moves.add(new ChessMove(myPosition, downRightAttempedMove, null));
            }
            else {
                if (checkIfCapturable(downRightAttempedMove)) {
                    moves.add(new ChessMove(myPosition, downRightAttempedMove, null));
                }
                    break;
            }
            downRightAttempedMove = new ChessPosition((downRightAttempedMove.getRow()+1), (downRightAttempedMove.getColumn()-1));
        }


        return moves;
    }

    public Collection<ChessMove> calculateWhitePawnMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        ChessPiece piece = board.getPiece(myPosition);
        ChessGame.TeamColor color  = piece.getTeamColor();
                if (myPosition.getRow() == 2) { // covers if pawn has not moved can move once or twice
                    ChessPosition upAttemptedMove = new ChessPosition((myPosition.getRow()+1), (myPosition.getColumn()));
                       if (inBounds(upAttemptedMove)) {
                           if (checkIfEmpty(upAttemptedMove)) {
                               moves.add(new ChessMove(myPosition, upAttemptedMove, null));
                           }
                           ChessPosition doubleUpAttemptedMove = new ChessPosition((myPosition.getRow() + 2), (myPosition.getColumn()));
                           if (checkIfEmpty(upAttemptedMove) && checkIfEmpty(doubleUpAttemptedMove)) {
                               moves.add(new ChessMove(myPosition, doubleUpAttemptedMove, null));
                           }
                           ChessPosition upLeftAttemptedMove = new ChessPosition((myPosition.getRow() + 1), (myPosition.getColumn() - 1));
                           ChessPosition upRightAttemptedMove = new ChessPosition((myPosition.getRow() + 1), (myPosition.getColumn() + 1));
                           if (inBounds(upLeftAttemptedMove) && checkIfCapturable(upLeftAttemptedMove)) {
                                   moves.add(new ChessMove(myPosition, upLeftAttemptedMove, null));
                           }

                        if (inBounds(upRightAttemptedMove) && checkIfCapturable(upRightAttemptedMove)) {
                               moves.add(new ChessMove(myPosition, upRightAttemptedMove, null));
                       }
                    }
                }

                else if (myPosition.getRow() == 7) {
                    ChessPosition upAttemptedMove = new ChessPosition((myPosition.getRow() + 1), (myPosition.getColumn()));
                    if (inBounds(upAttemptedMove)) {
                        if (checkIfEmpty(upAttemptedMove)) {
                            moves.add(new ChessMove(myPosition, upAttemptedMove, ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, upAttemptedMove, ChessPiece.PieceType.KNIGHT));
                            moves.add(new ChessMove(myPosition, upAttemptedMove, ChessPiece.PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, upAttemptedMove, ChessPiece.PieceType.QUEEN));
                        }
                        ChessPosition upLeftAttemptedMove = new ChessPosition((myPosition.getRow() + 1), (myPosition.getColumn() - 1));
                        ChessPosition upRightAttemptedMove = new ChessPosition((myPosition.getRow() + 1), (myPosition.getColumn() + 1));
                        if (inBounds(upLeftAttemptedMove)) {
                            if (checkIfCapturable(upLeftAttemptedMove)) {
                                moves.add(new ChessMove(myPosition, upLeftAttemptedMove, ChessPiece.PieceType.ROOK));
                                moves.add(new ChessMove(myPosition, upLeftAttemptedMove, ChessPiece.PieceType.KNIGHT));
                                moves.add(new ChessMove(myPosition, upLeftAttemptedMove, ChessPiece.PieceType.BISHOP));
                                moves.add(new ChessMove(myPosition, upLeftAttemptedMove, ChessPiece.PieceType.QUEEN));

                            }
                        }
                        if (inBounds(upRightAttemptedMove)) {
                            if (checkIfCapturable(upRightAttemptedMove)) {
                                moves.add(new ChessMove(myPosition, upRightAttemptedMove, ChessPiece.PieceType.ROOK));
                                moves.add(new ChessMove(myPosition, upRightAttemptedMove, ChessPiece.PieceType.KNIGHT));
                                moves.add(new ChessMove(myPosition, upRightAttemptedMove, ChessPiece.PieceType.BISHOP));
                                moves.add(new ChessMove(myPosition, upRightAttemptedMove, ChessPiece.PieceType.QUEEN));
                            }
                        }
                }
                }

                else {
                    ChessPosition upAttemptedMove = new ChessPosition((myPosition.getRow() + 1), (myPosition.getColumn()));
                    if (inBounds(upAttemptedMove)) {
                        if (checkIfEmpty(upAttemptedMove)) {
                            moves.add(new ChessMove(myPosition, upAttemptedMove, null));
                        }
                        ChessPosition upLeftAttemptedMove = new ChessPosition((myPosition.getRow() + 1), (myPosition.getColumn() - 1));
                        ChessPosition upRightAttemptedMove = new ChessPosition((myPosition.getRow() + 1), (myPosition.getColumn() + 1));
                        if (inBounds(upLeftAttemptedMove)) {
                            if (checkIfCapturable(upLeftAttemptedMove)) {
                                moves.add(new ChessMove(myPosition, upLeftAttemptedMove, null));
                            }
                        }
                        if (inBounds(upRightAttemptedMove)) {
                            if (checkIfCapturable(upRightAttemptedMove)) {
                                moves.add(new ChessMove(myPosition, upRightAttemptedMove, null));
                            }
                        }
                    }
                }

        return moves;
    }

    public Collection<ChessMove> calculateBlackPawnMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        ChessPiece piece = board.getPiece(myPosition);
        ChessGame.TeamColor color  = piece.getTeamColor();
            if (myPosition.getRow() == 7) { // covers if pawn has not moved can move once or twice
                ChessPosition downAttemptedMove = new ChessPosition((myPosition.getRow() - 1), (myPosition.getColumn()));
                if (inBounds(downAttemptedMove)) {
                    if (checkIfEmpty(downAttemptedMove)) {
                        moves.add(new ChessMove(myPosition, downAttemptedMove, null));
                    }
                    ChessPosition doubleDownAttemptedMove = new ChessPosition((myPosition.getRow() - 2), (myPosition.getColumn()));
                    if (checkIfEmpty(downAttemptedMove) && checkIfEmpty(doubleDownAttemptedMove)) {
                        moves.add(new ChessMove(myPosition, doubleDownAttemptedMove, null));
                    }
                    ChessPosition downLeftAttemptedMove = new ChessPosition((myPosition.getRow() - 1), (myPosition.getColumn() - 1));
                    ChessPosition downRightAttemptedMove = new ChessPosition((myPosition.getRow() - 1), (myPosition.getColumn() + 1));

                    if (inBounds(downLeftAttemptedMove) && checkIfCapturable(downLeftAttemptedMove)) {
                            moves.add(new ChessMove(myPosition, downLeftAttemptedMove, null));
                    }
                    if (inBounds(downRightAttemptedMove) && checkIfCapturable(downRightAttemptedMove)) {
                            moves.add(new ChessMove(myPosition, downRightAttemptedMove, null));
                        }
                }
            }

            else if (myPosition.getRow() == 2) {
                ChessPosition downAttemptedMove = new ChessPosition((myPosition.getRow() - 1), (myPosition.getColumn()));
                if (inBounds(downAttemptedMove)) {
                    if (checkIfEmpty(downAttemptedMove)) {
                        moves.add(new ChessMove(myPosition, downAttemptedMove, ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(myPosition, downAttemptedMove, ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(myPosition, downAttemptedMove, ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, downAttemptedMove, ChessPiece.PieceType.QUEEN));
                    }
                    ChessPosition downLeftAttemptedMove = new ChessPosition((myPosition.getRow() - 1), (myPosition.getColumn() - 1));
                    ChessPosition downRightAttemptedMove = new ChessPosition((myPosition.getRow() - 1), (myPosition.getColumn() + 1));
                    if (inBounds(downLeftAttemptedMove) && checkIfCapturable(downLeftAttemptedMove)) {
                            moves.add(new ChessMove(myPosition, downLeftAttemptedMove, ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, downLeftAttemptedMove, ChessPiece.PieceType.KNIGHT));
                            moves.add(new ChessMove(myPosition, downLeftAttemptedMove, ChessPiece.PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, downLeftAttemptedMove, ChessPiece.PieceType.QUEEN));
                    }
                    if (inBounds(downRightAttemptedMove) && checkIfCapturable(downRightAttemptedMove)) {
                            moves.add(new ChessMove(myPosition, downRightAttemptedMove, ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, downRightAttemptedMove, ChessPiece.PieceType.KNIGHT));
                            moves.add(new ChessMove(myPosition, downRightAttemptedMove, ChessPiece.PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, downRightAttemptedMove, ChessPiece.PieceType.QUEEN));
                    }
                }
            }

            else {
                ChessPosition downAttemptedMove = new ChessPosition((myPosition.getRow()-1), (myPosition.getColumn()));
                if (inBounds(downAttemptedMove)) {
                    if (checkIfEmpty(downAttemptedMove)) {
                        moves.add(new ChessMove(myPosition, downAttemptedMove, null));
                    }
                    ChessPosition downLeftAttemptedMove = new ChessPosition((myPosition.getRow() - 1), (myPosition.getColumn() - 1));
                    ChessPosition downRightAttemptedMove = new ChessPosition((myPosition.getRow() - 1), (myPosition.getColumn() + 1));
                    if (inBounds(downLeftAttemptedMove)) {
                        if (checkIfCapturable(downLeftAttemptedMove)) {
                            moves.add(new ChessMove(myPosition, downLeftAttemptedMove, null));
                        }
                    }
                    if (inBounds(downRightAttemptedMove)) {
                        if (checkIfCapturable(downRightAttemptedMove)) {
                            moves.add(new ChessMove(myPosition, downRightAttemptedMove, null));
                        }
                    }
                }
            }
        return moves;
    }

    public Collection<ChessMove> calculateRookMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        ChessPosition rightAttempedMove = new ChessPosition((myPosition.getRow()), (myPosition.getColumn() + 1));
        while (inBounds(rightAttempedMove)) {
            if (checkIfEmpty(rightAttempedMove)) {
                moves.add(new ChessMove(myPosition, rightAttempedMove, null));
            }
            else {
                if (checkIfCapturable(rightAttempedMove)) {
                    moves.add(new ChessMove(myPosition, rightAttempedMove, null));
                }
                break;
            }
            rightAttempedMove = new ChessPosition((rightAttempedMove.getRow()), (rightAttempedMove.getColumn()+1));
        }

        ChessPosition leftAttempedMove = new ChessPosition((myPosition.getRow()), (myPosition.getColumn()-1));
        while (inBounds(leftAttempedMove)) {
            if (checkIfEmpty(leftAttempedMove)) {
                moves.add(new ChessMove(myPosition, leftAttempedMove, null));
            }
            else {
                if (checkIfCapturable(leftAttempedMove)) {
                    moves.add(new ChessMove(myPosition, leftAttempedMove, null));
                }
                break;
            }
            leftAttempedMove = new ChessPosition((leftAttempedMove.getRow()), (leftAttempedMove.getColumn()-1));

        }

        ChessPosition upAttempedMove = new ChessPosition((myPosition.getRow()+1), (myPosition.getColumn()));
        while (inBounds(upAttempedMove)) {
            if (checkIfEmpty(upAttempedMove)) {
                moves.add(new ChessMove(myPosition, upAttempedMove, null));
            }
            else {
                if (checkIfCapturable(upAttempedMove)) {
                    moves.add(new ChessMove(myPosition, upAttempedMove, null));
                }
                break;
            }
            upAttempedMove = new ChessPosition((upAttempedMove.getRow()+1), (upAttempedMove.getColumn()));
        }

        ChessPosition downAttempedMove = new ChessPosition((myPosition.getRow()-1), (myPosition.getColumn()));
        while (inBounds(downAttempedMove)) {
            if (checkIfEmpty(downAttempedMove)) {
                moves.add(new ChessMove(myPosition, downAttempedMove, null));
            }
            else {
                if (checkIfCapturable(downAttempedMove)) {
                    moves.add(new ChessMove(myPosition, downAttempedMove, null));
                }
                break;
            }
            downAttempedMove = new ChessPosition((downAttempedMove.getRow()-1), (downAttempedMove.getColumn()));
        }
        return moves;

    }
    public Collection<ChessMove> calculateKingMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        ChessPosition rightAttempedMove = new ChessPosition((myPosition.getRow()), (myPosition.getColumn() + 1));
        if (inBounds(rightAttempedMove)) {
            if (checkIfEmpty(rightAttempedMove)) {
                moves.add(new ChessMove(myPosition, rightAttempedMove, null));
            }
            else {
                if (checkIfCapturable(rightAttempedMove)) {
                    moves.add(new ChessMove(myPosition, rightAttempedMove, null));
                }
            }
        }

        ChessPosition leftAttempedMove = new ChessPosition((myPosition.getRow()), (myPosition.getColumn()-1));
        if (inBounds(leftAttempedMove)) {
            if (checkIfEmpty(leftAttempedMove)) {
                moves.add(new ChessMove(myPosition, leftAttempedMove, null));
            }
            else {
                if (checkIfCapturable(leftAttempedMove)) {
                    moves.add(new ChessMove(myPosition, leftAttempedMove, null));
                }
            }

        }

        ChessPosition upAttempedMove = new ChessPosition((myPosition.getRow()+1), (myPosition.getColumn()));
        if (inBounds(upAttempedMove)) {
            if (checkIfEmpty(upAttempedMove)) {
                moves.add(new ChessMove(myPosition, upAttempedMove, null));
            }
            else {
                if (checkIfCapturable(upAttempedMove)) {
                    moves.add(new ChessMove(myPosition, upAttempedMove, null));
                }
            }
        }

        ChessPosition downAttempedMove = new ChessPosition((myPosition.getRow()-1), (myPosition.getColumn()));
        if (inBounds(downAttempedMove)) {
            if (checkIfEmpty(downAttempedMove)) {
                moves.add(new ChessMove(myPosition, downAttempedMove, null));
            }
            else {
                if (checkIfCapturable(downAttempedMove)) {
                    moves.add(new ChessMove(myPosition, downAttempedMove, null));
                }
            }
        }
        ChessPosition upRightAttempedMove = new ChessPosition((myPosition.getRow() + 1), (myPosition.getColumn() + 1));
        if (inBounds(upRightAttempedMove)) {
            if (checkIfEmpty(upRightAttempedMove)) {
                moves.add(new ChessMove(myPosition, upRightAttempedMove, null));
            }
            else {
                if (checkIfCapturable(upRightAttempedMove)) {
                    moves.add(new ChessMove(myPosition, upRightAttempedMove, null));
                }
            }
        }

        ChessPosition downLeftAttempedMove = new ChessPosition((myPosition.getRow()-1), (myPosition.getColumn()-1));
        if (inBounds(downLeftAttempedMove)) {
            if (checkIfEmpty(downLeftAttempedMove)) {
                moves.add(new ChessMove(myPosition, downLeftAttempedMove, null));
            }
            else {
                if (checkIfCapturable(downLeftAttempedMove)) {
                    moves.add(new ChessMove(myPosition, downLeftAttempedMove, null));
                }
            }
        }

        ChessPosition upLeftAttempedMove = new ChessPosition((myPosition.getRow()-1), (myPosition.getColumn()+1));
        if (inBounds(upLeftAttempedMove)) {
            if (checkIfEmpty(upLeftAttempedMove)) {
                moves.add(new ChessMove(myPosition, upLeftAttempedMove, null));
            }
            else {
                if (checkIfCapturable(upLeftAttempedMove)) {
                    moves.add(new ChessMove(myPosition, upLeftAttempedMove, null));
                }
            }
        }

        ChessPosition downRightAttempedMove = new ChessPosition((myPosition.getRow()+1), (myPosition.getColumn()-1));
        if (inBounds(downRightAttempedMove)) {
            if (checkIfEmpty(downRightAttempedMove)) {
                moves.add(new ChessMove(myPosition, downRightAttempedMove, null));
            }
            else {
                if (checkIfCapturable(downRightAttempedMove)) {
                    moves.add(new ChessMove(myPosition, downRightAttempedMove, null));
                }
            }
        }
        return moves;
    }
    public Collection<ChessMove> calculateQueenMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        ChessPosition rightAttempedMove = new ChessPosition((myPosition.getRow()), (myPosition.getColumn() + 1));
        while (inBounds(rightAttempedMove)) {
            if (checkIfEmpty(rightAttempedMove)) {
                moves.add(new ChessMove(myPosition, rightAttempedMove, null));
            }
            else {
                if (checkIfCapturable(rightAttempedMove)) {
                    moves.add(new ChessMove(myPosition, rightAttempedMove, null));
                }
                break;
            }
            rightAttempedMove = new ChessPosition((rightAttempedMove.getRow()), (rightAttempedMove.getColumn()+1));
        }

        ChessPosition leftAttempedMove = new ChessPosition((myPosition.getRow()), (myPosition.getColumn()-1));
        while (inBounds(leftAttempedMove)) {
            if (checkIfEmpty(leftAttempedMove)) {
                moves.add(new ChessMove(myPosition, leftAttempedMove, null));
            }
            else {
                if (checkIfCapturable(leftAttempedMove)) {
                    moves.add(new ChessMove(myPosition, leftAttempedMove, null));
                }
                break;
            }
            leftAttempedMove = new ChessPosition((leftAttempedMove.getRow()), (leftAttempedMove.getColumn()-1));

        }

        ChessPosition upAttempedMove = new ChessPosition((myPosition.getRow()+1), (myPosition.getColumn()));
        while (inBounds(upAttempedMove)) {
            if (checkIfEmpty(upAttempedMove)) {
                moves.add(new ChessMove(myPosition, upAttempedMove, null));
            }
            else {
                if (checkIfCapturable(upAttempedMove)) {
                    moves.add(new ChessMove(myPosition, upAttempedMove, null));
                }
                break;
            }
            upAttempedMove = new ChessPosition((upAttempedMove.getRow()+1), (upAttempedMove.getColumn()));
        }

        ChessPosition downAttempedMove = new ChessPosition((myPosition.getRow()-1), (myPosition.getColumn()));
        while (inBounds(downAttempedMove)) {
            if (checkIfEmpty(downAttempedMove)) {
                moves.add(new ChessMove(myPosition, downAttempedMove, null));
            }
            else {
                if (checkIfCapturable(downAttempedMove)) {
                    moves.add(new ChessMove(myPosition, downAttempedMove, null));
                }
                break;
            }
            downAttempedMove = new ChessPosition((downAttempedMove.getRow()-1), (downAttempedMove.getColumn()));
        }
        ChessPosition upRightAttempedMove = new ChessPosition((myPosition.getRow() + 1), (myPosition.getColumn() + 1));
        while (inBounds(upRightAttempedMove)) {
            if (checkIfEmpty(upRightAttempedMove)) {
                moves.add(new ChessMove(myPosition, upRightAttempedMove, null));
            }
            else {
                if (checkIfCapturable(upRightAttempedMove)) {
                    moves.add(new ChessMove(myPosition, upRightAttempedMove, null));
                }
                break;
            }
            upRightAttempedMove = new ChessPosition((upRightAttempedMove.getRow()+1), (upRightAttempedMove.getColumn()+1));
        }

        ChessPosition downLeftAttempedMove = new ChessPosition((myPosition.getRow()-1), (myPosition.getColumn()-1));
        while (inBounds(downLeftAttempedMove)) {
            if (checkIfEmpty(downLeftAttempedMove)) {
                moves.add(new ChessMove(myPosition, downLeftAttempedMove, null));
            }
            else {
                if (checkIfCapturable(downLeftAttempedMove)) {
                    moves.add(new ChessMove(myPosition, downLeftAttempedMove, null));
                }
                break;
            }
            downLeftAttempedMove = new ChessPosition((downLeftAttempedMove.getRow()-1), (downLeftAttempedMove.getColumn()-1));

        }

        ChessPosition upLeftAttempedMove = new ChessPosition((myPosition.getRow()-1), (myPosition.getColumn()+1));
        while (inBounds(upLeftAttempedMove)) {
            if (checkIfEmpty(upLeftAttempedMove)) {
                moves.add(new ChessMove(myPosition, upLeftAttempedMove, null));
            }
            else {
                if (checkIfCapturable(upLeftAttempedMove)) {
                    moves.add(new ChessMove(myPosition, upLeftAttempedMove, null));
                }
                break;
            }
            upLeftAttempedMove = new ChessPosition((upLeftAttempedMove.getRow()-1), (upLeftAttempedMove.getColumn()+1));
        }

        ChessPosition downRightAttempedMove = new ChessPosition((myPosition.getRow()+1), (myPosition.getColumn()-1));
        while (inBounds(downRightAttempedMove)) {
            if (checkIfEmpty(downRightAttempedMove)) {
                moves.add(new ChessMove(myPosition, downRightAttempedMove, null));
            }
            else {
                if (checkIfCapturable(downRightAttempedMove)) {
                    moves.add(new ChessMove(myPosition, downRightAttempedMove, null));
                }
                break;
            }
            downRightAttempedMove = new ChessPosition((downRightAttempedMove.getRow()+1), (downRightAttempedMove.getColumn()-1));
        }


        return moves;
    }
    public Collection<ChessMove> calculateKnightMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        ChessPosition rightAttempedMove = new ChessPosition((myPosition.getRow()+2), (myPosition.getColumn()-1));
        if (inBounds(rightAttempedMove)) {
            if (checkIfEmpty(rightAttempedMove)) {
                moves.add(new ChessMove(myPosition, rightAttempedMove, null));
            }
            else {
                if (checkIfCapturable(rightAttempedMove)) {
                    moves.add(new ChessMove(myPosition, rightAttempedMove, null));
                }
            }
        }

        ChessPosition leftAttempedMove = new ChessPosition((myPosition.getRow()+2), (myPosition.getColumn()+1));
        if (inBounds(leftAttempedMove)) {
            if (checkIfEmpty(leftAttempedMove)) {
                moves.add(new ChessMove(myPosition, leftAttempedMove, null));
            }
            else {
                if (checkIfCapturable(leftAttempedMove)) {
                    moves.add(new ChessMove(myPosition, leftAttempedMove, null));
                }
            }

        }

        ChessPosition upAttempedMove = new ChessPosition((myPosition.getRow()+1), (myPosition.getColumn()+2));
        if (inBounds(upAttempedMove)) {
            if (checkIfEmpty(upAttempedMove)) {
                moves.add(new ChessMove(myPosition, upAttempedMove, null));
            }
            else {
                if (checkIfCapturable(upAttempedMove)) {
                    moves.add(new ChessMove(myPosition, upAttempedMove, null));
                }
            }
        }

        ChessPosition downAttempedMove = new ChessPosition((myPosition.getRow()-1), (myPosition.getColumn()+2));
        if (inBounds(downAttempedMove)) {
            if (checkIfEmpty(downAttempedMove)) {
                moves.add(new ChessMove(myPosition, downAttempedMove, null));
            }
            else {
                if (checkIfCapturable(downAttempedMove)) {
                    moves.add(new ChessMove(myPosition, downAttempedMove, null));
                }
            }
        }
        ChessPosition upRightAttempedMove = new ChessPosition((myPosition.getRow()-2), (myPosition.getColumn()+1));
        if (inBounds(upRightAttempedMove)) {
            if (checkIfEmpty(upRightAttempedMove)) {
                moves.add(new ChessMove(myPosition, upRightAttempedMove, null));
            }
            else {
                if (checkIfCapturable(upRightAttempedMove)) {
                    moves.add(new ChessMove(myPosition, upRightAttempedMove, null));
                }
            }
        }

        ChessPosition downLeftAttempedMove = new ChessPosition((myPosition.getRow()-2), (myPosition.getColumn()-1));
        if (inBounds(downLeftAttempedMove)) {
            if (checkIfEmpty(downLeftAttempedMove)) {
                moves.add(new ChessMove(myPosition, downLeftAttempedMove, null));
            }
            else {
                if (checkIfCapturable(downLeftAttempedMove)) {
                    moves.add(new ChessMove(myPosition, downLeftAttempedMove, null));
                }
            }
        }

        ChessPosition upLeftAttempedMove = new ChessPosition((myPosition.getRow()-1), (myPosition.getColumn()-2));
        if (inBounds(upLeftAttempedMove)) {
            if (checkIfEmpty(upLeftAttempedMove)) {
                moves.add(new ChessMove(myPosition, upLeftAttempedMove, null));
            }
            else {
                if (checkIfCapturable(upLeftAttempedMove)) {
                    moves.add(new ChessMove(myPosition, upLeftAttempedMove, null));
                }
            }
        }

        ChessPosition downRightAttempedMove = new ChessPosition((myPosition.getRow()+1), (myPosition.getColumn()-2));
        if (inBounds(downRightAttempedMove)) {
            if (checkIfEmpty(downRightAttempedMove)) {
                moves.add(new ChessMove(myPosition, downRightAttempedMove, null));
            }
            else {
                if (checkIfCapturable(downRightAttempedMove)) {
                    moves.add(new ChessMove(myPosition, downRightAttempedMove, null));
                }
            }
        }
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

        else if (piece.getPieceType() == ChessPiece.PieceType.PAWN && piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            return calculateWhitePawnMoves(board, myPosition);
        }
        else if (piece.getPieceType() == ChessPiece.PieceType.PAWN && piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            return calculateBlackPawnMoves(board, myPosition);
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
        return List.of();

    }


}