package chatclient;

import java.io.IOException;
import java.net.UnknownHostException;

import global.Config;
import infrastructure.client.ClientRequestHandler;

public class SubscriptionUpdater implements Runnable{

	private String channel;
	private ValueEventListener listener;
	
	private volatile boolean stopFlag;
	
	// checks internet connection
	// checks for stop flag
	// keeps connection open until it is asked to close

	public SubscriptionUpdater(String channel, ValueEventListener listener){
		this.channel = channel;
		this.listener = listener;
		this.stopFlag = false;
	}
	
	@Override
	public void run() {
		initHandler();
		sendStartUpdateMessage();
		// TODO Auto-generated method stub
		//send listen message to server and pass updates to listener
		while(!stopFlag){
			//checks internet connection
			//messge = handler.receive();
			//unmarshall message
			//call listener for message
			
		}
	}
	
	public void stopUpdates(){
		stopFlag = true;
	}

	
	private void initHandler() {
		try {
			ClientRequestHandler handler = new ClientRequestHandler(Config.HOST, Config.PORT);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void sendStartUpdateMessage(){
		
	}


	
	
}
