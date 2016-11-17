package chatserver;

import global.Config;

public class MainServer {

	//channel manager (realtime data)
	//channel data handler
	
	public static void main(String[] args){
		MessageServer server = new MessageServer(Config.PORT);
		
		ChatDatabase database = new ChatDatabase();
		server.addMessageChannel(Config.CHAT_CHANNEL, database);
		
		server.start();
		
	}
}
