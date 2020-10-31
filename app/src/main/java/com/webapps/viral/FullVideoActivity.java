package com.webapps.viral;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.FFmpegExecuteResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import com.webapps.viral.utils.DownloadManager;
import com.webapps.viral.utils.VideoPlayerConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class FullVideoActivity extends AppCompatActivity implements Player.EventListener{

    String url;
    ImageView share,download,volume
            ;
    PlayerView simpleExoPlayerView;
    SimpleExoPlayer player;
    ProgressBar spinnerVideoDetails;
    Handler mHandler;
    Runnable mRunnable;
    FFmpeg fFmpeg;
    ProgressDialog pd;
    String  endTimeOfVideo="50",VideoPath1;
    int xPosition=50,yPosition=50,timeInsec=10;
    EditText et;

    private static FullVideoActivity instance;
    Button submit;
    private final int REQUEST_CODE_PERMISSION = 55;
    boolean down=false;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_video);
        share=findViewById(R.id.share);
        download=findViewById(R.id.download);
        volume=findViewById(R.id.volume);
                url="https://webappssoft.com/theutredns/utredns_admires/content/uploads"+getIntent().getExtras().getString("url");
       // MKPlayer mkplayer = new MKPlayer(FullVideoActivity.this);
        //mkplayer.play(url);

        spinnerVideoDetails=findViewById(R.id.spinnerVideoDetails);
        simpleExoPlayerView=findViewById(R.id.video_view);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player != null) {
                player.stop();
                }
                down=true;
                new DownloadManager(FullVideoActivity.this, url);
            }
        });

        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float currentvolume = player.getVolume();
                if(currentvolume==0)
                {
                    player.setVolume(currentvolume);

                }
                else {
                    player.setVolume(0f);
                }
            }
        });
        instance = this;
        pd= new ProgressDialog(this);
        pd.setTitle("Please Wait...");
        try {
            loadFFmpegLibrary();
        } catch (FFmpegNotSupportedException e) {
            e.printStackTrace();
        }if(checkStoragePermissions()){

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION);
            }
        }
        copyAssets();
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                //sharingIntent.setType("text/plain");
               // String shareBody = "Click the below link and download our app https://play.google.com/store/apps/details?id="+getPackageName();
               // sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "The Utrend Viral");
             //   sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
               // startActivity(Intent.createChooser(sharingIntent, "Share via"));
              //  shareVideo("share",url);
               down=false;
                new DownloadManager(FullVideoActivity.this, url);

            }
        });
        setUp();
    }
    public void shareVideo(final String title, String path) {

        MediaScannerConnection.scanFile(FullVideoActivity.this, new String[] { path },

                null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Intent shareIntent = new Intent(
                                android.content.Intent.ACTION_SEND);
                        shareIntent.setType("video/*");
                        shareIntent.putExtra(
                                android.content.Intent.EXTRA_SUBJECT, title);
                        shareIntent.putExtra(
                                android.content.Intent.EXTRA_TITLE, title);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        shareIntent.putExtra(Intent.EXTRA_TEXT,"Welcome to the utrend Viral download our app https://play.google.com/store/apps/details?id="+getPackageName());

                        shareIntent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        startActivity(Intent.createChooser(shareIntent,
                                "Share this video"));

                    }
                });
    }
    private void setUp() {
        initializePlayer();
        if (url == null) {
            return;
        }
        buildMediaSource(Uri.parse(url));
    }

    private void initializePlayer() {
        if (player == null) {
            // 1. Create a default TrackSelector
            LoadControl loadControl = new DefaultLoadControl(
                    new DefaultAllocator(true, 16),
                    VideoPlayerConfig.MIN_BUFFER_DURATION,
                    VideoPlayerConfig.MAX_BUFFER_DURATION,
                    VideoPlayerConfig.MIN_PLAYBACK_START_BUFFER,
                    VideoPlayerConfig.MIN_PLAYBACK_RESUME_BUFFER, -1, true);
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);
            // 2. Create the player
            player =
                    ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), trackSelector,
                            loadControl);
            simpleExoPlayerView.setPlayer(player);
            float currentvolume = player.getVolume();
            if(currentvolume==0)
            {
                player.setVolume(0f);

            }
            else {
                player.setVolume(currentvolume);
            }
        }
    }
    private void buildMediaSource(Uri mUri) {
        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, getString(R.string.app_name)), bandwidthMeter);
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mUri);
        // Prepare the player with the source.
        player.prepare(videoSource);
        player.setPlayWhenReady(true);
        player.addListener(this);
    }
    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }
    private void pausePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }
    private void resumePlayer() {
        if (player != null) {
            player.setPlayWhenReady(true);
            player.getPlaybackState();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        pausePlayer();
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        resumePlayer();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
    }
    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }
    @Override
    public void onLoadingChanged(boolean isLoading) {
    }
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case Player.STATE_BUFFERING:
                spinnerVideoDetails.setVisibility(View.VISIBLE);
                break;
            case Player.STATE_ENDED:
                // Activate the force enable
                break;
            case Player.STATE_IDLE:
                break;
            case Player.STATE_READY:
                spinnerVideoDetails.setVisibility(View.GONE);
                break;
            default:
                // status = PlaybackStatus.IDLE;
                break;
        }
    }
    @Override
    public void onRepeatModeChanged(int repeatMode) {
    }
    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
    }
    @Override
    public void onPlayerError(ExoPlaybackException error) {
    }
    @Override
    public void onPositionDiscontinuity(int reason) {
    }
    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
    }
    @Override
    public void onSeekProcessed() {
    }  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION && checkStoragePermissions()) {

        }
    }
    public static FullVideoActivity getInstance() {
        return instance;
    }
    private boolean checkStoragePermissions() {
        return
                ContextCompat.checkSelfPermission(FullVideoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(FullVideoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(FullVideoActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
    public  void  loadFFmpegLibrary() throws FFmpegNotSupportedException {
        if(fFmpeg==null)
        {
            fFmpeg= FFmpeg.getInstance(this);

            fFmpeg.loadBinary(new FFmpegLoadBinaryResponseHandler() {
                @Override
                public void onFailure() {

                   // Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess() {
                  //  Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();

                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFinish() {

                }
            });

        }

    }
    public String getURLForResource (int resourceId) {
        //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }
    public  void  AddWatermark(String image)
    { String[] complexCommand;

        int frameCount = (int) (timeInsec * 30);
        int startTimeFrame = frameCount - 5;
        File f = new File("/storage/emulated/0");
        VideoPath1=image;
        String rootPath = f.getPath();
        Random random = new Random();
        int randomNumber = random.nextInt(99-11) + 11;
        String a="/storage/emulated/0/viral_"+randomNumber+".mp4";
        complexCommand = new String[]{"-y" ,"-i", image,"-strict","experimental", "-vf", "movie=/storage/emulated/0/viral/logo.png [watermark]; [in][watermark] overlay=main_w-overlay_w-10:10 [out]","-s", "320x240","-r", "30", "-b", "15496k", "-vcodec", "mpeg4","-ab", "48000", "-ac", "2", "-ar", "22050", ""+a+""};

        try {

            final String finalRootPath = rootPath;
            fFmpeg.execute(complexCommand, new FFmpegExecuteResponseHandler() {
                @Override
                public void onSuccess(String message) {
                    Log.d("Success", message);
                    System.out.println(new File(image).getAbsoluteFile().delete());

                    Toast.makeText(getApplicationContext(), "Successfullly Downloaded" + finalRootPath
                                    .toString(),
                            Toast.LENGTH_LONG).show();
                    if(!down) {
                        shareVideo("Share Video", a);
                    }
                    // Uri path = Uri.parse(finalRootPath + "/outputFrame.mp4");
                    //  playVideo(path.toString());

                }

                @Override
                public void onStart() {
                    Log.d("Start", "merge started");
                }

                @Override
                public void onProgress(String message) {
                    Log.d("progress", message);
                    pd.show();
                }

                @Override
                public void onFailure(String message) {
                    Log.d("failure", message);
                    pd.dismiss();

                }


                @Override
                public void onFinish() {
                    Log.d("finish", "merge finish");
                    pd.dismiss();
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            e.printStackTrace();
        }
    }

    private void copyAssets() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        for(String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                String outDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/viral/" ;
                File outFile = new File(outDir, filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}

