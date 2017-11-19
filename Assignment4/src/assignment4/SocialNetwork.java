package assignment4;

import java.util.*;

/**
 * Social Network consists of methods that filter users matching a
 * condition.
 *
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Get K most followed Users.
     *
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @param k
     *            integer of most popular followers to return
     * @return the set of usernames who are most mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getName()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like ethomaz@utexas.edu does NOT
     *         contain a mention of the username.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static List<String> findKMostFollower(List<Tweets> tweets, int k) {
        List<String> mostFollowers = new ArrayList<>();
        HashMap<String, Integer> followers = new HashMap<>();

        //record mentions for all users
        for(Tweets tweet: tweets){
            String[] words = tweet.getText().split(" ");
            for(String word: words){
                if(word.matches("(?<![A-Za-z0-9_])[@]([A-Za-z0-9_]+)")){
                    if(!followers.containsKey(word)) {
                        followers.put(word,1);
                    }
                    else {
                        followers.put(word, followers.get(word)+1);
                    }
                }
            }

        }
        ArrayList<User> sortedUsers = new ArrayList<>();
        for(String key: followers.keySet()){
            User user = new User(key, followers.get(key));
            sortedUsers.add(user);
        }
        Collections.sort(sortedUsers, (user1, user2) -> user2.getFollowers()-user1.getFollowers());
        for(int i = 0;i<k;i++){
            mostFollowers.add(sortedUsers.get(i).getUsername());
        }
        System.out.println(mostFollowers);
        return mostFollowers;
    }

    /**
     * Find all cliques in the social network.
     *
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     *
     * @return list of set of all cliques in the graph
     */
    List<Set<String>> findCliques(List<Tweets> tweets) {
        List<Set<String>> result = new ArrayList<Set<String>>();
        return result;
    }
}


