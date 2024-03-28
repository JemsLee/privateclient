package com.pim.client;


import com.alibaba.fastjson.JSONObject;
import com.pim.client.beans.MessageBody;
import com.pim.client.observer.PriManager;
import com.pim.client.observer.PriObserver;
import com.pim.client.utils.IMTimeUtils;


public class PriClientUser1 implements PriObserver {


    String fromUid = "game_1001_lg1000368";
    String toUid = "game_1001_lg1000368";//for test
    String token = "BRjwaXrqdZj7oleGJEbh4jBbuyPV6FSS";
    String deviceId = IMTimeUtils.getNanoTime() + "";

//    String serverIp = "wss://im.polardata.cc"; //测试IM
    String serverIp = "wss://im.polarmeta.cc"; //预发布IM




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
            if(jsonObject.getString("resDesc").indexOf("登录成功") >= 0){
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

        for (int i = 0; i < 10; i++) {
            MessageBody messageBody = new MessageBody();
            messageBody.setEventId("1000001");
            messageBody.setFromUid(fromUid);
            messageBody.setToUid(toUid);
            messageBody.setMType("1");
            messageBody.setCTimest(IMTimeUtils.getTimeSt());
            messageBody.setDataBody("private message for test 🍋🍋🍋🍌🍌🍌🍇🍇🍇🍇");
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
