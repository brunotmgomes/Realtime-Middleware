package chatserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import global.datatypes.chat.ChatMessage;
import server.distribution.ChannelDataHandler;

public class ChatDatabase implements ChannelDataHandler {

	
	private List<ChatMessage> messages;
	
	public ChatDatabase(){
		messages = new ArrayList<ChatMessage>();
	}
	
	@Override
	public Serializable addData(Serializable data) {
		if(data instanceof ChatMessage){
			ChatMessage message = (ChatMessage) data;
			messages.add(message);
			System.out.println("ChatDatabase - New message added to db:");
			System.out.println(message.mensagem);
			return message;
		}
		return null;
	}
	
	
	
}
