package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
    GamePanel gp;

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        name = "Heart";
        image = setup("live.png");
        image2 = setup("ghost.png");

    }
}
