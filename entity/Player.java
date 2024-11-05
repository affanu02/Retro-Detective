package entity;

import java.awt.*;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

import java.awt.image.BufferedImage;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // solidArea = new Rectangle(8, 16, 32, 32);
        solidArea = new Rectangle(12, 16, 24, 32);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = gp.worldWidth / 600;
        direction = "idle";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player-up-1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player-up-2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player-down-1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player-down-2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player-left-1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player-left-2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player-right-1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player-right-2.png"));
            idle = ImageIO.read(getClass().getResourceAsStream("/res/player/player-idle.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        boolean isMoving = false;

        // Check if any key is pressed
        if (keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // Checks for collisions here
            collisionON = false;
            gp.col_Checker.checkTile(this);

            // If collisionON is FALSE, player can move
            if (!collisionON) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            // Set moving flag to true when the player is moving
            isMoving = true;
        } else {
            spriteNum = 1; // This line ensures the animation stops at the first frame when idle
        }

        // Handle animation only when moving
        if (isMoving) {
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1)
                    image = up1;
                if (spriteNum == 2)
                    image = up2;
                break;
            case "down":
                if (spriteNum == 1)
                    image = down1;
                if (spriteNum == 2)
                    image = down2;
                break;
            case "right":
                if (spriteNum == 1)
                    image = right1;
                if (spriteNum == 2)
                    image = right2;
                break;
            case "left":
                if (spriteNum == 1)
                    image = left1;
                if (spriteNum == 2)
                    image = left2;
                break;
            case "idle":
                image = idle;
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}