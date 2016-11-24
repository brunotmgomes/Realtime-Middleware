package server.distribution;

import java.io.IOException;
import java.util.HashMap;

import global.Config;
import global.Marshaller;
import global.datatypes.messages.DataMessage;
import global.datatypes.messages.Message;
import global.datatypes.messages.SubscribeMessage;
import global.datatypes.messages.SubscribeResponseMessage;
import server.infrastructure.ServerRequestHandler;

public class ConnectionManager implements Runnable{
	
	private ServerRequestHandler rqHandler;
	private HashMap<String, ChannelQueueManager> channels;
	private volatile boolean stopFlag;
	
	public ConnectionManager(int port){
		try {
			rqHandler = new ServerRequestHandler(Config.PORT);
			channels = new HashMap<>();
			stopFlag = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addChannel(ChannelQueueManager channel){
		channels.put(channel.name, channel);
	}
	
	@Override
	public void run(){
		try {
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void start() throws IOException{
		rqHandler.startWelcomeConnection();
		while(!stopFlag){
			try {
				byte[] msg = rqHandler.receive();
				Message message = new Marshaller().unmarshall(msg);
				
				switch(message.header.messageType){
				case NEW_MESSAGE:
					System.out.println("Received data message");
					DataMessage dataMsg = (DataMessage) message;
					sendDataToChannel(dataMsg);
					break;
				case SUBSCRIBE:
					System.out.println("Received subscribe message");
					SubscribeMessage subMsg = (SubscribeMessage) message;
					addSubscriber(subMsg);
					break;
				case UNSUBSCRIBE:
					System.out.println("Receibed unsubscribe message");
					unsubscribeClient(message);
				case RESUBSCRIBE:
					System.out.println("Received resubscribe message");
					resubscribeClient(message);
				default:
					//do nothing
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		dropAllChannelConnections();
		rqHandler.closeConnection();
		stopFlag = false;
	}
	
	private void dropAllChannelConnections(){
		for (ChannelQueueManager cqm : channels.values()){
			cqm.dropClients();
		}
	}
	
	public void stop(){
		stopFlag = true;
	}
	
	private void sendDataToChannel(DataMessage dataMsg){
		String channel = dataMsg.header.channel;
		ChannelQueueManager chHandler = getHandlerForChannel(channel);
		chHandler.addData(dataMsg.body.object);
	}
	
	private void addSubscriber(SubscribeMessage subMsg){
		String channel = subMsg.header.channel;
		ChannelQueueManager chHandler = getHandlerForChannel(channel);
		String clientId = chHandler.addSubscriber(rqHandler.getSocket());
		sendIdToClient(clientId);
	}
	
	private void sendIdToClient(String clientId) {
		SubscribeResponseMessage message = new SubscribeResponseMessage(clientId);
		Marshaller mrsh = new Marshaller();
		byte[] msg;
		try {
			msg = mrsh.marshall(message);
			rqHandler.send(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void unsubscribeClient(Message msg){
		String channel = msg.header.channel;
		String clientId = (String)msg.body.object;
		ChannelQueueManager chHandler = getHandlerForChannel(channel);
		try{
			chHandler.unsubscribeClient(clientId);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void resubscribeClient(Message msg){
		String channel = msg.header.channel;
		String clientId = (String)msg.body.object;
		ChannelQueueManager chHandler = getHandlerForChannel(channel);
		chHandler.resubscribeClient(clientId, rqHandler.getSocket());		
	}
	
	private ChannelQueueManager getHandlerForChannel(String channel){
		if(channels.containsKey(channel)){
			return channels.get(channel);
		}else{
			return null;
			//throw new InexistentChannelException();
		}
	}	

}
