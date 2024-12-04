package Running_tony;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.Objects;

public class MusicHandler{
    MediaPlayer mediaPlayer;
    MediaPlayer SFXPlayer;
    Media[] musicMedia = new Media[3];
    Media[] SFXMedia = new Media[1];

    public MusicHandler(){
        musicMedia[0] = new Media(Objects.requireNonNull(getClass().getResource("/Music/MainMusic.wav")).toString());
        musicMedia[1] = new Media(Objects.requireNonNull(getClass().getResource("/Music/MenuMusic.wav")).toString());
        musicMedia[2] = new Media(Objects.requireNonNull(getClass().getResource("/Music/EndingMusic.wav")).toString());

        SFXMedia[0] = new Media(Objects.requireNonNull(getClass().getResource("/Music/GameOver.wav")).toString());
    }

    public void setMusicFile(int index){
        if(mediaPlayer!=null){
            mediaPlayer.stop();
        }
        mediaPlayer = new MediaPlayer(musicMedia[index]);
    }

    public void setSFXFile(int index) {
        if (SFXPlayer != null) {
            SFXPlayer.stop();
        }
        SFXPlayer = new MediaPlayer(SFXMedia[index]);
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
        if(SFXPlayer!=null){
            SFXPlayer.setCycleCount(0);
            SFXPlayer.play();
        }
    }

    public void changeMusicVolume(float amplitude){
        if(mediaPlayer!=null){
            mediaPlayer.setVolume(amplitude);
        }
    }

    public void changeSFXVolume(float amplitude){
        if(SFXPlayer!=null){
            SFXPlayer.setVolume(amplitude);
        }
    }

    public void stop(){
        if(mediaPlayer!=null){
            mediaPlayer.stop();
        }
        if(SFXPlayer!=null){
            SFXPlayer.stop();
        }
    }
}
