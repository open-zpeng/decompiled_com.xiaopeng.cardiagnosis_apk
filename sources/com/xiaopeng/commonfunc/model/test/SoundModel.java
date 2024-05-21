package com.xiaopeng.commonfunc.model.test;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.utils.FileUtil;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.xmlconfig.Support;
import java.io.IOException;
import net.lingala.zip4j.util.InternalZipConstants;
/* loaded from: classes4.dex */
public class SoundModel implements ISoundModel {
    private static final String AVAS_START = "AVAS_OUTPUT=start";
    private static final String AVAS_STOP = "AVAS_OUTPUT=stop";
    private static final String TAG = "SoundModel";
    private AudioManager mAudioManager;
    private final boolean mAvasTest;
    private final Context mContext;
    private MediaPlayer mMediaPlayer;
    private Ringtone mRingtone;

    public SoundModel(Context context, int resId) {
        this.mContext = context;
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        Uri uri = Uri.parse("android.resource://" + this.mContext.getPackageName() + InternalZipConstants.ZIP_FILE_SEPARATOR + resId);
        this.mRingtone = RingtoneManager.getRingtone(this.mContext, uri);
        Ringtone ringtone = this.mRingtone;
        if (ringtone != null) {
            ringtone.setStreamType(3);
        }
        this.mAvasTest = Support.Case.getEnabled(Support.Case.TEST_AVAS);
    }

    @Override // com.xiaopeng.commonfunc.model.test.ISoundModel
    public int getCurrentMediaVolume() {
        return this.mAudioManager.getStreamVolume(3);
    }

    @Override // com.xiaopeng.commonfunc.model.test.ISoundModel
    public int getMediaMaxVolume() {
        return this.mAudioManager.getStreamMaxVolume(3);
    }

    @Override // com.xiaopeng.commonfunc.model.test.ISoundModel
    public void setMediaVolume(int volume) {
        this.mAudioManager.setStreamVolume(3, volume, 0);
    }

    @Override // com.xiaopeng.commonfunc.model.test.ISoundModel
    public int getTTSMaxVolume() {
        return this.mAudioManager.getStreamMaxVolume(9);
    }

    @Override // com.xiaopeng.commonfunc.model.test.ISoundModel
    public void setTTSVolume(int volume) {
        this.mAudioManager.setStreamVolume(9, volume, 0);
    }

    @Override // com.xiaopeng.commonfunc.model.test.ISoundModel
    public String getAudioPaStatus() {
        String status = FileUtil.readAll(Support.Path.getFilePath(Support.Path.PATH_AUDIO_EXTERNAL_PA), Constant.SPACE_3_STRING);
        LogUtils.d(TAG, "getAudioPaStatus " + status);
        return status;
    }

    @Override // com.xiaopeng.commonfunc.model.test.ISoundModel
    public void onVolumeChanged(int progress, int minVol) {
        LogUtils.d(TAG, "onVolumeChanged progress = " + progress + " minVol = " + minVol);
        this.mAudioManager.setStreamVolume(3, progress + minVol, 0);
        startPlay();
    }

    private void startPlay() {
        Ringtone ringtone;
        Ringtone ringtone2 = this.mRingtone;
        if (ringtone2 != null && !ringtone2.isPlaying() && (ringtone = this.mRingtone) != null) {
            ringtone.play();
        }
    }

    @Override // com.xiaopeng.commonfunc.model.test.ISoundModel
    public void testTrack(int resid) {
        Uri uri = Uri.parse("android.resource://" + this.mContext.getPackageName() + InternalZipConstants.ZIP_FILE_SEPARATOR + resid);
        if (this.mMediaPlayer == null) {
            Log.d(TAG, "testTrack mMediaPlayer == null ");
            if (this.mAvasTest) {
                Log.i(TAG, AVAS_START);
                this.mAudioManager.setParameters(AVAS_START);
            }
            this.mMediaPlayer = new MediaPlayer();
            this.mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.xiaopeng.commonfunc.model.test.SoundModel.1
                @Override // android.media.MediaPlayer.OnPreparedListener
                public void onPrepared(MediaPlayer mp) {
                    Log.d(SoundModel.TAG, " mMediaPlayer onPrepared");
                    mp.start();
                }
            });
            this.mMediaPlayer.setLooping(true);
        } else {
            Log.d(TAG, "testTrack mMediaPlayer != null ");
            this.mMediaPlayer.reset();
        }
        try {
            this.mMediaPlayer.setDataSource(this.mContext, uri);
            this.mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaopeng.commonfunc.model.test.ISoundModel
    public void testTrack(int resid, boolean isMusic) {
        Uri uri = Uri.parse("android.resource://" + this.mContext.getPackageName() + InternalZipConstants.ZIP_FILE_SEPARATOR + resid);
        int i = 1;
        if (this.mMediaPlayer == null) {
            LogUtils.d(TAG, "testTrack mMediaPlayer == null ");
            if (this.mAvasTest && isMusic) {
                Log.i(TAG, AVAS_START);
                this.mAudioManager.setParameters(AVAS_START);
            }
            this.mMediaPlayer = new MediaPlayer();
            this.mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.xiaopeng.commonfunc.model.test.-$$Lambda$SoundModel$QlBcjOZDoGs1g5n14fZpoV8J9YI
                @Override // android.media.MediaPlayer.OnPreparedListener
                public final void onPrepared(MediaPlayer mediaPlayer) {
                    SoundModel.lambda$testTrack$0(mediaPlayer);
                }
            });
            this.mMediaPlayer.setLooping(true);
        } else {
            LogUtils.d(TAG, "testTrack mMediaPlayer != null ");
            this.mMediaPlayer.reset();
        }
        try {
            MediaPlayer mediaPlayer = this.mMediaPlayer;
            AudioAttributes.Builder builder = new AudioAttributes.Builder();
            if (!isMusic) {
                i = 12;
            }
            mediaPlayer.setAudioAttributes(builder.setUsage(i).build());
            this.mMediaPlayer.setDataSource(this.mContext, uri);
            this.mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$testTrack$0(MediaPlayer mp) {
        LogUtils.d(TAG, " mMediaPlayer onPrepared");
        mp.start();
    }

    @Override // com.xiaopeng.commonfunc.model.test.ISoundModel
    public void destroy(int preVol) {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                this.mMediaPlayer.stop();
            }
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
        this.mAudioManager.setStreamVolume(3, preVol, 0);
        if (this.mAvasTest) {
            Log.i(TAG, AVAS_STOP);
            this.mAudioManager.setParameters(AVAS_STOP);
        }
    }
}
