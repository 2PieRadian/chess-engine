package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    /**
     * The coordinate of the tile on the board.
     * Marked as 'protected' to allow access in subclasses for extensibility.
     */
    protected final int coordinate;

    private final static Map<Integer, EmptyTile> EMPTY_TILES_CACHE = getAllEmptyTiles();

    private static Map<Integer, EmptyTile> getAllEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for (int i = 0; i < 64; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return Map.copyOf(emptyTileMap); // Immutable Map
    }

    public static Tile createTile(int coordinate, Piece piece) {
        return piece != null ? new OccupiedTile(coordinate, piece) : EMPTY_TILES_CACHE.get(coordinate);
    }

    private Tile(int coordinate) {
        this.coordinate = coordinate;
    }

    public abstract boolean isTileEmpty();
    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile {
        private EmptyTile(final int coordinate) {
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
        private final Piece piece;

        private OccupiedTile(int coordinate, Piece piece) {
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
