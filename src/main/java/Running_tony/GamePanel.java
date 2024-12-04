package Running_tony;

import Entities.Hut;
import Entities.Player;
import Tiles.TileHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GamePanel extends Scene implements Runnable{
    static final int tileSize = 32; //32x32 tile
    public static final int scale = 2;
    public static final int trueTileSize =scale * tileSize; //64x64 tile
    public static final int screenColumns = 20;
    public static final int screenRows = 12;
    public static final int screenWidth = trueTileSize * screenColumns; //1280 pixels
    public static final int screenHeight = trueTileSize * screenRows; //768 pixels
    static final int FPS = 60;

    public static final int totalMaps = 5;
    public static int currentMap = 0;
    double playTimer;

    //Initialization of the canvas
    static Canvas canvas = new Canvas(screenWidth,screenHeight);
    GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();

    //Initialization of the key event handler
    private final KeyInputHandler keyHandler = new KeyInputHandler(this);

    //Initialization of the tile and collision handlers
    public final TileHandler tileHandler = new TileHandler(this);
    private final CollisionHandler collisionHandler = new CollisionHandler(tileHandler);

    //Initialization of the Player object
    public final Player player = new Player(this,keyHandler,collisionHandler);

    //Initialization of the UI object
    UI ui = new UI(this);

    //Initialization of the music handlers
    private final MusicHandler musicHandler = new MusicHandler();
    private final MusicHandler sfxHandler = new MusicHandler();

    //Game states
    final int TITLE_SCREEN = 0;
    final int PLAYING = 1;
    final int PAUSED = 2;
    final int ABOUT_SCREEN = 3;
    final int CONTROLS = 4;
    public static final int GAME_OVER = 5;
    final int ENDING = 6;
    final int OPTIONS = 7;
    public int gameState = TITLE_SCREEN;

    public GamePanel(Parent root){
        super(root, screenWidth, screenHeight);
        this.setOnKeyPressed(keyEvent -> keyHandler.keyPressed(keyEvent));
        this.setOnKeyReleased(keyEvent -> keyHandler.keyReleased(keyEvent));
    }

    Thread gameThread;

    public void startGameThread(){
        gameThread = new Thread(this);
        playMusic(1); //Plays menu music
        gameThread.start();
    }

    @Override
    public void run(){ //This will be the core of the game

        double drawInterval = 1000000000.0/FPS; //The screen is painted once every 16.17 ms
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread!=null){

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta>=1){
                update();
                paintComponent(graphicsContext2D);
                delta--;
            }
        }
    }

    //Updates the information such as player's position inside the game loop
    public void update() {
        if(gameState == PLAYING){
            player.update();
            playTimer+=1/60.0; //update method is called once every 60 seconds

            //Checks if player has reached the hut
            if(tileHandler.hut != null && tileHandler.hut.isPlayerTouchingHut(player.getHitbox())){
                goToNextLevel();
            }
        }
        musicHandler.changeMusicVolume(ui.musicVolumeAmplitude);
        sfxHandler.changeSFXVolume(ui.sfxVolumeAmplitude);
    }

    //Handles level transitions
    private void goToNextLevel(){

        //Transitions to the next level
        if(currentMap + 1 < totalMaps){
            currentMap++;
            tileHandler.loadMap("/Levels/Level" + (currentMap + 1) + ".txt", TileHandler.mapNumbers, currentMap);

            //Resets player position based on current map
            switch(currentMap) {
                case 0:
                case 1:
                    spawnPlayer(GamePanel.trueTileSize * 2, GamePanel.trueTileSize * 9);
                    break;
                case 2:
                case 3:
                case 4:
                    spawnPlayer(GamePanel.trueTileSize, GamePanel.trueTileSize * 9);
                    break;
            }

        }else{
            //If no more levels, displays an ending screen
            gameState = ENDING;
            stopMusic(); //Stops main music
            playMusic(2); //Plays ending music
        }
    }

    //Resets game state to start a new game
    public void resetGame() {
        currentMap = 0;
        gameState = PLAYING;
        ui.menuSelector = -1;

        //Stops menu music and starts the main music
        stopMusic();
        playMusic(0);

        //Reloads the first level
        tileHandler.loadMap("/Levels/Level1.txt", TileHandler.mapNumbers, currentMap);

        //Resets player position to initial spawn point
        spawnPlayer(GamePanel.trueTileSize * 2, GamePanel.screenHeight - (2 * GamePanel.trueTileSize));

        //Reinitializes the hut object for the first level
        tileHandler.hut = new Hut(tileHandler.hutX, tileHandler.hutY, trueTileSize);
    }

    //Repaints the components inside the game loop
    public void paintComponent(GraphicsContext gc) {
        if(gameState == TITLE_SCREEN){
            ui.draw(gc);
        }else{
            tileHandler.draw(gc);
            player.draw(gc);

            //Use the hut from the current map's tileHandler
            if(tileHandler.hut != null){
                tileHandler.hut.draw(gc);
            }

            ui.draw(gc);
        }
    }

    //Avoids player movement when window has lost focus
    public void focusLost(){
        keyHandler.resetKeys();
    }

    public void spawnPlayer(int x, int y){
        player.setX(x);
        player.setY(y);
    }

    public void playMusic(int index){
        musicHandler.setMusicFile(index);
        musicHandler.start();
        musicHandler.loopMusic();
    }
    public void stopMusic(){
        musicHandler.stop();
    }

    public void playSFX(int index){
        sfxHandler.setSFXFile(index);
        sfxHandler.start();
        sfxHandler.loopSFX();
    }

    public void stopSFX(){
        sfxHandler.stop();
    }
    public MusicHandler getMusicHandler(){
        return musicHandler;
    }

}