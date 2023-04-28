package entity;

import java.util.Random;

import main.GamePanel;

public class impostor extends Entity {
    public impostor(GamePanel gp, int index) {
        super(gp);
        direction = "left";
        speed = 2;
        solidArea.x = 0;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 48;
        solidArea.height = 32;

        setDialogue();
        setImage(index);
    }

    public void setImage(int index) {
        if (gp.characterIndex == 0) {
            // red pics
            left = setup("Redleft.png");
            right = setup("Redright.png");
            joined = "Red";
        }
        if (gp.characterIndex == 1) {
            // purple
            left = setup("Purpleleft.png");
            right = setup("Purpleright.png");
            joined = "Purple";
        }
        if (gp.characterIndex == 2) {
            // yellow
            left = setup("Yellowleft.png");
            right = setup("Yellowright.png");
            joined = "Yellow";
        }
        if (gp.characterIndex == 3) {
            // green
            left = setup("Greenleft.png");
            right = setup("Greenright.png");
            joined = "Green";
        }
    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockCounter = 0;
        }

    }

    public void setDialogue() {
        dialogues[0] = "Hello, there";
        dialogues[1] = "YO";
        dialogues[2] = "SPACEEE";
        dialogues[3] = "WOOOO";
    }

    public void speak() {
        super.speak();

    }

}
