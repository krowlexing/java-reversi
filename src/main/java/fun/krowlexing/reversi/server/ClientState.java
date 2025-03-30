package fun.krowlexing.reversi.server;

import fun.krowlexing.reversi.client.data.Point;
import fun.krowlexing.reversi.client.data.Size;
import fun.krowlexing.reversi.server.services.ColorService;
import fun.krowlexing.reversi.server.services.GameTimer;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import static fun.krowlexing.reversi.logger.Logger.print;

public class ClientState {
    public boolean auth = false;
    private int playerId = -1;

    public int secondsLeft;
    public int totalTime;

    public boolean gameStarted = false;

    private final ColorService colorService;

    private int totalPairs = 0;
    public int pairsOpened = 0;

    public Runnable onTimeout;
    public Consumer<Long> onSuccess;
    private final Set<Point> completed = new HashSet<>();
    private GameTimer timer;

    public ClientState(ColorService colorService) {
        this.colorService = colorService;
    }

    public ClientState setPlayerId(int id) {
        this.auth = true;
        this.playerId = id;
        return this;
    }

    public int playerId() {
        return this.playerId;
    }

    public void prepareGame(int width, int height, int seconds) {
        this.colorService.init(new Size(width, height));
        this.totalPairs = colorService.getCellCount(new Size(width, height)) / 2;

        timer = new GameTimer(seconds, onTimeout);
        timer.start();

        this.totalTime = seconds;


    }


    public boolean isMatch(Point a, Point b) {
        if (completed.contains(a) || completed.contains(b)) {
            return false;
        }

        this.pairsOpened += 1;

        var result = this.colorService.isMatch(a, b);

        if (result) {
            completed.add(a);
            completed.add(b);
        }

        if (isDone()) {
            print("done bam wam");
            var secondsLeft = timer.stop();
            onSuccess.accept(secondsLeft);
        }

        print("Left: " + (totalPairs * 2 - completed.size()));

        return result;
    }

    public boolean isDone() {
        return this.completed.size() == totalPairs * 2;
    }

    public int color(Point point) {
        return this.colorService.color(point);
    }
}
