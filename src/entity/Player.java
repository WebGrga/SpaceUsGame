package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    // public int hasKey = 0;
    public String color;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        setDefaultValues();
        getPlayerImage();

    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 7;
        worldY = gp.tileSize * 6;
        speed = 4;
        direction = "left";

        // player status
        Life = 1;
        Death = 0;
    }

    public void update() {
        if (keyH.upPressed == true || keyH.downPressed == true || keyH.rightPressed == true
                || keyH.leftPressed == true) {

            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            }

            collisionOn = false;

            gp.cChecker.checkTile(this);

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            gp.eHandler.checkEvent();

            gp.keyH.enterPressed = false;
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
            if (Life <= 0) {
                gp.gameState = gp.gameOverState;
            }

        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {

        }
    }

    public void interactNPC(int i) {
        if (i != 999) {
            if (gp.keyH.enterPressed == true) {
                Life = 0;
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();

            }

        }

    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
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
        g2.drawImage(image, screenX, screenY, null);
    }

    public void getPlayerImage() {

        int number = 0 ;

        if(this.color != null){
            switch(this.color){
                case "Purple":
                number = 1 ;
                break;
                case "Yellow":
                number = 2; 
                break;
                case "Green":
                number = 3;
                break;
                case "Red":
                number = 0; 
                break;
            }
        }else{
            number = gp.characterIndex;
        }

        if (number == 0) {
            // red pics
            left = setup("Redleft.png");
            right = setup("Redright.png");
            joined = "Red";
        }
        if (number== 1) {
            // purple
            left = setup("Purpleleft.png");
            right = setup("Purpleright.png");
            joined = "Purple";
        }
        if (number == 2) {
            // yellow
            left = setup("Yellowleft.png");
            right = setup("Yellowright.png");
            joined = "Yellow";
        }
        if (number== 3) {
            // green
            left = setup("Greenleft.png");
            right = setup("Greenright.png");
            joined = "Green";
        }

    }
    /**
     * Color getter for each index 
     * @return the color 
     */
    public String getColor(){
        return this.color;
    }
}
