package chatserver;

import java.util.HashMap;
import java.util.List;

import global.datatypes.chat.ChatMessage;

public class ChatRealtimeDb {

	
	
	private List<ChatMessage> messages;
	
	public ChatRealtimeDb(){
		
	}
	
	public void add(ChatMessage message){
		messages.add(message);
		updateClients(message);
	}
	
	public void updateClients(ChatMessage message){
		
	}
	
	
	
}
