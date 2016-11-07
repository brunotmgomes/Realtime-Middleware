package infrastructure.server;

import java.io.IOException;

public class ServerRequestHandler {

	private int portNumber;
	private ConnectionHandler connHandler;
	
	
	public ServerRequestHandler(int port){
		this.portNumber = port;
	}
	
	public byte[] receive() throws IOException{
		initConnectionHandler();
		return connHandler.receive();
	}
	
	public void send(byte[] msg) throws IOException{
		connHandler.send(msg);
	}

	private void initConnectionHandler() {
		connHandler = new TcpConnectionHandler(portNumber);
	}
	
	
}
