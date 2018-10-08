package api.audio;

abstract public class PlayThread extends Thread {
    public abstract void setLooping(boolean loop);
    public abstract void playSound();
    public abstract void resumeSound();
    public abstract void stopSound();
}
