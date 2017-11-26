package service.churchapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.linelib.LineGLView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    private LineGLView mGLView;
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        FrameLayout fl_gl_layout = (FrameLayout) findViewById(R.id.fl_gl_layout);
        mGLView = new LineGLView(this);
        mGLView.requestFocus();
        mGLView.setFocusableInTouchMode(true);
        fl_gl_layout.addView(mGLView);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
