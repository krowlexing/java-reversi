package fun.krowlexing.reversi.client.components;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Timer extends Label {
    private Timeline timeline;
    private int seconds;

    public Timer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            this.seconds -= 1;
            this.setText(this.seconds + "");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    public void setTime(int seconds) {
        this.seconds = seconds;
        this.setText(seconds + "");

    }

    public void start() {
        timeline.play();
    }

    public void pause() {
        timeline.stop();
    }
}
