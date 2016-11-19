package queue;

import java.io.IOException;

public class QueueServer {

	public static void main(String[] args) throws IOException, Throwable {
		//inicia o servidor de filas na porta 9000.
		
		QueueManager queueManager = new QueueManager(9000);
		queueManager.listen();
	}

}
