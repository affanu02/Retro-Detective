package object;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import main.GamePanel;

/* Parent Class of all Object classes*/
public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;

    // draws the object
    public void draw(Graphics2D g2, GamePanel gp) {
        double screenX = worldX - gp.player.worldX + gp.player.screenX;
        double screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, (int) screenX, (int) screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
