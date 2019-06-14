package com.example.musicplayer1.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.arch.lifecycle.MutableLiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.musicplayer1.R;
import com.example.musicplayer1.models.Music;

import java.io.IOException;
import java.util.ArrayList;

//tạo một service để chạy một bài hát khi tắt app
public class Mp3Service extends Service implements MediaPlayer.OnCompletionListener,Runnable {

    private final String ACTION_MP3_PREV = "action.mp3.PREV";
    private final String ACTION_MP3_NEXT = "action.mp3.next";
    private final String ACTION_MP3_PLAY = "action.mp3.play";
    private final String ACTION_MP3_EXIT = "action.mp3.exit";

    private Thread t;
    private Boolean isRunning = true;

    private ArrayList<Music> arrMusic = new ArrayList<>();                                                                                   //khai báo một mảng chứa bài hát
    private MediaPlayer player;                                                                                         //khai báo một đối tượng để lưu bài hát
    private int currentIndex;                                                                                           //khai báo biến này chỉ số hiện tại của bài hát đang phát trong mảng bài hát

    private MutableLiveData<Boolean> isPlaying = new MutableLiveData<>();                                                   //khai báo livedata: kiểm tra xem có đang phát nhạc không
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLife = new MutableLiveData<>();                                                  //cờ để hiển thị notification
    private MutableLiveData<Music> music = new MutableLiveData<>();                                                         //đưa bài hát đang phát vào liveData
    private MutableLiveData<Integer> current = new MutableLiveData<>();                                                     //current lấy thời gian hệ thống để chạy seekBar vào livedata


    //khởi chạy service
    @Override
    public void onCreate() {
        super.onCreate();
        t = new Thread(this);
        t.start();


        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_MP3_NEXT);
        filter.addAction(ACTION_MP3_PLAY);
        filter.addAction(ACTION_MP3_EXIT);
        filter.addAction(ACTION_MP3_PREV);
        registerReceiver(receiver,filter);                                                           //khai báo BrocastReceiver vừa khởi tạo ở dưới cùng




////        Một số sự kiện phải đăng ký trên context
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Intent.ACTION_SCREEN_ON);
//        filter.addAction(Intent.ACTION_SCREEN_OFF);
//        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//        filter.addAction(Intent.ACTION_BATTERY_LOW);
//        MP3Receiver receiver = new MP3Receiver();
//        registerReceiver(receiver,filter);
//        Intent intent= new Intent();                                                                    //chuyển dữ liệu ở đây
//        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        release();
        unregisterReceiver(receiver);
    }

    //phương thức nhận dữ liệu từ các activity sang
    @Override
    public IBinder onBind(Intent intent) {
        return new MP3Binder(this);
    }

    public void setArrMusic(ArrayList<Music> arrMusic) {
        this.arrMusic = arrMusic;
    }

    public void create(final int index){
        release();
        if (arrMusic == null){
            new Throwable("Need call setArrMusic before call create");
            return;
        }


        try {
            player = new MediaPlayer();
            String data = arrMusic.get(index).getData();
            if (arrMusic.get(index).getAlbum() == null) {
                player.setDataSource("http://192.168.1.68/mp3music/" + data);
            } else {
                player.setDataSource(this, Uri.parse(data));
            }
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.setOnCompletionListener(Mp3Service.this);
                    currentIndex = index;
                    name.postValue(arrMusic.get(index).getTitle());
                    isLife.postValue(true);
                    music.postValue(arrMusic.get(index));
                    start();
                }
            });
            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void release(){
        if (player != null){
            player.release();
            isPlaying.postValue(false);
        }
    }

    public void start(){
        if (player != null){
            player.start();
            isPlaying.postValue(true);
            pushNotification();
        }
    }

    public void stop(){
        if (player != null){
            player.pause();
        }
    }

    public void seekTo(int position){
        if (player != null){
            player.seekTo(position);
        }
    }

    public void loop(boolean isLoop){
        if (player != null){
            player.setLooping(isLoop);
        }
    }
    public int getDuration(){
        if (player != null){
            return player.getDuration();
        }
        return 0;
    }

    public int getPosition(){
        if ( player != null&& player.isPlaying()){
            return player.getCurrentPosition();
        }
        return 0;
    }
    public void pause(){
        if (player != null){
            player.pause();
            isPlaying.postValue(false);
            pushNotification();
        }
    }

    public static final int NEXT = 1;
    public static final int PREV = -1;

    @Override
    public void run() {                                                                             //thread để lấy thời gian của hệ thống
        while (isRunning){
            current.postValue(getPosition());                                                           //lấy thời gian hiện tại để post vào duration
            try {
                Thread.sleep(1000);                                                             //nghỉ một giây lấy thời gian một lần
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @IntDef({NEXT, PREV})
    public @interface TypeChange{

    }

    public void change(@TypeChange int value){
        currentIndex += value;
        if (currentIndex >= arrMusic.size()){
            currentIndex = 0;
        }else if (currentIndex <0){
            currentIndex = arrMusic.size()-1;
        }
        create(currentIndex);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    public class MP3Binder extends Binder{
        private Mp3Service service;

        public MP3Binder(Mp3Service service) {
            this.service = service;
        }

        public Mp3Service getService() {
            return service;
        }
    }

    private void pushNotification() {
        Intent intent = new Intent(this,getClass());
        startService(intent);


        String CHANNEL_ID = "CHANNEL_ID";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O  ) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, CHANNEL_ID,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }


        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.ui_notification);                   //đưa notification vào
        remoteViews.setTextViewText(R.id.tv_name, arrMusic.get(currentIndex).getTitle());                       //id, nội dung muốn hiển thị

        if (player.isPlaying()){
            remoteViews.setImageViewResource(R.id.im_play,R.drawable.ic_pause);
        }else{
            remoteViews.setImageViewResource(R.id.im_play,R.drawable.ic_play);
        }

        setAction(remoteViews,R.id.im_prev,ACTION_MP3_PREV);
        setAction(remoteViews,R.id.im_next,ACTION_MP3_NEXT);
        setAction(remoteViews,R.id.im_play,ACTION_MP3_PLAY);
        setAction(remoteViews,R.id.im_close,ACTION_MP3_EXIT);



        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setCustomBigContentView(remoteViews);

        startForeground(1213232, builder.build());

    }

    private void setAction(RemoteViews remoteViews, int resID, String action){
        Intent intent = new Intent(action);
        PendingIntent pending = PendingIntent.getBroadcast(this,0,intent,0);                           //khi kick vào sẽ send tới broadcast
        remoteViews.setOnClickPendingIntent(resID,pending);
    }

    public MutableLiveData<Boolean> getIsPlaying() {
        return isPlaying;
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<Boolean> getIsLife() {
        return isLife;
    }

    public ArrayList<Music> getArrMusic() {
        return arrMusic;
    }

    public MutableLiveData<Music> getMusic() {
        return music;
    }

    public MutableLiveData<Integer> getCurrent() {
        return current;
    }



    //khởi tạo một BroadcastRecieve ngay tại đây nhận sự kiện click từ hệ thống( notification)
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case ACTION_MP3_NEXT:
                    change(NEXT);
                    break;
                case ACTION_MP3_PREV:
                    change(PREV);
                    break;
                case ACTION_MP3_PLAY:
                    if (player.isPlaying()){
                        pause();
                    }else {
                        start();
                    }
                    break;
                case ACTION_MP3_EXIT:
                    stopForeground(true);//chuyển service từ trạng thái background sang foreGround
                    t.interrupt();                                                                  //bắt thread phải dừng, destroy luôn
                    release();                                                                      //giải phóng bài hát đang chạy
                    stopSelf();                                                                     //dừng background service
                    isLife.postValue(false);

                    break;
            }
        }
    };
}
