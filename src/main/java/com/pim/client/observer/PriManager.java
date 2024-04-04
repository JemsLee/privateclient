package com.pim.client.observer;

import com.alibaba.fastjson.JSONObject;
import com.pim.client.beans.MessageBody;
import com.pim.client.socket.PriWebSocketClient;
import com.pim.client.utils.EncryptUtil;

import javax.net.ssl.*;
import java.net.Socket;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import java.util.concurrent.*;


public class PriManager {

    private LinkedList<String> linkedList = new LinkedList<>();
    private static PriManager priManager;


    public String imIPAndPort = "";
    public String fromUid = "";
    public String token = "";
    public String deviceId = "";


    public PriWebSocketClient priWebSocketClient;
    public PriManagerSubject priManagerSubject;

    ScheduledExecutorService resendScheduler;

    private static final int CORE_POOL_SIZE = 200;
    private static final int MAX_POOL_SIZE = 10000;
    private static final int QUEUE_CAPACITY = 1;
    private static final Long KEEP_ALIVE_TIME = 1L;
    ThreadPoolExecutor executor = null;
    private void  initExecutor(){
        executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }


    private PriManager() {
        if(executor == null){
            initExecutor();
        }
        priManagerSubject = new PriManagerSubject();
    }

    public static PriManager instance() {
        if (null == priManager) {
            priManager = new PriManager();
        }
        return priManager;
    }

    public void setSSL() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{MyTrustManager.trustManager}, null);
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            priWebSocketClient.setSocketFactory(socketFactory);
        } catch (Exception e) {

        }
    }

    /**
     * Initialize and start
     */
    public void startSocket() {

        try {
            URI serverUri = URI.create(imIPAndPort);
            priWebSocketClient = new PriWebSocketClient(serverUri);
            if (imIPAndPort.indexOf("wss://") >= 0) {
                setSSL();
            }
            priWebSocketClient.priManagerSubject = priManagerSubject;
            priWebSocketClient.fromUid = fromUid;
            priWebSocketClient.token = token;
            priWebSocketClient.deviceId = deviceId;
            priWebSocketClient.start();
            scheduleResend();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * stop
     */
    public void stopSocket() {
        priWebSocketClient.stopSchedulePing();
        priWebSocketClient.stopSchedulePing();
        priWebSocketClient.close();

    }

    /**
     * Send a message
     *
     * @param messageBody
     * @return
     */
    public boolean sendMessage(MessageBody messageBody) {
        boolean sendrs = false;
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(messageBody);
        String str = EncryptUtil.encrypt(EncryptUtil.getUidKey(fromUid), jsonObject.toJSONString());
        try {
            Thread.sleep(2);
            if (priWebSocketClient.isLogin) {
                //priWebSocketClient.send(str);
                MessageSender messageSender = new MessageSender();
                messageSender.priWebSocketClient = priWebSocketClient;
                messageSender.message = str;
                executor.execute(messageSender);
                sendrs = true;
            } else {
                linkedList.addFirst(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendrs;
    }


    private void scheduleResend() {
        resendScheduler = Executors.newSingleThreadScheduledExecutor();
        long delay = 10; //Delay execution time (seconds)
        long period = 5; //Execution interval (seconds)
        resendScheduler.scheduleAtFixedRate(this::checkReSend, delay, period, TimeUnit.SECONDS);
    }

    private void checkReSend() {
        if (priWebSocketClient.isLogin) {
            while (!linkedList.isEmpty()) {
                priWebSocketClient.send(linkedList.removeLast());
            }
        }
    }


}