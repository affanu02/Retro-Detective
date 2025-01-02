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
    GamePanel gp;
    Font silkscreen;
    BufferedImage keyImage;

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

    public void draw(Graphics2D g2) {
        g2.setFont(silkscreen);
        g2.setColor(Color.white);
        g2.drawImage(keyImage, (gp.tileSize / 2) - 10, (gp.tileSize / 2) - 14, gp.tileSize, gp.tileSize, null);
        g2.drawString("x" + gp.player.hasKey, 55, 55);
    }
}
