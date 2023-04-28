package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.*;

import main.GamePanel;
import main.KeyHandler;
/*New Player added to Server */
public class NewPlayer extends Entity {

  

    public final int screenX;
    public final int screenY;
    // public int hasKey = 0;
    public String color;

    public NewPlayer(GamePanel gp) {
        super(gp);
       

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
        getNewPlayerImage();

    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 7;
        worldY = gp.tileSize * 6;
        speed = 4;
        direction = "left";

        // NewPlayer status
        Life = 1;
        Death = 0;
    }

    public void update() {
       // if (keyH.upPressed == true || keyH.downPressed == true || keyH.rightPressed == true
        //        || keyH.leftPressed == true) {

        //       if (keyH.upPressed == true) {
         //           direction = "up";
        //         } else if (keyH.downPressed == true) {
        //            direction = "down";
      //       } else if (keyH.rightPressed == true) {
         //           direction = "right";
             //       } else if (keyH.leftPressed == true) {
         //           direction = "left";
         //       }
             setAction();
            collisionOn = false;

            gp.cChecker.checkTile(this);

           // int objIndex = gp.cChecker.checkObject(this, true);
            //pickUpObject(objIndex);
            gp.cChecker.checkObject(this, true);

           // int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            //interactNPC(npcIndex);
            gp.cChecker.checkPlayer(this);
          //  gp.eHandler.checkEvent();

          
           
           
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
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldX + gp.player.screenY;
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
        g2.drawImage(image, screenX, screenY,gp.tileSize,gp.tileSize, null);
    }

    public void getNewPlayerImage() {

        int number = 0;

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
        if (number == 1) {
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
        if (number == 3) {
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
