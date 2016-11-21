package server.distribution;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;

import global.datatypes.messages.NotifyMessage;

public class ChannelQueueManager {
	
	public final String name;
	private ChannelDataHandler dataHandler;
	private HashMap<String, ConnectionQueue> subscriberQueues;
	
	public ChannelQueueManager(String name, ChannelDataHandler dataHandler){
		this.name = name;
		this.dataHandler = dataHandler;
		this.subscriberQueues = new HashMap<>();
	}
	
	public void addData(Object data){
		Serializable channelUpdate = dataHandler.addData((Serializable)data);
		updateAllSubscribers(channelUpdate);
	}
	
	public String addSubscriber(Socket connectionSocket){
		String id = UUID.randomUUID().toString();
		ConnectionQueue cObj = new ConnectionQueue(id, connectionSocket);
		subscriberQueues.put(cObj.getId(), cObj);
	
		return id;
	}
	
	public void resubscribeClient(String clientId, Socket connectionSocket){
		ConnectionQueue cObj = subscriberQueues.get(clientId);
		if(cObj != null){
			cObj.setSocket(connectionSocket);
		}
	}
	
	public void unsubscribeClient(String clientId) throws IOException{
		ConnectionQueue cObj = subscriberQueues.get(clientId);
		if(cObj != null){
			cObj.closeConnection();
			subscriberQueues.remove(clientId);
		}
	}
	
	private void updateAllSubscribers(Serializable channelUpdate){
		System.out.println("notifying subscribers");
		NotifyMessage message = new NotifyMessage(channelUpdate);
		for(ConnectionQueue cObj : subscriberQueues.values()){
			cObj.enqueue(message);
			cObj.notifyClient();
		}		
	}
	

}
