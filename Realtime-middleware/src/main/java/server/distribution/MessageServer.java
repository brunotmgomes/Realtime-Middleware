package server.distribution;

import java.io.IOException;

public class MessageServer {

	private ConnectionManager connectionManager;
	
	public MessageServer(int port){
		connectionManager = new ConnectionManager(port);
	}
	
	public void addMessageChannel(String channelName, ChannelDataHandler dataHandler){
		ChannelQueueManager chHandler = new ChannelQueueManager(channelName, dataHandler);
		connectionManager.addChannel(chHandler);
	}
	
	public void start() throws IOException{
		System.out.println("Server running");
		connectionManager.start();
	}
	
	
	
	
}
