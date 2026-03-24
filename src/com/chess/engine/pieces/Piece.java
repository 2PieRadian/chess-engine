package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    private final int coordinate;
    private final Alliance alliance;

    Piece (final int coordinate, final Alliance alliance) {
        this.coordinate = coordinate;
        this.alliance = alliance;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public int getCoordinate() {
        return coordinate;
    }

    public Alliance getAlliance() {
        return alliance;
    }
}
