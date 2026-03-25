package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.*;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVES = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(final int coordinate, final Alliance alliance) {
        super(coordinate, alliance);
    }

    private static boolean isFirstColumnExclusion(final int coordinate, final int offset) {
        return BoardUtils.FIRST_COLUMN[coordinate] && (offset == -17 || offset == -10 || offset == 6 || offset == 15);
    }

    private static boolean isSecondColumnExclusion(final int coordinate, final int offset) {
        return BoardUtils.SECOND_COLUMN[coordinate] && (offset == -10 || offset == 6);
    }

    private static boolean isSeventhColumnExclusion(final int coordinate, final int offset) {
        return BoardUtils.SEVENTH_COLUMN[coordinate] && (offset == -6 || offset == 10);
    }

    private static boolean isEightColumnExclusion(final int coordinate, final int offset) {
        return BoardUtils.EIGHT_COLUMN[coordinate] && (offset == -15 || offset ==  -6 || offset == 10 || offset == 17);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidate : CANDIDATE_MOVES) {
            final int piecePosition = getCoordinate();
            final int candidateDestination = currentCandidate + piecePosition;

            if (isFirstColumnExclusion(piecePosition, currentCandidate)
                    || isSecondColumnExclusion(piecePosition, currentCandidate)
                    || isSeventhColumnExclusion(piecePosition, currentCandidate)
                    || isEightColumnExclusion(piecePosition, currentCandidate))  {
                continue;
            }

            if (BoardUtils.isValidTileCoordinate(candidateDestination)) {
                final Tile tileAtDestination = board.getTile(candidateDestination);

                if (tileAtDestination.isTileEmpty()) {
                    legalMoves.add(new NormalMove(board, this, candidateDestination));
                } else {
                    final Piece destinationPiece = tileAtDestination.getPiece();
                    final Alliance destinationPieceAlliance = destinationPiece.getAlliance();

                    if (getAlliance() != destinationPieceAlliance) {
                        legalMoves.add(new AttackMove(board, this, candidateDestination, destinationPiece));
                    }
                }
            }
        }

        return List.copyOf(legalMoves);
    }
}