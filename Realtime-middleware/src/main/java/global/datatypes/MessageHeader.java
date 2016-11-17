package global.datatypes;

import java.io.Serializable;

public class MessageHeader implements Serializable{

	private static final long serialVersionUID = 4018292857619596347L;
	
	public MessageType messageType;
	public String channel;
	
	
}
