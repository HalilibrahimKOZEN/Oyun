package odev;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.DefaultListModel;

class Listen extends Thread {

}

public class Client {

    private Socket socket;
    private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;
    private ServerListenThread ListenThread;
    DefaultListModel defaultListModel;
    private String server;
    private int port;
    private String username;

    public Client(String server, int port, String username, DefaultListModel defaultListModel) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.defaultListModel = defaultListModel;
    }

    public boolean start() {
        try {
            socket = new Socket(this.server, this.port);
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
            this.ListenThread = new ServerListenThread();
            this.ListenThread.start();
            //String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
            //display(msg);
        } catch (Exception e) {
            display("Error connecting to server: " + e);
        }
        try {
            sOutput.writeObject(username);
        } catch (Exception e) {
            display("Error doing login: " + e);
            disconnect();
            return false;
        }
        return true;
    }

    public static void display(String msg) {

        System.out.println(msg);

    }

    public void sendMessage(String msg) {
        try {
            sOutput.writeObject(msg);
        } catch (Exception e) {
            display("Exception writing to server: " + e);
        }
    }

//    public void sendMessage(Object msg) {
//        try {
//            sOutput.writeObject(msg);
//        } catch (Exception e) {
//        }
//    }

    public void disconnect() {
        try {
            if (sInput != null) {
                sInput.close();
            }
            if (sOutput != null) {
                sOutput.close();
            }
            if (this.ListenThread != null) {
                this.ListenThread.interrupt();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
        }
    }

    class ServerListenThread extends Thread {

        public void run() {
            while (true) {
                try {
                    Object msg = sInput.readObject();
                    if (msg instanceof String) {
                        String message = msg.toString();
                        ClientGame.ThisGame.doOperations(message);
//                        String[] dizi = message.split(" ");
                        
                        

                        //ClientGame.doOperations(Integer.parseInt(dizi[0]), Integer.parseInt(dizi[1]));
//                        System.out.println(message);

                    }
                } catch (Exception e) {
                    display("Server Kapatıldı " + e);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 1500, "halilibrahim ", null);
        client.start();
        client.sendMessage("Halil Ibrahim");
    }

}
