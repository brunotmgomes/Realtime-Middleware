package global.datatypes.messages;

import java.io.Serializable;

import global.datatypes.messages.components.MessageBody;
import global.datatypes.messages.components.MessageHeader;
import global.datatypes.messages.components.MessageType;

public class NotifyMessage extends Message{

	private static final long serialVersionUID = 7435944998598687468L;

	public NotifyMessage(Serializable content){
		header = new MessageHeader();
		header.messageType = MessageType.NOTIFY;
		body = new MessageBody();
		body.object = content;
	}
	
	
	
	
	
}
