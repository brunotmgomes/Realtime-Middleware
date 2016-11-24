package global.datatypes.messages;

import global.datatypes.messages.components.MessageBody;
import global.datatypes.messages.components.MessageHeader;
import global.datatypes.messages.components.MessageType;

public class SubscribeResponseMessage extends Message{

	private static final long serialVersionUID = 5029924075732388764L;

	public SubscribeResponseMessage(String clientId){
		header = new MessageHeader();
		header.messageType = MessageType.SUBSCRIBE;
		body = new MessageBody();
		body.object = clientId;
	}
	
}
