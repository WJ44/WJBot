package nl.wj44.wjbot;
import java.io.*;

/**
 * Part of WJBot.
 * Created by Wesley Joosten on 04/01/2019
 */
public class WJBot {
	
	public static void main(String[] args) {
		Connection connection = new Connection();
		Bot bot = new Bot();
		connection.addObserver(bot);
		connection.connect();
		try {
			connection.run();
		} catch (IOException e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
		}
	}
}
