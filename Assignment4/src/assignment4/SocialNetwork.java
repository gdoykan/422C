package assignment4;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Social Network consists of methods that filter users matching a
 * condition.
 *
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {
    public static final String regex = "(?<![A-Za-z0-9_])[@]([A-Za-z0-9_]+)";

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

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tweets.get(0).getText());

        //record mentions for all users
        for(Tweets tweet: tweets){
            matcher = pattern.matcher((tweet.getText()));
            while(matcher.find()){
                for(int i = 0; i< matcher.groupCount();i++){
                    if(!followers.containsKey(matcher.group(i).toLowerCase())) {
                        followers.put(matcher.group(i).toLowerCase(),1);
                    }
                    else {
                        followers.put(matcher.group(i).toLowerCase(), followers.get(matcher.group(i).toLowerCase())+1);
                    }
                }
            }

        }

        //sort all users based on followers
        ArrayList<User> sortedUsers = new ArrayList<>();
        for(String key: followers.keySet()){
            User user = new User(key, followers.get(key));
            sortedUsers.add(user);
        }
        Collections.sort(sortedUsers, (user1, user2) -> user2.getFollowers()-user1.getFollowers());

        //put k mostfollowed user into arraylist
        if(k>sortedUsers.size()){
            for(int i = 0;i<sortedUsers.size();i++){
                mostFollowers.add(sortedUsers.get(i).getUsername());
            }
        }else{
            for(int i = 0;i<k;i++){
                mostFollowers.add(sortedUsers.get(i).getUsername().replace("@", ""));
            }
        }

        System.out.println(mostFollowers);
        return mostFollowers;
    }

    public static HashMap<String, HashSet> userMentions(List<Tweets> tweets){
        HashMap<String, HashSet> users = new HashMap<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tweets.get(0).getText());

        for(Tweets tweet: tweets) {
            matcher = pattern.matcher((tweet.getText()));

            if (!users.containsKey(tweet.getName().toLowerCase())) { //new user
                HashSet<String> val = new HashSet<>();
                users.put(tweet.getName().toLowerCase(), val);

                while(matcher.find()){ //add mentions to hashset
                    for(int i = 0; i< matcher.groupCount();i++){
                        val.add(matcher.group(i).replace("@", "").toLowerCase());
                    }
                }
            }else{ //existing user, add users he mentions
                while(matcher.find()){
                    for(int i = 0; i< matcher.groupCount();i++){
                        users.get(tweet.getName().toLowerCase()).add((matcher.group(i)).replace("@", "").toLowerCase());
                    }
                }
            }
        }

        return users;
    }

    /**
     * Find all cliques in the social network.
     *
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     *
     * @return list of set of all cliques in the graph
     */
    public static List<Set<String>> findCliques(List<Tweets> tweets) {
        List<Set<String>> result = new ArrayList<Set<String>>();
        HashMap<String, HashSet> usersMap = userMentions(tweets);

        for(String k: usersMap.keySet()){
            //iterate through every person mentioned by this user
            boolean flag = false;
            boolean duplicate = false;
            HashSet<String> clique = new HashSet<>();
            for(Object mentionedUser: usersMap.get(k)){
                if(mentionedUser.toString().equals(k)){ //if user mentions himself
                    continue;
                }
                if(!usersMap.containsKey(mentionedUser.toString())){ //if mentioned user has never tweeted before
                    continue;
                }
                for(Object j: usersMap.get(mentionedUser.toString())){
                    //System.out.println(j);
                    //if mutually mentioned add to clique
                    if(j.toString().toLowerCase().equals(k.toLowerCase())){
                        if(!flag){
                            clique.add(k);
                            clique.add(mentionedUser.toString());
                            flag = true;
                        }else{
                            clique.add(mentionedUser.toString());
                        }
                        break;
                    }
                }
            }
            //check if clique was formed
            if(flag) {
                //check if this clique already exists
                for(Object m: result){
                    if(clique.equals(m)){
                        duplicate = true;
                        break;
                    }
                }
                if(!duplicate){
                    result.add(clique);
                    //System.out.println(clique);
                }
            }
        }
        //System.out.println(result);
        return result;
    }
}


