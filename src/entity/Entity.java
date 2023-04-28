package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
    public UUID uuid; //random key generator 
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public String dialogues[] = new String[20];
    int dialogueIndex = 0;
    public BufferedImage image, image2;
    public String name;
    public boolean collision = false;
    public int characterIndex = 1;
    // character status
    public int Life;
    public int Death;
    public String joined = "red";
    public int speed;
    public int level;
    public int gameId = 1; //would be incremented if current game is full
    GamePanel gp;
    public BufferedImage left, right;
    public String direction = "left";

         
    public Entity(GamePanel gp) {
        this.gp = gp;
        this.uuid = UUID.randomUUID();//randomly genrated id done by inbuilt java function 
    }



    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

  
    public void setDirection(String direction) {
        this.direction = direction;
    }

  

    public void setLook(String colorN) {
        if (colorN.equals("Red")) {
            // red pics
            left = setup("Redleft.png");
            right = setup("Redright.png");
        }
        if (colorN.equals("Purple")) {
            // purple
            left = setup("Purpleleft.png");
            right = setup("Purpleright.png");
        }
        if (colorN.equals("Yellow")) {
            // yellow
            left = setup("Yellowleft.png");
            right = setup("Yellowright.png");
        }
        if (colorN.equals("Green")) {
            // green
            left = setup("Greenleft.png");
            right = setup("Greenright.png");
        }
    }

    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass()
                    .getResourceAsStream(imageName));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            // TODO: handle exception
        }
        return image;

    }

    public void setAction() {

    }

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
    }

    public void update() {
        setAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);

        if (collisionOn == false) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;

            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        switch (direction) {
            case "left":
                image = left;
                break;
            case "right":
                image = right;
                break;
            case "up":
                image = left;
                break;
            case "down":
                image = left;
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }



    public UUID getUuid() {
        return uuid;
    }



    public void setUuid(UUID uuid2) {
        this.uuid = uuid2;
    }



    public int getLevel() {
        return level;
    }



    public void setLevel(int level) {
        this.level = level;
    }



    public int getGameId() {
        return gameId;
    }



    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    

}
