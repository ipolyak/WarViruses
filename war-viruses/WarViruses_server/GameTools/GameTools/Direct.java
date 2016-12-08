/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameTools;

/**
 *
 * @author Игорь
 */
public class Direct {
    public boolean isDiscovered;
    public int row;
    public int column;
    
    public Direct(int _row, int _col) {
        boolean isDiscovered = false;
        
        row = _row;
        column = _col;
    }
    
    public void directDiscovered() {
        isDiscovered = true;
    }
        
    public void clearDirect() {
        isDiscovered = false;
        
        row = -1;
        column = -1;
    }
}
