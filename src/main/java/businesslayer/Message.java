package businesslayer;

import java.io.InputStream;
import java.util.Date;

public class Message implements Comparable<Message>{
    private String postedBy;
    private String messageContent;
    private Date datePosted;
    private InputStream attachmentBytes;
    private int messageID;
    private boolean edited;
    public Message(String _postedBy, String _messageContent, InputStream _attachmentBytes,  Date _datePosted, int _messageID){
        datePosted = _datePosted;
        messageContent = _messageContent;
        postedBy = _postedBy;
        attachmentBytes= _attachmentBytes;
        messageID = _messageID;
        edited = false;

    }
    public InputStream getAttachmentBytes() { return attachmentBytes; }
    public String getPostedBy(){
        return postedBy;
    }
    public String getMessageContent(){
        return messageContent;
    }
    public Date getDatePosted(){
        return datePosted;
    }
    public int getMessageID(){
        return messageID;
    }
    public void setMessageContent(String m){
        messageContent = m;
    }
    public boolean isEdited(){
        return edited;
    }
    public void setEdited(boolean isEdited){
        edited = isEdited;
    }
    @Override
    public int compareTo(Message o) {
        return datePosted.compareTo(o.getDatePosted());
    }
}
