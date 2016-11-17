package global.datatypes.request;

import java.io.Serializable;

import global.Message;
import global.datatypes.MessageBody;
import global.datatypes.MessageHeader;
import global.datatypes.MessageType;

public class DataMessage extends Message{

	private static final long serialVersionUID = -2168219083125361903L;

	
	public DataMessage(Serializable object){
		this.header = new MessageHeader();
		this.header.messageType = MessageType.SEND_DATA;
		this.body = new MessageBody();
		this.body.object = object;
	}
	
	
	
}
