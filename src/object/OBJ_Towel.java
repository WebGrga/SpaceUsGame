package object;

import main.GamePanel;
import entity.Entity;

public class OBJ_Towel extends Entity {
    GamePanel gp;

    public OBJ_Towel(GamePanel gp) {
        super(gp);
        name = "Towel";
        left = setup("towel.png");

    }
}
