package Controller;

import java.io.ObjectInputFilter.Config;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.plugin.Plugin;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        //Javalin app = Javalin.create();
        Javalin app = Javalin.create(config -> {
        config.plugins.enableDevLogging();
        });
        
      

// Process on the endpoint POST localhost:8080/register
        app.post("/register", this::postAccountRegister);

// Process on the endpoint POST localhost:8080/login.
        app.post("/login", this::postLogin);

// Process on the endpoint POST localhost:8080/messages.
        app.post("/messages", this::postMessages); 

// Process on the endpoint GET localhost:8080/messages.
        app.get("/messages", this::getAllMessagesHandler);

// Process on the endpoint GET  localhost:8080/messages/{message_id}.
        app.get("/messages/{message_id}", this::getMessageById);

// Process on the endpoint DELETE localhost:8080/messages/{message_id}.
        app.delete("/messages/{message_id}", this::deleteMessages);

// Process on the endpoint PATCH localhost:8080/messages/{message_id}
        app.patch("/messages/{message_id}", this::updateMessageHandler);

// Process on the endpoint GET localhost:8080/accounts/{account_id}/messages.
        app.get("/accounts/{account_id}/messages", this::getMessagesByUser);



        return app;
    }

    private void postAccountRegister(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount != null){
            ctx.json(mapper.writeValueAsString(addedAccount));
            ctx.status(200);
        } else{
   
                ctx.status(400);
        }
             
    }

    private void postMessages(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
       Message addedMessage = messageService.addMessage(message);
        if(addedMessage !=null){
            ctx.json(mapper.writeValueAsString(addedMessage));
            ctx.status(200);
             
        }else{
            ctx.status(400);
        } 
    }

    private void getMessageById(Context ctx)throws JsonProcessingException{ 
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message messageID = messageService.getMessageById(message_id);
        if ( messageID != null){      
        ctx.json(messageService.getMessageById((message_id)));
        }
        ctx.status(200);
        }
    
    

    private void getMessagesByUser(Context ctx) throws JsonProcessingException{ // Problem: Error: Jackson Mixmatched Exception
        ObjectMapper mapper = new ObjectMapper();
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessageByAccountId(account_id);
        if (messages != null){
            ctx.json(mapper.writeValueAsString(messages));
            ctx.status(200);
        }else{
          
          ctx.status(200);
        }
        
    }

    
    private void deleteMessages(Context ctx) throws JsonProcessingException { //Problem: Error 500
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam( "message_id"));
       Message deleteMessage = messageService.deletingMessageByID(message_id++);
        if( deleteMessage !=null){ 
            ctx.json(mapper.writeValueAsString(deleteMessage));
            ctx.status(200);  
        }else{
            ctx.status(200);
        }
    }

    

    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

  

    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
       Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message_id, message);
        System.out.println(updatedMessage);
        if(updatedMessage == null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }

    }

    private void postLogin(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
       Account addedLogin = accountService.login(account);
        if(addedLogin !=null){
            ctx.json(mapper.writeValueAsString(addedLogin));
            ctx.status(200);
             
        }else{
            ctx.status(401);
        }
    }


}