package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {
    GamePanel gp;

    public OBJ_Door(GamePanel gp) {
        super(gp);
        name = "Door";
        left = setup("door.png");
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 48;
        solidArea.height = 32;
    }
}
