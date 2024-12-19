package Tiles;

import Entities.Hut;
import Running_tony.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.Objects;

public class TileHandler {
    static GamePanel gamePanel;

    public Hut hut;
    public int hutX;
    public int hutY;
    public Tile[] tile;
    public static int[][][] mapNumbers; //A 3D array for storing the level data, first dimensions stores current map index
    public TileHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        mapNumbers = new int[GamePanel.totalMaps][GamePanel.screenColumns][GamePanel.screenRows];
        getTileImage();
        loadMap("/Levels/Level1.txt", mapNumbers, 0);
        loadMap("/Levels/Level2.txt", mapNumbers, 1);
    }


    public void loadMap(String levelPath, int[][][] mapTileNum, int currentMap) {
        try {
            InputStream is = TileHandler.class.getResourceAsStream(levelPath);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int rows = mapTileNum[currentMap][0].length;
            int cols = mapTileNum[currentMap].length;

            for (int row = 0; row < rows; row++) {
                String line = br.readLine();
                String[] nums = line.split(" ");

                for (int col = 0; col < cols; col++) {
                    int tileNum = Integer.parseInt(nums[col]);
                    mapTileNum[currentMap][col][row] = tileNum;

                    //Check if this tile is the hut
                    if (tileNum == 6) { //Hut roof
                        if (currentMap == GamePanel.currentMap) {
                            this.hut = new Hut(col * GamePanel.trueTileSize, row * GamePanel.trueTileSize, GamePanel.trueTileSize);
                            this.hutX = col * GamePanel.trueTileSize;
                            this.hutY = row * GamePanel.trueTileSize;
                        }
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getTileImage(){
        try{
            tile[0] = new Tile();
            tile[0].sprite = new Image(Objects.requireNonNull(getClass().getResource("/Environment/Sky.png")).toExternalForm());

            tile[1] = new Tile();
            tile[1].sprite = new Image(Objects.requireNonNull(getClass().getResource("/Environment/Ground.png")).toExternalForm());
            tile[1].hasCollision = true;

            tile[2] = new Tile();
            tile[2].sprite = new Image(Objects.requireNonNull(getClass().getResource("/Signs/RightSign.png")).toExternalForm());

            tile[3] = new Tile();
            tile[3].sprite = new Image(Objects.requireNonNull(getClass().getResource("/Signs/UpSign.png")).toExternalForm());

            tile[4] = new Tile();
            tile[4].sprite = new Image(Objects.requireNonNull(getClass().getResource("/Signs/LeftSign.png")).toExternalForm());

            tile[5] = new Tile();
            tile[5].sprite = new Image(Objects.requireNonNull(getClass().getResource("/Environment/HutBody.png")).toExternalForm());

            tile[6] = new Tile();
            tile[6].sprite = new Image(Objects.requireNonNull(getClass().getResource("/Environment/HutRoof.png")).toExternalForm());

            tile[7] = new Tile();
            tile[7].sprite = new Image(Objects.requireNonNull(getClass().getResource("/Environment/Lava.png")).toExternalForm());

            tile[8] = new Tile();
            tile[8].sprite = new Image(Objects.requireNonNull(getClass().getResource("/Environment/LeftStone.png")).toExternalForm());
            tile[8].hasCollision = true;

            tile[9] = new Tile();
            tile[9].sprite = new Image(Objects.requireNonNull(getClass().getResource("/Environment/RightStone.png")).toExternalForm());
            tile[9].hasCollision = true;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void draw(GraphicsContext gc){
        int y = 0;

        gc.setFill(Color.valueOf("#097dab"));
        gc.fillRect(0,0,GamePanel.screenWidth,GamePanel.screenHeight);

        for(int row = 0; row < GamePanel.screenRows; row++){
            int x = 0;
            for(int col = 0; col < GamePanel.screenColumns; col++){
                int tileNum = mapNumbers[GamePanel.currentMap][col][row];
                if(tileNum!=0){
                    gc.drawImage(tile[tileNum].sprite, x, y, GamePanel.trueTileSize, GamePanel.trueTileSize);
                }
                x += GamePanel.trueTileSize; //Moves to the next horizontal sprite
            }
            y += GamePanel.trueTileSize; //Moves to the next vertical sprite
        }
    }
}