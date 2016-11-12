/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameTools;

import java.util.Map;
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
    
    public static boolean GameIsEnd(int num_tics, int num_toes) {
        if(num_tics == 0 || num_toes == 0)
            return true;
        
        return false;
    }
    
    public static String WhoWin(int num_tics, int num_toes) {
        if(num_tics == 0) {
            return TOES;
        } else if(num_toes == 0) {
            return TICS;
        }
        
        return "ERROR";
    }
    
    public static boolean IsCellAvailable(String group_name, String row, String column, Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState) {
        if(group_name.equals(TICS)) {
            return IsCellAvailableForTics(row, column, GameState);
        } else if(group_name.equals(TOES)) {
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
    
    public static boolean IsTicInSurround(String row, String column, Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState) {
        int row_int = Integer.parseInt(row);
        int column_int = LetterToNumber(column);

        if (!IsBorderInSurround(row, column)) {
            for (int i = row_int - 1; i <= row_int + 1; i++) {
                for (int j = column_int - 1; j <= column_int + 1; j++) {
                    if (i != row_int || j != column_int) {
                        String curr_row = Integer.toString(i);
                        String curr_column = NumberToLetter(j);
                                            System.out.println(curr_row);
                    System.out.println(curr_column);
                        if (GameState.get(curr_row).get(curr_column).equals(GameAreaParameters.CELL_STATE.TIC_HERE) &&
                           (GameState.get(row).get(column).equals(GameAreaParameters.CELL_STATE.CELL_EMPTY) || 
                            GameState.get(row).get(column).equals(GameAreaParameters.CELL_STATE.TOE_HERE))) {
                            return true;
                        }
                    }
                }
            }

            // Try to move over killed toes (to determine availability of cell)
            for (int i = row_int - 1; i <= row_int + 1; i++) {
                for (int j = column_int - 1; j <= column_int + 1; j++) {
                    String curr_row = Integer.toString(i);
                    String curr_column = NumberToLetter(j);

                    if (GameState.get(curr_row).get(curr_column).equals(GameAreaParameters.CELL_STATE.TOE_KILLED)
                            && (GameState.get(row).get(column).equals(GameAreaParameters.CELL_STATE.CELL_EMPTY)
                            || GameState.get(row).get(column).equals(GameAreaParameters.CELL_STATE.TOE_HERE))) {
                        return IsTicInSurround(curr_row, curr_column, GameState);
                    }
                }
            }

        } else {
            return true;
        }

        return false;
    }
    
    public static int LetterToNumber(String column) {
        for(int i = 0; i < GameAreaParameters.NUM_OF_COLUMNS; i++)
            if(GameAreaParameters.Column[i].equals(column)) {
                return i;
            }
        
        return 0;
    }
    
    public static String NumberToLetter(int num) {
        return GameAreaParameters.Column[num];
    }
    
    public static boolean IsToeInSurround(String row, String column, Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState) {
        int row_int = Integer.parseInt(row);
        int column_int = LetterToNumber(column);
        
        if (!IsBorderInSurround(row, column)) {
            for (int i = row_int - 1; i <= row_int + 1; i++) {
                for (int j = column_int - 1; j <= column_int + 1; j++) {
                    if (i != row_int || j != column_int) {
                        String curr_row = Integer.toString(i);
                        String curr_column = NumberToLetter(j);
                        if (GameState.get(curr_row).get(curr_column).equals(GameAreaParameters.CELL_STATE.TOE_HERE) &&
                           (GameState.get(row).get(column).equals(GameAreaParameters.CELL_STATE.CELL_EMPTY) || 
                            GameState.get(row).get(column).equals(GameAreaParameters.CELL_STATE.TIC_HERE))) {
                            return true;
                        }
                    }
                }
            }
            
            // Try to move over killed tics (to determine availability of cell)
            for (int i = row_int - 1; i <= row_int + 1; i++) {
                for (int j = column_int - 1; j <= column_int + 1; j++) {
                    String curr_row = Integer.toString(i);
                    String curr_column = NumberToLetter(j);
                    if (GameState.get(curr_row).get(curr_column).equals(GameAreaParameters.CELL_STATE.TIC_KILLED)
                            && (GameState.get(row).get(column).equals(GameAreaParameters.CELL_STATE.CELL_EMPTY)
                            || GameState.get(row).get(column).equals(GameAreaParameters.CELL_STATE.TIC_HERE))) {
                        return IsToeInSurround(curr_row, curr_column, GameState);
                    }
                }
            }

        } else {
            return true;
        }

        return false;
    }
    
    public static boolean IsBorderInSurround(String row, String column) {
        int row_int = Integer.parseInt(row);
        int column_int = LetterToNumber(column);
        
        if(row_int - 1 > 0 && row_int - 1 <= GameAreaParameters.NUM_OF_ROWS &&
           column_int - 1 > 0 && column_int - 1 <= GameAreaParameters.NUM_OF_COLUMNS) {
            return false;
        }
        
        return true;
    }
    
    public static String WhoseTurn() {
        if(num_turn < 3  && AvailableCellExist(group_active)) {
            return group_active;
        } else if(AvailableCellExist(InvertGroup(group_active))){
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
        if(_group_name.equals(TICS)) {
            return TOES;
        } else {
            return TICS;
        }
    }
    
    private static boolean AvailableCellExist(String _group_name) {
        return true;
    }
}
