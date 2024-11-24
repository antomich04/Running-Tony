package Entities;

import Running_tony.CollisionHandler;
import Running_tony.GamePanel;
import Running_tony.KeyInputHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gamePanel;
    private final CollisionHandler collisionHandler;

    public Player(GamePanel gamePanel, KeyInputHandler keyHandler, CollisionHandler collisionHandler){
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.collisionHandler = collisionHandler;

        init();
        getPlayerImage();
    }

    private void init(){
        this.x = 100;
        this.y = GamePanel.screenHeight - (2 * GamePanel.trueTileSize)+5;
        this.startingY = this.y;
        this.groundY = GamePanel.screenHeight;
        this.width = GamePanel.trueTileSize;
        this.height = GamePanel.trueTileSize;
        this.speed = 6;

        //Gravity variables
        this.gravity = 0.5f; //Gravity force
        this.terminalVelocity = 10; //Max falling speed
        this.velocityY = 0; //Current vertical speed

        this.direction = "down";
        this.isFalling = false;
        this.isJumping = false;
        this.isRunning = false;
        initHitbox();
        this.isOnAir = !collisionHandler.isOnFloor(hitbox);
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    private void initHitbox(){
        hitbox = new Rectangle(x, y, 15 * GamePanel.scale, 29 * GamePanel.scale);
    }

    public void updateHitbox(){
        hitbox.setX(this.x);
        hitbox.setY(this.y);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void getPlayerImage(){
        try{
            down = new Image(Objects.requireNonNull(getClass().getResource("/Player/PlayerDown.png")).toExternalForm());
            left1 = new Image(Objects.requireNonNull(getClass().getResource("/Player/PlayerLeft1.png")).toExternalForm());
            left2 = new Image(Objects.requireNonNull(getClass().getResource("/Player/PlayerLeft2.png")).toExternalForm());
            left3 = new Image(Objects.requireNonNull(getClass().getResource("/Player/PlayerLeft3.png")).toExternalForm());
            right1 = new Image(Objects.requireNonNull(getClass().getResource("/Player/PlayerRight1.png")).toExternalForm());
            right2 = new Image(Objects.requireNonNull(getClass().getResource("/Player/PlayerRight2.png")).toExternalForm());
            right3 = new Image(Objects.requireNonNull(getClass().getResource("/Player/PlayerRight3.png")).toExternalForm());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void update(){
        updatePosition();
    }

    private void updatePosition(){
        isOnAir = !collisionHandler.isOnFloor(hitbox); //Updates to guarantee gravity works even if player doesn't jump

        isRunning = keyHandler.shiftPressed;
        speed = (isRunning) ? 8 : 5;

        if(keyHandler.spacePressed && !isJumping && !isFalling){
            isJumping = true;
            if(isRunning){
                velocityY = -12;  //Initial jump speed when running
            }else{
                velocityY = -10;  //Initial jump speed when walking
            }
        }

        //Vertical movement logic
        float nextY = y;
        if(isJumping){
            isOnAir = true;
            nextY += velocityY;
            if(collisionHandler.isValidMove((int) hitbox.getX(), (int)nextY, (int) hitbox.getWidth(), (int) hitbox.getHeight())){
                y = (int)nextY;
                velocityY += gravity;  //Applies gravity during jump ascent
                if(velocityY>=0){  //Starts falling once upward velocity is gone
                    isJumping = false;
                    isFalling = true;
                }
            }else{  //Hits a ceiling, starts falling
                velocityY = 0;
                isJumping = false;
                isFalling = true;
            }

        }else if(isFalling){
            velocityY = Math.min(velocityY + gravity, terminalVelocity);  // Increases fall speed up to terminal velocity
            nextY += velocityY;
            if(collisionHandler.isValidMove((int) hitbox.getX(), (int)nextY, (int) hitbox.getWidth(), (int) hitbox.getHeight())){
                y = (int)nextY;
            }else{  //Stops falling when hitting the ground or platform
                isFalling = false;
                velocityY = 0;
                startingY = y;  //Resets startingY to the platform height
                isOnAir = false;

            }
            if(collisionHandler.isOnLava(hitbox)){ //Falls into lava tile
                keyHandler.spacePressed = false;
                gamePanel.gameState = GamePanel.GAME_OVER;
                gamePanel.stopMusic();
                gamePanel.playSFX(1);
            }
        }else if(isOnAir){  //Starts falling if in the air and not jumping
            isFalling = true;
        }

        //Horizontal movement logic
        int nextX = x;
        if(keyHandler.leftPressed && !keyHandler.rightPressed){
            direction = "left";
            nextX -= speed;
            if(collisionHandler.isValidMove(nextX, (int) hitbox.getY(), (int) hitbox.getWidth(), (int) hitbox.getHeight())){
                x = nextX;
            }
        }else if(keyHandler.rightPressed && !keyHandler.leftPressed){
            direction = "right";
            nextX += speed;
            if(collisionHandler.isValidMove(nextX, (int) hitbox.getY(), (int) hitbox.getWidth(), (int) hitbox.getHeight())){
                x = nextX;
            }
        }else{
            direction = "down"; //Idle direction
        }

        //Updates the hitbox position after each valid movement
        updateHitbox();

        //Sprite animation logic
        if(keyHandler.leftPressed || keyHandler.rightPressed){
            spriteCounter++;
            if(spriteCounter>15){
                spriteNum = (spriteNum % 3) + 1;  //Cycles through sprite states (1, 2, 3)
                spriteCounter = 0;
            }
        }else{  //Resets to idle sprite when no movement keys are pressed
            spriteNum = 1;
        }
    }

    public void draw(GraphicsContext gc) {
        Image image = null;

        //Hitbox offsets
        final int xOffset = 10 * GamePanel.scale;
        final int yOffset = 2 * GamePanel.scale;

        switch (direction){
            case "down":
                image = down;
                break;
            case "left":
                if (spriteNum==1){
                    image = left1;
                }else if(spriteNum==2){
                    image = left2;
                }else if(spriteNum==3){
                    image = left3;
                }
                break;
            case "right":
                if(spriteNum==1){
                    image = right1;
                }else if(spriteNum==2){
                    image = right2;
                }else if(spriteNum==3){
                    image = right3;
                }
                break;
        }
        gc.drawImage(image, hitbox.getX() - xOffset, hitbox.getY() - yOffset, width, height);
    }

}
