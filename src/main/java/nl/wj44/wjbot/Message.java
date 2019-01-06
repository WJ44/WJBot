package nl.wj44.wjbot;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Part of WJBot.
 * Created by Wesley Joosten on 04/01/2019
 */
public class Message {

    private static final String REGEX = "@badges=((?:\\w+/\\d+,?)*)?.*display-name=(\\w+).*mod=(\\d).* :\\w+!\\w+@(\\w+).tmi.twitch.tv PRIVMSG.* :(.*)";
    private String user;
    private String displayName;
    private List<String> badges;
    private boolean isMod;
    private String message;

    public Message(String msg) {
        badges = new ArrayList<>();
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(msg);
        while (m.find()) {
            if (!m.group(1).isEmpty()) {
                String[] groups = m.group(1).split(",");
                for (String badge : groups) {
                    badges.add(badge.substring(0, badge.indexOf('/')));
                }
            }
            displayName = m.group(2);
            isMod = m.group(3).equals("1");
            user = m.group(4);
            message = m.group(5);
        }
    }

    public String getUser() {
        return user;
    }

    public List<String> badgeList() {
        return badges;
    }

    public boolean isModerator() {
        return isMod;
    }

    public String getName() {
        return displayName != null ? displayName : user;
    }

    public String getMessage() {
        return message;
    }
}
