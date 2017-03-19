package com.company;

import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import static org.mockito.Mockito.*;


/**
 * Created by Siphamandla on 3/18/2017.
 */
public class UserMapTest {
    @Mock
    UserMap uMap;

    @org.junit.Before
    public void setUp() throws Exception {
            //create mock objects
        uMap=new UserMap();
    }

    @org.junit.After
        public void tearDown() throws Exception {

        }

    @org.junit.Test(expected = Exception.class)
    public void readInUsers() throws Exception {
        //Check what happens when file specified is not found
        uMap=mock(UserMap.class);//mocking the UserMap class
        doThrow(new FileNotFoundException()).when(uMap).readInUsers("wrong.txt");

        //Check if readUser method calls populateMap
        UserMap spy= spy(new UserMap());
        doReturn(1).when(spy).populateMap();

    }

    @org.junit.Test
    public void checkUserSentenceStructure() throws Exception {
        //Checks the sentence structure for a user entry
        UserMap uMap=new UserMap();
        String sentenceOne=" Tim and paul, mark ";//now ;follows in the sentence
        boolean isCorrect=uMap.checkUserSentenceStructure(sentenceOne);
        Assert.assertEquals(sentenceOne,false,isCorrect);

        String sentenceTwo=" follows paul, mark "; //Missing user before the follows
        isCorrect=uMap.checkUserSentenceStructure(sentenceTwo);
        Assert.assertEquals(sentenceOne,false,isCorrect);

        String sentenceThree=" Tim follows paul, mark "; //Missing user before the follows
        isCorrect=uMap.checkUserSentenceStructure(sentenceThree);
        Assert.assertEquals(sentenceOne,true,isCorrect);
    }

    @org.junit.Test
    public void checkTweetSentenceStructure() throws Exception {

        UserMap uMap=new UserMap();
        String sentenceOne=" > This message is incorrect!";
        boolean isCorrect=uMap.checkTweetSentenceStructure(sentenceOne);
        Assert.assertEquals(sentenceOne,false,isCorrect);

        String sentenceTwo=" Alan -- This message does not have the correct seperator";
        isCorrect=uMap.checkTweetSentenceStructure(sentenceTwo);
        Assert.assertEquals(sentenceTwo,false,isCorrect);

        String sentenceThree="Alan > This sentence is well formed!";
        isCorrect=uMap.checkTweetSentenceStructure(sentenceThree);
        Assert.assertEquals(sentenceThree,true,isCorrect);
    }

    @org.junit.Test(expected = Exception.class)
    public void displayMapData() throws Exception {

        //Check File not found exception case
        uMap=mock(UserMap.class);//mocking the UserMap class
        doThrow(new FileNotFoundException()).when(uMap).attachTweet(anyString(),anySet(),"wrong.txt");

        //Check if user data calls attachTweet method
        UserMap spy= spy(new UserMap());
        doReturn(1).when(spy).attachTweet(anyString(),anySet(),anyString());

    }

}