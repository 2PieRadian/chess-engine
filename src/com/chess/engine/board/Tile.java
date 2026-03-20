package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    // 'protected' - for future extensibility
    protected final int coordinate;

    private final static Map<Integer, EmptyTile> EMPTY_TILES = getAllEmptyTiles();

    private static Map<Integer, EmptyTile> getAllEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for (int i = 0; i < 64; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return Map.copyOf(emptyTileMap);
    }

    // Factory Method - because we need dynamic object type which we can't do inside fixed return Type inside a Constructor
    public static Tile createTile(int coordinate, Piece piece) {
        return piece != null ? new OccupiedTile(coordinate, piece) : EMPTY_TILES.get(coordinate);
    }

    private Tile(int coordinate) {
        this.coordinate = coordinate;
    }

    public abstract boolean isTileEmpty();
    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile {
        // 'final' - guarantees that 'coordinate' can't be modified inside this method
        EmptyTile(final int coordinate) {
            super(coordinate);
        }

        @Override
        public boolean isTileEmpty() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    public static final class OccupiedTile extends Tile {
        private Piece piece;

        OccupiedTile(int coordinate, Piece piece) {
            super(coordinate);
            this.piece = piece;
        }

        @Override
        public boolean isTileEmpty() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return piece;
        }
    }
}
