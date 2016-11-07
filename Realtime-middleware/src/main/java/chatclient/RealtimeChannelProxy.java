package chatclient;

import java.io.IOException;
import java.net.UnknownHostException;

import global.Config;
import global.Marshaller;
import global.Message;
import global.datatypes.request.DataMessage;
import infrastructure.client.ClientRequestHandler;

public class RealtimeChannelProxy {

	private String channel;
	private SubscriptionUpdater updater;
	
	public RealtimeChannelProxy(String channel){
		this.channel = channel;
	}
	
	public void subscribe(ValueEventListener listener){
		//start connection to listen
		if(updater == null){
			this.updater = new SubscriptionUpdater(channel, listener);
			Thread updaterThread = new Thread(updater);
			updaterThread.start();
		}else{
			this.updater.setListener(listener);
		}		
		
	}
	
	public void unsubscribe(){
		this.updater.stopUpdates();
	}
	
	public void sendUpdate(Object messageContent){
		DataMessage message = new DataMessage(messageContent);
		byte[] byteMsg = marshallMessage(message);
		ClientRequestHandler handler;
		try {
			handler = new ClientRequestHandler(Config.HOST, Config.PORT);
			//fire and forget
			handler.send(byteMsg);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private byte[] marshallMessage(Message msg){
		Marshaller mrsh = new Marshaller();
		try {
			return mrsh.marshall(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
}
