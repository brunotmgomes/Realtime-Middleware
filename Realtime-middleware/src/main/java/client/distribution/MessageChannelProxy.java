package client.distribution;

import java.io.IOException;
import java.io.Serializable;
import java.net.UnknownHostException;

import client.infrastructure.ClientRequestHandler;
import global.Config;
import global.Marshaller;
import global.Message;
import global.datatypes.request.DataMessage;

public class MessageChannelProxy {

	private String channel;
	private ClientUpdater updater;
	
	public MessageChannelProxy(String channel){
		this.channel = channel;
	}
	
	public void subscribe(ChannelUpdateListener listener){
		if(updater == null){
			startUpdateConnection(listener);
		}else{
			this.updater.setListener(listener);
		}		
	}
	
	private void startUpdateConnection(ChannelUpdateListener listener){
		this.updater = new ClientUpdater(channel, listener);
		Thread updaterThread = new Thread(updater);
		updaterThread.start();
	}
	
	public void unsubscribe(){
		this.updater.stopUpdates();
	}
	
	public void sendUpdate(Serializable messageContent){
		DataMessage message = new DataMessage(messageContent);
		message.header.channel = this.channel;
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
