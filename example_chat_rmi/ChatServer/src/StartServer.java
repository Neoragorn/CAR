
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class StartServer {

    public static void main(String[] args) {
        try {
            //System.setSecurityManager(new RMISecurityManager());
            Registry registry = java.rmi.registry.LocateRegistry.createRegistry(4011);
            ChatServerInt skeleton = (ChatServerInt) UnicastRemoteObject.exportObject(new ChatServer(), 4011);
            registry.rebind("mini-chat", skeleton);
            System.out.println("[System] Chat Server is ready.");
        } catch (Exception e) {
            System.out.println("Chat Server failed: " + e);
        }
    }
}
