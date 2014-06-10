/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Hanzo
 */
@SessionScoped
@ManagedBean
public class PushServiceBean implements Serializable {
    
     // The SENDER_ID here is the "Browser Key" that was generated when I
    // created the API keys for my Google APIs project.
    private static final String SENDER_ID = "AIzaSyByWm5bQ3QTebD9LmgjnSN1GVxiCCqWn4o";
     
    // This is a *cheat*  It is a hard-coded registration ID from an Android device
    // that registered itself with GCM using the same project id shown above.
    private static final String DROID_BIONIC = "APA91bEju-eB74DWRChlVt5gh7YfIVzNOr8gRYPisFbmcwBPlMJeGTYmdF7cYR3oL-F9KqmTey016drxmWAkYa4WQv9pQ_KvRzI1VUkql6ObbYGPkV7UBsm6pYoBw0dEk3veh60v3lVhDtLztWIbDc3XqtjU_fE_0g";
         
    // This array will hold all the registration ids used to broadcast a message.
    // for this demo, it will only have the DROID_BIONIC id that was captured 
    // when we ran the Android client app through Eclipse.
    private List<String> androidTargets = new ArrayList<String>();
    
    public void doPushNotification(ActionEvent actionEvent){
    doPush("Hello ", DROID_BIONIC);
    }
    
    protected void doPush(String userMessage,String deviceId) {
         
        // We'll collect the "CollapseKey" and "Message" values from our JSP page
        String collapseKey = "GCM_Message";
       androidTargets.add(deviceId);   
    
        // Instance of com.android.gcm.server.Sender, that does the
        // transmission of a Message to the Google Cloud Messaging service.
        Sender sender = new Sender(SENDER_ID);
         
        // This Message object will hold the data that is being transmitted
        // to the Android client devices.  For this demo, it is a simple text
        // string, but could certainly be a JSON object.
        Message message = new Message.Builder()
         
        // If multiple messages are sent using the same .collapseKey()
        // the android target device, if it was offline during earlier message
        // transmissions, will only receive the latest message for that key when
        // it goes back on-line.
        .collapseKey(collapseKey)
        .timeToLive(30)
        .delayWhileIdle(true)
        .addData("message", userMessage)
        .build();
         
        try {
            // use this for multicast messages.  The second parameter
            // of sender.send() will need to be an array of register ids.
            Result result = sender.send(message, deviceId, 1);
             
            if (result.getMessageId() != null) {
                System.out.println("Result of Push: "+result.toString());

            } else {
               String error = result.getErrorCodeName();
                System.out.println("Broadcast failure: " + error);
            }
             
        } catch (Exception e) {
            e.printStackTrace();
        }

                 
    }
    
}
