package com.t3h.Manager;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundManager {
    public static void play(String name){
        try {
            File f = new File("src/sound/"+name);
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(f);
            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);

            //clip.loop(2);               //lặp lại số lần nhạc
            clip.start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
