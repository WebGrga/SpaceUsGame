package entity;

import java.util.HashMap;
import java.util.UUID;


public class Map{
    


    public static int starLimit =3;

    public static int limit = 4;

    public int id;

    public HashMap<UUID,PlayerServer> players = new HashMap<>();

    public Map(int id) {
        this.id = id;
    }

    public HashMap<UUID, PlayerServer> getPlayers() {
        return players;
    }

    public void setPlayers(HashMap<UUID, PlayerServer> players) {
        this.players = players;
    }

    public boolean addPlayer(PlayerServer player){
        //as long as the hashmash is equal or below our number of players, keep adding 
        if(this.players.size() <= Map.limit){
            this.players.put(player.getUuid(),player);
            return true;
        }
        return false;
    }

    //check if playeruuid exist on the map 
    public boolean checkIfPlayerUuidExists(UUID uuid){
        for(HashMap.Entry<UUID,PlayerServer> entry : players.entrySet()){
            PlayerServer checkPlayer = entry.getValue();
            if(checkPlayer.getUuid().equals(uuid)){
                return true;
            }    
        }
        return false;

    }


    //check if playercolor is on the map
    public boolean checkIfPlayerColorExists(String color){
        for(HashMap.Entry<UUID,PlayerServer> entry : players.entrySet()){
            PlayerServer checkPlayer = entry.getValue();
            if(checkPlayer.color != null && checkPlayer.color.equals(color)){
                return true;
            }    
        }
        return false;
   
   
    }


    //method to update the color 
    public HashMap<UUID,PlayerServer> updateColor(UUID uuid,String color ){
        PlayerServer player = players.get(uuid);
        if(player != null){
            player.setColor(color);
        }return players;

    }




    //method to update the position 
    public HashMap<UUID,PlayerServer> updatePosition(UUID uuid,int xPos, int yPos, String direction ){
        PlayerServer player = players.get(uuid);
        if(player != null){
            player.setScreenY(yPos);
            player.setScreenX(xPos);
            player.setDirection(direction);
        }return players;

        
    }


    
  
}
