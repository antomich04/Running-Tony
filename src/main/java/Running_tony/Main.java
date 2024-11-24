package Running_tony;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage stage){

        //Initialization of Game Panel (Scene)
        Group root = new Group(GamePanel.canvas);
        GamePanel gamePanel = new GamePanel(root);

        //Initialization of the stage
        stage.setScene(gamePanel);
        stage.setTitle("Running Tony");
        stage.getIcons().add(new Image("Game_Icon.png"));
        stage.setResizable(false);

        //Checks if the window has lost focus
        stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){ //Window gained focus
                gamePanel.getMusicHandler().start();
            }else{ //Window lost focus
                gamePanel.getMusicHandler().stop();
                gamePanel.focusLost();
            }
        });

        stage.show();

        //The program terminates once the window is closed
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        //The game begins
        gamePanel.startGameThread();


    }

    public static void main(String[] args) {
        launch(args);
    }
}