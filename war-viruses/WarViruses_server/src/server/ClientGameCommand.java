/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Игорь
 */
public class ClientGameCommand {
    private final String TICS = "Tic";
    private final String TOES = "Toe";
    
    public String command;
    public Object mutex;
    public String group_name;
    public int num_moves = 0;
    
    public ClientGameCommand(String _command, String _group_name) {
        command = _command;
        mutex = new Object();
        group_name = _group_name;
    }
}
