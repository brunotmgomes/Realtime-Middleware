package server.distribution;

import java.io.IOException;

public class MessageServer {

	private Thread connManagerThread;
	private ConnectionManager connectionManager;
	
	public MessageServer(int port){
		connectionManager = new ConnectionManager(port);
	}
	
	public void addMessageChannel(String channelName, ChannelDataHandler dataHandler){
		ChannelQueueManager chHandler = new ChannelQueueManager(channelName, dataHandler);
		connectionManager.addChannel(chHandler);
	}
	
	public void start() throws IOException{
		connManagerThread = new Thread(connectionManager);
		connManagerThread.start();
		System.out.println("Server running");
	}
	
	public void stop(){
		System.out.println("Stopping server - Will stop after another message is received");
		connectionManager.stop();
		try {
			connManagerThread.join();
			System.out.println("Server stopped");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
