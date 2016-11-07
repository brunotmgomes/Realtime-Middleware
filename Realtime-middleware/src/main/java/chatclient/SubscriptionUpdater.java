package chatclient;

import java.io.IOException;
import java.net.UnknownHostException;

import global.Config;
import global.Marshaller;
import global.datatypes.request.SubscribeMessage;
import global.datatypes.response.UpdateMessage;
import infrastructure.client.ClientRequestHandler;

public class SubscriptionUpdater implements Runnable{

	private String channel;
	private volatile ValueEventListener listener;
	
	private ClientRequestHandler handler;
	
	private volatile boolean stopFlag;
	
	// checks internet connection
	// checks for stop flag
	// keeps connection open until it is asked to close

	public SubscriptionUpdater(String channel, ValueEventListener listener){
		this.channel = channel;
		this.listener = listener;
		this.stopFlag = false;
	}
	
	public void setListener(ValueEventListener listener){
		this.listener = listener;
	}
	
	@Override
	public void run() {
		initHandler();
		//send listen message to server and pass updates to listener
		sendStartUpdateMessage();
		while(!stopFlag){
			try {
				byte [] msgBytes = this.handler.receive();
				Marshaller mrsh = new Marshaller();
				UpdateMessage message = (UpdateMessage) mrsh.unmarshall(msgBytes);
				this.listener.onDataUpdate(message.body.object);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		sendStopUpdateMesage();
	}
	
	public void stopUpdates(){
		stopFlag = true;
	}

	
	private void initHandler() {
		try {
			this.handler = new ClientRequestHandler(Config.HOST, Config.PORT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendStartUpdateMessage(){
		SubscribeMessage msg = new SubscribeMessage(channel);
		Marshaller mrsh = new Marshaller();
		byte[] msgBytes;
		try {
			msgBytes = mrsh.marshall(msg);
			handler.send(msgBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
	
}
