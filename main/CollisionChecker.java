package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        // coordinates of entity collision area
        int entityLeftWorldX = (int) entity.worldX + entity.solidArea.x;
        int entityRightWorldX = (int) entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = (int) entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = (int) entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - (int) entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];

                if (gp.tileM.tiles[tileNum1].collision || gp.tileM.tiles[tileNum2].collision) {
                    entity.collisionON = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + (int) entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                if (gp.tileM.tiles[tileNum1].collision || gp.tileM.tiles[tileNum2].collision) {
                    entity.collisionON = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - (int) entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];

                if (gp.tileM.tiles[tileNum1].collision || gp.tileM.tiles[tileNum2].collision) {
                    entity.collisionON = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + (int) entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                if (gp.tileM.tiles[tileNum1].collision || gp.tileM.tiles[tileNum2].collision) {
                    entity.collisionON = true;
                }
                break;

            default:
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        // scan objects array
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                // get entity's solid area position
                entity.solidArea.x = (int) entity.worldX + entity.solidArea.x;
                entity.solidArea.y = (int) entity.worldY + entity.solidArea.y;

                // get the object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionON = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionON = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionON = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionON = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    default:
                        break;
                }

                // reset values to default values after checking for collision intersections
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }
}
