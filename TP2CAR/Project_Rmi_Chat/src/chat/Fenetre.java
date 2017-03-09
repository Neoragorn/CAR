package chat;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2010</p>
 * <p>Société : </p>
 * @author non attribuable
 * @version 1.0
 */


import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class Fenetre
    extends JPanel{
    private String image = "C:/Documents and Settings/MZT/jbproject/Chat/classes/chat/Images/cadre.png";
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(Toolkit.getDefaultToolkit().getImage(image),0,0,this);
    }
    public Fenetre(){
      super();
    }
}
