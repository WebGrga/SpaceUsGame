package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_key extends Entity {
    GamePanel gp;

    public OBJ_key(GamePanel gp) {
        super(gp);
        name = "Key";
        left = setup("key.png");

    }
}
