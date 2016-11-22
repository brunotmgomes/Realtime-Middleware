package chatclient;

import java.util.Scanner;

import client.distribution.ChannelUpdateListener;
import client.distribution.MessageChannelProxy;
import global.datatypes.chat.ChatMessage;

public class ChatRoom {

	private MessageChannelProxy messageChannel;
	private ChatUpdateListener listener;
	
	private String myName;
	private String roomName;
	
	private Scanner reader;
	
	public ChatRoom(String roomName, Scanner reader) {
		this.roomName = roomName;
		this.reader = reader;
	}
	
	public void enter(){
		System.out.println("Choose your name:");
		reader.nextLine();
		myName = reader.nextLine();
	
		messageChannel = new MessageChannelProxy(roomName);
		listener = new ChatUpdateListener();
		messageChannel.subscribe(listener);
		
		System.out.println("Welcome to the chat " + roomName);

		inputLoop(messageChannel);
	}
	
	private void inputLoop(MessageChannelProxy realtimeChannel){
		
		while(true){
			String msg = reader.nextLine();
			
			switch (msg){
			case ("/unsubscribe"):
				System.out.println("info - stopping chat updates");
				messageChannel.unsubscribe();
				break;
			case ("/subscribe"):
				System.out.println("info - restarting updates");
				messageChannel.subscribe(listener);
				break;
			default:
				ChatMessage message = new ChatMessage();
				message.mensagem = msg;
				message.autor = myName;
				realtimeChannel.sendUpdate(message);
			}

		}
	}
	
	
	
	
	class ChatUpdateListener implements ChannelUpdateListener{

		@Override
		public void onNewData(Object object) {
			ChatMessage message = (ChatMessage) object;
			System.out.print(message.autor + ": ");
			System.out.println(message.mensagem);
			
		}
		
	}
	

}
