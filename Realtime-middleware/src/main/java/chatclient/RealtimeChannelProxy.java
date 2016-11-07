package chatclient;

import global.ChatMessage;
import infrastructure.client.ClientRequestHandler;

public class RealtimeChannelProxy {

	private String channel;
	private ValueEventListener listener;
	
	public RealtimeChannelProxy(String channel){
		this.channel = channel;
	}
	
	public void subscribe(ValueEventListener listener){
		//start connection to listen
		this.listener = listener;
		SubscriptionUpdater updater = new SubscriptionUpdater(channel, listener);
		Thread updaterThread = new Thread(updater);
		
		
		
		//new thread
		//new connection
		//send subscribe message and start listening
		
		
		//start new connection to listen
	}
	
	
	
	public void sendUpdate(ChatMessage message){
		ClientRequestHandler handler = new ClientRequestHandler();
		//marshall message
		//send message
		handler.update();
	}
	
	
	
	
}
