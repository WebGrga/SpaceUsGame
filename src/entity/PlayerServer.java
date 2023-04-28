package entity;



import java.io.*;
import java.util.UUID;



public class PlayerServer implements Serializable  {
    public  int screenY;
    // public int hasKey = 0;
    public String color;
    public String direction = "left";
    private static final long serialVersionUID = 1L;
  public UUID uuid;

  public String name;

    public  int screenX;
    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getScreenX() {
        return screenX;
    }
    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }
    public int getScreenY() {
        return screenY;
    }
    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public PlayerServer(UUID uuid) {
        this.uuid = uuid;
    }
    
    
    @Override
    public int hashCode() {
       
        return this.getScreenY()+this.getScreenX() + this.getUuid().hashCode();
    }
  
  


}
