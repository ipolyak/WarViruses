/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;
import javax.swing.JTextArea;

/**
 *
 * @author Игорь
 */
public class Log {
    static void AddToLog(String info,
                         JTextArea jTextArea,
                         String my_name) {
        
        String curr_info = jTextArea.getText();
        curr_info += my_name + ": " + info + "\n";
        jTextArea.setText(curr_info);
    }
}