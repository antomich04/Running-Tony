package Entities;

import Running_tony.KeyInputHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public abstract class Entity {
    public KeyInputHandler keyHandler;
    protected boolean isJumping, isFalling, isRunning, isOnAir;
    protected int x, y, startingY, groundY, width, height, speed, terminalVelocity;
    protected float gravity, velocityY;
    protected String direction;

    public Image down, left1, left2, left3, right1, right2, right3;

    //Variables used to draw sprites
    public int spriteCounter = 0;
    public int spriteNum = 1;


    protected Rectangle hitbox;
    public abstract void update();
    public abstract void updateHitbox();
    public abstract void draw(GraphicsContext gc);
}
