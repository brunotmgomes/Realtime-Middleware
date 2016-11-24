package global.datatypes.messages;

import global.datatypes.messages.components.MessageHeader;
import global.datatypes.messages.components.MessageType;

public class UnsubscribeMessage extends Message{

	private static final long serialVersionUID = -6793251894435967550L;

	public UnsubscribeMessage(){
		this.header = new MessageHeader();
		this.header.messageType = MessageType.UNSUBSCRIBE;
	}
	
}
