package io.github.jemslee.observer;

import io.github.jemslee.socket.PriWebSocketClient;

import java.util.Random;

public class MessageSender extends Thread{

    public PriWebSocketClient priWebSocketClient;
    public String message;

    @Override
    public void run() {
        super.run();

        try {
            int randomNumber = new Random().nextInt(5);
            Thread.sleep(randomNumber);
            priWebSocketClient.send(message);
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }
}
