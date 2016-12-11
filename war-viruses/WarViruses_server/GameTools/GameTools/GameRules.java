/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameTools;

import java.util.Map;
import java.util.Stack;
import GameTools.GameAreaParameters;

/**
 *
 * @author Игорь
 */
public class GameRules {

    private static final String TICS = "Tic";
    private static final String TOES = "Toe";

    public static int num_turn = 0;
    private static String group_active = "Tic";
    private static Stack<CellCoord> cells_bypass = new Stack<CellCoord>();

    public static boolean GameIsEnd(int num_tics, int num_toes) {
        if (num_tics == 0 || num_toes == 0) {
            return true;
        }

        return false;
    }

    public static String WhoWin(int num_tics, int num_toes) {
        if (num_tics == 0) {
            return TOES;
        } else if (num_toes == 0) {
            return TICS;
        }

        return "ERROR";
    }

    public static boolean IsCellAvailable(String group_name, String row, String column, Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState) {
        if (group_name.equals(TICS)) {
            return IsCellAvailableForTics(row, column, GameState);
        } else if (group_name.equals(TOES)) {
            return IsCellAvailableForToes(row, column, GameState);
        }

        return false;
    }

    public static boolean IsCellAvailableForTics(String row, String column, Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState) {
        return IsTicInSurround(row, column, GameState) ? true : false;
    }

    public static boolean IsCellAvailableForToes(String row, String column, Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState) {
        return IsToeInSurround(row, column, GameState) ? true : false;
    }

    public static boolean CheckTic8(String row, String column, Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState) {
        int row_int = Integer.parseInt(row);
        int column_int = LetterToNumber(column);

        for (int i = row_int - 1; i <= row_int + 1; i++) {
            for (int j = column_int - 1; j <= column_int + 1; j++) {
                if (i != row_int || j != column_int) {
                    String curr_row = Integer.toString(i);
                    String curr_column = NumberToLetter(j);
                    if (GameState.get(curr_row).get(curr_column).equals(GameAreaParameters.CELL_STATE.TIC_HERE)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean CheckToe8(String row, String column, Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState) {
        int row_int = Integer.parseInt(row);
        int column_int = LetterToNumber(column);

        for (int i = row_int - 1; i <= row_int + 1; i++) {
            for (int j = column_int - 1; j <= column_int + 1; j++) {
                if ((i != row_int || j != column_int)) {
                    String curr_row = Integer.toString(i);
                    String curr_column = NumberToLetter(j);
                    if (GameState.get(curr_row).get(curr_column).equals(GameAreaParameters.CELL_STATE.TOE_HERE)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean IsTicInSurround(String row, String column, Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState) {
        int row_int = Integer.parseInt(row);
        int column_int = LetterToNumber(column);

        for (int i = row_int - 1; i <= row_int + 1; i++) {
            for (int j = column_int - 1; j <= column_int + 1; j++) {
                if (i != row_int || j != column_int) {
                    String curr_row = Integer.toString(i);
                    String curr_column = NumberToLetter(j);
                    if (GameState.get(curr_row).get(curr_column).equals(GameAreaParameters.CELL_STATE.TIC_HERE)
                            && (GameState.get(row).get(column).equals(GameAreaParameters.CELL_STATE.CELL_EMPTY)
                            || GameState.get(row).get(column).equals(GameAreaParameters.CELL_STATE.TOE_HERE))) {
                        return true;
                    }
                }
            }
        }

        return MoveOverKilledToes(row, column, GameState);
    }

    public static boolean MoveOverKilledToes(String row, String column, Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState) {
        CellCoord CC = new CellCoord();
        int row_int = Integer.parseInt(row);
        int column_int = LetterToNumber(column);
        CC.row = row_int;
        CC.col = column_int;

        cells_bypass.push(CC);

        while (!cells_bypass.empty()) {
            CC = cells_bypass.peek();

            row_int = CC.row;
            column_int = CC.col;

            if (!(GameAreaParameters.Directs[row_int - 1][column_int].DirectedWasCalced)) {
                GameAreaParameters.fillDirectsForCell(GameAreaParameters.TICS, row_int, column_int, GameState);
            }

            if (GameAreaParameters.isDirectExist(row_int, column_int)) {
                Direct curr_direct = GameAreaParameters.getExistDirect(row_int, column_int);

                if (CheckTic8(Integer.toString(curr_direct.row), NumberToLetter(curr_direct.column), GameState)) {
                    GameAreaParameters.clearDirects();

                    return true;
                } else {
                    CellCoord C = new CellCoord();

                    C.row = curr_direct.row;
                    C.col = curr_direct.column;

                    cells_bypass.push(C);
                }
            } else {
                cells_bypass.remove(CC);
            }
        }
        /*for (int i = row_int - 1; i <= row_int + 1; i++) {
            for (int j = column_int - 1; j <= column_int + 1; j++) {
                if (i != row_int || j != column_int) {
                    String curr_row = Integer.toString(i);
                    String curr_column = NumberToLetter(j);
                    if (GameState.get(curr_row).get(curr_column).equals(GameAreaParameters.CELL_STATE.TIC_HERE)
                            && GameState.get(row).get(column).equals(GameAreaParameters.CELL_STATE.TOE_KILLED)) {
                        return true;
                    }
                }
            }
        }*/

        GameAreaParameters.clearDirects();

        return false;
    }

    public static boolean MoveOverKilledTics(String row, String column, Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState) {
        CellCoord CC = new CellCoord();
        int row_int = Integer.parseInt(row);
        int column_int = LetterToNumber(column);
        CC.row = row_int;
        CC.col = column_int;

        cells_bypass.push(CC);

        while (!cells_bypass.empty()) {
            CC = cells_bypass.peek();

            row_int = CC.row;
            column_int = CC.col;
            
            if (!(GameAreaParameters.Directs[row_int - 1][column_int].DirectedWasCalced)) {
                System.out.println("2");
                GameAreaParameters.fillDirectsForCell(GameAreaParameters.TOES, row_int, column_int, GameState);
              /*  for (int i = 0; i < 8; i++) {
                    System.out.println(GameAreaParameters.Directs[row_int - 1][column_int].Directs[i].row);
                    System.out.println(GameAreaParameters.Directs[row_int - 1][column_int].Directs[i].column);
                }*/
            }

            if (GameAreaParameters.isDirectExist(row_int, column_int)) {
                Direct curr_direct = GameAreaParameters.getExistDirect(row_int, column_int);

                System.out.println(curr_direct.row);
                System.out.println(curr_direct.column);

                if (CheckToe8(Integer.toString(curr_direct.row), NumberToLetter(curr_direct.column), GameState)) {
                    GameAreaParameters.clearDirects();

                    return true;
                } else {
                    CellCoord C = new CellCoord();

                    C.row = curr_direct.row;
                    C.col = curr_direct.column;

                    cells_bypass.push(C);
                }
            } else {
                System.out.println("3");
                cells_bypass.remove(CC);
            }
        }

        /* for (int i = row_int - 1; i <= row_int + 1; i++) {
            for (int j = column_int - 1; j <= column_int + 1; j++) {
                if (i != row_int || j != column_int) {
                    String curr_row = Integer.toString(i);
                    String curr_column = NumberToLetter(j);
                    if (GameState.get(curr_row).get(curr_column).equals(GameAreaParameters.CELL_STATE.TOE_HERE)
                            && (GameState.get(row).get(column).equals(GameAreaParameters.CELL_STATE.TIC_KILLED))) {
                        return true;
                    }
                }
            }
        }*/
        GameAreaParameters.clearDirects();

        return false;
    }

    public static int LetterToNumber(String column) {
        for (int i = 0; i < GameAreaParameters.NUM_OF_COLUMNS; i++) {
            if (GameAreaParameters.Column[i].equals(column)) {
                return i;
            }
        }

        return 0;
    }

    public static String NumberToLetter(int num) {
        return GameAreaParameters.Column[num];
    }

    public static boolean IsToeInSurround(String row, String column, Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState) {
        int row_int = Integer.parseInt(row);
        int column_int = LetterToNumber(column);

        for (int i = row_int - 1; i <= row_int + 1; i++) {
            for (int j = column_int - 1; j <= column_int + 1; j++) {
                if ((i != row_int || j != column_int)) {
                    String curr_row = Integer.toString(i);
                    String curr_column = NumberToLetter(j);
                    if (GameState.get(curr_row).get(curr_column).equals(GameAreaParameters.CELL_STATE.TOE_HERE)
                            && (GameState.get(row).get(column).equals(GameAreaParameters.CELL_STATE.CELL_EMPTY)
                            || GameState.get(row).get(column).equals(GameAreaParameters.CELL_STATE.TIC_HERE))) {
                        return true;
                    }
                }
            }
        }

        return MoveOverKilledTics(row, column, GameState);
    }

    public static String WhoseTurn() {
        if (num_turn < 3 && AvailableCellExist(group_active)) {
            return group_active;
        } else if (AvailableCellExist(InvertGroup(group_active))) {
            num_turn = 0;
            group_active = InvertGroup(group_active);
            return group_active;
        } else {
            return "DRAW";
        }
    }

    public static GameAreaParameters.CELL_STATE GetValue(String row, String col,
            Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState) {
        return GameState.get(row).get(col);
    }

    public static void KillTic(String row, String col, Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState) {
        GameState.get(row).replace(col, GameAreaParameters.CELL_STATE.TIC_KILLED);
    }

    public static void KillToe(String row, String col, Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState) {
        GameState.get(row).replace(col, GameAreaParameters.CELL_STATE.TOE_KILLED);
    }

    public static void OccupieCell(String row, String col, Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState, String group_name) {
        if (group_name.equals("Tic")) {
            GameState.get(row).replace(col, GameAreaParameters.CELL_STATE.TIC_HERE);
        } else {
            GameState.get(row).replace(col, GameAreaParameters.CELL_STATE.TOE_HERE);
        }
    }

    private static String InvertGroup(String _group_name) {
        if (_group_name.equals(TICS)) {
            return TOES;
        } else {
            return TICS;
        }
    }

    private static boolean AvailableCellExist(String _group_name) {
        return true;
    }
}
