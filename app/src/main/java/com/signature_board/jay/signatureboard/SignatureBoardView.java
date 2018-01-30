package com.signature_board.jay.signatureboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by jay on 30/01/18.
 */

public class SignatureBoardView extends View {
    private Canvas canvas;
    private Bitmap bitmap;

    private Path path;
    private Paint paint;

    public SignatureBoardView(Context context) {

        super(context);
        setDrawingCacheEnabled(true);
        bitmap = Bitmap.createBitmap(820, 480, Bitmap.Config.ARGB_4444);

        canvas = new Canvas(bitmap);
        paint = new Paint();

        path = new Path();
        paint.setDither(true);

        paint.setColor(getResources().getColor(android.R.color.black));

        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeJoin(Paint.Join.ROUND);

        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setStrokeWidth(2);

        this.setBackgroundColor(Color.WHITE);
    }

    private ArrayList<DrawingClass> DrawingClassArrayList = new ArrayList<DrawingClass>();

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        DrawingClass pathWithPaint = new DrawingClass();

        canvas.drawPath(path, paint);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            path.moveTo(event.getX(), event.getY());

            path.lineTo(event.getX(), event.getY());
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

            path.lineTo(event.getX(), event.getY());

            pathWithPaint.setPath(path);

            pathWithPaint.setPaint(paint);

            DrawingClassArrayList.add(pathWithPaint);
        }

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (DrawingClassArrayList.size() > 0) {

            canvas.drawPath(
                    DrawingClassArrayList.get(DrawingClassArrayList.size() - 1).getPath(),

                    DrawingClassArrayList.get(DrawingClassArrayList.size() - 1).getPaint());
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }


    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public ArrayList<DrawingClass> getDrawingClassArrayList() {
        return DrawingClassArrayList;
    }

    public void setDrawingClassArrayList(ArrayList<DrawingClass> drawingClassArrayList) {
        DrawingClassArrayList = drawingClassArrayList;
    }
}
