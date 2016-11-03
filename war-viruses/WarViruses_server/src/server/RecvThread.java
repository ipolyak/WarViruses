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
    
    private void SendReplyToPlayer(String reply) {
        
    }
    
    public void WrongCommand() {
        SendReplyToPlayer("WC");
    }
    
      /*
       1. Commands of server:
       
           1.5 "SO" - Receive file is OK
           1.6 "SN" - Receive file is not OK
           1.7 "DO" - Disconnect from server is OK
           1.8 "DN" - Disconnect from server is not OK
           1.9 "WC" - Wrong Command
     */
       
    private void HandlerOfClient() {
        DataInputStream sdis = new DataInputStream(sis);
        
        String command_info = "";
        String command = "";
        try {
            command_info = sdis.readUTF();
            command = sdis.readUTF();
            
            if(command_info.equals("SI")) {

            } else if(command_info.equals("GI")) {
                
            } else {
                WrongCommand();
            }
        } catch (IOException ex) {
            Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
        }

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
