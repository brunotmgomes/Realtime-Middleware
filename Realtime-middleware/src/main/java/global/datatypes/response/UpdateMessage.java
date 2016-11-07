package global.datatypes.response;

import global.Message;
import global.datatypes.MessageType;

public class UpdateMessage extends Message{

	public UpdateMessage(Object content){
		header.messageType = MessageType.UPDATE.getCode();
		body.object = content;
	}
	
	
	
	
	
}
