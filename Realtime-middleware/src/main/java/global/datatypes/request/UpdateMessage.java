package global.datatypes.request;

import global.Message;
import global.datatypes.MessageHeader;
import global.datatypes.MessageType;

public class UpdateMessage extends Message{
	
	
	public UpdateMessage(){
		this.header = new MessageHeader();
		this.header.messageType = MessageType.UPDATE.getCode();
	}
	
	
	
}
