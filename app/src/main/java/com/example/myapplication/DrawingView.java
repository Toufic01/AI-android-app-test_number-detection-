package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private float lastX, lastY;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing(context);
    }

    private void setupDrawing(Context context) {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        // Convert 400dp to pixels
        int sizeInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, context.getResources().getDisplayMetrics());
        bitmap = Bitmap.createBitmap(sizeInPixels, sizeInPixels, Bitmap.Config.ARGB_8888); // Create a bitmap
        canvas = new Canvas(bitmap);
        canvas.drawColor(Color.BLACK); // Set background color to black
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, null); // Draw the bitmap
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                drawOnCanvas(x, y);
                break;
            case MotionEvent.ACTION_UP:
                drawOnCanvas(x, y);
                break;
            default:
                return false;
        }

        invalidate(); // Redraw the view
        return true;
    }

    private void drawOnCanvas(float x, float y) {
        canvas.drawLine(lastX, lastY, x, y, paint); // Draw line on the canvas
        lastX = x;
        lastY = y;
    }

    public Bitmap getBitmap() {
        // Scale the bitmap to 28x28 for the model input
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 28, 28, false);
        return scaledBitmap;
    }

    public void clearCanvas() {
        bitmap.eraseColor(Color.BLACK); // Clear the canvas
        invalidate(); // Redraw the view
    }
}
