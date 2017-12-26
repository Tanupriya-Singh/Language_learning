package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
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

        final ArrayList<Word>familyWord = new ArrayList<>();
        familyWord.add(new Word("әpә","father",R.drawable.family_father,R.raw.family_father));
        familyWord.add(new Word("әṭa","mother",R.drawable.family_mother,R.raw.family_mother));
        familyWord.add(new Word("angsi","son",R.drawable.family_son,R.raw.family_son));
        familyWord.add(new Word("tune","daughter",R.drawable.family_daughter,R.raw.family_daughter));
        familyWord.add(new Word("taachi","older brother",R.drawable.family_older_brother,R.raw.family_older_brother));
        familyWord.add(new Word("chalitti","younger brother",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        familyWord.add(new Word("teṭe","older sister",R.drawable.family_older_sister,R.raw.family_older_sister));
        familyWord.add(new Word("kolliti","younger sister",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        familyWord.add(new Word("ama","grandmother",R.drawable.family_grandmother,R.raw.family_grandmother));
        familyWord.add(new Word("paapa","grandfather",R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter itemsAdapter = new WordAdapter(this,familyWord,R.color.category_family);
        GridView gridView = (GridView) findViewById(R.id.wordList);
        gridView.setAdapter(itemsAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //release media player if it exists
                releaseMediaPlayer();
                //get the word at that position
                Word funWord = familyWord.get(i);
                //after getting the word, request for audio focus
                int result = audioManager.requestAudioFocus(mAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                //check the result
                if(result ==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // now play the sound
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, funWord.getSoundResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
                // if not granted do not do anything.
            }
        });






        //request audio focus
        //audioManager.requestAudioFocus(audioListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

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
    }



}

