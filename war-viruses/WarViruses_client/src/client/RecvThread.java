/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import LogTools.Log;

/**
 *
 * @author Игорь
 */
public class RecvThread extends Thread {
    final String MY_NAME = "RecvThread";
    final int MAX_ROW_NUM = 10;
    final int MAX_COLUMN_NUM = 10;
    
    JTextArea Logs = null;
    Socket cs = null;
    InputStream cis = null;
    boolean IsConnect = true;
    JTable table;

    public RecvThread(Socket _cs,
                      JTextArea _Logs,
                      JTable _table) {
        
        cs = _cs;
        Logs = _Logs;
        table = _table;
        
        if (cs != null) {
            try {
                cis = cs.getInputStream(); // Get the intput stream. Now we may receive the data from server
            } catch (IOException ex) {
                Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, "Error of getting intput stream", ex);
            }
        }
    }

    @Override
    public void run() {
        while (IsConnect) {
            

               /* DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setValueAt(name, table_size, 0);
                model.setValueAt(path_to_file, table_size, 1);
                table.setModel(model);*/
            }
           
        }
        
                    
    }
