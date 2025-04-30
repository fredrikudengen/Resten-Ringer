package sound;

import java.io.InputStream;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;

public class GameSong implements Runnable {

    private InputStream song;
    private String songName;
    private Sequencer sequencer;

    public GameSong() {
        this.setSongName("metroid-theme.mid");
    }

    public void setSongName(String songName) {
        this.songName = songName;
        this.song = GameSong.class.getResourceAsStream("/resources/" + songName);
    }

    public String getSongName() {
        return songName;
    }

    @Override
    public void run() {
        this.doPlayMidi(song, true);
    }


    private void doPlayMidi(final InputStream is, final boolean loop) {
        try {
            this.doStopMidiSounds();
            (this.sequencer = MidiSystem.getSequencer()).setSequence(MidiSystem.getSequence(is));
            if (loop) {
                this.sequencer.setLoopCount(-1);
            }
            this.sequencer.open();
            this.sequencer.start();
        } catch (Exception e) {
            this.midiError("" + e);
        }
    }
    
    private void doStopMidiSounds() {
        try {
            if (this.sequencer == null || !this.sequencer.isRunning()) {
                return;
            }
            this.sequencer.stop();
            this.sequencer.close();
        } catch (Exception e) {
            this.midiError("" + e);
        }
        this.sequencer = null;
    }

    /**
     * Pauses the current midi song playing.
     */
    public void doPauseMidiSounds() {
        try {
            if (this.sequencer == null || !this.sequencer.isRunning()) {
                return;
            }
            this.sequencer.stop();
        } catch (Exception e) {
            this.midiError("" + e);
        }
    }

    private void midiError(final String msg) {
        System.err.println("Midi error: " + msg);
        this.sequencer = null;
    }

    public void setSongName(InputStream song) {
        this.song = song;
    }
}