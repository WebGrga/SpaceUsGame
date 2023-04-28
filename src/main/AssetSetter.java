package main;

import entity.impostor;
import object.OBJ_Boots;
import object.OBJ_temp;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_Boots(gp);
        gp.obj[0].worldX = gp.tileSize * 5;
        gp.obj[0].worldY = gp.tileSize * 10;

        gp.obj[1] = new OBJ_temp(gp);
        gp.obj[1].worldX = gp.tileSize * 7;
        gp.obj[1].worldY = gp.tileSize * 10;

    }

    public void setNPC() {

        gp.npc[0] = new impostor(gp, 3);
        gp.npc[0].worldX = gp.tileSize * 4;
        gp.npc[0].worldY = gp.tileSize * 3;
        gp.npc[0].characterIndex = 0;

        // gp.npc[1] = new impostor(gp,4);
        // gp.npc[1].worldX = gp.tileSize * 4;
        // gp.npc[1].worldY = gp.tileSize * 5;
        // gp.npc[1].characterIndex = 1;
        // gp.npc[1].setImage();

        // gp.npc[2] = new impostor(gp,2);
        // gp.npc[2].worldX = gp.tileSize * 7;
        // gp.npc[2].worldY = gp.tileSize * 3;
        // gp.npc[2].characterIndex = 2;
        // gp.npc[2].setImage();
    }
}
