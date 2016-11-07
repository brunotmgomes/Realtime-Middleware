package infrastructure.client.connection;

import java.io.IOException;
import java.net.UnknownHostException;

public interface ConnectionHandler {

	void send(byte[] msg) throws UnknownHostException, IOException;
	byte[] receive() throws IOException;
	void closeConnection() throws IOException;
	
}
