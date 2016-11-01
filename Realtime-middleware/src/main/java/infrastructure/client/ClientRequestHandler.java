package infrastructure.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import chatclient.ValueEventListener;

public class ClientRequestHandler {

	private ValueEventListener listener;
	
	
	public ClientRequestHandler(){
		//init connection
		clientSocket = new Socket(this.host, this.port);
		outToServer = new DataOutputStream(clientSocket.getOutputStream());
		inFromServer = new DataInputStream(clientSocket.getInputStream());
		
		int sentMessageSize = msg.length;
		outToServer.writeInt(sentMessageSize);
		outToServer.write(msg,0,sentMessageSize);
		outToServer.flush();
	}
	
	public void send(){
		connHandler.send(msg);
	}
	
	
	public void subscribe(String channel){
		
	}
	
	public void update(){

		
		
		listener.onDataChange(data);
	}
	
	public void unsubscribe(){
		
	}
	
}
