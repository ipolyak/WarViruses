/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.net.Socket;
import javax.swing.JTextArea;

/**
 *
 * @author Игорь
 */
public class Sender {
    final String MY_NAME = "Sender";
    
    final String GAME_INFO = "GI";
    final String SERVICE_INFO = "SI";
    
    JTextArea Logs;
    
    Socket cs;
    
    public Sender(JTextArea _Logs, Socket _cs) {
        cs = _cs;
        Logs = _Logs;
    }
    
    /*
    Structure of message to server:
       1. Info about command (Sevice info or game info)
       2. Command
    
    LIST OF COMMANDS:
    1. Service Information (SI):
    
         1.1. CNG - Create new game
         1.2. JG - Join to existing game
         1.3. DG - Disconnect from game
    
    2. Game Information (GI):
         2.1 <num_row>:<num_column>
    */
    
    private void SendServiceCommand(String command) {
        
    }
    
    private void SendGameCommand(String command) {

    }

    private String ParseCommand(String command) {
        if (command == "CNG"
                || command == "JG"
                || command == "DG") {
            return SERVICE_INFO;
        }   else if(command )

    }

    public int SendCommand(String command) {
        if (ParseCommand(command) == SERVICE_INFO) {
            SendServiceCommand(command);
        } else if(ParseCommand(command) == GAME_INFO) {
            SendGameCommand(command);
        } else {
            return -1;
        }
        
        return 0;
    }
            
}
