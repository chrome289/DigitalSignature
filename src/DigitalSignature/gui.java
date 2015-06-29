package DigitalSignature;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Siddharth on 29-06-2015.
 */
public class gui {

    private JPanel panel1;
    private JButton button1;
    private JButton button2;
    private JButton verify;
    public static JFrame frame;
    public gui() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jf = new JFileChooser();
                jf.setCurrentDirectory(new File("D:" + File.pathSeparator));
                int result = jf.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String filePath = String.valueOf(jf.getSelectedFile());
                    new sign().signit(filePath);
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jf = new JFileChooser();
                jf.setCurrentDirectory(new File("D:" + File.pathSeparator));
                int result = jf.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String filePath = String.valueOf(jf.getSelectedFile());
                    new verify().verifyit(filePath);
                }
            }
        });
    }


    public static void main(String[] args) {
        frame= new JFrame("Digital Signature");
        frame.setContentPane(new gui().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocation(100,50);
        frame.setVisible(true);
    }
}
