package com.example.shokr1.tweetlist;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends Activity {

    tweeterAdapter adapter;
    ListView tweetsList;


    //int [] usersImageResource = {R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.img5,
    //      R.drawable.img6,R.drawable.img7,R.drawable.img8};
    //String [] userNames;
    //String [] tweetsText;
    //tweeterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        int [] usersImageResource = {R.drawable.default_profile_4,R.drawable.default_profile_4,R.drawable.default_profile_4,
//                R.drawable.default_profile_4,R.drawable.default_profile_4,R.drawable.default_profile_4,
//                R.drawable.default_profile_4,R.drawable.default_profile_4};
//
//        ListView  tweetsList = (ListView) findViewById(R.id.tweetList);
//        String []  userNames = getResources().getStringArray(R.array.names);
//        String []  tweetsText = getResources().getStringArray(R.array.tweets);
//        String [] tweetTimes = getResources().getStringArray(R.array.time);
//        String [] accountNames = getResources().getStringArray(R.array.accountnames);
//
//        adapter = new tweeterAdapter(getApplicationContext(),R.layout.tweet_row_layout);
//
//        tweetsList.setAdapter(adapter);
//        for(int i=0;i<usersImageResource.length;i++)
//        {
//            //exception when using all images, So I used first image only for now
//            tweetsData tweetsObject = new tweetsData(usersImageResource[i],userNames[i],tweetsText[i],accountNames[i],tweetTimes[i]);
//            adapter.add(tweetsObject);
//        }

        adapter = new tweeterAdapter(getApplicationContext(), R.layout.tweet_row_layout);
        tweetsList = (ListView) findViewById(R.id.tweetList);

        tweetsList.setAdapter(adapter);
        FetchTweets ft = new FetchTweets();
        ft.execute();

        tweetsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {

                tweetsData selectedFromList = (tweetsData) (tweetsList.getItemAtPosition(myItemInt));
                String[] data = {String.valueOf(selectedFromList.getImageSource()),selectedFromList.getUserName(),selectedFromList.getAccountName(),
                selectedFromList.getTime(),selectedFromList.getTweetText()};

                Intent intent = new Intent(MainActivity.this, PopUpWindow.class);
                intent.putExtra("tweet",data);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            FetchTweets ft = new FetchTweets();
//            ft.execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class FetchTweets extends AsyncTask<Void, Void, ArrayList<tweetsData>> {

        @Override
        protected ArrayList<tweetsData> doInBackground(Void... params) {

            String jsonText = loadJSONFromAsset();
            ArrayList<tweetsData> tweets = new ArrayList<tweetsData>();
            try {
                tweets = getTweets(jsonText);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return tweets;
            //return new tweetsData[0];
        }

        public String loadJSONFromAsset() {
            String json = null;
            try {
                InputStream is = getAssets().open("tweets.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }

        @Override
        protected void onPostExecute(ArrayList<tweetsData> tweets) {
            if (tweets != null) {
                adapter.clear();
                for (int i = 0; i < tweets.size(); i++) {
                    adapter.add(tweets.get(i));
                }
            }
        }

        private ArrayList<tweetsData> getTweets(String jsonString) throws JSONException {


            JSONObject jsonTweets = new JSONObject(jsonString);
            JSONArray Statuses = jsonTweets.getJSONArray("statuses");

            ArrayList<tweetsData> tweets = new ArrayList<tweetsData>();

            for (int i = 0; i < Statuses.length(); i++) {

                JSONObject status = Statuses.getJSONObject(i);
                String statusText = status.getString("text");

                String full_time = status.getString("created_at");

                String time = getTime(full_time);

                JSONObject user = status.getJSONObject("user");

                String userName = user.getString("name");
                String accountName = user.getString("screen_name");

                //String image_url = user.getString("profile_image_url");


                tweetsData tweetsObject = new tweetsData(R.drawable.default_profile_4, userName, statusText, "@" + accountName, time);

                tweets.add(tweetsObject);
            }

            return tweets;

        }

        private String getTime(String time) {

            Date d = new Date(time);
            Date now = new Date();
            double diff = now.getTime() - d.getTime();

            int days = (int) (diff / (1000 * 60 * 60 * 24));
            int hours = (int) ((diff - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            int minutes = (int) (diff - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);

            int weeks = (int) days / 7;
            int months = (int) days / 30;
            int years = (int) days / 365;

            String result = "";

            if (years > 0) {
                result = years + "years";
            } else if (months > 0) {
                result = months + "months";
            } else if (weeks > 0) {
                result = weeks + "weeks";
            } else if (days > 0) {
                result = days + "days";
            } else if (hours > 0) {
                result = hours + "hours";
            } else if (minutes > 0) {
                result = minutes + "mins";
            } else {
                result = diff + "Secs";
            }

            return result;
        }
    }
}
