package queue;

import global.Marshaller;
import global.Message;
import global.datatypes.MessageBody;
import global.datatypes.MessageHeader;
import global.datatypes.MessageType;

public class QueueManagerProxy implements IQueueManager{
	String queueName;
	
	public QueueManagerProxy(String queueName) {
		this.queueName = queueName;
	}
	
	public void publish(String msgString) throws Exception {
		ClientRequestHandler crh = new ClientRequestHandler("localhost", 1313);
		
		Marshaller marshaller = new Marshaller();
		Message msg = new Message();
		msg.header = new MessageHeader();
		msg.header.messageType = MessageType.NEWMESSAGE.getCode();
		msg.body = new MessageBody();
		msg.body.object = msgString;
		
		crh.send(marshaller.marshall(msg));
	}

	public String receive() throws Exception {
		ClientRequestHandler crh = new ClientRequestHandler("localhost", 1313);
		byte[] msgBytes = crh.receive();
		Marshaller marshaller = new Marshaller();
		Message msg = marshaller.unmarshall(msgBytes);
		
//		if(msg.header.messageType == MessageType.NEWMESSAGE.getCode()){
//			
//		}else if(msg.header.messageType == MessageType.SUBSCRIBE.getCode()){
//			
//		}else if(msg.header.messageType == MessageType.UNSUBSCRIBE.getCode()){
//			
//		}else if(msg.header.messageType == MessageType.RESUBSCRIBE.getCode()){
//			
//		}
		
		
		return (String) msg.body.object;
		
		
	}
	
}
