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
public class CellDirects {
    public Direct[] Directs;
    public boolean AllDirectsDiscovered = false;
    public boolean DirectedWasCalced = false;
    
    CellDirects() {
        Directs = new Direct[8];
        
        for (int i = 0; i < 8; i++) {
            Directs[i] = new Direct();
        }
    }

    public void clearCellDirects() {
        for(int i = 0; i < 8; i++) {
            Directs[i].clearDirect();
        }
        
        AllDirectsDiscovered = false;
        DirectedWasCalced = false;
    }
}
