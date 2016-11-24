package global;

public class ExponentialBackoffHandler {

	private int numAttempts;
	
	
	public ExponentialBackoffHandler(){
		numAttempts = 0;
	}
	
	public void waitABit(){
		int waitTime = (int) Math.pow(2,numAttempts);
		waitTime *= 200;
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		numAttempts++;
	}
	
	
}
