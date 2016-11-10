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
        
        GAME_STATE_INIT = Collections.unmodifiableMap(duplicate_GAME_STATE_INIT);
    }
}
