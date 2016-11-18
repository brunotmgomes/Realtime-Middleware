package global.datatypes;

public enum MessageType {
	SUBSCRIBE(0),
	UNSUBSCRIBE(1),
	RESUBSCRIBE(2),
	NEWMESSAGE(3);
	
	
	private final int code;
	
	private MessageType(int code){
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}
	
}
