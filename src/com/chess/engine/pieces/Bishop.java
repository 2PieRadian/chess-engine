package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Bishop extends Piece {

    private final static int[] CANDIDATE_MOVES = {-9, -7, 7, 9};

    Bishop(int coordinate, Alliance alliance) {
        super(coordinate, alliance);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int offset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (offset == -9 || offset == 7);
    }

    private static boolean isEightColumnExclusion(final int currentPosition, final int offset) {
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (offset == -7 || offset == 9);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        List<Move> legalMoves = new ArrayList<>();

        for (final int currentOffset : CANDIDATE_MOVES) {
            int currentDestinationCoordinate = this.getCoordinate();

            while (BoardUtils.isValidTileCoordinate(currentDestinationCoordinate)) {
                if (isFirstColumnExclusion(currentDestinationCoordinate, currentOffset)
                        || isEightColumnExclusion(currentDestinationCoordinate, currentOffset)) {
                    continue;
                }

                currentDestinationCoordinate += currentOffset;
                // If the next move is LEGAL
                if (BoardUtils.isValidTileCoordinate(currentDestinationCoordinate)) {
                    // Check if the tile is empty
                    final Tile tileAtDestination = board.getTile(currentDestinationCoordinate);
                    if (tileAtDestination.isTileEmpty()) {
                        legalMoves.add(new Move.NormalMove(board, this, currentDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = tileAtDestination.getPiece();
                        if (this.getAlliance() != pieceAtDestination.getAlliance()) {
                            legalMoves.add(new Move.AttackMove(board, this, currentDestinationCoordinate, pieceAtDestination));
                        }
                        // Bishop cannot move further, as this piece blocks next diagonal movement
                        break;
                    }
                }
            }

        }

        return legalMoves;
    }
}
