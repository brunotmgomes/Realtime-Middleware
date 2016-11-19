package queue;

import java.io.IOException;

import global.Marshaller;
import global.Message;
import global.datatypes.MessageBody;
import global.datatypes.MessageHeader;
import global.datatypes.MessageType;

//proxy que deve ser usado para enviar/receber mensagens para uma fila, além de dar subscribe,
// unsubscribe e resubscribe.


public class QueueManagerProxy implements IQueueManager{
	String queueName;
	ClientRequestHandler outCrh; // clientRequestHandler para enviar mensagens.
	ClientRequestHandler inCrh; // clientRequestHandler para receber mensagens.
	Marshaller marshaller;
	
	
	//nome para fila, e host e port para o servidor de filas.
	public QueueManagerProxy(String queueName, String host, int port) throws IOException {
		this.queueName = queueName;
		this.inCrh = new ClientRequestHandler(host, port);
		this.outCrh = new ClientRequestHandler(host, port);
		this.marshaller = new Marshaller(); //inicia um marshaller para ser usado 
	}
	
	//usado para enviar uma mensagem.
	public void publish(String msgString) throws Exception {

		Message msg = new Message();
		msg.header = new MessageHeader();
		msg.header.messageType = MessageType.NEWMESSAGE.getCode();
		msg.header.queue = queueName;
		msg.body = new MessageBody();
		msg.body.object = msgString;
		
		inCrh.send(marshaller.marshall(msg));
	}
	
	//se inscreve em uma fila e retorna um id que representa seu id no servidor de filas.
	public String subscribe() throws IOException, InterruptedException, ClassNotFoundException{
		Message msg = new Message();
		msg.header = new MessageHeader();
		msg.header.messageType = MessageType.SUBSCRIBE.getCode();
		msg.header.queue = queueName;
		
		msg.body = new MessageBody();
		
		inCrh.send(marshaller.marshall(msg));
		
		byte[] msgRecivedBytes = inCrh.receive();
		
		return (String) marshaller.unmarshall(msgRecivedBytes).body.object;
	}
	
	//retirando o subscribe, para usuário não receber mais mensagens dessa fila.s
	public void unsubscribe(String id) throws IOException, InterruptedException{
		Message msg = new Message();
		msg.header = new MessageHeader();
		msg.header.messageType = MessageType.UNSUBSCRIBE.getCode();
		msg.header.queue = queueName;
		
		msg.body = new MessageBody();
		
		inCrh.send(marshaller.marshall(msg));
	}
	
	//caso a conexão caia, o usuário deve utilizar essa função para reestabelecer a conexão
	public void resubscribe(String id) throws IOException, InterruptedException{
		Message msg = new Message();
		msg.header = new MessageHeader();
		msg.header.messageType = MessageType.RESUBSCRIBE.getCode();
		msg.header.queue = queueName;
		
		msg.body = new MessageBody();
		msg.body.object = id;
		
		inCrh.send(marshaller.marshall(msg));
	}
	
	
	//aguarda por uma mensagem do servidor de filas
	public String receive() throws Exception {	
		byte[] msgBytes = inCrh.receive();
		Marshaller marshaller = new Marshaller();
		Message msg = marshaller.unmarshall(msgBytes);
	
		return (String) msg.body.object;
	}
	
}
