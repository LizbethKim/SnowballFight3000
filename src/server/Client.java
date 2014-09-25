package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {
	private final String host;

	private Socket connection;

	public Client(String host) {
		this.host = host;
		try {
			connection = new Socket(host, 6015);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public void run() {
		while(true) {
			try {
				// debugging code
				byte in = readFromSocket();
				System.out.println(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
	}

	public void startReceiving() {
		Thread t = new Thread(this);
		t.start();
	}

	private byte readFromSocket() throws IOException {
		int input = connection.getInputStream().read();
		if (input == -1) {
			throw new IOException("Socket closed");
		}
		return (byte) input;
	}
}
