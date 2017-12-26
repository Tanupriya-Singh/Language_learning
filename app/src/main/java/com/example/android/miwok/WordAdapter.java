package com.example.android.miwok;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Tanupriya on 10-Nov-17.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    //MediaPlayer mMediaPlayer;
    private int categoryColorId;


    public WordAdapter(Activity context, ArrayList<Word> androidWord,int colorId) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, androidWord);
        categoryColorId = colorId;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        final Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.miwokword);
        //set the background color
        nameTextView.setBackgroundResource(categoryColorId);
        // Get the miwok translation and
        // set this text on the name TextView
        nameTextView.setText(currentWord.getMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.engword);
        //set the background color
        numberTextView.setBackgroundResource(categoryColorId);
        // Get the default translation and
        // set this text on the number TextView
        numberTextView.setText(currentWord.getDefaultTranslation());

        //get the image and set it to imageview
        ImageView displayImage = (ImageView)listItemView.findViewById(R.id.image_view);
        if(currentWord.hasImage()) {
            displayImage.setImageResource(currentWord.getImageResourceId());
            displayImage.setVisibility(View.VISIBLE);
        }

        else displayImage.setVisibility(View.GONE);


        /*ImageView playButton = (ImageView)listItemView.findViewById(R.id.play_image);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer = MediaPlayer.create(getContext(),currentWord.getSoundResourceId());
                mMediaPlayer.start();
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
            }
        });*/
        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}


