package tp_secondpart_client;

import Frame.MyFrame;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyThread extends Thread {

    private boolean running = false;
    private boolean interreuptedCt = false;
    
    public MyThread() {
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void stopThread() {
        try {
            Client.getInstance().setMyThread(new MyThread());
        } catch (RemoteException | NotBoundException | NoSuchAlgorithmException ex) {
            Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        running = false;
        this.stop();
    }

    @Override
    public void run() {
        running = true;
        for (int seconds = 30; seconds >= 0; seconds--) {
            try {
                if (interreuptedCt)
                {
                    seconds = 30;
                    interreuptedCt = false;
                }
                if (seconds == 0) {
                    running = false;
                    System.out.println("Auto deconnect");
                    try {
                        Client.getInstance().getUser().getStub().Disonnect(Client.getInstance().getUser().getPseudo(), Client.getInstance().getUser().getClInter());
                        MyFrame.getInstance().quit();
                        Client.getInstance().setMyThread(new MyThread());
                        this.join();
                    } catch (RemoteException | NotBoundException | NoSuchAlgorithmException ex) {
                        Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                System.out.println(seconds);
                MyThread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isInterreuptedCt() {
        return interreuptedCt;
    }

    public void setInterreuptedCt(boolean interreuptedCt) {
        this.interreuptedCt = interreuptedCt;
    }
    
    
}
