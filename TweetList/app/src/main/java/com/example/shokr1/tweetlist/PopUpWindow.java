package com.example.shokr1.tweetlist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by shokr 1 on 8/18/2015.
 */
public class PopUpWindow extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_layout);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int heigt = metrics.heightPixels;
        int width = metrics.widthPixels;

        getWindow().setLayout(width,(int)(heigt*.5));

        //getWindow().setLayout(width,heigt);

//        String[] data = {String.valueOf(selectedFromList.getImageSource()),selectedFromList.getUserName(),selectedFromList.getAccountName(),
//        selectedFromList.getTime(),selectedFromList.getTweetText()};

    String[] data = getIntent().getExtras().getStringArray("tweet");


        ImageView img = (ImageView) findViewById(R.id.user_image);
        img.setImageResource(Integer.parseInt(data[0]));

        TextView userName = (TextView) findViewById(R.id.user_name);
        userName.setText(data[1]);

        TextView accountName = (TextView) findViewById(R.id.username);
        accountName.setText(data[2]);

        TextView time = (TextView) findViewById(R.id.post_time);
        time.setText(data[3]);

        TextView tweetText = (TextView) findViewById(R.id.tweet_text);
        tweetText.setText(data[4]);
        tweetText = setColor(tweetText);



    }

    private TextView setColor(TextView view ) {

        String tweetText = view.getText().toString();
        view.setText(tweetText, TextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) view.getText();


        String[] words = tweetText.split(" ");

        for(int i=0; i<words.length;i++)
        {
            if (words[i].startsWith("@") || words[i].startsWith("#") || words[i].startsWith("http"))
            {
                int j = tweetText.indexOf(words[i]);
                str.setSpan(new ForegroundColorSpan(Color.BLUE), j, j + words[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
        }

        view.setText(str);
        return view;


    }


}
