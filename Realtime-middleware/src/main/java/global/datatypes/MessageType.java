package global.datatypes;

public enum MessageType {
	SEND_UPDATE(0),
	SUBSCRIBE(1),
	UNSUBSCRIBE(2),
	UPDATE(3);
	
	
	private final int code;
	
	private MessageType(int code){
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}
	
}
