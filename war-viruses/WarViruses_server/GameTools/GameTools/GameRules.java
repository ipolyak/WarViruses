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
        return IsTickInSurround(row, column, GameState) ? true : false;
    }
    
    public static boolean IsCellAvailableForToes(String row, String column, Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState) {
        return IsToeInSurround(row, column, GameState) ? true : false;
    }
    
    public static boolean IsTickInSurround(String row, String column, Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState) {
        int row_int = Integer.parseInt(row);
        int column_int = LetterToNumber(column);

        if (!IsBorderInSurround(row, column)) {
            for (int i = row_int - 1; i < row_int + 1; i++) {
                for (int j = column_int - 1; j < column_int + 1; j++) {
                    String curr_row = Integer.toString(i);
                    String curr_column = Integer.toString(j);
                    if (GameState.get(curr_row).get(curr_column).equals(GameAreaParameters.CELL_STATE.TIC_HERE)) {
                        return true;
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
                return i + 1;
            }
        
        return 0;
    }
    
    public static boolean IsToeInSurround(String row, String column, Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState) {
        int row_int = Integer.parseInt(row);
        int column_int = LetterToNumber(column);
        
        if (!IsBorderInSurround(row, column)) {
            for (int i = row_int - 1; i < row_int + 1; i++) {
                for (int j = column_int - 1; j < column_int + 1; j++) {
                    String curr_row = Integer.toString(i);
                    String curr_column = Integer.toString(j);
                    if (GameState.get(curr_row).get(curr_column).equals(GameAreaParameters.CELL_STATE.TOE_HERE)) {
                        return true;
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
}
