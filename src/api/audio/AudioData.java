package api.audio;

import javax.sound.sampled.*;

public class AudioData {
    public AudioInputStream audioInputStream = null;
    public DataLine dataLine = null;
    public PlayThread thread = null;
}
