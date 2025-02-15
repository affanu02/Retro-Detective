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
    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // solidArea = new Rectangle(8, 16, 32, 32);
        solidArea = new Rectangle();
        solidArea.x = 12;
        solidArea.y = 16;
        solidArea.width = 24;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

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

            // Checks for tile collisions
            collisionON = false;
            gp.col_Checker.checkTile(this);

            // checks for object collisions
            int objIndex = gp.col_Checker.checkObject(this, true);
            pickUpObject(objIndex);

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

    public void pickUpObject(int index) {
        if (index != 999) {
            String objectName = gp.obj[index].name;

            switch (objectName) {
                case "Key":
                    // add key count and make key disappear
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[index] = null;
                    gp.ui.showMessage("I found a key! Hmm what does it unlock...?");
                    break;
                case "Door":
                    // check if player has a key to open the door
                    if (hasKey > 0) {
                        gp.playSE(3);
                        gp.obj[index] = null;
                        hasKey--;
                        gp.ui.showMessage("Door unlocked! The mystery deepens...");
                    } else {
                        gp.ui.showMessage("The door is locked. I got to find a key...");
                    }
                    break;
                case "Chest":
                    // check if player has a key to open the chest
                    if (hasKey > 0) {
                        gp.playSE(3);
                        gp.obj[index] = null;
                        hasKey--;
                        gp.ui.showMessage("Chest opened! Lets see what's inside...");
                        gp.ui.gameFinished = true;
                        gp.StopMusic();
                        gp.playSE(4);
                    } else {
                        gp.ui.showMessage("The chest is locked. The key should be around...");
                    }
                    break;
                case "Boots":
                    gp.playSE(2);
                    speed += 2;
                    gp.obj[index] = null;
                    gp.ui.showMessage("Shiny shoes! These make me run faster!");
                    break;
                default:
                    break;
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
