package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.UUID;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import entity.Entity;
import entity.Player;
import entity.NewPlayer;
import entity.PlayerServer;
import environment.EnvironmentManager;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxScreenRow;

    int FPS = 60;
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);

    Sound sound = new Sound();
    Sound se = new Sound();
    Thread gameThread;

    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    ArrayList<Entity> entityList = new ArrayList<>();
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    public boolean fullScreenOn = false;
    Config config = new Config(this);
    EnvironmentManager eManager = new EnvironmentManager(this);
    public int characterIndex = 0;
    public int gameState = 0;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int optionsState = 4;
    public final int gameOverState = 5;
    public UUID uuid = UUID.randomUUID();

    public int playerNum = 0;
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 10;
    Socket cSocket;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    ObjectInputStream inputStream;
    Object message;
    public ChatPanel chatPanel = new ChatPanel();
    HashMap<UUID, PlayerServer> players = new HashMap<>();

    HashMap<UUID, NewPlayer> playersDraws = new HashMap<>();
    // Server server = new Server();
    // ArrayList<Socket> clientss = server.clients;

    // SERVER
    // public ArrayList<Socket> clients = new ArrayList<>();
    // HashMap<Socket, String> clientNameList = new HashMap<Socket, String>();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.uuid = UUID.randomUUID();
        setLayout(new BorderLayout());// chat
        player.setUuid(this.uuid);
        new Thread(() -> connectToServer()).start();

        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JPanel gameWindow = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                GamePanel.this.paintComponent(g);
            }

        };

        gameWindow.setPreferredSize(new Dimension((int) (screenWidth * 0.8), screenWidth));
        gameWindow.setBackground(Color.WHITE);
        gameWindow.setDoubleBuffered(true);
        gameWindow.addKeyListener(keyH);
        gameWindow.setFocusable(true);
        add(gameWindow);
    }

    public void connectToServer() {
        try {
            cSocket = new Socket("localhost", 5000);
            System.out.println("Connected");
            oos = new ObjectOutputStream(cSocket.getOutputStream());
            ois = new ObjectInputStream(cSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void communicateWithServer() {
        try {
            while (true) {
                // Read data from the server
                Object data = ois.readObject();
                // TODO: handle the data
                System.out.println(data);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setupGame() {

        aSetter.setObject();
        aSetter.setNPC();
        eManager.setup();
        // playMusic(0);
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();

        // new Thread(() -> communicateWithServer()).start(); // Create a new thread to
        // handle communication
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();

                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                // System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public class ChatPanel extends JPanel {

        private JTextArea messageArea;
        private JTextField inputField;

        public ChatPanel() {
            setLayout(new BorderLayout());
            messageArea = new JTextArea();
            messageArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(messageArea);
            inputField = new JTextField();

            inputField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    sendMessage(inputField.getText());
                    inputField.setText("");
                }

            });

            add(scrollPane, BorderLayout.CENTER);
            add(inputField, BorderLayout.SOUTH);
            setPreferredSize(new Dimension(200, screenHeight));

        }

        public void sendMessage(String message) {
            messageArea.append("Me: " + message + "\n");

        }

        public void receiveMessage(String message) {
            messageArea.append(message + "\n");

        }
    }

    public void update() {

        if (gameState == playState) {
            player.update();

            Send sendObject = new Send(this.uuid, player.joined, player.worldX, player.worldY, player.direction,
                    player.getGameId()); // create the Send object to send
            sendObjectToAllClients(sendObject);

            Send mes = recieve();

            if (mes != null) {

                // TODO

                System.out.println(mes);
                for (HashMap.Entry<UUID, PlayerServer> entry : (mes).getPlayers().entrySet()) {
                    // PlayerServer player = entry.getValue();
                }
                players = (mes).getPlayersExceptMyself(uuid);
                for (HashMap.Entry<UUID, PlayerServer> entry : players.entrySet()) {
                    PlayerServer anotherPlayer = entry.getValue();

                    if (playersDraws.get(anotherPlayer.getUuid()) == null) {
                        NewPlayer newPlayer = new NewPlayer(this);
                        newPlayer.setUuid(anotherPlayer.getUuid());
                        newPlayer.worldX = anotherPlayer.getScreenX();
                        newPlayer.worldY = anotherPlayer.getScreenY();
                        newPlayer.color = anotherPlayer.getColor();
                        newPlayer.characterIndex = 1;
                        newPlayer.direction = anotherPlayer.getDirection();
                        newPlayer.joined = anotherPlayer.getColor();
                        playersDraws.put(newPlayer.getUuid(), newPlayer);

                    }
                    {
                        NewPlayer newPlayer = playersDraws.get(anotherPlayer.getUuid());
                        newPlayer.worldX = anotherPlayer.getScreenX();
                        newPlayer.worldY = anotherPlayer.getScreenY();
                        newPlayer.direction = anotherPlayer.getColor();
                        // newPlayer.color = anotherPlayer.getColor();
                        playersDraws.remove(newPlayer.getUuid());
                        playersDraws.put(newPlayer.getUuid(), newPlayer);

                    }
                }

                // Send recieved = (Send) message;
                // String colornpc = recieved.getPlayerColor();
                // int wX = recieved.getWorldXplayer();
                // int wY = recieved.getWorldYplayer();
                //// if (colornpc.equals("Purple")) {
                // npc[0].setLook("Purple");
                // npc[0].setWorldX(wX);
                // npc[0].setWorldY(wY);
                // } else if (colornpc.equals("Yellow")) {
                // npc[0].setLook("Yellow");
                // npc[0].setWorldX(wX);
                // npc[0].setWorldY(wY);
                // } else if (colornpc.equals("Green")) {
                // npc[0].setLook("Green");
                //// npc[0].setWorldX(wX);
                // npc[0].setWorldY(wY);
                /// }

                // if the player doesnt exist, create object and move to position.
                // if the player exist, move it
            }

            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].characterIndex = 1;

                    npc[i].update();
                }
            }

        }
        if (gameState == pauseState) {

        }
        if (gameState == dialogueState) {

        }

    }

    public void sendObjectToAllClients(Send sendObject) {

        try {

            oos.writeObject(sendObject);
            oos.flush();

        } catch (IOException e) {
            // handle exception
        }
    }

    public Send recieve() {

        try {

            return (Send) ois.readObject();
            // System.out.println("Received message from server: " + message);

        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            ois.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (gameState == titleState) {

            if (chatPanel.getParent() != null) {
                add(chatPanel);
                revalidate();
            }
            ui.draw(g2);
        } else if (gameState == playState) {
            if (chatPanel.getParent() == null) {
                add(chatPanel);
                revalidate();
            }
            for (HashMap.Entry<UUID, NewPlayer> entry : playersDraws.entrySet()) {
                NewPlayer playerA = entry.getValue();
                entityList.add(playerA);
                System.out.println(entry.toString() + "\n");
            }

            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }

            tileM.draw(g2);
            entityList.add(player);

            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            // for (int i = 0; i < entityList.size(); i++) {
            // entityList.remove(i);
            // }

            eManager.draw(g2);
            ui.draw(g2);

        }

        g2.dispose();
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}
