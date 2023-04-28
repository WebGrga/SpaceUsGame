package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Eye extends Entity {
    GamePanel gp;

    public OBJ_Eye(GamePanel gp) {
        super(gp);
        name = "Eye";
        left = setup("eye.png");
    }
}
