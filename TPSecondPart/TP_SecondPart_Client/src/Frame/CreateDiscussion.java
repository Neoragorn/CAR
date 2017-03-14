/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import Bean.DiscussionGroupBean;
import Bean.UserBean;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author sofian
 */
public class CreateDiscussion extends JPanel implements ActionListener {

    private JButton validation;
    static private JTextField TFTitre;
    static private JTextField TFDescription;
    static private String titre;
    static private String description;

    public CreateDiscussion() {
        setLayout(null);
        setPreferredSize(new Dimension(800, 400));

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setOpaque(false);

        validation = new JButton("Valid");
        TFTitre = new JTextField("titre");
        TFDescription = new JTextField("description");

        TFTitre.setBackground(new Color(100, 100, 100));
        TFTitre.setForeground(new Color(255, 255, 255));

        TFDescription.setBackground(new Color(100, 100, 100));
        TFDescription.setForeground(new Color(255, 255, 255));

        TFTitre.setBounds(200, 50, 200, 30);
        TFDescription.setBounds(200, 100, 200, 30);

        validation.setBounds(250, 200, 150, 20);
        validation.addActionListener(this);

        p1.add(TFTitre);
        p1.add(TFDescription);
        p1.add(validation);

        p1.setBounds(0, 0, 500, 300);

        add(p1);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Valid")) {
            titre = TFTitre.getText();
            description = TFDescription.getText();
            try {
                DiscussionGroupBean.getInstance().createDiscussion(UserBean.getInstance().getUser(), titre, description);
                MyFrame.getInstance().getSecondMyFrame().getFrame().dispose();
                MyFrame.getInstance().changeFrame(new Home());
            } catch (Exception err) {
                System.out.println(e);
            }
        }
    }
}
