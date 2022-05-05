package example.com.btandroid5_bai2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class GraphicsView extends View {
    int x=0;
    int y=0;
    int d=100;
    int r=50;

    public GraphicsView(Context context){
        super(context);
    }
    @Override
    protected void onDraw (Canvas canvas){
        if(x!=0 && y!=0) {
            int right = x + d;
            int bottom = y + r;
            Rect r = new Rect(x,y,right,bottom);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLUE);
            canvas.drawRect(r, paint);
        }
        invalidate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
    x=(int)event.getX();
    y=(int)event.getY();
    return super.onTouchEvent (event);
    }
}