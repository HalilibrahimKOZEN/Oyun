package odev;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import static odev.Server.ConnectedClients;

/**
 *
 * @author ibrahim
 */
public class Server {

    static ServerSocket serverSocket;
    public static ServerThread RunThread;
    public static int port = 1500;
    public static boolean keepGoing;
    public static int uniqueId;
    static ArrayList<ServerGame> ConnectedClients;

    public Server(int port) {

        Server.port = port;
        Server.ConnectedClients = new ArrayList<ServerGame>();
    }

    public static void display(String msg) {

        System.out.println(msg);
    }

    public static synchronized void remove(int id) {

        for (int i = 0; i < ConnectedClients.size(); i++) {
            ServerGame ct = ConnectedClients.get(i);
            if (ct.id == id) {
                ct.close();
                ConnectedClients.remove(i);
                return;
            }

        }
    }

    public static void Set_Server(int port) {
        try {
            Server.serverSocket = new ServerSocket(port);
            Server.port = port;
            Server.ConnectedClients = new ArrayList<ServerGame>();
            RunThread = new ServerThread();
            RunThread.start();
        } catch (Exception e) {
        }

    }

    public static void start() {

        if (Server.serverSocket != null) {

            return;

        }

        try {
            Server.serverSocket = new ServerSocket(port);

            Server.ConnectedClients = new ArrayList<ServerGame>();
            Server.RunThread = new ServerThread();
            RunThread.start();

        } catch (Exception e) {
        }

    }

    public void stop() {

        if (Server.serverSocket.isClosed()) {

            return;

        }
        keepGoing = false;

        try {
            for (int i = ConnectedClients.size(); --i >= 0;) {
                ServerGame sG = ConnectedClients.get(i);
                sG.close();;
                ConnectedClients.remove(i);
            }

            Server.RunThread.interrupt();

            Server.serverSocket.close();

            Server.serverSocket = null;

        } catch (Exception e) {
        }

    }

    public static synchronized void broadcast(String message) {
        for (int i = ConnectedClients.size(); --i >= 0;) {
            ServerGame sG = ConnectedClients.get(i);
            if (!sG.writeMsg(message)) {

                ConnectedClients.remove(i);
                display("Disconnected Client " + sG.username + "remove from list");
            }
        }

    }

    public static void main(String[] args) {
        start();
    }
}

class ServerThread extends Thread {

    public void run() {

        try {

            while (!Server.serverSocket.isClosed()) {

                Server.display("server waiting " + Server.port + ".");

                Socket socket = Server.serverSocket.accept();
                ServerGame newClient = new ServerGame(socket);

                Server.ConnectedClients.add(newClient);

                newClient.start();
                //newClient.writeMsg("baglandı");

            }
            try {

                Server.serverSocket.close();

                for (int i = 0; i < Server.ConnectedClients.size(); i++) {
                    ServerGame game = Server.ConnectedClients.get(i);
                    game.close();

                }
            } catch (Exception e) {
                Server.display("execption perfection " + e);
            }

        } catch (IOException e) {
            String msg = new Date().toString() + "Exception satisfaction " + e + "\n";

            Server.display(msg);
        }

    }

}
