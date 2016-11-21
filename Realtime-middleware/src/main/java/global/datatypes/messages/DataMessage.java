package global.datatypes.messages;

import java.io.Serializable;

import global.datatypes.messages.components.MessageBody;
import global.datatypes.messages.components.MessageHeader;
import global.datatypes.messages.components.MessageType;

public class DataMessage extends Message{

	private static final long serialVersionUID = -2168219083125361903L;

	public DataMessage(Serializable object){
		this.header = new MessageHeader();
		this.header.messageType = MessageType.NEW_MESSAGE;
		this.body = new MessageBody();
		this.body.object = object;
	}
	
	
	
}
