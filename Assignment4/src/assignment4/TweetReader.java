package assignment4;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * TweetReader contains method used to return tweets from method
 * Do not change the method header
 */
public class TweetReader {

    /**
     * Find tweets written by a particular user.
     *
     * @param url
     *            url used to query a GET Request from the server
     * @return return list of tweets from the server
     *
     */
    public static List<Tweets> readTweetsFromWeb(String url) throws Exception
    {
        List<Tweets> tweetList = new ArrayList<>();

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //GET request
        con.setRequestMethod("GET");
        //checking response code

        //getting data from website
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //Json -> Java object
        ObjectMapper mapper = new ObjectMapper();
        Tweets [] tweeters = mapper.readValue(response.toString(), Tweets[].class);
        for(Tweets tweet: tweeters){
            if(checkTweet(tweet)){
                tweetList.add(tweet);
            }
        }

        //System.out.println(tweetList);

        return tweetList;
    }

    //check if tweet is valid
    public static boolean checkTweet(Tweets tweet){
        if(!((tweet.getId() > 0) && (tweet.getId() < Math.pow(2, 31)))){
            return false;
        }
        if((tweet.getName()==null)||(!(tweet.getName().matches("([A-Za-z0-9_]+)")))){//check A-Z
            return false;
        }
        if((tweet.getText()==null)||(tweet.getText().length()>140)){
            return false;
        }
        if(tweet.getDate()==null) {
            return false;
        }
        if(tweet.getDate()!=null){
            try {
                Instant instant = Instant.parse(tweet.getDate());
            }catch(DateTimeParseException instant){
                
                return false;
            }
            return true;
        }
        return true;
    }
}
