package sim.astronomy.go;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

public class Faq extends Activity {
	
	TextView edTex, edText1, edText2, edText3;
	
	@Override
	public void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faq);
		LinearLayout faqlin=(LinearLayout)findViewById(R.id.faqlin);
		
		edTex = (TextView)findViewById(R.id.textView1FA);
		edTex.setText(R.string.FAQ);
		
		edText1 = (TextView)findViewById(R.id.FAQ1);
		edText1.setText(R.string.FAQ1);
		
		edText2 = (TextView)findViewById(R.id.FAQ2);
		edText2.setText(R.string.FAQ2);
		
		edText3= (TextView)findViewById(R.id.FAQ3);
		edText3.setText(R.string.FAQ3);
		
		
	}

}
