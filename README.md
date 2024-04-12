How to use this client</br></br></br>
->Download the source code and use it directly</br></br></br>

->Referenced through maven, it is recommended to use this mode</br></br>

pom.xml</br></br>

-< dependency></br>
     -< groupId>io.github.jemslee</groupId></br>
     -< artifactId>privateimclient</artifactId></br>
     -< version>1.0.2</version></br>
-< /dependency></br>
</br></br>

Application side:</br></br></br>

import com.alibaba.fastjson.JSONObject;
import io.github.jemslee.beans.MessageBody;
import io.github.jemslee.observer.PriManager;
import io.github.jemslee.observer.PriObserver;
import io.github.jemslee.utils.IMTimeUtils;


public class SokcetClientUser1 implements PriObserver {


     String fromUid = "1001_30320";
     String toUid = "1001_30319";//for test
     String token = "Q4cHrr9vTUZX5LmxuIbhHIWsR6Zm7TgG";
     String deviceId = IMTimeUtils.getNanoTime() + "";

     String serverIp = "ws://127.0.0.1:9922"; //Test Local




     public static void main(String[] args) {

         SokcetClientUser1 sokcetClientUser1 = new SokcetClientUser1();
         sokcetClientUser1.init();
     }


     public void init(){
         PriManager.instance().imIPAndPort = serverIp;
         PriManager.instance().fromUid = fromUid;
         PriManager.instance().token = token;
         PriManager.instance().deviceId = deviceId;
         PriManager.instance().priManagerSubject.addObserver(this);
         PriManager.instance().startSocket();
         System.out.println("---");
     }



     @Override
     public void onIMMessage(String message) {

         JSONObject jsonObject = JSONObject.parseObject(message);
         if(jsonObject.containsKey("resDesc")){
             if(jsonObject.getString("resDesc").indexOf("Login successful") >= 0
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
      * Send private messages to users
      */
     private void sendToOtherUser(){

         for (int i = 0; i < 1; i++) {
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
      * Create a chat group
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
      * Join a chat group
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
      * Leave a chat group
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
      * Disband a chat group
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
      * Send group messages
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
