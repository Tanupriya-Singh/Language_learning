package com.example.android.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    //variable for audio focus
    AudioManager audioManager;
    //obtain mediaplayer listener instance because it will be used again and agin
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
            //implement audio focus listener

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
        final ArrayList<Word> numbersWord = new ArrayList<>();
        numbersWord.add(new Word("lutti", "one", R.drawable.number_one, R.raw.number_one));
        numbersWord.add(new Word("otiiko", "two", R.drawable.number_two, R.raw.number_two));
        numbersWord.add(new Word("tolookosu", "three", R.drawable.number_three, R.raw.number_three));
        numbersWord.add(new Word("oyyisa", "four", R.drawable.number_four, R.raw.number_four));
        numbersWord.add(new Word("massokka", "five", R.drawable.number_five, R.raw.number_five));
        numbersWord.add(new Word("temmokka", "six", R.drawable.number_six, R.raw.number_six));
        numbersWord.add(new Word("kenekaku", "seven", R.drawable.number_seven, R.raw.number_seven));
        numbersWord.add(new Word("kawinta", "eight", R.drawable.number_eight, R.raw.number_eight));
        numbersWord.add(new Word("wo’e", "nine", R.drawable.number_nine, R.raw.number_nine));
        numbersWord.add(new Word("na’aacha", "ten", R.drawable.number_ten, R.raw.number_ten));


        WordAdapter itemsAdapter = new WordAdapter(this, numbersWord, R.color.category_numbers);

        GridView gridView = (GridView) findViewById(R.id.wordList);

        gridView.setAdapter(itemsAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //release media player if it exists
                releaseMediaPlayer();
                //get the word at that position
                Word funWord = numbersWord.get(i);
                //after getting the word, request for audio focus
                int result = audioManager.requestAudioFocus(mAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                //check the result
                if(result ==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // now play the sound
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, funWord.getSoundResourceId());
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

    public void releaseMediaPlayer(){
            if(mMediaPlayer!=null){
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
            audioManager.abandonAudioFocus(mAudioFocusChangeListener);
        }


    }


