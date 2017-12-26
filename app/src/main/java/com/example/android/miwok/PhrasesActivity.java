package com.example.android.miwok;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.IOException;
import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
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
        final ArrayList<Word> phrasesWord = new ArrayList<>();
        phrasesWord.add(new Word("minto wuksus", "Where are you going?", R.raw.phrase_where_are_you_going));
        phrasesWord.add(new Word("tinnә oyaase'nә", "What is your name", R.raw.phrase_what_is_your_name));
        phrasesWord.add(new Word("oyaaset", "My name is:", R.raw.phrase_my_name_is));
        phrasesWord.add(new Word("michәksәs?", "How are you feeling?", R.raw.phrase_how_are_you_feeling));
        phrasesWord.add(new Word("kuchi achit", "I'm feeling good", R.raw.phrase_im_feeling_good));
        phrasesWord.add(new Word("әәnәs'aa?", "Are you coming?", R.raw.phrase_are_you_coming));
        phrasesWord.add(new Word("hәә’ әәnәm", "Yes,I'm coming", R.raw.phrase_yes_im_coming));
        phrasesWord.add(new Word("әәnәm", "I'm coming", R.raw.phrase_im_coming));
        phrasesWord.add(new Word("yoowutis", "Let's go", R.raw.phrase_lets_go));
        phrasesWord.add(new Word("әnni'nem", "Come here", R.raw.phrase_come_here));

        WordAdapter itemsAdapter = new WordAdapter(this, phrasesWord, R.color.category_phrases);
        GridView gridView = (GridView) findViewById(R.id.wordList);
        gridView.setAdapter(itemsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //release media player if it exists
                releaseMediaPlayer();
                //get the word at that position
                Word funWord = phrasesWord.get(i);
                //after getting the word, request for audio focus
                int result = audioManager.requestAudioFocus(mAudioFocusChangeListener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                //check the result
                if(result ==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // now play the sound
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, funWord.getSoundResourceId());
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
    }
}


