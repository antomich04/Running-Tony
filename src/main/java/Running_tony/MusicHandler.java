package Running_tony;

import javafx.scene.media.AudioClip;


import java.net.URL;

public class MusicHandler {
    AudioClip audioClip;
    URL[] musicURL = new URL[4];
    public MusicHandler(){
        musicURL[0] = getClass().getResource("/Music/MainMusic.wav");
        musicURL[1] = getClass().getResource("/Music/GameOver.wav");
        musicURL[2] = getClass().getResource("/Music/MenuMusic.wav");
        musicURL[3] = getClass().getResource("/Music/EndingMusic.wav");
    }

    public void setFile(int index){
        audioClip = new AudioClip(musicURL[index].toString());
    }

    public void start(){
        if (audioClip != null && !audioClip.isPlaying()) {
            audioClip.play();
        }
    }

    public void loopMusic(){
        if (audioClip != null) {
            audioClip.setCycleCount(AudioClip.INDEFINITE);
        }
    }

    public void loopSFX(){
        if (audioClip != null) {
            audioClip.setCycleCount(0);
        }
    }

    public void stop(){
        if (audioClip != null && audioClip.isPlaying()) {
            audioClip.stop();
        }
    }

}
