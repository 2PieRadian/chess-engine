package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rook extends Piece {

    private final static int[] CANDIDATE_MOVES = {-8, -1, 1, 8};

    Rook(int coordinate, Alliance alliance) {
        super(coordinate, alliance);
    }

    private static boolean isFirstColumnExclusion(final int coordinate, final int offset) {
        return BoardUtils.FIRST_COLUMN[coordinate] && (offset == -1);
    }

    private static boolean isEighthColumnExclusion(final int coordinate, final int offset) {
        return BoardUtils.EIGHT_COLUMN[coordinate] && (offset == 1);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentOffset : CANDIDATE_MOVES) {
            int currentDestinationCoordinate = this.getCoordinate();

            while (BoardUtils.isValidTileCoordinate(currentDestinationCoordinate)) {
                // Check Column Exclusions
                if (isFirstColumnExclusion(currentDestinationCoordinate, currentOffset)
                        || isEighthColumnExclusion(currentDestinationCoordinate, currentOffset)) {
                    break;
                }

                currentDestinationCoordinate += currentOffset;

                if (BoardUtils.isValidTileCoordinate(currentDestinationCoordinate)) {
                    final Tile tileAtDestination = board.getTile(currentDestinationCoordinate);
                    if (tileAtDestination.isTileEmpty()) {
                        legalMoves.add(new Move.NormalMove(board, this, currentDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = tileAtDestination.getPiece();
                        if (this.getAlliance() != pieceAtDestination.getAlliance()) {
                            legalMoves.add(new Move.AttackMove(board, this, currentDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }

        return List.copyOf(legalMoves);
    }
}
