package main;
import entity.PlayerServer;
import java.util.HashMap;
import java.io.Serializable;
import java.util.UUID;

/*Class which Contains player data
 * Sending Players UUID(Unique Id), Color,WorldX,WorldY,DirectionFaced and gameID
 */
public class Send implements Serializable {
    String playerColor;
    int worldXplayer;
    int worldYplayer;
    int taskPlayer; // task
    int gameId;
    String direction;
    int code = 0;
    public HashMap<UUID, PlayerServer> players; // hashMap from Player Server Class
    UUID uuid;//

    /**
     * Send constructor for Player Values 
     * @param uuid      randomly generated unique ID for each player
     * @param color     player color
     * @param worldx    worldx for player
     * @param worldy    worldY for player
     * @param Direction direction faced by player so the image is switched based on
     *                  key pressed
     * @param gameId    id for which current game is being played on the server
     */
    public Send(UUID uuid, String color, int worldx, int worldy, String direction, int gameId) {
        this.uuid = uuid;
        this.playerColor = color;
        this.worldXplayer = worldx;
        this.worldYplayer = worldy;
        this.gameId = gameId;
        this.players = new HashMap<UUID, PlayerServer>();
        this.direction = direction;

    }
    /**
     * Get the game ID 
     * @return gameID
     */
    public int getGameId() {
        return gameId;
    }
    /**
     * Setter for gameID
     * @param gameId
     */
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
    /**
     * Getter for direction
     * @return Direction faced
     */
    public String getDirection() {
        return direction;
    }
    /**
     * Set the direction the character faces
     * @param direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }
    /**
     * Getter for code
     * @return
     */
    public int getCode() {
        return code;
    }
    /**
     * Code for checking if element is repeated (Setter)
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }
    /**
     * Getter for Player from PlayerServer
     * @return
     */
    public HashMap<UUID, PlayerServer> getPlayers() {
        return players;
    }
    /**
     * Set Player from playerServer
     * @param players
     */
    public void setPlayers(HashMap<UUID, PlayerServer> players) {
        this.players = players;
    }
    /**
     * Getter for UUID
     * @return UUID
     */
    public UUID getUuid() {
        return uuid;
    }
    /**
     * Set UUID(unique id )
     * @param uuid
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    /**
     * Getter for player color
     * @return player Color
     */
    public String getPlayerColor() {
        return playerColor;
    }
    /**
     * Set the PLayer Color 
     * @param playerColor
     */
    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }
    /**
     * Getter for World Player X
     * @return WorldXplayer
     */
    public int getWorldXplayer() {
        return worldXplayer;
    }

    public void setWorldXplayer(int worldXplayer) {
        this.worldXplayer = worldXplayer;
    }
    /**
     * Getter for World Player Y
     * @return WorldYplayer
     */
    public int getWorldYplayer() {
        return worldYplayer;
    }
    /**
     * Setter for Player World Y
     * @param worldYplayer
     */
    public void setWorldYplayer(int worldYplayer) {
        this.worldYplayer = worldYplayer;
    }
    /**
     * Getter for Task PLayer
     * @return
     */
    public int getTaskPlayer() {
        return taskPlayer;
    }
    /**
     * Setter first task
     * @param taskPlayer
     */
    public void setTaskPlayer(int taskPlayer) {
        this.taskPlayer = taskPlayer;
    }


    /**
     * This method allow the user to get a HashMap of every player except one player specified based on UUID in the parameter 
     * @param uuid the parameter for the player to exclude 
     * @return hashmap of players not to exclude
     */
    public HashMap<UUID, PlayerServer> getPlayersExceptMyself(UUID uuid) {
        // CREATE NEW OBJECT FROM PLAYERSERVER CLASS
        HashMap<UUID, PlayerServer> result = new HashMap<>();

        // run a loop through the
        for (HashMap.Entry<UUID, PlayerServer> entry : players.entrySet()) {
            PlayerServer player = entry.getValue();
            // condition for if player does not equal the unique id in the parameter, add to result arraylist
            if (!player.getUuid().equals(uuid)) {
                result.put(player.getUuid(), player);
            }

        }
        return result;

    }
    /**ToString Method 
     * converting the object toString using color, worldx,worldy and adding which players are currently on the client to the arrayList 
    */
    @Override
    public String toString() {
    String result = "Send [playerColor=" + playerColor + ", worldXplayer=" + worldXplayer + ", worldYplayer=" + worldYplayer
                + "]";

                HashMap<UUID,PlayerServer> players = this.getPlayers();

                //printing out result from our entry set 
                for(HashMap.Entry<UUID,PlayerServer> entry : players.entrySet()){
                    PlayerServer player = entry.getValue();
                    result += "color: " + player.getColor() + "x: " + player.getScreenX() + "y: " + player.getScreenY();
                    
                  
                }

                return result;
    }
    public void setPlayerList(HashMap<UUID, PlayerServer> players2) {
    }
}
