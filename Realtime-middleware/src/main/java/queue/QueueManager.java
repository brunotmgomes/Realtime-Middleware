package queue;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import global.Marshaller;
import global.Message;
import global.datatypes.MessageBody;
import global.datatypes.MessageHeader;
import global.datatypes.MessageType;


//aqui é o equivalente ao invoker, ele só tem um método para esperar conexões, e faz uma coisa diferente,
//dependendo do tipo  de mensagem.

public class QueueManager {
	private int port; //porta que o servidor vai escutar.
	Map<String, Queue> queues = new HashMap<String, Queue>(); // lista de filas que o servidor vai controlar.
	
	private ServerSocket welcomeSocket = null;
	
	public QueueManager(int port) {
		this.port = port;
	}
	
	public void listen() throws IOException, Throwable{
		
		welcomeSocket = new ServerSocket(port);
		
		while(true){
			
			//primeiro recebe uma mensagem.
			//aceita a conexão de um cliente através do welcomeSocket.
			byte [] msgBytes = null;
			Socket connectionSocket = welcomeSocket.accept();
			
			//lê a mensagem
			DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
			int receivedMessageSize = inFromClient.readInt();
			msgBytes = new byte[receivedMessageSize];
			
			Marshaller marshaller = new Marshaller();
			Message msg = marshaller.unmarshall(msgBytes);
			
			//pega o nome da fila para qual o cliente enviou.
			String queueName = msg.header.queue;
			
			Queue queue = queues.get(queueName); //tenta pegar a fila.
			
			if(msg.header.messageType == MessageType.NEWMESSAGE.getCode()){ //aqui, o cliente está tentando
																			//publicar nessa fila
				
				if(queue != null){ //se a fila existe, ele coloca a mensagem na fila e notifica todos os escritos.
					queue.enqueue(msg);
					queue.notifySubscribers();
				}
			}else if(msg.header.messageType == MessageType.SUBSCRIBE.getCode()){ //aqui o cliente está tentando
																				 //se inscrever na fila.
				
				String id = UUID.randomUUID().toString(); //gera um id para o cliente.
		
				ConnectionObject cObj = new ConnectionObject(id, connectionSocket); //cria um ConnectionObject passando
																					//o socket atual e o id gerado.
				
				if(queue != null){ //se a fila com o nome passado já existe, apenas inscreve o cliente na fila.
					queue.subscribe(cObj);
				}else{ //caso a fila com o nome passado não exista, cria a fila e depois inscreve o cliente.
					queue = new Queue();
					queue.subscribe(cObj);
					queues.put(queueName, queue);
				}
				
				//daqui pra baixo, o servidor está enviando o id que representa o cliente no servidor
				//para ele. Isso serve para poder se retirar de uma lista e se reconectar caso a conexão caia.
				DataOutputStream output = new DataOutputStream(connectionSocket.getOutputStream());

				Message replyMsg = new Message();
				replyMsg.header = new MessageHeader();
				replyMsg.header.messageType = MessageType.SUBSCRIBE.getCode();
				replyMsg.header.queue = queueName;
				replyMsg.body = new MessageBody();
				replyMsg.body.object = id;
				
				byte[] replyMsgBytes = marshaller.marshall(replyMsg);
				int replyMessageSize = replyMsgBytes.length;
				output.writeInt(replyMessageSize);
				output.write(replyMsgBytes,0, replyMessageSize);
				output.flush();
	
			}else if(msg.header.messageType == MessageType.UNSUBSCRIBE.getCode()){
				//se a fila existe, retira-se o cliente da lista, através do id que ele enviou na mensagem.
				if(queue != null){
					queue.unsubscribe((String) msg.body.object);
				}
			}else if(msg.header.messageType == MessageType.RESUBSCRIBE.getCode()){
				//se a fila existe, reconecta o cliente na lista, através do id que ele enviou na mensagem.
				if(queue != null){
					queue.resubscribe((String) msg.body.object, connectionSocket);
				}
			}
		}
		

	}
}
