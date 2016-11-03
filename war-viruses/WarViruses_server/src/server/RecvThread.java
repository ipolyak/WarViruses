/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import server.Log;
/**
 *
 * @author Игорь
 */
public class RecvThread extends Thread{
    final String MY_NAME = "RecvThread";
    
    Socket cs = null;
    InputStream sis = null;
    
    JTextArea Logs;
    boolean IsClientDisconnect = false;
    
    public RecvThread(Socket _cs,
                      JTextArea _Logs) {
        cs = _cs;
        Logs = _Logs;

        if (cs != null) {
            try {
                sis = cs.getInputStream(); // Get the output stream. Now we may receive the data from client
            } catch (IOException ex) {
                Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, "Error of getting intput stream", ex);
            }
        }
    }
    
    private void HandlerOfClient() {
        
    }
    
    @Override
    public void run() {
        while (true) {
            HandlerOfClient();
            if (IsClientDisconnect) {
                break;
            }
        }
    }

}
