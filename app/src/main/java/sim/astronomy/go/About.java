package sim.astronomy.go;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class About extends Activity {
	
	
	TextView editText;
	
	@Override
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		editText = (TextView)findViewById(R.id.textAboutFaq);
		editText.setText(R.string.ABOUT);
		
	}

}
