package nl.wj44.wjbot;
import java.util.Observable;
import java.util.Observer;

/**
 * Part of WJBot.
 * Created by Wesley Joosten on 04/01/2019
 */
public class Bot implements Observer {

	@Override
	public void update(Observable arg0, Object arg1) {
		Message msg = (Message) arg1;
		Connection connection = (Connection) arg0;
		if(msg.getMessage().equals("!hi")) {
			connection.sendChat("Hi " + msg.getName());
		} else if (msg.badgeList().contains("broadcaster") && msg.getMessage().equals("!stop")) {
			connection.sendChat("Goodbye! o/");
			connection.stop();
		}
		
	}

}
