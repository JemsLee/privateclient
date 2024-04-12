package io.github.jemslee;


import com.alibaba.fastjson.JSONObject;
import io.github.jemslee.beans.MessageBody;
import io.github.jemslee.observer.PriManager;
import io.github.jemslee.observer.PriObserver;
import io.github.jemslee.utils.IMTimeUtils;


public class PriClientUser1 implements PriObserver {


    String fromUid = "1001_30320";
    String toUid = "1001_30319";//for test
    String token = "0yKJdi584wRLrlSHrlAID0nrRegyzVe5";
    String deviceId = IMTimeUtils.getNanoTime() + "";

//    String serverIp = "wss://im.polardata.cc"; //测试IM
//    String serverIp = "wss://im.polarmeta.cc"; //预发布IM

    String serverIp = "ws://127.0.0.1:9922"; //测试Local




    public static void main(String[] args) {

        PriClientUser1 priClientUser1 = new PriClientUser1();
        priClientUser1.init();
    }


    private void init(){
        PriManager.instance().imIPAndPort = serverIp;
        PriManager.instance().fromUid = fromUid;
        PriManager.instance().token = token;
        PriManager.instance().deviceId = deviceId;
        PriManager.instance().priManagerSubject.addObserver(this);
        PriManager.instance().startSocket();

    }



    @Override
    public void onIMMessage(String message) {

        JSONObject jsonObject = JSONObject.parseObject(message);
        if(jsonObject.containsKey("resDesc")){
            if(jsonObject.getString("resDesc").indexOf("登录成功") >= 0
                    || jsonObject.getString("resDesc").indexOf("Login successful") >= 0){
                sendToOtherUser();
            }
        }
        System.out.println("im message received:" + message);

    }

    @Override
    public void onIMError(String message) {
        System.out.println("im error message received:" + message);
    }


    /**
     * 私发信息给用户
     */
    private void sendToOtherUser(){

        for (int i = 0; i < 30000; i++) {
            MessageBody messageBody = new MessageBody();
            messageBody.setEventId("1000001");
            messageBody.setFromUid(fromUid);
            messageBody.setToUid(toUid);
            messageBody.setMType("1");
            messageBody.setIsCache("1");
            messageBody.setCTimest(IMTimeUtils.getTimeSt());
            messageBody.setDataBody(i+ " private message for test 🍋🍋🍋🍌🍌🍌🍇🍇🍇🍇");
            PriManager.instance().sendMessage(messageBody);
        }

    }


    /**
     * 创建一个聊天群
     */
    private void createGroup(){
        MessageBody messageBody = new MessageBody();
        messageBody.setEventId("5000000");
        messageBody.setFromUid(fromUid);
        messageBody.setToUid("");
        messageBody.setIsGroup("1");
        messageBody.setGroupId("JOE100000000");
        messageBody.setCTimest(IMTimeUtils.getTimeSt());
        messageBody.setDataBody("");
        PriManager.instance().sendMessage(messageBody);
    }

    /**
     * 加入一个聊天群
     */
    private void joinGroup(){
        MessageBody messageBody = new MessageBody();
        messageBody.setEventId("5000001");
        messageBody.setFromUid(fromUid);
        messageBody.setToUid("");
        messageBody.setIsGroup("1");
        messageBody.setGroupId("JOE100000000");
        messageBody.setCTimest(IMTimeUtils.getTimeSt());
        messageBody.setDataBody("");
        PriManager.instance().sendMessage(messageBody);
    }

    /**
     * 离开一个聊天群
     */
    private void leaveGroup(){
        MessageBody messageBody = new MessageBody();
        messageBody.setEventId("5000002");
        messageBody.setFromUid(fromUid);
        messageBody.setToUid("");
        messageBody.setIsGroup("1");
        messageBody.setGroupId("JOE100000000");
        messageBody.setCTimest(IMTimeUtils.getTimeSt());
        messageBody.setDataBody("");
        PriManager.instance().sendMessage(messageBody);
    }

    /**
     * 解散一个聊天群
     */
    private void disbandGroup(){
        MessageBody messageBody = new MessageBody();
        messageBody.setEventId("5000003");
        messageBody.setFromUid(fromUid);
        messageBody.setToUid("");
        messageBody.setIsGroup("1");
        messageBody.setGroupId("JOE100000000");
        messageBody.setCTimest(IMTimeUtils.getTimeSt());
        messageBody.setDataBody("");
        PriManager.instance().sendMessage(messageBody);
    }

    /**
     * 发送群消息
     */
    private void sendMessageToGroup(){
        MessageBody messageBody = new MessageBody();
        messageBody.setEventId("5000004");
        messageBody.setFromUid(fromUid);
        messageBody.setToUid("");
        messageBody.setIsGroup("1");
        messageBody.setGroupId("JOE100000000");
        messageBody.setCTimest(IMTimeUtils.getTimeSt());
        messageBody.setDataBody("group message for test 🍋🍋🍋🍌🍌🍌🍇🍇🍇🍇");
        PriManager.instance().sendMessage(messageBody);
    }



}
