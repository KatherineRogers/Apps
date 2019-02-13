package com.example.katie.hrubieckatherine_ce07;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.katie.hrubieckatherine_ce07.activities.DrawingActivity;
import com.example.katie.hrubieckatherine_ce07.activities.MainActivity;

import java.util.ArrayList;
import java.util.Random;

public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback {

    private Rect mBackgroundDimens;
    private Paint mBlankPaint;
    private Bitmap mBackground;
    private Paint mTextPaint;
    private Bitmap mHole;

    public DrawingSurface(Context context) {
        super(context);
    }

    public DrawingSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawingSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DrawingSurface(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        storeDimensions(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        storeDimensions(surfaceHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    private void storeDimensions(SurfaceHolder _holder) {
        Canvas canvas = _holder.lockCanvas();

        mBackgroundDimens = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
        if(MainActivity.mPoints.size() == 0){
            for (TreasureItem i : MainActivity.items) {
                Random rh = new Random();
                int h = rh.nextInt(canvas.getHeight());
                i.setY(h);
                Random rw = new Random();
                int w = rw.nextInt(canvas.getWidth());
                i.setX(w);
            }
        }
        _holder.unlockCanvasAndPost(canvas);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setWillNotDraw(false);
        getHolder().addCallback(this);

        Resources res = getResources();

        mBackground = BitmapFactory.decodeResource(res, R.drawable.field);

        mBlankPaint = new Paint();
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.YELLOW);
        mTextPaint.setTextSize(60.0f);
        mTextPaint.setTypeface(Typeface.create("Arial", Typeface.BOLD));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mHole = BitmapFactory.decodeResource(res, R.drawable.hole);
    }

    //where the magic happens
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mBackground, null, mBackgroundDimens, mBlankPaint);

        //TODO : makes items visible on game board when uncommented - easier to test
        /*for(TreasureItem i:MainActivity.items){
            canvas.drawCircle(i.x,i.y, 10.0f, new Paint());
        }*/

        for (Point p : MainActivity.mPoints) {
            canvas.drawBitmap(mHole, p.x, p.y, new Paint());
        }

        canvas.drawText("There are " + MainActivity.items.size() + " treasures remaining!",
                canvas.getWidth()/2, 100.0f, mTextPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Point p = new Point((int) event.getX() - (mHole.getWidth() / 2), (int) event.getY() - (mHole.getHeight() / 2));
            MainActivity.mPoints.add(p);
            ArrayList<TreasureItem> toRemove = new ArrayList<>();

            for (TreasureItem i : MainActivity.items) {
                if ((i.x >= (int) event.getX() - ((mHole.getWidth() / 2)+20) && i.x <= (int) event.getX() + ((mHole.getWidth() / 2)+20))
                        && (i.y >= (int) event.getY() - ((mHole.getHeight() / 2)+20) && i.y <= (int) event.getY()
                        + ((mHole.getHeight() / 2)+20))) {
                    toRemove.add(i);
                    MainActivity.foundItems.add(i);
                }
            }
            for (TreasureItem tr : toRemove) {
                MainActivity.items.remove(tr);
            }
            if(MainActivity.items.size() == 0){
                final Context context = getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.game_over);
                builder.setMessage(R.string.you_found_all_the_treasure);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ((DrawingActivity)context).setResult(MainActivity.FINISHED);
                        ((DrawingActivity)context).finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
            postInvalidate();
        }

        return super.onTouchEvent(event);
    }
}
