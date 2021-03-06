package assignment4;

import java.util.HashSet;

/**
 * Created by gydoy on 11/17/2017.
 */
public class User {
    private String username;
    private int followers;

    public HashSet<String> getMentions() {
        return mentions;
    }

    public void setMentions(HashSet<String> mentions) {
        this.mentions = mentions;
    }

    private HashSet<String> mentions;

    public String getUsername() {
        return username;
    }

    User(String username, Integer followers){
        this.setUsername(username);
        this.setFollowers(followers);
        mentions = new HashSet<>();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }
}
