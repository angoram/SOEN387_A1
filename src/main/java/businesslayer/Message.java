package businesslayer;

import java.util.Date;

public class Message {
    private String postedBy;
    private String messageContent;
    private Date datePosted;
    public Message(String _postedBy, String _messageContent, Date _datePosted){
        datePosted = _datePosted;
        messageContent = _messageContent;
        postedBy = _postedBy;
    }
    public String getPostedBy(){
        return postedBy;
    }
    public String getMessageContent(){
        return messageContent;
    }
    public Date getDatePosted(){
        return datePosted;
    }
}
