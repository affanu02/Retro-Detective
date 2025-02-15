package main;

import javax.swing.JPanel;
import entity.Player;
import object.SuperObject;
import tile.TileManager;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scaler = 3;
    public int tileSize = originalTileSize * scaler; // 48 per tile size
    public int maxScreenCol = 16;
    public int maxScreenRow = 12;
    public int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // GAME SETTINGS
    int FPS = 60;
    TileManager tileM = new TileManager(this);
    Thread gameThread;
    KeyHandler keyH = new KeyHandler(this);
    public Player player = new Player(this, keyH);
    public UI ui = new UI(this);

    // SOUND SETTINGS
    Sound sound = new Sound();
    Sound music = new Sound();

    // OBJECT SETTINGS
    public AssetSetter aSetter = new AssetSetter(this);
    public SuperObject obj[] = new SuperObject[10];

    // COLLISION CHECKER
    public CollisionChecker col_Checker = new CollisionChecker(this);

    // Constructor Classes
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    // if option is 1, zoom in, if option -1 zoom out
    public void zoomInOut(int option) {
        int oldWorldWidth = tileSize * maxWorldCol;
        tileSize += option;
        int newWorldWidth = tileSize * maxWorldCol;

        double multiplier = (double) newWorldWidth / oldWorldWidth;
        player.speed = (double) newWorldWidth / 600;

        // updates new player coordinates X and Y
        double newPlayerWorldX = player.worldX * multiplier;
        double newPlayerWorldY = player.worldY * multiplier;
        player.worldX = newPlayerWorldX;
        player.worldY = newPlayerWorldY;

        // updates all object coordinates X and Y
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                double newobjWorldX = obj[i].worldX * multiplier;
                double newobjWorldY = obj[i].worldY * multiplier;
                obj[i].worldX = (int) newobjWorldX;
                obj[i].worldY = (int) newobjWorldY;
            }
        }

        col_Checker = new CollisionChecker(this);
    }

    // draws and sets objects
    public void setupGame() {
        aSetter.setObject();
        playMusic(0); // background music
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    // changes player positions
    public void update() {
        player.update();
    }

    // Repaints entire game panel through draw methods
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        // player drawing
        player.draw(g2);

        // ui drawing
        ui.draw(g2);

        g2.dispose();
    }

    // plays music
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void StopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }
}
