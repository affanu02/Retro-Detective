package tile;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;

    public Tile[] tiles;
    public int mapTileNum[][];

    // Costructor and default map loader
    public TileManager(GamePanel gp) {
        this.gp = gp;

        tiles = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/res/maps/demomap2.txt");
    }

    public void getTileImage() {
        try {
            // default five
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass.png"));
            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/wall.png"));
            tiles[1].collision = true;
            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/water.png"));
            tiles[2].collision = true;
            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/earth.png"));
            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tree.png"));
            tiles[4].collision = true;
            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/sand.png"));

            /* Secondary tiles */
            tiles[6] = new Tile();
            tiles[6].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass-1.png"));
            tiles[7] = new Tile();
            tiles[7].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/wall-1.png"));
            tiles[8] = new Tile();
            tiles[8].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tree-1.png"));

            // Sand with grass borders Numbers
            /*
             * tiles[6] = new Tile();
             * tiles[6].image =
             * ImageIO.read(getClass().getResourceAsStream("/res/tiles/sand-grass-b.png"));
             * tiles[7] = new Tile();
             * tiles[7].image =
             * ImageIO.read(getClass().getResourceAsStream("/res/tiles/sand-grass-br.png"));
             * tiles[8] = new Tile();
             * tiles[8].image =
             * ImageIO.read(getClass().getResourceAsStream("/res/tiles/sand-grass-l.png"));
             * tiles[9] = new Tile();
             * tiles[9].image =
             * ImageIO.read(getClass().getResourceAsStream("/res/tiles/sand-grass-lb.png"));
             * tiles[10] = new Tile();
             * tiles[10].image =
             * ImageIO.read(getClass().getResourceAsStream("/res/tiles/sand-grass-r.png"));
             * tiles[11] = new Tile();
             * tiles[11].image =
             * ImageIO.read(getClass().getResourceAsStream("/res/tiles/sand-grass-rt.png"));
             * tiles[12] = new Tile();
             * tiles[12].image =
             * ImageIO.read(getClass().getResourceAsStream("/res/tiles/sand-grass-t.png"));
             * tiles[13] = new Tile();
             * tiles[13].image =
             * ImageIO.read(getClass().getResourceAsStream("/res/tiles/sand-grass-tl.png"));
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapAddress) {
        try {
            InputStream is = getClass().getResourceAsStream(mapAddress);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }

            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0, worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            double screenX = worldX - gp.player.worldX + gp.player.screenX;
            double screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tiles[tileNum].image, (int) screenX, (int) screenY, gp.tileSize, gp.tileSize, null);
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
