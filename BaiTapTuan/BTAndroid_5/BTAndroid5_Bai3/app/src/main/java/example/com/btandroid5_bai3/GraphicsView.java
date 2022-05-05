package example.com.btandroid5_bai3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class GraphicsView extends View {
    int i=0;
    Bitmap[] frames = new Bitmap[3];

    public GraphicsView(Context context){
        super(context);
        frames[0] = BitmapFactory.decodeResource(getResources(), R.drawable.strawberry);
        frames[1] = BitmapFactory.decodeResource(getResources(), R.drawable.mano);
        frames[2] = BitmapFactory.decodeResource(getResources(), R.drawable.orange);
    }
    @Override
    protected void onDraw (Canvas canvas){
        if(i<3){
            canvas.drawBitmap(frames[i],40,40,new Paint());
        }else{
            i=0;
        }
        invalidate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN)
                i++;
        return super.onTouchEvent(event);
    }
}