package Running_tony;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.EventListener;

public class KeyInputHandler implements EventListener {

    GamePanel gamePanel;
    public boolean leftPressed, rightPressed, shiftPressed;
    public boolean spacePressed = false;
    public KeyInputHandler(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    public void keyPressed(KeyEvent e){
        KeyCode code = e.getCode();

        if(gamePanel.gameState==gamePanel.TITLE_SCREEN){

            //Menu selection indicator traversal
            if(code==KeyCode.UP || code==KeyCode.W){
                gamePanel.ui.menuSelector--;
                if(gamePanel.ui.menuSelector<0){
                    gamePanel.ui.menuSelector = 2;
                }
            }
            if(code==KeyCode.DOWN || code==KeyCode.S){
                gamePanel.ui.menuSelector++;
                if(gamePanel.ui.menuSelector>2){
                    gamePanel.ui.menuSelector = 0;
                }
            }

            //Menu selection action
            if(code == KeyCode.ENTER){
                if(gamePanel.ui.menuSelector==0){ //START was pressed
                    gamePanel.resetGame();
                }else if(gamePanel.ui.menuSelector==1){ //ABOUT pressed
                    gamePanel.gameState = gamePanel.ABOUT_SCREEN;
                    gamePanel.ui.menuSelector = -1;
                }else if(gamePanel.ui.menuSelector==2){ //EXIT pressed
                    Platform.exit();
                    System.exit(0);
                }
            }
        }else if(gamePanel.gameState==gamePanel.PLAYING){ //Player movement must be done only when playing
            if(code==KeyCode.A || code==KeyCode.LEFT){
                leftPressed = true;
            }
            if(code==KeyCode.D || code==KeyCode.RIGHT){
                rightPressed = true;
            }
            if(code==KeyCode.SHIFT){
                shiftPressed = true;
            }
            if(code==KeyCode.SPACE){
                spacePressed = true;
            }
        }else if(gamePanel.gameState==gamePanel.PAUSED){

            //Pause selection indicator traversal
            if(code==KeyCode.UP || code==KeyCode.W){
                gamePanel.ui.pauseSelector--;
                if(gamePanel.ui.pauseSelector<0){
                    gamePanel.ui.pauseSelector = 3;
                }
            }
            if(code==KeyCode.DOWN || code==KeyCode.S){
                gamePanel.ui.pauseSelector++;
                if(gamePanel.ui.pauseSelector>3){
                    gamePanel.ui.pauseSelector = 0;
                }
            }

            //Pause selection action
            if(code==KeyCode.ENTER){
                if(gamePanel.ui.pauseSelector==0){ //RESUME pressed
                    gamePanel.gameState = gamePanel.PLAYING;
                    gamePanel.ui.pauseSelector = -1;
                }else if(gamePanel.ui.pauseSelector==1){ //SETTINGS was pressed
                    gamePanel.gameState = gamePanel.OPTIONS;
                    gamePanel.ui.pauseSelector = -1;
                }else if(gamePanel.ui.pauseSelector==2){ //CONTROLS pressed
                    gamePanel.gameState = gamePanel.CONTROLS;
                    gamePanel.ui.pauseSelector = -1;
                }else if(gamePanel.ui.pauseSelector==3){ //EXIT pressed, exits to main menu
                    gamePanel.gameState = gamePanel.TITLE_SCREEN;
                    gamePanel.ui.menuSelector = -1;
                    gamePanel.ui.pauseSelector = -1;
                    gamePanel.stopMusic(); //Stops main music
                    gamePanel.playMusic(1); //Plays menu music
                }
            }
        }else if(gamePanel.gameState==gamePanel.OPTIONS){
            if(code==KeyCode.W || code==KeyCode.UP){
                gamePanel.ui.optionsSelector--;
                if(gamePanel.ui.optionsSelector<0){
                    gamePanel.ui.optionsSelector = 1;
                }
            }
            if(code==KeyCode.S || code==KeyCode.DOWN){
                gamePanel.ui.optionsSelector++;
                if(gamePanel.ui.optionsSelector>1){
                    gamePanel.ui.optionsSelector = 0;
                }
            }
            if (code == KeyCode.A || code == KeyCode.LEFT) {
                if (gamePanel.ui.optionsSelector == 0) {
                    gamePanel.ui.musicVolumeAmplitude = Math.max(0, Math.round((gamePanel.ui.musicVolumeAmplitude - 0.1f) * 10.0f) / 10.0f);
                } else if (gamePanel.ui.optionsSelector == 1) {
                    gamePanel.ui.sfxVolumeAmplitude = Math.max(0, Math.round((gamePanel.ui.sfxVolumeAmplitude - 0.1f) * 10.0f) / 10.0f);
                }
            }

            if (code == KeyCode.D || code == KeyCode.RIGHT) {
                if (gamePanel.ui.optionsSelector == 0) {
                    gamePanel.ui.musicVolumeAmplitude = Math.min(1.0f, Math.round((gamePanel.ui.musicVolumeAmplitude + 0.1f) * 10.0f) / 10.0f);
                } else if (gamePanel.ui.optionsSelector == 1) {
                    gamePanel.ui.sfxVolumeAmplitude = Math.min(1.0f, Math.round((gamePanel.ui.sfxVolumeAmplitude + 0.1f) * 10.0f) / 10.0f);
                }
            }
        }else if(gamePanel.gameState==GamePanel.GAME_OVER){
            if(code==KeyCode.R){ //RESTART pressed
                gamePanel.spawnPlayer(100, GamePanel.screenHeight - (2 * GamePanel.trueTileSize)+5);
                gamePanel.gameState = gamePanel.PLAYING;
                gamePanel.stopSFX();
                gamePanel.playMusic(0);
            }else if(code==KeyCode.X){ //EXIT pressed, exits to main menu
                gamePanel.gameState = gamePanel.TITLE_SCREEN;
                GamePanel.currentMap = 0;
                gamePanel.stopSFX();
                gamePanel.playMusic(1); //Plays menu music
            }
        }


        if(code==KeyCode.ESCAPE){
            if(gamePanel.gameState==gamePanel.ABOUT_SCREEN){ //Exits from ABOUT to main menu
                gamePanel.gameState = gamePanel.TITLE_SCREEN;
            }else if(gamePanel.gameState==gamePanel.OPTIONS){
                gamePanel.gameState = gamePanel.PAUSED;
                gamePanel.ui.optionsSelector = -1;
            }else{
                if(gamePanel.gameState==gamePanel.PLAYING || gamePanel.gameState==gamePanel.CONTROLS){ //Pauses the game/exits from CONTROLS to pause menu
                    gamePanel.gameState = gamePanel.PAUSED;
                }else if(gamePanel.gameState==gamePanel.PAUSED){ //Resumes the game
                    gamePanel.gameState = gamePanel.PLAYING;
                }else if(gamePanel.gameState==gamePanel.ENDING){ //Exits to main menu
                    gamePanel.gameState = gamePanel.TITLE_SCREEN;
                    gamePanel.stopMusic();
                    gamePanel.playMusic(1); //Plays menu music
                    gamePanel.playTimer = 0;
                }
            }
        }
    }

    public void keyReleased(KeyEvent e){
        KeyCode code = e.getCode();
        if(code==KeyCode.A || code==KeyCode.LEFT){
            leftPressed = false;
        }
        if(code==KeyCode.D || code==KeyCode.RIGHT){
            rightPressed = false;
        }
        if(code==KeyCode.SHIFT){
            shiftPressed = false;
        }
        if(code==KeyCode.SPACE){
            spacePressed = false;
        }
    }

    //Resets all movement booleans when the window loses focus
    public void resetKeys(){
        leftPressed = false;
        rightPressed = false;
        spacePressed = false;
        shiftPressed = false;
    }
}
