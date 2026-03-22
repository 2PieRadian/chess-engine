package com.chess.engine.board;

public class BoardUtils {

    private BoardUtils() {}

    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < 64;
    }

}
