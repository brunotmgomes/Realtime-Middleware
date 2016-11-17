package global;

import java.io.Serializable;

import global.datatypes.MessageBody;
import global.datatypes.MessageHeader;

public class Message implements Serializable{
	
	private static final long serialVersionUID = 8825766283644521343L;
	
	public MessageHeader header;
	public MessageBody body;
	
}
