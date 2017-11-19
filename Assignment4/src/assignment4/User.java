package assignment4;

/**
 * Created by gydoy on 11/17/2017.
 */
public class User {
    private String username;
    private int followers;

    public String getUsername() {
        return username;
    }

    User(String username, Integer followers){
        this.setUsername(username);
        this.setFollowers(followers);
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
