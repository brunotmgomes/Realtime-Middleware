package server.infrastructure;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRequestHandler {

	private int portNumber;
	
	private ServerSocket welcomeSocket = null;
	private Socket connectionSocket = null;
	private int sentMessageSize;
	private int receivedMessageSize;
	private DataOutputStream outToClient = null;
	private DataInputStream inFromClient = null;
	
	public ServerRequestHandler(int port) throws IOException{
		this.portNumber = port;
	}
	
	public ServerRequestHandler(Socket socket) throws IOException{
		this.connectionSocket = socket;
		outToClient = new DataOutputStream(connectionSocket.getOutputStream());
	}
	
	public void startWelcomeConnection() throws IOException{
		welcomeSocket = new ServerSocket(portNumber);
	}
	
	public byte[] receive() throws IOException{
		byte[] rcvMsg = null;
		connectionSocket = welcomeSocket.accept();
		
		outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		inFromClient = new DataInputStream(connectionSocket.getInputStream());
		
		receivedMessageSize = inFromClient.readInt();
		rcvMsg = new byte[receivedMessageSize];
		
		inFromClient.read(rcvMsg, 0, receivedMessageSize);	
		return rcvMsg;
	}
	
	public void send(byte[] msg) throws IOException{
		sentMessageSize = msg.length;
		outToClient.writeInt(sentMessageSize);
		outToClient.write(msg);
		outToClient.flush();
	}
	
	public void closeConnection() throws IOException {
		welcomeSocket.close();
		connectionSocket.close();
		outToClient.close();
		inFromClient.close();
	}

	public Socket getSocket() {
		return connectionSocket;
	}

	
}
