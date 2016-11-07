package global.datatypes.request;

import global.Message;
import global.datatypes.MessageType;

public class SubscribeMessage extends Message{

	public SubscribeMessage(String channel){
		header.messageType = MessageType.SUBSCRIBE.getCode();
		body.object = channel;
	}
	
}
