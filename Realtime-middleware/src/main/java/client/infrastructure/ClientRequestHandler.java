package client.infrastructure;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import global.ExponentialBackoffHandler;
import global.exceptions.ConnectionDownException;

public class ClientRequestHandler {
	
	private int port;
	private String host;
	
	private Socket clientSocket = null;
	private DataOutputStream outToServer = null;
	private DataInputStream inFromServer = null;
	
	private ExponentialBackoffHandler expBackoff;
	
	public ClientRequestHandler(String host, int port) throws UnknownHostException, IOException{
		this.host = host;
		this.port = port;
		expBackoff = new ExponentialBackoffHandler();
		initConnection();
	}
	
	public void send(byte[] msg) throws UnknownHostException, IOException{
		int sentMessageSize = msg.length;
		outToServer.writeInt(sentMessageSize);
		outToServer.write(msg,0,sentMessageSize);
		outToServer.flush();
	}
	
	public byte[] receive() throws IOException{
		byte[] msg = null;
		try{
			int receiveMessageSize = inFromServer.readInt();
			msg = new byte[receiveMessageSize];
			inFromServer.read(msg, 0, receiveMessageSize);
		}catch(SocketException e){
			throw new ConnectionDownException();
		}catch(EOFException e){
			throw new ConnectionDownException();
		}
		return msg;
	}
	
	private void initConnection() throws UnknownHostException, IOException{
		try{
			clientSocket = new Socket(this.host, this.port);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new DataInputStream(clientSocket.getInputStream());
			System.out.println("info - Connection OK");
		}catch(ConnectException e){
			System.out.println("info - Connection to server failed");
			expBackoff.waitABit();
			System.out.println("info - Trying again");
			initConnection();
		}
	}
	
	public void closeConnection() throws IOException{
		clientSocket.close();
		outToServer.close();
		inFromServer.close();
	}
	
	
}
