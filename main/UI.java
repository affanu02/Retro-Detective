package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import object.OBJ_Key;

/* Handles all on screen UI */
public class UI {
    // variables
    GamePanel gp;
    Font silkscreen;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    // constructor
    public UI(GamePanel gp) {
        this.gp = gp;

        // images
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;

        try {
            createFont();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // private class to create custom fonts
    private void createFont() throws FontFormatException {
        try {
            // Load the Silkscreen font
            File fontFile = new File("res/fonts/Silkscreen-Regular.ttf");
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            silkscreen = baseFont.deriveFont(40f);
        } catch (IOException e) {
            e.printStackTrace();
            silkscreen = new Font("Arial", Font.PLAIN, 40);
        }
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        // level/game finished notifications
        if (gameFinished) {
            g2.setFont(silkscreen);
            g2.setColor(Color.white);

            String text = "Level Complete!";
            int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x = gp.screenWidth / 2 - textLength / 2;
            int y = gp.screenWidth / 2;
            g2.drawString(text, x, y);

            g2.setColor(Color.yellow);
            g2.setFont(g2.getFont().deriveFont(50F));
            text = "Congratulations!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenWidth / 2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);

            gp.gameThread = null;
        }

        g2.setFont(silkscreen);
        g2.setColor(Color.white);
        g2.drawImage(keyImage, (gp.tileSize / 2) - 10, (gp.tileSize / 2) - 14, gp.tileSize, gp.tileSize, null);
        g2.drawString("x" + gp.player.hasKey, 55, 55);

        // Message notifications
        if (messageOn) {
            g2.setFont(g2.getFont().deriveFont(20F));
            g2.drawString(message, 150, 45);
            messageCounter++;

            if (messageCounter > 120) {
                messageCounter = 0;
                messageOn = false;
            }
        }
    }
}
