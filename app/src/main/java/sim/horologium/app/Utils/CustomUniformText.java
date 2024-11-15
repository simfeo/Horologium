package sim.horologium.app.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;


public class CustomUniformText extends AppCompatTextView {
    public CustomUniformText(@NonNull Context context)
    {
        this(context, null);
    }

    public CustomUniformText(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public CustomUniformText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.BOTTOM|getGravity());
        TextViewCompat.setAutoSizeTextTypeWithDefaults(this, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        setMaxLines(1);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(this, 1,
                (int)getTextSize(), 2,
                TypedValue.COMPLEX_UNIT_PX);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.v("", "w "+w+" h "+ h + " oldw " + oldw + " oldh "+ oldh);
    }
}
