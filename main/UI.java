package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

/* Handles all on screen UI */
public class UI {
    GamePanel gp;
    Font silkscreen;

    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            createFont();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        g2.drawString("Key = " + gp.player.hasKey, 30, 50);
    }
}
