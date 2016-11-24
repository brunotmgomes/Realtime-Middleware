package chatclient;

import java.util.Scanner;

import global.Config;

public class MainClient {

	
	public static void main(String[] args) {		
		System.out.println("Choose a chat to join");
		System.out.println("1 - " + Config.CHAT_CHANNEL1);
		System.out.println("2 - " + Config.CHAT_CHANNEL2) ;

		Scanner reader = new Scanner(System.in);
		
		int choice = reader.nextInt();
		String roomName;
		
		switch(choice){
			case 1: 
				roomName = Config.CHAT_CHANNEL1;
				break;
			case 2:
				roomName = Config.CHAT_CHANNEL2;
				break;
			default:
				reader.close();
				throw new RuntimeException();
		}
		
		ChatRoom room = new ChatRoom(roomName, reader);
		room.enter();
	}
	
	
	
	
	
	
	
	
	

}
