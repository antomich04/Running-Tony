package Running_tony;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.Objects;

public class MusicHandler{
    MediaPlayer mediaPlayer;
    Media[] musicMedia = new Media[4];

    public MusicHandler(){
        musicMedia[0] = new Media(Objects.requireNonNull(getClass().getResource("/Music/MainMusic.wav")).toString());
        musicMedia[1] = new Media(Objects.requireNonNull(getClass().getResource("/Music/GameOver.wav")).toString());
        musicMedia[2] = new Media(Objects.requireNonNull(getClass().getResource("/Music/MenuMusic.wav")).toString());
        musicMedia[3] = new Media(Objects.requireNonNull(getClass().getResource("/Music/EndingMusic.wav")).toString());
    }

    public void setFile(int index){
        if(mediaPlayer!=null){
            mediaPlayer.stop();
        }
        mediaPlayer = new MediaPlayer(musicMedia[index]);
    }

    public void start(){
        if(mediaPlayer!=null){
            mediaPlayer.play();
        }
    }

    public void loopMusic(){
        if(mediaPlayer!=null){
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
    }

    public void loopSFX(){
        if(mediaPlayer!=null){
            mediaPlayer.setCycleCount(0);
            mediaPlayer.play();
        }
    }

    public void stop(){
        if(mediaPlayer!=null){
            mediaPlayer.stop();
        }
    }
}
