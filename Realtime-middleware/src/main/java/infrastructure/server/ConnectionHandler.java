package infrastructure.server;

import java.io.IOException;

public interface ConnectionHandler {

	byte[] receive() throws IOException;
	void send(byte[] msg) throws IOException;
	
}
