package global.datatypes.messages;

import global.datatypes.messages.components.MessageBody;
import global.datatypes.messages.components.MessageHeader;
import global.datatypes.messages.components.MessageType;

public class SubscribeMessage extends Message{

	private static final long serialVersionUID = 5029924075732388764L;

	public SubscribeMessage(String channel){
		header = new MessageHeader();
		header.messageType = MessageType.SUBSCRIBE;
		header.channel = channel;
		body = new MessageBody();
	}
	
}
