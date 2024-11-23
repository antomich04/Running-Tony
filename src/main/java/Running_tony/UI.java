package Running_tony;

import javafx.application.Platform;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.text.DecimalFormat;


public class UI {
    GamePanel gamePanel;
    GraphicsContext gc;
    Font font = new Font("Arial",40);
    Font largeFont = new Font("Arial",80);
    Font smallFont = new Font("Arial",26);
    int menuSelector = -1;
    int pauseSelector = -1;
    Image indicator = new Image("indicator.png");

    Text aboutText;
    DecimalFormat df = new DecimalFormat("#0.00");
    public UI(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public void draw(GraphicsContext gc) {
        this.gc = gc;
        if(gamePanel.gameState==gamePanel.TITLE_SCREEN){
            drawTitleScreen();
        }else if(gamePanel.gameState==gamePanel.PAUSED){
            drawPauseScreen();
        }else if(gamePanel.gameState==gamePanel.ABOUT_SCREEN){
            drawAboutScreen();
        }else if(gamePanel.gameState==gamePanel.CONTROLS){
            drawControlsScreen();
        }else if(gamePanel.gameState==gamePanel.GAME_OVER){
            drawGameOverScreen();
        }else if(gamePanel.gameState==gamePanel.ENDING){
            drawEndingScreen();
        }
    }

    private void drawTitleScreen(){
        String titleText = "Running Tony";
        gc.setFont(largeFont);

        //Background color
        gc.setFill(Color.LIGHTSKYBLUE);
        gc.fillRect(0,0,GamePanel.screenWidth, GamePanel.screenHeight);

        double x = GamePanel.trueTileSize * 6.5;
        double y = GamePanel.trueTileSize * 2;

        //Shadow for text
        gc.setFill(Color.DIMGRAY);
        gc.fillText(titleText,x+5,y+5);

        //Draws text
        gc.setFill(Color.WHITE);
        gc.fillText(titleText,x,y);

        //Draws menu, START button first
        titleText = "START";
        gc.setFont(font);
        x = GamePanel.trueTileSize * 9;
        y += GamePanel.trueTileSize * 3;
        gc.fillText(titleText, x, y);

        if(menuSelector==0){ //Draws a menu selection indicator
            gc.drawImage(indicator,x-GamePanel.trueTileSize, y-32);
        }

        //ABOUT button
        titleText = "ABOUT";
        y += GamePanel.trueTileSize;
        gc.fillText(titleText, x, y);

        if(menuSelector==1){
            gc.drawImage(indicator,x-GamePanel.trueTileSize, y-32);
        }

        //EXIT button
        titleText = "EXIT";
        y += GamePanel.trueTileSize;
        gc.fillText(titleText, x, y);

        if(menuSelector==2){
            gc.drawImage(indicator,x-GamePanel.trueTileSize, y-32);
        }
    }

    private void drawPauseScreen() {

        //Background color
        gc.setFill(Color.LIGHTSKYBLUE);
        gc.fillRect(GamePanel.trueTileSize*5,GamePanel.trueTileSize*3,GamePanel.trueTileSize*10, GamePanel.trueTileSize*6);


        String pauseText = "PAUSED";
        gc.setFont(largeFont);
        gc.setFill(Color.WHITE);


        double x = GamePanel.trueTileSize * 8;
        double y = GamePanel.trueTileSize*4.5;
        gc.fillText(pauseText, x, y);

        //RESUME button
        pauseText = "RESUME";
        gc.setFont(smallFont);
        x = GamePanel.trueTileSize * 9.25;
        y += GamePanel.trueTileSize;
        gc.fillText(pauseText, x, y);

        if(pauseSelector==0){ //Draws a menu selection indicator
            gc.drawImage(indicator,x-GamePanel.trueTileSize, y-28);
        }

        pauseText = "CONTROLS";
        y += GamePanel.trueTileSize;
        gc.fillText(pauseText, x, y);

        if(pauseSelector==1){
            gc.drawImage(indicator,x-GamePanel.trueTileSize, y-28);
        }

        //EXIT button
        pauseText = "EXIT";
        y += GamePanel.trueTileSize;
        gc.fillText(pauseText, x, y);

        if(pauseSelector==2){
            gc.drawImage(indicator,x-GamePanel.trueTileSize, y-28);
        }

    }

    private void drawAboutScreen() {
        //Background color
        gc.setFill(Color.LIGHTSKYBLUE);
        gc.fillRect(0, 0, GamePanel.screenWidth, GamePanel.screenHeight);

        //Initialization of aboutText object
        aboutText = new Text("""
                Running Tony is a simple 2d platformer game that was developed as part of the course "Human-Computer Interaction" during the fifth semester at International Hellenic University. The game's goal is to succesfully traverse through all five levels without
                 dying. The author of this program is Antonios Michailos, who was born on July 12th, 2004, in Katerini, Greece. Apart from Intellij IDEA, Pixilart.com was used to create the game's sprites as well as Pixabay.com to form the game's soundtrack.
                
                Author's contact information:
                Email: antomich04@gmail.com
                Discord: antomich04
                Github: antomich04
                        
                        
                Press ESC to exit
                """);

        aboutText.setFont(smallFont);
        aboutText.setFill(Color.WHITE);
        aboutText.setWrappingWidth(GamePanel.screenWidth * 0.8);
        aboutText.setTextAlignment(TextAlignment.LEFT);

        //Calculates starting position
        double x = (GamePanel.screenWidth - aboutText.getWrappingWidth()) / 2;
        double y = GamePanel.trueTileSize * 2;

        //Uses Platform.runLater to take the snapshot on the javaFX Application Thread
        Platform.runLater(() -> {
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT); //Ensures text has transparent background to avoid overlapping
            WritableImage textImage = aboutText.snapshot(params, null);

            //Draws the snapshot image on the canvas
            gc.drawImage(textImage, x, y);
        });
    }


    private void drawControlsScreen(){
        //Background color
        gc.setFill(Color.LIGHTSKYBLUE);
        gc.fillRect(GamePanel.trueTileSize*5,GamePanel.trueTileSize*3,GamePanel.trueTileSize*10, GamePanel.trueTileSize*6);

        String controlsText = "MOVE LEFT: A";
        gc.setFont(smallFont);
        gc.setFill(Color.WHITE);

        double x = GamePanel.trueTileSize * 8.5;
        double y = GamePanel.trueTileSize * 4;

        //Draws text
        gc.fillText(controlsText,x,y);

        controlsText = "MOVE RIGHT: D";
        y += GamePanel.trueTileSize;
        gc.fillText(controlsText, x, y);

        controlsText = "SPRINT: SHIFT";
        y += GamePanel.trueTileSize;
        gc.fillText(controlsText, x, y);

        controlsText = "JUMP: SPACE";
        y += GamePanel.trueTileSize;
        gc.fillText(controlsText, x, y);

        controlsText = "PRESS ESC TO CLOSE";
        x = GamePanel.trueTileSize * 8;
        y += GamePanel.trueTileSize;
        gc.fillText(controlsText, x, y);
    }

    private void drawGameOverScreen(){
        String gameOverText = "GAME OVER";
        gc.setFont(largeFont);

        //Background color
        gc.setFill(Color.LIGHTSKYBLUE);
        gc.fillRect(0,0,GamePanel.screenWidth, GamePanel.screenHeight);

        double x = GamePanel.trueTileSize * 7;
        double y = GamePanel.trueTileSize * 2;

        //Shadow for text
        gc.setFill(Color.DIMGRAY);
        gc.fillText(gameOverText,x-25,y+5);

        //Draws text
        gc.setFill(Color.WHITE);
        gc.fillText(gameOverText,x-30,y);

        gc.setFont(font);
        gameOverText = "Press R to restart";
        x = GamePanel.trueTileSize * 7.25;
        y += GamePanel.trueTileSize * 4;
        gc.fillText(gameOverText, x, y);

        gameOverText = "Press X to exit";
        y += GamePanel.trueTileSize * 2;
        gc.fillText(gameOverText, x, y);

    }

    private void drawEndingScreen(){

        //Background
        gc.setFill(Color.LIGHTSKYBLUE);
        gc.fillRect(0,0,GamePanel.screenWidth,GamePanel.screenHeight);

        String endingText = "CONGRATULATIONS!";
        gc.setFont(largeFont);
        gc.setFill(Color.WHITE);
        double x = GamePanel.trueTileSize * 4;
        int y = GamePanel.trueTileSize * 2;
        gc.fillText(endingText,x,y);

        endingText = "Total play time: " + df.format(gamePanel.playTimer) + " seconds";
        gc.setFont(smallFont);
        x = GamePanel.trueTileSize * 7.5;
        y += GamePanel.trueTileSize * 2;
        gc.fillText(endingText,x,y);

        endingText = "Press ESC to exit";
        gc.setFont(font);
        x = calculateTextX(gc,endingText);
        y += GamePanel.trueTileSize * 2;
        gc.fillText(endingText,x,y);

    }

    private static int calculateTextX(GraphicsContext gc, String txt){
        double textWidth = gc.getFont().getSize() * txt.length() * 0.5;
        return (int) ((GamePanel.screenWidth - textWidth) / 2);
    }

}
