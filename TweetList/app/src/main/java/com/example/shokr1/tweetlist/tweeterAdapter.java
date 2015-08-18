package com.example.shokr1.tweetlist;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by shokr 1 on 8/8/2015.
 */
public class tweeterAdapter extends ArrayAdapter {

    private List myList = new ArrayList();

    public tweeterAdapter(Context context, int resource)
    {
        super(context,resource);
    }

    static class DataHandler
    {
        ImageView userImage;
        TextView userName;
        TextView tweetText;
        TextView time;
        TextView accountName;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        myList.add(object);
    }

    @Override
    public int getCount() {
        return this.myList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.myList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        DataHandler handler;

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.tweet_row_layout,parent,false);
           handler = new DataHandler();
            handler.userImage = (ImageView) row.findViewById(R.id.user_image);
            handler.userName = (TextView) row.findViewById(R.id.user_name);

            handler.tweetText = (TextView) row.findViewById(R.id.tweet_text);

            handler.accountName = (TextView) row.findViewById(R.id.username);
            handler.time = (TextView) row.findViewById(R.id.post_time);
            row.setTag(handler);
        }
        else
        {
            handler = (DataHandler) row.getTag();
        }

        tweetsData tweetsObject = (tweetsData)this.getItem(position);

        handler.userName.setText(tweetsObject.getUserName());

        TextView tweet_text = (TextView) row.findViewById(R.id.tweet_text);
        tweet_text.setText(tweetsObject.getTweetText());
        tweet_text = setColor(tweet_text);
        //handler.tweetText.setText(tweetsObject.getTweetText());

        handler.tweetText.setText(tweet_text.getText());

        handler.accountName.setText(tweetsObject.getAccountName());
        handler.time.setText(tweetsObject.getTime());
        handler.userImage.setImageResource(tweetsObject.getImageSource());

        return row;
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
