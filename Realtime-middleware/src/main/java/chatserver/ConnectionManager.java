package chatserver;

import java.util.ArrayList;
import java.util.HashMap;

import global.Config;
import global.Marshaller;
import global.Message;
import global.datatypes.MessageType;
import global.datatypes.request.SubscribeMessage;
import global.datatypes.request.UnsubscribeMessage;
import infrastructure.server.ServerRequestHandler;

public class ConnectionManager {
	
	private ServerRequestHandler handler;
	private HashMap<String, ArrayList<ServerRequestHandler>> channels;
	
	public ConnectionManager(){
		//abre conexao
		//que informacoes guardo do cliente sobre a conexao?
		handler = new ServerRequestHandler(Config.PORT);
		
	}
	
	public void start(){
		while(true){
			try {
				byte[] msg = handler.receive();
				Message message = new Marshaller().unmarshall(msg);
				if(message.header.messageType == MessageType.SUBSCRIBE.getCode()){
					SubscribeMessage subMsg = (SubscribeMessage) message;
					String channel = (String)subMsg.body.object;
					//DataSyncer.addSubscriber(channel, connection)
					saveSubscriberConnection(channel);
					startNewConnection();
				}else{
					if(message.header.messageType == MessageType.SEND_DATA.getCode()){
						//UnsubscribeMessage subMsg = (UnsubscribeMessage) message;
						String channel = (String) subMsg.body.object;
						saveSubscriberConnection(channel);
						// pass message to db.
						// db broadcasts update to userupdater
						// 
						startNewConnection();
					}
				}
				
				// unmarshall
				// update the db
				// when the db is updated, it sends the signal
				// trigger data sync when updated
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void saveSubscriberConnection(String channel) {
		if(channels.containsKey(channel)){
			ArrayList<ServerRequestHandler> connections = channels.get(channel);
			connections.add(handler);
		}else{
			ArrayList<ServerRequestHandler> connections = new ArrayList<>();
			connections.add(handler);
			channels.put(channel, connections);
		}
	}
	
	private void startNewConnection(){
		handler = new ServerRequestHandler(Config.PORT);
	}
	

}
