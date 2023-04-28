package main;

import entity.impostor;
import object.OBJ_Boots;
import object.OBJ_Door;
import object.OBJ_Eye;
import object.OBJ_Towel;
import object.OBJ_key;
import object.OBJ_temp;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_Boots(gp);
        gp.obj[0].worldX = gp.tileSize * 5;
        gp.obj[0].worldY = gp.tileSize * 6;

        gp.obj[1] = new OBJ_temp(gp);
        gp.obj[1].worldX = gp.tileSize * 7;
        gp.obj[1].worldY = gp.tileSize * 10;

        gp.obj[2] = new OBJ_Door(gp);
        gp.obj[2].worldX = gp.tileSize * 7;
        gp.obj[2].worldY = gp.tileSize * 11;

        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = gp.tileSize * 8;
        gp.obj[3].worldY = gp.tileSize * 11;

        gp.obj[4] = new OBJ_Door(gp);
        gp.obj[4].worldX = gp.tileSize * 9;
        gp.obj[4].worldY = gp.tileSize * 11;

        gp.obj[5] = new OBJ_Door(gp);
        gp.obj[5].worldX = gp.tileSize * 10;
        gp.obj[5].worldY = gp.tileSize * 11;

        gp.obj[6] = new OBJ_Eye(gp);
        gp.obj[6].worldX = gp.tileSize * 10;
        gp.obj[6].worldY = gp.tileSize * 8;

        gp.obj[7] = new OBJ_key(gp);
        gp.obj[7].worldX = gp.tileSize * 12;
        gp.obj[7].worldY = gp.tileSize * 8;

        gp.obj[8] = new OBJ_Towel(gp);
        gp.obj[8].worldX = gp.tileSize * 8;
        gp.obj[8].worldY = gp.tileSize * 36;

        gp.obj[9] = new OBJ_Towel(gp);
        gp.obj[9].worldX = gp.tileSize * 12;
        gp.obj[9].worldY = gp.tileSize * 36;

        gp.obj[10] = new OBJ_Towel(gp);
        gp.obj[10].worldX = gp.tileSize * 15;
        gp.obj[10].worldY = gp.tileSize * 36;

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
