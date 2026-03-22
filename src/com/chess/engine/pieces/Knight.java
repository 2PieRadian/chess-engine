package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVES = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(final int coordinate, final Alliance alliance) {
        super(coordinate, alliance);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        int candidateDestination;

        for (final int currentCandidate : CANDIDATE_MOVES) {
            candidateDestination = currentCandidate + getCoordinate();

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
