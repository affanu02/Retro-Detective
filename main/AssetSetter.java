package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Sets all the objects on the map using X and why coordinates according to what
     * map is chosen.
     * 
     * @param mapName
     * @return NULL
     * @throws
     */
    public void setObject() {
        // Keys home map
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;

        // Doors home Map
        gp.obj[3] = new OBJ_Door();
        gp.obj[3].worldX = 10 * gp.tileSize;
        gp.obj[3].worldY = 11 * gp.tileSize;

        // Chest home Map
        gp.obj[4] = new OBJ_Chest();
        gp.obj[4].worldX = 10 * gp.tileSize;
        gp.obj[4].worldY = 7 * gp.tileSize;

        // Boots Home Map
        gp.obj[5] = new OBJ_Boots();
        gp.obj[5].worldX = 37 * gp.tileSize;
        gp.obj[5].worldY = 42 * gp.tileSize;
    }
}
