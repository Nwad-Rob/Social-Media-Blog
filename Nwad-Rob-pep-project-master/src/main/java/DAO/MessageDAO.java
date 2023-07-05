package DAO;

import Util.ConnectionUtil;
import Model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

//TODO: retrieve all messages from the message table.

    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> Messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message Message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                Messages.add(Message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return Messages;
    }

    

//TODO: retrieve a message from the message table, identified by its message_id.
    
    public Message getMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
             preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){

                Message Message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return Message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessagebyPost(int account_id){

        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM Message where posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
            //write preparedStatement's setInt method here.
             preparedStatement.setInt(1, account_id);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                rs.getInt(account_id),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
         return messages;
    }

//TODO: retrieve a message from the message table, identified by its time posted.
    
public Message getMessageByPost(int id){
    Connection connection = ConnectionUtil.getConnection();
    try {
        //Write SQL logic here
        String sql = "SELECT * FROM message where posted_by = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        //write preparedStatement's setInt method here.
         preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Message Message = new Message(rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
            return Message;
        }
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return null;
}

//TODO: delete a message from the message table
    
public Message deleteMessageById(int id){
    Connection connection = ConnectionUtil.getConnection();
    Message deleteMessage = null;
    try {
        //Write SQL logic here
        deleteMessage = getMessageById(id);
        String sql = "DELETE FROM Message WHERE message_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //write preparedStatement's setInt method here.
         preparedStatement.setInt(1, id);
         preparedStatement.executeUpdate();

    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return deleteMessage;
}

//TODO: retrieve a message from the message table, identified by its message_text.
    
public Message getMessageByText(String text){
    Connection connection = ConnectionUtil.getConnection();
    try {
        //Write SQL logic here
        String sql = "SELECT *FROM message where message_text = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //write preparedStatement's setInt method here.
         preparedStatement.setString(1, text);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Message Message = new Message(rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
            return Message;
        }
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return null;
}



//TODO: insert an message into the Message table.

public Message insertMessage(Message message){
    Connection connection = ConnectionUtil.getConnection();
    try {

        String sql = "INSERT INTO message (time_posted_epoch, message_text, posted_by) VALUES (?, ?, ?) " ;
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = null;
        //write preparedStatement's setString method here.
        
        preparedStatement.setLong(1,message.getTime_posted_epoch());
        preparedStatement.setString(2,message.getMessage_text());
        preparedStatement.setInt(3,message.getPosted_by());
        preparedStatement.executeUpdate();
        
        rs = preparedStatement.getGeneratedKeys();
        if(rs.next()){
            message.setMessage_id(rs.getInt(1));
            return message;
        }
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return null;
}

//TODO: update a message into the Message table.
    public void updateMessage(int id, Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write PreparedStatement setString and setInt methods here.
            preparedStatement.setString(1,message.getMessage_text());
            preparedStatement.setInt(2,id);

            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public List<Message> getAllMessagebyUser(String username){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> Messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM Message WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //write PreparedStatement setString and setInt methods here.
            preparedStatement.setString(1,username);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                 Message Message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                Messages.add(Message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return Messages;
    }

}
