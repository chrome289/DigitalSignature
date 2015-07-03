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
    private JTextArea textArea1;
    public static JFrame frame;

    public gui() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                JFileChooser jf = new JFileChooser();
                jf.setCurrentDirectory(new File("D:"));
                int result = jf.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    textArea1.append("\nSigning the file ...\n");
                    textArea1.setCaretPosition(textArea1.getDocument().getLength());
                    textArea1.paintImmediately(textArea1.getVisibleRect());
                    String filePath = String.valueOf(jf.getSelectedFile());
                    String temp = new sign().signit(filePath);
                    if(temp.charAt(0)!=' ') {
                        textArea1.append("File signed successfully\n");
                        textArea1.append("File Signature ---> \n" + temp + "\n");
                    }
                    else
                        textArea1.append(temp.substring(1)+"\n");
                    textArea1.setCaretPosition(textArea1.getDocument().getLength());
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jf = new JFileChooser();
                jf.setCurrentDirectory(new File("D:"));
                int result = jf.showOpenDialog(frame);
                boolean isTrue = false;
                if (result == JFileChooser.APPROVE_OPTION) {
                    textArea1.append("\nVerifying selected file ...\n");
                    textArea1.setCaretPosition(textArea1.getDocument().getLength());
                    String filePath = String.valueOf(jf.getSelectedFile());
                    JOptionPane.showMessageDialog(null, "Make sure the Public Key and Signature files are there in the same directory", "ALERT", JOptionPane.WARNING_MESSAGE);
                    isTrue = new verify().verifyit(filePath);
                    if (isTrue)
                        textArea1.append("File Signature verified\n");
                    else
                        textArea1.append("File Signature verification failed\n");
                    textArea1.setCaretPosition(textArea1.getDocument().getLength());
                }
            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("Digital Signature");
        frame.setContentPane(new gui().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 390);
        frame.setLocation(300, 250);
        frame.setVisible(true);
    }
}
