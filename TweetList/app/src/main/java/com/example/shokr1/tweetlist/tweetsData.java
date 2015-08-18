package com.example.shokr1.tweetlist;

/**
 * Created by shokr 1 on 8/8/2015.
 */
public class tweetsData {

    private String userName;
    private String tweetText;
    private int imageSource;

    private String accountName;
    private String time;

    public tweetsData(int imageSource,String userName,String tweetText, String accountName, String time)
    {
        this.setImageSource(imageSource);
        this.setUserName(userName);
        this.setTweetText(tweetText);
        this.setAccountName(accountName);
        this.setTime(time);

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText)
    {
        this.tweetText = tweetText;
    }


    public int getImageSource() {
        return imageSource;
    }

    public void setImageSource(int imageSource) {
        this.imageSource = imageSource;
    }


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
