package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;


public class MessageService {
    public MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
   
// TODO: Use the messageDAO to retrieve all message.

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
   

    public Message addMessage(Message message) {
      
            if(message.getMessage_text().length() < 255 && !(message.getMessage_text().isBlank())){
                return messageDAO.insertMessage(message);
                }
        
       return null;
    }
     
    
   
    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);

    }

    public List<Message> getMessageByUsername(String username) {
        return messageDAO.getAllMessagebyUser(username);
        
    }


    public Message updateMessage (int message_id, Message message){
        if (messageDAO.getMessageById(message.getMessage_id()) == null){
            messageDAO.updateMessage(message_id, message);
            if(message.getMessage_text().length() < 255 && !(message.getMessage_text().isBlank())){
                return messageDAO.getMessageById(message_id);
                }
            return null;
        }
        
        return messageDAO.getMessageById(message_id); 
    }
    
    public Message deletingMessageByID(int id) {
       
        return messageDAO.deleteMessageById(id);
        
    }

    public List<Message> getMessageByAccountId(int account_id) {
        return messageDAO.getAllMessagebyPost(account_id);
        
    }
}