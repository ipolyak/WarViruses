/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import LogTools.Log;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author Игорь
 */
public class Sender {

    final String MY_NAME = "Sender";
    private final String TICS = "Tic";
    private final String TOES = "Toe";
    
    JTextArea Logs;
    Socket cs;
    String group_name = "";
    
    OutputStream cos = null; // Client Output Stream

    public Sender(JTextArea _Logs, Socket _cs, String _group_name) {
        cs = _cs;
        Logs = _Logs;
        group_name = _group_name;

        if (cs != null) {
            try {
                cos = cs.getOutputStream();
            } catch (IOException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int SendCommand(String command) {
        if (group_name.equals(TICS)) {
            SendCommandToTics(command);
        } else if(group_name.equals(TOES)) {
            SendCommandToToes(command);
        } else {
            return -1;
        }
        
        return 0;
    }
    
    public void SendCommandToTics(String command) {
    }
    
    public void SendCommandToToes(String command) {
    }
}
