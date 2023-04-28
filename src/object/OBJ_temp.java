package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_temp extends Entity {
    GamePanel gp;

    public OBJ_temp(GamePanel gp) {
        super(gp);
        name = "Temp";
        left = setup("temp.png");
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 48;
        solidArea.height = 32;
    }
}
