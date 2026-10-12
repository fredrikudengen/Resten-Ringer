// Made with help from chatGPT 22.04.24, but edited to fit game function

package no.uib.inf101.resten_ringer.sound;

import javax.sound.sampled.*;

public class SoundPlayer {

    /**
     * Plays a .wav sound effect from resources. The Clip and AudioInputStream are
     * closed once playback finishes, instead of being left open for the rest of
     * the game.
     */
    private static void playSound(String resourcePath) {
        try {
            AudioInputStream audioIn = AudioSystem
                    .getAudioInputStream(SoundPlayer.class.getResource(resourcePath));

            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            audioIn.close();

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays sound effect of beer can opening.
     */
    public static void playBeerCanOpen() {
        playSound("soundeffects/beer-can-open.wav");
    }

    /**
     * Plays sound effect of gunshot.
     */
    public static void gunShot() {
        playSound("soundeffects/gun-shot.wav");
    }

    /**
     * Plays game over tune.
     */
    public static void gameOver() {
        playSound("soundeffects/game-over.wav");
    }

    /**
     * Plays sound effect of man dying.
     */
    public static void manDie1() {
        playSound("soundeffects/man-die.wav");
    }

    /**
     * Plays sound effect of man dying.
     */
    public static void mandDie2() {
        playSound("soundeffects/man-die2.wav");
    }

    /**
     * Plays sound effect of man dying.
     */
    public static void manDie3() {
        playSound("soundeffects/man-die3.wav");
    }
}
