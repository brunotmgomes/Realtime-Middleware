package chatserver;

import java.io.IOException;

import global.Config;
import server.distribution.MessageServer;

public class MainServer {

	//channel manager (realtime data)
	//channel data handler
	
	public static void main(String[] args){
		MessageServer server = new MessageServer(Config.PORT);
		
		ChatDatabase database = new ChatDatabase();
		server.addMessageChannel(Config.CHAT_CHANNEL, database);
		
		try {
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
