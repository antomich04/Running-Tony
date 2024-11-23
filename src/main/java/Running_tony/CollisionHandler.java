package Running_tony;

import Tiles.TileHandler;
import javafx.scene.shape.Rectangle;

public class CollisionHandler{
    private static TileHandler tileHandler = null;

    public CollisionHandler(TileHandler tileHandler){
        this.tileHandler = tileHandler;
    }

    //Checks if the player can move to the next position
    public boolean isValidMove(int nextX, int nextY, int width, int height){

        Rectangle nextHitbox = new Rectangle(nextX, nextY, width, height); //Defines hitbox for the next potential position

        //Defines each corner or critical point to check
        int[][] checkPoints = {
                {nextX, nextY},                    //Top-left corner
                {nextX + width - 1, nextY},        //Top-right corner
                {nextX, nextY + height - 1},       //Bottom-left corner
                {nextX + width - 1, nextY + height - 1} //Bottom-right corner
        };

        //Checks if any of these points collide
        for(int[] point : checkPoints){
            if(nextHitbox.contains(point[0], point[1]) && hasCollision(point[0], point[1])){
                return false; //Collision detected, movement is not valid
            }
        }

        //No collisions detected, movement is valid
        return true;
    }

    private static boolean hasCollision(int x, int y){
        if(x < 0 || x >= GamePanel.screenWidth || y < 0 || y >= GamePanel.screenHeight){
            return true; //Out of bounds
        }

        //Pixel coordinates are converted to tile coordinates
        int tileX = x/GamePanel.trueTileSize;
        int tileY = y/GamePanel.trueTileSize;

        return tileHandler.tile[TileHandler.mapNumbers[GamePanel.currentMap][tileX][tileY]].hasCollision;
    }

    //Checks if the bottom side of the player is on a ground tile
    public static boolean isOnFloor(Rectangle hitbox){
        return hasCollision((int)hitbox.getX(), (int)(hitbox.getY() + hitbox.getHeight() + 1)) //Bottom left corner
                || hasCollision((int)(hitbox.getX() + hitbox.getWidth()),(int) (hitbox.getY() + hitbox.getHeight() + 1)); //Bottom right corner
    }

    //Checks if player fell into lava
    public boolean isOnLava(Rectangle hitbox){
        return (hitbox.getY() + hitbox.getHeight()+2)>=GamePanel.screenHeight; //Lava tiles are placed in the bottom row of the map
    }
}
