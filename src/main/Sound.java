package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip;//use it to access the java sound
    URL SoundURL[]=new URL[30]; //store the file path
    public Sound(){
        SoundURL[0]=getClass().getResource("/res/sound/Main.wav");
        SoundURL[1]=getClass().getResource("/res/sound/coin.wav");
        SoundURL[2]=getClass().getResource("/res/sound/powerup.wav");
        SoundURL[3]=getClass().getResource("/res/sound/unlock.wav");
        SoundURL[4]=getClass().getResource("/res/sound/fanfare.wav");
        SoundURL[5]=getClass().getResource("/res/sound/Adventure.wav");
        SoundURL[6]=getClass().getResource("/res/sound/hitmonster.wav");
        SoundURL[7]=getClass().getResource("/res/sound/gameover.wav.wav");



    }
    public void setFile(int i){
        try{
            AudioInputStream ais= AudioSystem.getAudioInputStream(SoundURL[i]);
            clip=AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Play(){
        clip.start();
    }
    public void Loop(){
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
