package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here

        UserMap mapInstance=new UserMap();
        mapInstance.readInUsers("user.txt");
        mapInstance.DisplayMapData("tweet.txt");
    }
}
