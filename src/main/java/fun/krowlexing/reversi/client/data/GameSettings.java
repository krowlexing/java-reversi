package fun.krowlexing.reversi.client.data;

public class GameSettings {
    public int width;
    public int height;
    public int seconds;

    public GameSettings(int width, int height, int seconds) {
        this.width = width;
        this.height = height;
        this.seconds = seconds;
    }
}
