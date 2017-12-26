package com.example.android.miwok;

/**
 * Created by Tanupriya on 23-Oct-17.
 */

public class Word {
    private String mMiwokTranslation;
    private String mDefaultTranslation;
    private int mSoundResourceId;
    private int mImageResourceId = NO_IMAGE_PRESENT;
    private static final int NO_IMAGE_PRESENT = -1;

    public Word(String miwokTranslation,String defaultTranslation,int soundResourceId ){
        mMiwokTranslation = miwokTranslation;
        mDefaultTranslation = defaultTranslation;
        mSoundResourceId = soundResourceId;
    }

    public Word(String miwokTranslation,String defaultTranslation,int imageResourceId,int soundResourceId){
        mMiwokTranslation = miwokTranslation;
        mDefaultTranslation = defaultTranslation;
        mImageResourceId = imageResourceId;
        mSoundResourceId = soundResourceId;
    }
    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }

    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }

    public int getImageResourceId(){return mImageResourceId;}

    public int getSoundResourceId(){return mSoundResourceId;}

    public boolean hasImage(){return mImageResourceId != NO_IMAGE_PRESENT;}

    @Override
    public String toString() {
        return "Word{" +
                "mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mSoundResourceId=" + mSoundResourceId +
                ", mImageResourceId=" + mImageResourceId +
                '}';
    }
}
