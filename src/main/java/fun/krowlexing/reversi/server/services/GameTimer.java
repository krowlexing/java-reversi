package fun.krowlexing.reversi.server.services;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class GameTimer {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final Runnable onTimeout;
    private final long durationSeconds;
    private ScheduledFuture<?> future;

    public GameTimer(long durationSeconds, Runnable onTimeout) {
        this.durationSeconds = durationSeconds;
        this.onTimeout = onTimeout;
    }

    public void start() {
        future = scheduler.schedule(this::timeout, durationSeconds, TimeUnit.SECONDS);
    }

    public long stop() {
        scheduler.shutdownNow();
        return future.getDelay(TimeUnit.SECONDS);
    }

    private void timeout() {
        onTimeout.run();
    }
}
