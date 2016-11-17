package client.infrastructure;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientRequestHandler {
	
	private TcpConnectionHandler connHandler;
	
	
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
