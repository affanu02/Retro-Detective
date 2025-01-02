package tile;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tiles;
    public int mapTileNum[][];

    /**
     * Pulls images of tiles and addes to tiles[] array.
     * 
     * @param GamePanel
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;

        // Arrays for tile managements and count
        tiles = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/res/maps/demomap2.txt");
    }

    /**
     * Pulls images of tiles and addes to tiles[] array.
     * 
     * @param tiles[]
     * @throws IOException          if an error occurs while reading the image files
     * @throws NullPointerException if the resource path is incorrect or the file is
     *                              not found
     */
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
            tiles[7].collision = true;

            tiles[8] = new Tile();
            tiles[8].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tree-1.png"));
            tiles[8].collision = true;

            tiles[9] = new Tile();
            tiles[9].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/rock.png"));
            tiles[9].collision = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads in the integer information in the map and creates a doube arrau
     * 
     * @param mapAddress map address in files
     * @throws IOException           if an error occurs while reading the map file
     * @throws NullPointerException  if the specified map file is not found
     * @throws NumberFormatException if the map file contains invalid numbers
     */
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
            e.printStackTrace();
        }
    }

    /**
     * Draws images according to the inputstreamed integer map loaded.
     * 
     * @param Graphics2D Graphics variable
     */
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
