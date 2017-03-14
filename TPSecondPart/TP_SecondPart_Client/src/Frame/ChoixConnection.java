/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import static Frame.ChampsSaisie.TFPseudo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChoixConnection extends JPanel implements ActionListener {

    JButton inscription;
    JButton connection;

    public ChoixConnection() {
        setLayout(null);
        setPreferredSize(new Dimension(500, 300));
        inscription = new JButton("Inscription");
        connection = new JButton("Connection");
        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setOpaque(false);

        p1.add(inscription);
        p1.add(connection);
        inscription.setBounds(70, 150, 150, 20);
        inscription.addActionListener(this);

        connection.setBounds(250, 150, 150, 20);
        connection.addActionListener(this);

        p1.setBounds(0, 0, 500, 300);
        add(p1);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Inscription")) {
            JPanel f = new ChampsSaisie();
            MyFrame.getInstance().changeFrame(f);
        }
        else if (e.getActionCommand().equals("Connection")) {
            JPanel f = new Connection();
            MyFrame.getInstance().changeFrame(f);
        }
    }
}
