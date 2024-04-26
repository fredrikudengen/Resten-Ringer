// Made with help from chatGPT 22.04.24, but edited to fit game function

package no.uib.inf101.sample.sound;

import javax.sound.sampled.*;

public class SoundPlayer {

    public static void playBeerCanOpen() {
        try {
            AudioInputStream audioIn = AudioSystem
                    .getAudioInputStream(SoundPlayer.class.getResource("soundeffects/beer-can-open.wav"));

            Clip clip = AudioSystem.getClip();

            clip.open(audioIn);

            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void gunShot() {
        try {
            AudioInputStream audioIn = AudioSystem
                    .getAudioInputStream(SoundPlayer.class.getResource("soundeffects/gun-shot.wav"));

            Clip clip = AudioSystem.getClip();

            clip.open(audioIn);

            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void gameOver() {
        try {
            AudioInputStream audioIn = AudioSystem
                    .getAudioInputStream(SoundPlayer.class.getResource("soundeffects/game-over.wav"));

            Clip clip = AudioSystem.getClip();

            clip.open(audioIn);

            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void manDie1() {
        try {
            AudioInputStream audioIn = AudioSystem
                    .getAudioInputStream(SoundPlayer.class.getResource("soundeffects/man-die.wav"));

            Clip clip = AudioSystem.getClip();

            clip.open(audioIn);

            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void mandDie2() {
        try {
            AudioInputStream audioIn = AudioSystem
                    .getAudioInputStream(SoundPlayer.class.getResource("soundeffects/man-die2.wav"));

            Clip clip = AudioSystem.getClip();

            clip.open(audioIn);

            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void manDie3() {
        try {
            AudioInputStream audioIn = AudioSystem
                    .getAudioInputStream(SoundPlayer.class.getResource("soundeffects/man-die3.wav"));

            Clip clip = AudioSystem.getClip();

            clip.open(audioIn);

            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}