package io.github.jemslee.observer;

public interface PriObserver {
    public void onIMMessage(String message);
    public void onIMError(String message);
}
