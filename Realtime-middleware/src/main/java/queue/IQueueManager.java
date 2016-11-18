package queue;

import global.Message;

public interface IQueueManager {
	public void publish(String msg) throws Exception;
	
	public String receive() throws Exception;
}
