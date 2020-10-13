package businesslayer;


import java.util.ArrayList;
import java.util.Date;

/**
 * com.company.ChatManager implementation
 */
public class ChatManager {
    private static ArrayList<Message> messages = new ArrayList<Message>();

    public static void postMessage(String _userName, String _message){
        Message message = new Message(_userName,_message,new Date(System.currentTimeMillis()));
        messages.add(message);
    }
    public static void clearChat(Date _d1, Date _d2){
        if(_d1 == null && _d2 == null){
            messages.clear();
        }
        else{
            for(Message m : messages) {
                if (_d1 == null && m.getDatePosted().before(_d2)) {
                    messages.remove(m);
                } else if (_d2 == null && m.getDatePosted().after(_d1)) {
                    messages.remove(m);
                } else if (m.getDatePosted().after(_d1) && m.getDatePosted().before(_d2)) {
                    messages.remove(m);
                }
            }
        }
    }
    public static ArrayList<Message> listMessages(Date _d1, Date _d2){
        ArrayList<Message> messageList = new ArrayList<Message>();
        if(_d1 == null && _d2 == null){
            messageList = new ArrayList<Message>(messages);
        }
        else {
            for (Message m : messages) {
                if (_d1 == null && m.getDatePosted().before(_d2)) {
                    messageList.add(m);
                } else if (_d2 == null && m.getDatePosted().after(_d1)) {
                    messageList.add(m);
                } else if (m.getDatePosted().after(_d1) && m.getDatePosted().before(_d2)) {
                    messageList.add(m);
                }
            }
        }
        return messageList;
    }

}
