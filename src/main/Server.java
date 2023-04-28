package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import entity.ChatMessage;
import entity.Map;
import entity.PlayerServer;



public class Server {

    public static ArrayList<ClientThread> clients = new ArrayList<>();// ArrayList of clients
    private ServerSocket sSocket = null;

    public static final int SERVER_PORT = 12345;
    // For synchonizing client in the list
    public static Object lock = new Object();
    public static int counter = 1;
    private static ObjectInputStream ois = null;
    private static ObjectOutputStream oos = null;
    private String cName = "wisdom";
    public static HashMap<Integer, Map> maps = new HashMap<>();

    public static void main(String[] args) {

        new Server();
    }

    public Server() {
        // call the serverthread
        System.out.println("The Server Started");
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
                int gameNumber = 1;
                while (true) {
                    Socket socket = sSocket.accept();
                    System.out.println("Connection from " + socket + "!");

                    // ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    // Object message = ois.readObject();

                    // sendToAll(message);

                    ClientThread ct = new ClientThread(socket, "client:" + counter, gameNumber);
                    clients.add(ct);
                    System.out.println("Connection from " + "client " + counter + "!");
                    counter++;
                    ct.start();

                }
            } catch (SocketException SoException ) {
                SoException .printStackTrace();
                System.out.println("CLient has disconneted");
            }catch (IOException f) {
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
      
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        private int color = -1;
        private String cName = "";
        private int gameNumber = 1; 
        Map map;
        // constructor for the ClientThread
        public ClientThread(Socket cSocket, String name, int gameNumber) {
            this.cSocket = cSocket;
            this.cName = name;
            this.gameNumber = gameNumber;
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
                        int gameId = ((Send)obj).getGameId();
                      
                        Integer number = Integer.parseInt(gameId + "");
                        if(maps.size() == 0){
                            map = new Map(gameId);//had gameid in parenthesis
                            maps.put(number,map);

                        }else{
                            map = maps.get(number);
                        }

                        if(!map.checkIfPlayerUuidExists(((Send) obj).getUuid())){
                            if(!map.checkIfPlayerColorExists(((Send) obj).playerColor)){
                                PlayerServer player = new PlayerServer(((Send)obj).getUuid());
                                if(map.addPlayer(player)){
                                    //update position inint
                                    this.updatePostion(map,(Send)obj);

                                }else{
                                    
                                    //limit of player has been reached 
                                    ((Send)obj).setCode(-1);
                                    oos.writeObject(obj);

                                }
                            }else{
                                //Same color but different player 
                                ((Send)obj).setCode(-2);
                                oos.writeObject(obj);

                            }


                        }else{
                            // already connected
                           this.updatePostion(map, (Send) obj);
                            

                        }
                        oos.flush();
                        oos.reset();
                      //  Send playermovement = (Send) obj;
                        // loop to write to all clients
                        // System.out.println(playermovement);
                        // for all the players
                       // if (clients.size() >= 2) {

                      //      sendToAll(playermovement, this);
                      //  } else {
                    //        oos.writeObject(null);
                     //   }

                        // oos.writeObject(obj);

                        // write the object to another player
               }else if (obj instanceof ChatMessage){
                ChatMessage chatMessage = (ChatMessage ) obj;
                System.out.println(chatMessage.getSender() + " : " + chatMessage.getMessage());
                sendToAll(chatMessage, this);
               }

                }
            }  catch (SocketException SoException ) {
              
                System.out.println("disconnected is " + cName);
            }catch (IOException e) {
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

        private void updatePostion (Map map, Send obj) throws IOException{

            if (obj.getPlayerColor() != null){
                map.setPlayers(map.updateColor(obj.uuid, obj.playerColor));
            }
            map.setPlayers(map.updatePosition(obj.uuid, obj.worldXplayer,obj.worldYplayer, obj.direction));
            obj.setCode(200);//everything is alright 
            obj.setPlayers(map.getPlayers());

            oos.writeObject(obj);
        }

    }
   
        public void sendMessage(String message) throws IOException{

            oos.writeObject(new ChatMessage(cName, message));
            oos.flush();
            oos.reset();


        }
        public ChatMessage receiveMessage() throws IOException, ClassNotFoundException{

            Object obj = ois.readObject();

            if(obj instanceof ChatMessage){
                return (ChatMessage)obj;
            }
            return null;


        }
    // Method to send to all arrayList of the movemen
    public void sendToAll(Object obj, ClientThread clientThread) {

        for (ClientThread client : clients) {
            try {
                if (clientThread == client) {
                    client.oos.writeObject(obj);
                    client.oos.flush();
                    client.oos.reset();
                }

            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
    }// end of sendtoAll objects

}
