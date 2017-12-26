package com.example.android.miwok;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class ColorsActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    //variable for audio focus
    AudioManager audioManager;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };
    //obtain audiomanager listener instance becuase it will beused again and again
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {

                @Override
                public void onAudioFocusChange(int focusChange) {

                    if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
                    {
                        if(mMediaPlayer!= null){
                            mMediaPlayer.pause();
                            mMediaPlayer.seekTo(0);}

                    }
                    if(focusChange == AudioManager.AUDIOFOCUS_GAIN)
                        if(mMediaPlayer!= null)mMediaPlayer.start();

                    if(focusChange == AudioManager.AUDIOFOCUS_LOSS)
                        releaseMediaPlayer();

                }
            };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        // initialize audiomanager instance
        audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        final ArrayList<Word> colorsWord = new ArrayList<>();
        colorsWord.add(new Word("weṭeṭṭi", "red", R.drawable.color_red, R.raw.color_red));
        colorsWord.add(new Word("chokokki", "green", R.drawable.color_green, R.raw.color_green));
        colorsWord.add(new Word("ṭakaakki", "brown", R.drawable.color_brown, R.raw.color_brown));
        colorsWord.add(new Word("ṭopoppi", "gray", R.drawable.color_gray, R.raw.color_gray));
        colorsWord.add(new Word("kululli", "black", R.drawable.color_black, R.raw.color_black));
        colorsWord.add(new Word("kelelli", "white", R.drawable.color_white, R.raw.color_white));
        colorsWord.add(new Word("ṭopiisә", "dusty yellow", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        colorsWord.add(new Word("chiwiiṭә", "mustard yellow", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        WordAdapter itemsAdapter = new WordAdapter(this, colorsWord, R.color.category_colors);
        GridView gridView = (GridView) findViewById(R.id.wordList);
        gridView.setAdapter(itemsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //release media player if it exists
                releaseMediaPlayer();
                //get the word at that position
                Word funWord = colorsWord.get(i);
                //after getting the word, request for audio focus
                int result = audioManager.requestAudioFocus(mAudioFocusChangeListener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                //check the result
                if(result ==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // now play the sound
                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this, funWord.getSoundResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
                // if not granted do not do anything.
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    public void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

}


