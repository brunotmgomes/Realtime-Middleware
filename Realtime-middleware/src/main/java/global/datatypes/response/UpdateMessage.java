package global.datatypes.response;

import java.io.Serializable;

import global.Message;
import global.datatypes.MessageType;

public class UpdateMessage extends Message{

	public UpdateMessage(Serializable content){
		header.messageType = MessageType.UPDATE;
		body.object = content;
	}
	
	
	
	
	
}
