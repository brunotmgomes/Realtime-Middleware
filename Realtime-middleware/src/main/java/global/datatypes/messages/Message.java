package global.datatypes.messages;

import java.io.Serializable;

import global.datatypes.messages.components.MessageBody;
import global.datatypes.messages.components.MessageHeader;

public class Message implements Serializable{
	
	private static final long serialVersionUID = 8825766283644521343L;
	
	public MessageHeader header;
	public MessageBody body;
	
}
