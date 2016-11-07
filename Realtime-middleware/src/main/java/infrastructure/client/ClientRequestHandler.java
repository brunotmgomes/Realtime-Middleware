package infrastructure.client;

import java.io.IOException;
import java.net.UnknownHostException;

import infrastructure.client.connection.ConnectionHandler;
import infrastructure.client.connection.TcpConnectionHandler;

public class ClientRequestHandler {
	
	private ConnectionHandler connHandler;
	
	
	public ClientRequestHandler(String host, int port) throws UnknownHostException, IOException{
		connHandler = new TcpConnectionHandler(host, port);
	}
	
	public void send(byte[] msg) throws UnknownHostException, IOException{
		connHandler.send(msg);
	}
	
	public byte[] receive() throws IOException{
		return connHandler.receive();
	}
	
	public void closeConnection() throws IOException{
		connHandler.closeConnection();
	}
	
	
}
