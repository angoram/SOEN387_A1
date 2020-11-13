package businesslayer;

import java.util.Date;

public class Message {
    private String postedBy;
    private String messageContent;
    private Date datePosted;
    private String attachmentFileName;
    public Message(String _postedBy, String _messageContent, String _attachmentFileName, Date _datePosted){
        datePosted = _datePosted;
        messageContent = _messageContent;
        postedBy = _postedBy;
        attachmentFileName = _attachmentFileName;

    }
    public String getAttachmentFilePath() { return attachmentFileName; }
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
