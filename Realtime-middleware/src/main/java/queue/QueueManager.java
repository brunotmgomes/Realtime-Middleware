package queue;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class QueueManager {
	private int port;
	Map<String, Queue> queues = new HashMap<String, Queue>();
	
	
	private ServerSocket welcomeSocket = null;
	private Socket connectionSocket = null;
	
	private int sentMessageSize;
	private int receivedMessageSize;
	private DataOutputStream outToClient = null;
	private DataInputStream  inFromClient = null;
	
	public QueueManager(String host, int port) {
		this.port = port;
	}
	
	public byte[] receive() throws IOException, Throwable{
		byte [] rcvMsg = null;
		//parei aqui
//		welcomeSocket = new ServerSocket(port);
//		connectionSocket = welcomeSocket.accept();
//		
//		outToClient = new DataOutputStream(connectionSocket.getOutputStream());
//		inFromClient = new DataInputStream(connectionSocket.getInputStream());
//		
//		receivedMessageSize = inFromClient.readInt();
//		rcvMsg = new byte[receivedMessageSize];
//		
//		inFromClient.read(rcvMsg, 0, receivedMessageSize);

		return rcvMsg;
	}
	
	public void send(byte[] msg) throws IOException, InterruptedException{
//		sentMessageSize = msg.length;
//		outToClient.writeInt(sentMessageSize);
//		outToClient.write(msg);
//		outToClient.flush();
//		
//		connectionSocket.close();
//		welcomeSocket.close();
//		outToClient.close();
//		inFromClient.close();
	}
}
