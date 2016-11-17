package chatclient;

import java.util.Scanner;

import client.distribution.MessageChannelProxy;
import client.distribution.ChannelUpdateListener;
import global.Config;
import global.datatypes.chat.ChatMessage;

public class MainClient {

	// client creates listener, that binds the infrastructure with the front end
	// listener is sent to clientRequest handler to send to the surface the
	// json data received on updates
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MessageChannelProxy realtimeChannel = new MessageChannelProxy(Config.CHAT_CHANNEL);
		//realtimeChannel.subscribe(new ChatUpdateListener());
		
		inputLoop(realtimeChannel);
	}
	
	private static void inputLoop(MessageChannelProxy realtimeChannel){
		while(true){
			Scanner reader = new Scanner(System.in);
			System.out.println("Enter your message: ");
			String msg = reader.nextLine();
			
			ChatMessage message = new ChatMessage();
			message.mensagem = msg;
			realtimeChannel.sendUpdate(message);
		}
	}
	
	static class ChatUpdateListener implements ChannelUpdateListener{

		@Override
		public void onNewData(Object object) {
			ChatMessage message = (ChatMessage) object;
			System.out.println(message.toString());
			
		}
		
	}

}
