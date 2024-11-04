package main;

import javax.swing.JPanel;
import entity.Player;
import tile.TileManager;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    final int originalTileSize = 16;                    //16x16 tile
    final int scaler = 3;

    public int tileSize = originalTileSize * scaler; //48 per tile size
    public int maxScreenCol = 16;
    public int maxScreenRow = 12;
    public int screenWidth = tileSize * maxScreenCol;    //768 pixels
    public int screenHeight = tileSize * maxScreenRow;   //576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //GAME SETTINGS
    TileManager tileM = new TileManager(this);
    Thread gameThread;
    KeyHandler keyH = new KeyHandler(this);
    public Player player = new Player(this, keyH);
    int FPS = 60;

    //COLLISION CHECKER
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
    public void zoomInOut(int option){
        int oldWorldWidth = tileSize * maxWorldCol;
        tileSize += option;
        int newWorldWidth = tileSize * maxWorldCol;

        double multiplier = (double)newWorldWidth/oldWorldWidth;
        player.speed = (double)newWorldWidth/600;

        double newPlayerWorldX = player.worldX * multiplier;
        double newPlayerWorldY = player.worldY * multiplier;

        player.worldX = newPlayerWorldX;
        player.worldY = newPlayerWorldY;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
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

    // repaints game panel
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2);
        player.draw(g2);

        g2.dispose();
    }
}
