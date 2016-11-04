/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Map;
import GameTools.GameRules;
import GameTools.GameAreaParameters;

import LogTools.Log;
import java.net.Socket;
import java.util.Hashtable;
import javax.swing.JTextArea;


/**
 *
 * @author Игорь
 */
public class GameThread extends Thread{
    private Map<String, Map<String, GameAreaParameters.CELL_STATE>> GameState = GameAreaParameters.GAME_STATE_INIT;
    private final String MY_NAME = "GameThread";
    private Hashtable<String, Socket> Players;
    
    JTextArea Logs;
    
    int num_toes = -1; // Current number of toes in game
    int num_tics = -1; // -//- tics
    
    String winner = "";
    
    public GameThread(JTextArea _Logs, Hashtable<String, Socket> _Players) {
        Logs = _Logs;
        Players = _Players;
    }
    
    @Override
    public void run() {
        while (!GameRules.GameIsEnd(num_tics, num_toes)) {

        }
        
        winner = GameRules.WhoWin(num_tics, num_toes);
    }
}
