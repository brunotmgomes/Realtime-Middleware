package queue;

//implementado por QueueManagerProxy.
public interface IQueueManager {
	public String subscribe() throws Exception;
	public void unsubscribe(String id) throws Exception;
	public void resubscribe(String id) throws Exception;
	
	//usado para enviar mensagens
	public void publish(String msg) throws Exception;
	
	//usado para receber mensagens
	public String receive() throws Exception;
}
