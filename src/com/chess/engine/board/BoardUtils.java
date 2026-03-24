package com.chess.engine.board;

import java.util.Arrays;

public class BoardUtils {

    private BoardUtils() {}

    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < 64;
    }

    public final static boolean[] FIRST_COLUMN = initColumn(0);
    public final static boolean[] SECOND_COLUMN = initColumn(1);
    public final static boolean[] SEVENTH_COLUMN = initColumn(6);
    public final static boolean[] EIGHT_COLUMN = initColumn(7);

    private static boolean[] initColumn(int columnNumber) {
        boolean[] column = new boolean[64];
        Arrays.fill(column, false);

        while (columnNumber < 64) {
            column[columnNumber] = true;
            columnNumber += 8;
        }

        return column;
    }

}
