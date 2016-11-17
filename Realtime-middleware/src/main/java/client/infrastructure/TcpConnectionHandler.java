package client.infrastructure;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpConnectionHandler{

	private int port;
	private String host;
	
	private Socket clientSocket = null;
	private DataOutputStream outToServer = null;
	private DataInputStream inFromServer = null;
	
	public TcpConnectionHandler(String host, int port) throws UnknownHostException, IOException{
		this.host = host;
		this.port = port;
		initConnection();
	}
	

	public void send(byte[] msg) throws IOException {
		int sentMessageSize = msg.length;
		outToServer.writeInt(sentMessageSize);
		outToServer.write(msg,0,sentMessageSize);
		outToServer.flush();
	}


	public byte[] receive() throws IOException{
		byte[] msg = null;
		
		int receiveMessageSize = inFromServer.readInt();
		msg = new byte[receiveMessageSize];
		inFromServer.read(msg, 0, receiveMessageSize);
				
		return msg;
	}
	
	private void initConnection() throws UnknownHostException, IOException{
		clientSocket = new Socket(this.host, this.port);
		outToServer = new DataOutputStream(clientSocket.getOutputStream());
		inFromServer = new DataInputStream(clientSocket.getInputStream());
	}
	
	
	public void closeConnection() throws IOException{
		clientSocket.close();
		outToServer.close();
		inFromServer.close();
	}


}
