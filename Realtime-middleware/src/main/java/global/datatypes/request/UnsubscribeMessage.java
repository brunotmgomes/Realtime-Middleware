package global.datatypes.request;

import global.Message;
import global.datatypes.MessageType;

public class UnsubscribeMessage extends Message{

	public UnsubscribeMessage(){
		this.header.messageType = MessageType.UNSUBSCRIBE;
	}
	
}
