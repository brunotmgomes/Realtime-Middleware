package queue;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import global.Message;

// A queue guarda a lista de clientes conectados.
public class Queue {
	//guarda um connectionObject, referenciando seu id.
	private HashMap<String, ConnectionObject> connectionQueue = new HashMap<String, ConnectionObject>();
	
	//adiciona um ConnectionObject a fila. (inscreve um cliente para receber mensagens).
	public void subscribe(ConnectionObject connectionObject){
		connectionQueue.put(connectionObject.getId(), connectionObject);
	}
	
	//retira o cliente da lista.
	public void unsubscribe(String id) throws IOException{
		ConnectionObject cObj = connectionQueue.get(id);
		if(cObj != null){
			cObj.closeSocket(); //fecha o socket, antes de remover o cliente da lista.
			connectionQueue.remove(id);
		}
		
	}
	
	public void resubscribe(String id, Socket skt){ //troca o socket, assim, reestabelecendo a conexão.
		ConnectionObject cObj= connectionQueue.get(id);
		if(cObj != null){
			cObj.setSocket(skt);
		}
		
	}
	
	public void enqueue(Message msg){ //adiciona uma mensagem a todos os clientes cadastrados na fila.
		for (ConnectionObject cObj : connectionQueue.values()) {
			cObj.enqueue(msg);
		}
	}

	public void notifySubscribers(){ //envia todas as mensagens que estão na lista do cliente, para ele.
		for (ConnectionObject cObj : connectionQueue.values()) {
			cObj.notifyClient();
		}
	}
}
