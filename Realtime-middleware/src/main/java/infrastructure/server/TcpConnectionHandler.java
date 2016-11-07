package infrastructure.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpConnectionHandler implements ConnectionHandler{

	private int portNumber;
	
	private ServerSocket welcomeSocket = null;
	private Socket connectionSocket = null;
	private int sentMessageSize;
	private int receivedMessageSize;
	private DataOutputStream outToClient = null;
	private DataInputStream inFromClient = null;
	
	public TcpConnectionHandler(int port){
		this.portNumber = port;
	}

	@Override
	public byte[] receive() throws IOException {
		byte[] rcvMsg = null;
		welcomeSocket = new ServerSocket(portNumber);
		connectionSocket = welcomeSocket.accept();
		
		outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		inFromClient = new DataInputStream(connectionSocket.getInputStream());
		
		receivedMessageSize = inFromClient.readInt();
		rcvMsg = new byte[receivedMessageSize];
		
		inFromClient.read(rcvMsg, 0, receivedMessageSize);
		
		return rcvMsg;
	}

	@Override
	public void send(byte[] msg) throws IOException {
		sentMessageSize = msg.length;
		outToClient.writeInt(sentMessageSize);
		outToClient.write(msg);
		outToClient.flush();
		
		closeConnection();
	}
	
	private void closeConnection() throws IOException {
		connectionSocket.close();
		welcomeSocket.close();
		outToClient.close();
		inFromClient.close();
	}
	
	
	
}
