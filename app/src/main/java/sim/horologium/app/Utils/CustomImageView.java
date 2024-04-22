package sim.horologium.app.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

public class CustomImageView extends AppCompatImageView {
    private float currentRotation;
    private float desiredRotation;
    long startTime = 0;

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentRotation = 0;
        startTime = System.nanoTime();
    }

    @Override
    public void onDraw(Canvas canvas) {
        long currentTime = System.nanoTime();
        float dt = (currentTime - startTime)/1000000000.0f;
        startTime = currentTime;
        currentRotation = clerp(currentRotation, desiredRotation, clamp(0.0f,1.0f, dt*2));
        Log.v("", ""+dt+" c: "+ currentRotation+" d: "+desiredRotation);
        super.setRotation(currentRotation);
        super.onDraw(canvas);
        if (Math.abs(currentRotation - desiredRotation) > 0.1) {
            invalidate();
        }
    }


    @Override
    public void setRotation(float rotation) {
        desiredRotation = rotation;
        invalidate();
    }
    public static float clerp(float from, float to, float percent)
    {
        float a1 = (from - to + 540)%360 -180;
        return (from - percent*a1)%360;
    }

    public static float clamp(float min, float max, float current)
    {
        return Math.max(min, Math.min(max, current));
    }

    public float getCurrentRotation ()
    {
        return currentRotation;
    }
}
