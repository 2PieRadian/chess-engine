package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends Piece {

    private final static int[] CANDIDATE_MOVES = {-9, -8, -7, -1, 1, 7 ,8, 9};

    Queen(int coordinate, Alliance alliance) {
        super(coordinate, alliance);
    }

    private static boolean isFirstColumnExclusion(final int coordinate, final int offset) {
        return BoardUtils.FIRST_COLUMN[coordinate] && (offset == -9 || offset == -1 || offset == 7);
    }

    private static boolean isEighthColumnExclusion(final int coordinate, final int offset) {
        return BoardUtils.EIGHT_COLUMN[coordinate] && (offset == -7 || offset == 1 || offset == 9);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentOffset : CANDIDATE_MOVES) {
            int destinationCoordinate = this.getCoordinate();

            while (true) {
                if (isFirstColumnExclusion(destinationCoordinate, currentOffset)
                        || isEighthColumnExclusion(destinationCoordinate, currentOffset))
                    break;

                destinationCoordinate += currentOffset;
                if (!BoardUtils.isValidTileCoordinate(destinationCoordinate))
                    break;

                final Tile tileAtDestination = board.getTile(destinationCoordinate);
                if (tileAtDestination.isTileEmpty()) {
                    legalMoves.add(new Move.NormalMove(board, this, destinationCoordinate));
                } else {
                    Piece pieceAtDestination = tileAtDestination.getPiece();
                    if (pieceAtDestination.getAlliance() != this.getAlliance())
                        legalMoves.add(new Move.AttackMove(board, this, destinationCoordinate, pieceAtDestination));

                    break;
                }
            }
        }

        return List.copyOf(legalMoves);
    }
}