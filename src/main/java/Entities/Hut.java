package Entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import java.util.Objects;

public class Hut {
    private int x, y; //Position of the hut
    private final Image hutBody, hutRoof;
    private final int tileSize;
    private Rectangle hitbox;

    public Hut(int x, int y, int tileSize) {
        this.x = x;
        this.y = y;
        this.tileSize = tileSize;

        //Loads hut images
        hutBody = new Image(Objects.requireNonNull(getClass().getResource("/Environment/HutBody.png")).toExternalForm());
        hutRoof = new Image(Objects.requireNonNull(getClass().getResource("/Environment/HutRoof.png")).toExternalForm());

        hitbox = new Rectangle(x, y + tileSize, tileSize, tileSize); // Hitbox covers only the body
    }

    //Draws the hut on the canvas
    public void draw(GraphicsContext gc) {
        gc.drawImage(hutRoof, x, y, tileSize, tileSize); //Hut roof
        gc.drawImage(hutBody, x, y + tileSize, tileSize, tileSize); //Hut body
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    //Checks if the player's hitbox intersects with the hut's hitbox
    public boolean isPlayerTouchingHut(Rectangle playerHitbox) {
        return playerHitbox.intersects(getHitbox().getBoundsInLocal());
    }
}
