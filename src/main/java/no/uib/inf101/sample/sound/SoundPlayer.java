package no.uib.inf101.sample.sound;

import javax.sound.sampled.*;

public class SoundPlayer {
    
    public static void playBeerCanOpen() {
        try {
            // Open an audio input stream for sound clip 1.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(SoundPlayer.class.getResource("beer-can-open.wav"));
            
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            
            // Play the sound clip.
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void gunShot() {
        try {
            // Open an audio input stream for sound clip 2.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(SoundPlayer.class.getResource("gun-shot.wav"));
            
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            
            // Play the sound clip.
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void gameOver() {
        try {
            // Open an audio input stream for sound clip 3.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(SoundPlayer.class.getResource("game-over.wav"));
            
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            
            // Play the sound clip.
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void manDie1() {
        try {
            // Open an audio input stream for sound clip 3.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(SoundPlayer.class.getResource("man-die.wav"));
            
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            
            // Play the sound clip.
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void mandDie2() {
        try {
            // Open an audio input stream for sound clip 3.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(SoundPlayer.class.getResource("man-die2.wav"));
            
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            
            // Play the sound clip.
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void manDie3() {
        try {
            // Open an audio input stream for sound clip 3.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(SoundPlayer.class.getResource("man-die3.wav"));
            
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            
            // Play the sound clip.
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}