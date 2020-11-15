package businesslayer;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * com.company.ChatManager implementation
 */
public class ChatManager {
    public static ArrayList<Message> messages = new ArrayList<Message>();
    public static ArrayList<Message> messagesToShow = new ArrayList<>();
    private static int currentMessageID = 0;
    public static String sampleUsername = "Andrew";
    public static String samplePasswordHashed = "oc8mlmPAqmVDbcVZFHRRzjNEDDfU7yl+BOvuQuMtPAo="; // the message digest of "Kor"


    public static void postMessage(String _userName, String _message, InputStream _attachmentBytes){
        Message message = new Message(_userName,_message,_attachmentBytes,new Date(System.currentTimeMillis()),currentMessageID);
        messages.add(message);
        currentMessageID++;
    }
    public static void filterMessages(String userSearch,String hashtagSearch, String _d1, String _d2) throws IOException, ParseException {
        //returns a chat log and fits it at the bottom of the chat log container.
        messagesToShow.clear();
        for(Message m : messages){
            SimpleDateFormat formatter =new SimpleDateFormat("dd/MM/yyyy");

            Date d1 = null, d2 = null;
            if(!_d1.equals("")) {
                d1 = formatter.parse(_d1);
            }
            if(!_d2.equals("")){
                d2 = formatter.parse(_d2);
            }

            if (d1 == null) {
                if(d2 == null){
                    //
                }
                else if (!m.getDatePosted().before(d2)) {
                    continue;
                }
            }
            if (d2 == null) {
                if(d1 == null){
                    //
                }
                else if (!m.getDatePosted().after(d1)) {
                    continue;
                }
            }
            if(d1 != null && d2 != null){
                if(!(m.getDatePosted().before(d2) && m.getDatePosted().after(d1))){
                    continue;
                }
            }

            // date is checked for.
            ArrayList<String> hashtags = new ArrayList<>();
            for(int i = 0; i < hashtagSearch.length(); i++){
                if(hashtagSearch.charAt(i) == '#'){
                    int j = i + 1;
                    while(j != hashtagSearch.length() && hashtagSearch.charAt(j) != ',' && hashtagSearch.charAt(j) != ' '){
                        j++;
                    }
                    hashtags.add(hashtagSearch.substring(i,j));
                }
            }
            // Collected the hashtags

            if(userSearch.equals("") && hashtags.size() == 0){
                messagesToShow.add(m);
            }
            else if(userSearch.equals("") && hashtags.size() > 0){
                for (String s : hashtags) {
                    if (Arrays.asList(m.getMessageContent().split(" ")).contains(s)) {
                        messagesToShow.add(m);
                    }
                }
            }
            else if(!userSearch.equals("") && hashtags.size() == 0){
                if(m.getPostedBy().equals(userSearch)){
                    messagesToShow.add(m);
                }
            }
            else if(!userSearch.equals("") && hashtags.size() > 0){
                if(m.getPostedBy().equals(userSearch)){
                    for (String s : hashtags) {
                        if (Arrays.asList(m.getMessageContent().split(" ")).contains(s)) {
                            messagesToShow.add(m);
                        }
                    }
                }
            }
        }
        messagesToShow.sort(Collections.reverseOrder());
    }
    public static Message findByMessageID(int messageID){
        for(Message m : messages){
            if(m.getMessageID() == messageID){
                return m;
            }
        }
        return null;
    }
    public static void deleteByMessageID(int messageID){
        for(Message m : messages){
            if(m.getMessageID() == messageID){
                messages.remove(m);
                return;
            }
        }
    }
    public static String hashedPasswordDigest(String plaintext) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(plaintext.getBytes(StandardCharsets.UTF_8));
        String hashString = Base64.getEncoder().encodeToString(hash);
        System.out.println(hashString);
        return hashString;
    }
    public static InputStream getAttachmentBytes(int messageID){
        for(Message m : messages){
            if(m.getMessageID() == messageID){
                return m.getAttachmentBytes();
            }
        }
        return null;
    }
}
