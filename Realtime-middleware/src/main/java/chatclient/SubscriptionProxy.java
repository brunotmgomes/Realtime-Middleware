package chatclient;

import global.ChatMessage;
import infrastructure.client.ClientRequestHandler;

public class SubscriptionProxy {

	private String channel;
	private ValueEventListener listener;
	
	public SubscriptionProxy(String channel){
		this.channel = channel;
	}
	
	public void addListener(ValueEventListener listener){
		//start connection to listen
		this.listener = listener;
	}
	
	public void sendUpdate(ChatMessage message){
		ClientRequestHandler handler = new ClientRequestHandler();
		//marshall message
		handler.update();
	}
	
	
	
}
