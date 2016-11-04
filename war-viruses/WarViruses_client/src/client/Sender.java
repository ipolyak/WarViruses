/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.net.Socket;
import javax.swing.JTextArea;
import GameTools.GameAreaParameters;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import LogTools.Log;

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
    String group_name = "";
    
    OutputStream cos = null; // Client Output Stream
    
    public Sender(JTextArea _Logs, Socket _cs) {
        cs = _cs;
        Logs = _Logs;
        
        if(cs != null) {
            try {
                cos = cs.getOutputStream();
            } catch (IOException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void SetGroupName(String _group_name) {
        group_name = _group_name;
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
        if(cs != null) {
            DataOutputStream cdos = new DataOutputStream(cos);
            try {
                cdos.writeUTF(SERVICE_INFO);
                cdos.writeUTF(command);
                cdos.writeUTF(group_name);
                
                Log.AddToLog("Service command send", Logs, MY_NAME);
            } catch (IOException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    private void SendGameCommand(String command) {
        if(cs != null) {
            DataOutputStream cdos = new DataOutputStream(cos);
            try {
                cdos.writeUTF(GAME_INFO);
                cdos.writeUTF(command);
                Log.AddToLog("Game command send", Logs, MY_NAME);
            } catch (IOException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    private String ParseCommand(String command) {
        if (command.equals("CNG") ||
            command.equals("JG")  ||
            command.equals("DG")) {
            return SERVICE_INFO;
        } else {
            return ParseGameCommand(command);
        }
    }
    
    private String ParseGameCommand(String command) {
        String first_part = getSubstringOfGameCommand(command, ":", 0);
        String second_part = getSubstringOfGameCommand(command, ":", 1);
        boolean first_part_ok = false;
        boolean second_part_ok = false;
        
        int num_or_rows = GameAreaParameters.NUM_OF_ROWS;
        int num_or_columns = GameAreaParameters.NUM_OF_COLUMNS;
        
        String Row[] = GameAreaParameters.Row;
        String Column[] = GameAreaParameters.Column;
        

        for (int i = 0; i < num_or_rows; i++) {
            if (first_part.equals(Row[i])) {
                first_part_ok = true;
            }
        }

        for (int i = 0; i < num_or_columns; i++) {
            if (second_part.equals(Column[i])) {
                second_part_ok = true;
            }
        }

        if (first_part_ok && second_part_ok) {
            return GAME_INFO;
        }
        
        return "WRONG_INFO";
    }

    private String getSubstringOfGameCommand(String command, String delimeter, int action) {
        // если в строке command есть delimeter и он не является первым символом в строке command и action == 1 (берем то, что после delimeter стоит)
        if (command.lastIndexOf(delimeter) != -1 && command.lastIndexOf(delimeter) != 0 && action == 1) // то вырезаем все знаки после delimeter в command, то есть <first_part><delimeter><second_part> -> <second_part>
        {
            return command.substring(command.lastIndexOf(delimeter) + 1);
        } // -//- action == 0 (берем то, что перед delimeter стоит)
        else if (command.lastIndexOf(delimeter) != -1 && command.lastIndexOf(delimeter) != 0 && action == 0) { // то вырезаем все знаки после delimeter в command, то есть <first_part><delimeter><second_part> -> <first_part>
            return command.substring(0, command.lastIndexOf(delimeter));
        }
        
        return ""; // Bad case
    }

    public int SendCommand(String command) {
        if (ParseCommand(command).equals(SERVICE_INFO)) {
            SendServiceCommand(command);
        } else if(ParseCommand(command).equals(GAME_INFO)) {
            SendGameCommand(command);
        } else {
            return -1;
        }
        
        return 0;
    }
            
}
