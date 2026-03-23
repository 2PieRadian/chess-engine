package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVES = {-17, -15, -10, -6, 6, 10, 15, 17};

    private final static boolean[] FIRST_COLUMN = initColumn(0);
    private final static boolean[] SECOND_COLUMN = initColumn(1);
    private final static boolean[] SEVENTH_COLUMN = initColumn(6);
    private final static boolean[] EIGHT_COLUMN = initColumn(7);

    Knight(final int coordinate, final Alliance alliance) {
        super(coordinate, alliance);
    }

    private static boolean[] initColumn(int columnNumber) {
        boolean[] column = new boolean[64];
        Arrays.fill(column, false);

        while (columnNumber < 64) {
            column[columnNumber] = true;
            columnNumber += 8;
        }

        return column;
    }

    private static boolean isFirstColumnExclusion(final int coordinate, final int offset) {
        return FIRST_COLUMN[coordinate] && (offset == -17 || offset == -10 || offset == 6 || offset == 15);
    }

    private static boolean isSecondColumnExclusion(final int coordinate, final int offset) {
        return SECOND_COLUMN[coordinate] && (offset == -10|| offset == 6);
    }

    private static boolean isSeventhColumnExclusion(final int coordinate, final int offset) {
        return SEVENTH_COLUMN[coordinate] && (offset == -6|| offset == 10);
    }

    private static boolean isEightColumnExclusion(final int coordinate, final int offset) {
        return EIGHT_COLUMN[coordinate] && (offset == -15 || offset ==  -6 || offset == 10 || offset == 17);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidate : CANDIDATE_MOVES) {
            final int candidateDestination = currentCandidate + getCoordinate();

            if (isFirstColumnExclusion(getCoordinate(), currentCandidate)) continue;
            if (isSecondColumnExclusion(getCoordinate(), currentCandidate)) continue;
            if (isSeventhColumnExclusion(getCoordinate(), currentCandidate)) continue;
            if (isEightColumnExclusion(getCoordinate(), currentCandidate)) continue;

            if (BoardUtils.isValidTileCoordinate(candidateDestination)) {
                Tile tileAtDestination = board.getTile(candidateDestination);

                if (tileAtDestination.isTileEmpty()) {
                    legalMoves.add(new Move()); // TODO
                } else {
                    final Piece destinationPiece = tileAtDestination.getPiece();
                    final Alliance destinationPieceAlliance = destinationPiece.getAlliance();

                    if (getAlliance() != destinationPieceAlliance) {
                        legalMoves.add(new Move()); // TODO
                    }
                }
            }
        }

        return legalMoves;
    }

}