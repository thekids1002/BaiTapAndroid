package example.com.btandroid5_bai4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Graphics extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GraphicsView(this));
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mPlayer != null) {
//            mPlayer.start();
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        if (mPlayer.isPlaying()) {
//            mPlayer.pause();
//        }
//        super.onStop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        if (mPlayer.isPlaying()) {
//            mPlayer.release();
//        }
//        super.onDestroy();
//    }

}
