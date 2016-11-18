package queue;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;

public class ClientRequestHandler {
	private String host;
	private int port;
	private int sentMessageSize;
	private int receiveMessageSize;
	
	private Socket clientSocket = null;
	private DataOutputStream outToServer;
	private DataInputStream inFromServer;
	
	InetAddress IPAddress;
	
	
	public ClientRequestHandler(String host, int port) throws IOException{
		this.host = host;
		this.port = port;
	    
	}
	
	public void send(byte [] msg) throws IOException, InterruptedException{
			clientSocket = new Socket(this.host, this.port);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new DataInputStream(clientSocket.getInputStream());
			
			sentMessageSize = msg.length;
			outToServer.writeInt(sentMessageSize);
			outToServer.write(msg,0, sentMessageSize);
			outToServer.flush();
	}
	
	public byte [] receive() throws IOException, InterruptedException, ClassNotFoundException{
		byte [] msg = null;
        receiveMessageSize = inFromServer.readInt();
        msg = new byte[receiveMessageSize];
        inFromServer.readFully(msg, 0, receiveMessageSize);
        
        clientSocket.close();
        outToServer.close();
        inFromServer.close();
		
		return msg;
		
	}
}
