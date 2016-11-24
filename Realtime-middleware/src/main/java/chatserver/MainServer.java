package chatserver;

import java.io.IOException;
import java.util.Scanner;

import global.Config;
import server.distribution.MessageServer;

public class MainServer {

	private static MessageServer server;
	
	public static void main(String[] args){
		server = new MessageServer(Config.PORT);
		
		ChatDatabase database = new ChatDatabase();
		server.addMessageChannel(Config.CHAT_CHANNEL1, database);
		
		ChatDatabase database2 = new ChatDatabase();
		server.addMessageChannel(Config.CHAT_CHANNEL2, database2);
		
		tryToStartServer();
		
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		while(true){
			String in = reader.nextLine();
			
			switch(in){
				case("stop"):
					server.stop();
					break;
				case("start"):
					tryToStartServer();
					break;				
			}
		}
	}
	
	private static void tryToStartServer(){
		try {
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
