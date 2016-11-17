package chatserver;

import java.io.IOException;
import java.util.HashMap;

import global.Config;
import global.Marshaller;
import global.Message;
import global.datatypes.MessageType;
import global.datatypes.request.DataMessage;
import global.datatypes.request.SubscribeMessage;
import server.infrastructure.ServerRequestHandler;

public class ConnectionManager {
	
	private ServerRequestHandler rqHandler;
	//private HashMap<String, ArrayList<ServerRequestHandler>> channels;
	private HashMap<String, ChannelHandler> channels;
	
	public ConnectionManager(int port){
		//abre conexao
		//que informacoes guardo do cliente sobre a conexao?
		try {
			rqHandler = new ServerRequestHandler(Config.PORT);
			channels = new HashMap<>();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addChannel(ChannelHandler channel){
		channels.put(channel.name, channel);
	}
	
	public void start(){
		while(true){
			try {
				byte[] msg = rqHandler.receive();
				Message message = new Marshaller().unmarshall(msg);
				
				switch(message.header.messageType){
				case SEND_DATA:
					System.out.println("Received data message");
					DataMessage dataMsg = (DataMessage) message;
					sendDataToHandler(dataMsg);
					break;
				case SUBSCRIBE:
					System.out.println("Received subscribe message");
					SubscribeMessage subMsg = (SubscribeMessage) message;
					addSubscriber(subMsg);
					break;
				default:
					//do nothing
					break;
				}
									
						//UnsubscribeMessage subMsg = (UnsubscribeMessage) message;
						
						// pass message to db.
						// db broadcasts update to userupdater
						// 
						//startNewConnection();
					
				
				// unmarshall
				// update the db
				// when the db is updated, it sends the signal
				// trigger data sync when updated
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void sendDataToHandler(DataMessage dataMsg){
		String channel = dataMsg.header.channel;
		ChannelHandler chHandler = getHandlerForChannel(channel);
		chHandler.addData(dataMsg.body.object);
		// preciso do handler daquele canal
	}
	
	private void addSubscriber(SubscribeMessage subMsg){
		String channel = subMsg.header.channel;
		ChannelHandler chHandler = getHandlerForChannel(channel);
		chHandler.addSubscriber(rqHandler);
	}

//	private void saveSubscriberConnection(String channel) {
//		if(channels.containsKey(channel)){
//			ArrayList<ServerRequestHandler> connections = channels.get(channel);
//			connections.add(handler);
//		}else{
//			ArrayList<ServerRequestHandler> connections = new ArrayList<>();
//			connections.add(handler);
//			channels.put(channel, connections);
//		}
//	}
	
	private ChannelHandler getHandlerForChannel(String channel){
		if(channels.containsKey(channel)){
			return channels.get(channel);
		}else{
			return null;
			//throw new InexistentChannelException();
		}
	}
	
	private void startNewConnection(){
		try {
			rqHandler = new ServerRequestHandler(Config.PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
