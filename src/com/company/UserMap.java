package com.company;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Siphamandla on 3/18/2017.
 */
public class UserMap {

    private SortedMap<String,Set> TwitterMap;
    private String[] followersList=null;
    private String[]sentenceSplit=null;

    public UserMap()
    {
        TwitterMap=new TreeMap<>();
    }

    /*
    Reads in a file name and creates a mapping of users and followers
     */
    public void readInUsers(String fileName)
    {
        try {
            FileInputStream userStream=new FileInputStream(fileName);
            BufferedReader userBuffer=new BufferedReader(new InputStreamReader(userStream));

            //Read in and check sentence criteria
            String strLine;

            while ((strLine=userBuffer.readLine())!=null)
            {

                boolean isCorrectStructure=checkUserSentenceStructure(strLine);
                if(isCorrectStructure)
                {
                    //Populate the map with users and their followers
                    populateMap();
                }
                else
                {
                    System.out.println("Incorrect user.txt sentence structure:"+"'"+strLine);
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Checks the sentence structure to validate:
    Sentence contains the word 'follows'
    Sentence must have a handle before the word follows
     */
    public boolean checkUserSentenceStructure(String sentence)
    {
        boolean correctStructure=false;
        followersList=null;
        if(sentence.contains("follows"))
        {
            followersList=sentence.split("follows");
            String follower=followersList[0].trim();
            //Check for follower
            if(follower.length()>0)
            {

                correctStructure=true;
            }
            else
            {
                correctStructure=false;
            }
        }
        else {
            correctStructure=false;
        }
        return correctStructure;
    }

    private void populateMap()
    {
        String [] peopleFollowing=followersList[1].split(",");
        Set<String>followerSet=null;

        if(TwitterMap.containsKey(followersList[0].trim()))
        {
            for(String person :peopleFollowing)
            {
                followerSet=TwitterMap.get(followersList[0].trim());
                followerSet.add(person.trim());

                if(!TwitterMap.keySet().contains(person.trim()))
                {
                    Set EmptySet=new HashSet();
                    TwitterMap.put(person.trim(),EmptySet);
                }
            }
        }
        else
        {
            followerSet=new HashSet<>();
            for(String  person : peopleFollowing)
            {
                followerSet.add(person.trim());
            }
            TwitterMap.put(followersList[0].trim(),followerSet);
        }
    }

    /*
    Checks the sentence sturcture for each tweet, a tweet must:
    contain '>' as a seperator
    have a handle before the >
     */
    public boolean checkTweetSentenceStructure(String sentence) {
        boolean isCorrect=false;

        if(sentence.contains(">"))
        {
            sentenceSplit=sentence.split(">");
            String Handle =sentenceSplit[0];
            String Message=sentenceSplit[1];
            if((Handle.trim().length()>0)&&(Message.length()>0))
            {
                isCorrect=true;
            }

        }
        else {
            isCorrect=false;
        }

        return isCorrect;
    }

    /*
    Attaches the message to the followers feed
     */
    public void DisplayMapData(String fileName)
    {
        for(Map.Entry<String,Set> mapEntry:TwitterMap.entrySet())
        {
            String sender=mapEntry.getKey();
            Set userBucket=mapEntry.getValue();
            System.out.println(sender);
            System.out.println();
            attachTweet(sender,userBucket,fileName);
        }
    }

    private void attachTweet(String sender,Set bucket,String fileName)
    {
        try {
            FileInputStream stream=new FileInputStream(fileName);
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(stream));
            String strLine;

            while ((strLine=bufferedReader.readLine())!=null)
            {
                boolean isCorrect=checkTweetSentenceStructure(strLine);
                if(isCorrect)
                {
                    //Then Display
                    if((sender.equals(sentenceSplit[0].trim()))||(bucket.contains(sentenceSplit[0].trim())))
                    {
                        System.out.println("    @"+sentenceSplit[0].trim()+": "+sentenceSplit[1]);
                        System.out.println();
                    }
                }
                else
                {
                    System.out.println("Incorrect tweet structure :"+"'"+strLine);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
