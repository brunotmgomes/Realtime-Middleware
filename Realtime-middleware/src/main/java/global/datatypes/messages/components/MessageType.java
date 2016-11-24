package global.datatypes.messages.components;

public enum MessageType {
	NEW_MESSAGE(0),
	SUBSCRIBE(1),
	UNSUBSCRIBE(2),
	NOTIFY(3),
	RESUBSCRIBE(4);
	
	
	private final int code;
	
	private MessageType(int code){
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}
	
}
