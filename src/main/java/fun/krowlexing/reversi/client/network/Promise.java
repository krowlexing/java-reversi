package fun.krowlexing.reversi.client.network;

import java.util.function.Consumer;

public class Promise<T> {

    private T value;
    private Throwable error;
    private boolean isResolved = false;
    private boolean isRejected = false;
    private Consumer<T> onResolved;
    private Consumer<Throwable> onRejected;

    public Promise() {

    }

    synchronized public void resolve(T value) {
        this.value = value;
        this.isResolved = true;
        if (onResolved != null) {
            onResolved.accept(value);
        }
    }

    public void reject(Throwable error) {
        this.error = error;
        this.isRejected = true;
        if (onRejected != null) {
            onRejected.accept(error);
        }
    }

    synchronized public Promise<T> then(Consumer<T> onResolved) {
        this.onResolved = onResolved;
        if (isResolved) {
            onResolved.accept(value);
        }
        return this;
    }

    public Promise<T> catchError(Consumer<Throwable> onRejected) {
        this.onRejected = onRejected;
        if (isRejected) {
            onRejected.accept(error);
        }
        return this;
    }
}
