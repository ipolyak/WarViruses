/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameTools;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Игорь
 */
public class GameAreaParameters {
    public final static String Row[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    public final static String Column[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "k"};
    
    public final static int NUM_OF_COLUMNS = 10;
    public final static int NUM_OF_ROWS = 10;
    
    public final static String TICS = "Tic";
    public final static String TOES = "Toe";
    
    public static enum CELL_STATE {CELL_EMPTY, TIC_HERE, TOE_HERE, TIC_KILLED, TOE_KILLED};
        
    public static final Map<String, Map<String, CELL_STATE>> GAME_STATE_INIT;
    /*
    0 - cell empty
    1 - tic here
    2 - toe here
    3 - tic_killed
    4 - toe_killed
    */
    
    static { // Initialization of GAME_STATE_INIT constant
        Map<String, Map<String, CELL_STATE>> duplicate_GAME_STATE_INIT;
        duplicate_GAME_STATE_INIT = new HashMap<String, Map<String, CELL_STATE>>();
        
        for(int i = 0; i < NUM_OF_ROWS; i++) {
            Map<String, CELL_STATE> itemOfGameStateInit = new HashMap<String, CELL_STATE>();
            for(int j = 0; j < NUM_OF_COLUMNS; j++) {
                itemOfGameStateInit.put(Column[j], CELL_STATE.CELL_EMPTY);
            }
            
            duplicate_GAME_STATE_INIT.put(Row[i], itemOfGameStateInit);
        }
        
        // Init state:
        duplicate_GAME_STATE_INIT.get("1").replace("a", CELL_STATE.TIC_HERE);
        duplicate_GAME_STATE_INIT.get("10").replace("k", CELL_STATE.TOE_HERE);
        
        GAME_STATE_INIT = Collections.unmodifiableMap(duplicate_GAME_STATE_INIT);
    }
    
    public static CellDirects[][] Directs = new CellDirects[10][10];
    static {
        for(int i = 0; i < NUM_OF_ROWS; i++) {
            for(int j = 0; j < NUM_OF_COLUMNS; j++) {
                CellDirects c = new CellDirects();
                
                Directs[i][j] = c;
            }
        }
    }
    
    public static void clearDirects() {
        for(int i = 0; i < NUM_OF_ROWS; i++) {
            for(int j = 0; j < NUM_OF_COLUMNS; j++) {
                Directs[i][j].clearCellDirects();
            }
        }
    }
    
    public static void fillDirectsForCell(String group_name, int row, int col, Map<String, Map<String, CELL_STATE>> game_state) {
        int curr_iter = -1;
        
        if(group_name.equalsIgnoreCase(TICS)) {
            for(int i = row - 1; i <= row + 1; i++) {
                for(int j = col - 1; j <= col + 1; j++) {
                    if(i != row || j != col) {
                        curr_iter++;
                    }
                    
                    if((i != row || j != col) && i > 0 && i <= NUM_OF_ROWS && j >= 0 && j < NUM_OF_COLUMNS) {
                        if(game_state.get(Integer.toString(i)).get(Column[j]).equals(CELL_STATE.TOE_KILLED) ) {
                            Directs[row - 1][col].Directs[curr_iter].row = i;
                            Directs[row - 1][col].Directs[curr_iter].column = j;
                        }
                    }
                }
            }
        } else if(group_name.equalsIgnoreCase(TOES)) {
            for(int i = row - 1; i <= row + 1; i++) {
                for(int j = col - 1; j <= col + 1; j++) {
                    if(i != row || j != col) {
                        curr_iter++;
                    }
                             
                    if((i != row || j != col) &&  i > 0 && i <= NUM_OF_ROWS && j >= 0 && j < NUM_OF_COLUMNS) {
                         System.out.println(Integer.toString(i));
                         System.out.println(Column[j]);
                         System.out.println(game_state.get(Integer.toString(i)).get(Column[j]));
                         
                        if(game_state.get(Integer.toString(i)).get(Column[j]).equals(CELL_STATE.TIC_KILLED) ) {
                            Directs[row - 1][col].Directs[curr_iter].row = i;
                            Directs[row - 1][col].Directs[curr_iter].column = j;
                            System.out.println("5");
                        }
                    }
                }
            }
        }
        
        Directs[row - 1][col].DirectedWasCalced = true;
    }
    
    public static boolean isDirectExist(int row, int col) {
        for (int i = 0; i < 8; i++) {
            if (Directs[row - 1][col].Directs[i].row > 0 && Directs[row - 1][col].Directs[i].column > 0 && !Directs[row - 1][col].Directs[i].isDiscovered) {
                return true;
            }
        }

        Directs[row - 1][col].AllDirectsDiscovered = true;
        return false;
    }
    
    public static Direct getExistDirect(int row, int col) {
        for (int i = 0; i < 8; i++) {
            if (Directs[row - 1][col].Directs[i].row > 0 && Directs[row - 1][col].Directs[i].column > 0 && !Directs[row - 1][col].Directs[i].isDiscovered) {
                Directs[row - 1][col].Directs[i].isDiscovered = true;
                return Directs[row - 1][col].Directs[i];
            }
        }
        
        Direct d = new Direct();
        
        return d;
    }
    
    public static void markDirectDiscovered(int row, int col, int pos_direct) {
        Directs[row - 1][col].Directs[pos_direct].directDiscovered();
    }
}
