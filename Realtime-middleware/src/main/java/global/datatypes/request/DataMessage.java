package global.datatypes.request;

import global.Message;
import global.datatypes.MessageHeader;
import global.datatypes.MessageType;

public class DataMessage extends Message{
	
	private Object object;
	
	public DataMessage(Object object){
		this.header = new MessageHeader();
		this.header.messageType = MessageType.UPDATE.getCode();
	}
	
	
	
}
