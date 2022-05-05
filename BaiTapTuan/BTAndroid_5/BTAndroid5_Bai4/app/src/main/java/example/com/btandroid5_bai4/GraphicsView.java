package example.com.btandroid5_bai4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

public class GraphicsView extends View {
    int i=0;

    long last_tick=0;
    long period=300;

    Bitmap[] frames = new Bitmap[3];
    Context ctext;
    MediaPlayer mPlayer;

    public GraphicsView(Context context){
        super(context);

        ctext=context;

        frames[0] = BitmapFactory.decodeResource(getResources(), R.drawable.strawberry);
        frames[1] = BitmapFactory.decodeResource(getResources(), R.drawable.mano);
        frames[2] = BitmapFactory.decodeResource(getResources(), R.drawable.orange);
        mPlayer = MediaPlayer.create(ctext, R.raw.gangnam);
        mPlayer.start();

    }
    @Override
    protected void onDraw (Canvas canvas){
        if(i<3){
            long time = (System.currentTimeMillis() - last_tick);
            if(time>=period){
                last_tick=System.currentTimeMillis();
                canvas.drawBitmap(frames[i],40,40,new Paint());
                i++;
                postInvalidate();
            } else{
                canvas.drawBitmap(frames[i],40,40,new Paint());
                postInvalidate();
            }
        } else{
            i=0;
            postInvalidate();
        }
    }


}