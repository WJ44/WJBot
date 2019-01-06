package nl.wj44.wjbot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

/**
 * Part of WJBot.
 * Created by Wesley Joosten on 04/01/2019
 */
public class Connection extends Observable{
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private boolean stopped = false;
	
	private String channel = "#wj44";
	
	public void connect() {
		try {
			socket = new Socket("irc.twitch.tv", 6667);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Unable to connect to server");
			e.printStackTrace();
		}
		
		sendMessage("PASS oauth:s79yap320n26ygp9itvrv25mzqqmd5");
		sendMessage("NICK WJBot");
		sendMessage("CAP REQ :twitch.tv/tags");
		sendMessage("JOIN " + channel);
		sendChat("Hello! o/");
	}
	
	public void run() throws IOException {
		while (!stopped) {
			String message;
			while (!stopped && (message = in.readLine()) != null) {
				System.out.println(message);
				if(message.equals("PING :tmi.twitch.tv")) {
					sendMessage("PONG :tmi.twitch.tv");
				} else if (message.contains("PRIVMSG " + channel + " :")) {
					setChanged();
					notifyObservers(new Message(message));
				}
			}
		}
	}
	
	public void sendMessage(String msg) {
		out.println(msg);
		System.out.println(">> " + msg);
	}
	
	public void sendChat(String msg) {
		sendMessage("PRIVMSG " + channel + " :" + msg);
	}
	
	public void stop() {
		try {
			stopped = true;
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("Error while closing server");
			e.printStackTrace();
		}

	}
}
