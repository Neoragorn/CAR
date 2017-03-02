/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp4_car;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sofian
 */
public class ServerTest {

    private tp4_car.Client client;
    private tp4_car.Client client2;
    private Server instance;

    public ServerTest() {
    }

    /**
     * Test of start method, of class Server.
     */
    @Test
    public void testStart() throws Exception {
        System.out.println("start");
        try {
            instance = new Server();
            instance.start();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Test
    public void testSend() throws Exception {
        System.out.println("Send");
        try {
            client = new Client("Neor", "123456", "Phrase de test");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
