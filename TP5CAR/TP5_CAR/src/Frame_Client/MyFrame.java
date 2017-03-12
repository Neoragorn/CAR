package Frame_Client;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyFrame extends JPanel {

    private JFrame frame = new JFrame("Messenger Connection");

    private MyFrame secondMyFrame;

    private JPanel actualPanel = null;

    public static MyFrame inst;

    public MyFrame() {
    }

    public MyFrame(String title) {
        this.frame.setTitle(title);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setSecondMyFrame(MyFrame secondMyFrame) {
        this.secondMyFrame = secondMyFrame;
    }

    public MyFrame getSecondMyFrame() {
        return secondMyFrame;
    }

    public static MyFrame getInst() {
        return inst;
    }

    public static void setInst(MyFrame inst) {
        MyFrame.inst = inst;
    }

    static public MyFrame getInstance() {
        if (inst == null) {
            inst = new MyFrame();
        }
        return inst;
    }

    public void setActualPanel(JPanel actualPanel) {
        this.actualPanel = actualPanel;
    }

    public void startPoint(JPanel jp) {
        frame.setContentPane(jp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.repaint();
        frame.revalidate();
        this.actualPanel = jp;
    }

    public void changeFrame(JPanel jp) {
        if (actualPanel != null) {
            actualPanel.removeAll();
        }
        frame.setContentPane(jp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.repaint();
        frame.revalidate();
        this.actualPanel = jp;
    }

    public void quit() {
        frame.dispose();
    }
    
    
}
