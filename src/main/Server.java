package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static ArrayList<ClientThread> clients = new ArrayList<>();// ArrayList of clients
    private ServerSocket sSocket = null;

    public static final int SERVER_PORT = 12345;
    // For synchonizing client in the list
    public static Object lock = new Object();
    public static int counter = 1;
    private static ObjectInputStream ois = null;
    private static ObjectOutputStream oos = null;

    public static void main(String[] args) {

        new Server();
    }

    public Server() {
        // call the serverthread
        System.out.println("The Server");
        ServerThread server;
        server = new ServerThread();
        server.start();

    }

    // server thread
    class ServerThread extends Thread {

        @Override
        public void run() {

            try {
                sSocket = new ServerSocket(5000);
                while (true) {
                    Socket socket = sSocket.accept();
                    System.out.println("Connection from " + socket + "!");

                    // ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    // Object message = ois.readObject();

                    // sendToAll(message);

                    ClientThread ct = new ClientThread(socket, "client:" + counter);
                    clients.add(ct);
                    System.out.println("Connection from " + "client " + counter + "!");
                    counter++;
                    ct.start();

                }
            } catch (IOException f) {
                f.printStackTrace();
                // } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
            } finally {
                try {
                    sSocket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

    }

    // client thread
    class ClientThread extends Thread {
        // Socket and name for clients
        private Socket cSocket = null;
        private String cName = "";
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        // constructor for the ClientThread
        public ClientThread(Socket cSocket, String name) {
            this.cSocket = cSocket;
            this.cName = name;
        }

        //

        // Run Method for Sending the
        @Override
        public void run() {
            // IO attributes

            try {
                // Instantiate the Object Streams
                oos = new ObjectOutputStream(cSocket.getOutputStream());
                ois = new ObjectInputStream(cSocket.getInputStream());

                // for receiving the movements
                while (true) {

                    Object obj = ois.readObject();

                    // check if objects sent is from send class
                    if (obj instanceof Send) {
                        // Create a Movement object and cast it as a mo
                        Send playermovement = (Send) obj;
                        // loop to write to all clients
                        // System.out.println(playermovement);
                        // for all the players
                        if (clients.size() >= 2) {

                            sendToAll(playermovement, this);
                        } else {
                            oos.writeObject(null);
                        }

                        // oos.writeObject(obj);

                        // write the object to another player
                    }

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    sSocket.close();
                    oos.close();
                    ois.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

    }

    // Method to send to all arrayList of the movemen
    public void sendToAll(Object obj, ClientThread clientThread) {

        for (ClientThread client : clients) {
            try {
                if (!clientThread.equals(client)) {
                    client.oos.writeObject(obj);
                    client.oos.flush();
                }

            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
    }// end of sendtoAll objects

}
