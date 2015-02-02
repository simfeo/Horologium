package sim.astronomy.go;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class Zodiac extends Activity implements OnGesturePerformedListener {

	private GestureLibrary mLibrary;
	private TextView Oven, Oven1, Telez, Telez1, Bliznez, Bliznez1, Rak, Rak1, Lev, Lev1, Deva, Deva1, Vesi, Vesi1, Scorpi, Scorpi1, Strelez, Strelez1, Kozer, Kozer1, Vodol, Vodol1, Ribi, Ribi1;
	private TextView act_m2, act_m1, act, act_1, act_2; 

	public static final int  IDM_OPENFAQ = 101, IDM_OPENABOUT=102;
	Resources res;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zodiac);
		Oven=(TextView)findViewById(R.id.oven1);
		Oven1=(TextView)findViewById(R.id.oven2);
		Telez=(TextView)findViewById(R.id.telez1);
		Telez1=(TextView)findViewById(R.id.telez2);
		Bliznez=(TextView)findViewById(R.id.bliznez1);
		Bliznez1=(TextView)findViewById(R.id.bliznez2);
		Rak=(TextView)findViewById(R.id.rak1);
		Rak1=(TextView)findViewById(R.id.rak2);
		Lev=(TextView)findViewById(R.id.lev1);
		Lev1=(TextView)findViewById(R.id.lev2);
		Deva=(TextView)findViewById(R.id.deva1);
		Deva1=(TextView)findViewById(R.id.deva2);
		Vesi=(TextView)findViewById(R.id.vesi1);
		Vesi1=(TextView)findViewById(R.id.vesi2);
		Scorpi=(TextView)findViewById(R.id.scorpi1);
		Scorpi1=(TextView)findViewById(R.id.scorpi2);
		Strelez=(TextView)findViewById(R.id.strelez1);
		Strelez1=(TextView)findViewById(R.id.strelez2);
		Kozer=(TextView)findViewById(R.id.kozer1);
		Kozer1=(TextView)findViewById(R.id.kozer2);
		Vodol=(TextView)findViewById(R.id.vodol1);
		Vodol1=(TextView)findViewById(R.id.vodol2);
		Ribi=(TextView)findViewById(R.id.ribi1);
		Ribi1=(TextView)findViewById(R.id.ribi2);
		act_m2=(TextView)findViewById(R.id.act_m2);
		act_m1=(TextView)findViewById(R.id.act_m1);
		act=(TextView)findViewById(R.id.act);
		act_1=(TextView)findViewById(R.id.act_1);
		act_2=(TextView)findViewById(R.id.act_2);
		
		res =getResources();


		mLibrary=GestureLibraries.fromRawResource(this, R.raw.gestures);
		if (!mLibrary.load())
		{
			finish();
		}

		GestureOverlayView gesture = (GestureOverlayView)findViewById(R.id.gesturesz);
		gesture.addOnGesturePerformedListener(this);


		{String dt=new java.text.SimpleDateFormat("dd-MM-yyyy").format(java.util.Calendar.getInstance ().getTime());

		String days[]=dt.split("-");

		int	day=Integer.parseInt(days[0]);
		int mon=Integer.parseInt(days[1]);
		int year=Integer.parseInt(days[2]);
		yyyer (year-2, act_m2);
		yyyer (year-1, act_m1);
		yyyer (year, act);
		yyyer (year+1, act_1);
		yyyer (year+2, act_2);
		if ((mon==3 && day>=21)  || (mon==4 && day<=20)) zhir (Oven, Oven1);
		if ((mon==4 && day>=21)  || (mon==5 && day<=20)) zhir (Telez, Telez1);
		if ((mon==5 && day>=21)  || (mon==6 && day<=21)) zhir (Bliznez, Bliznez1);
		if ((mon==6 && day>=22)  || (mon==7 && day<=22)) zhir (Rak, Rak1);
		if ((mon==7 && day>=23)  || (mon==8 && day<=23)) zhir (Lev, Lev1);
		if ((mon==8 && day>=24)  || (mon==9 && day<=23)) zhir (Deva, Deva1);
		if ((mon==9 && day>=24)  || (mon==10 && day<=22)) zhir (Vesi, Vesi1);
		if ((mon==10 && day>=23) || (mon==11 && day<=22)) zhir (Scorpi, Scorpi1);
		if ((mon==11 && day>=23) || (mon==12 && day<=21)) zhir (Strelez, Strelez1);
		if ((mon==12 && day>=22) || (mon==1 && day<=20)) zhir (Kozer, Kozer1);
		if ((mon==1 && day>=21)  || (mon==2 && day<=19)) zhir (Vodol, Vodol1);
		if ((mon==2 && day>=20)  || (mon==3 && day<=20)) zhir (Ribi, Ribi1);
		}


	}

	@Override
	public boolean onCreateOptionsMenu (Menu menu)
	{
		menu.add(Menu.NONE, IDM_OPENFAQ, Menu.NONE, res.getString(R.string.Menu_FAQ));

		menu.add(Menu.NONE, IDM_OPENABOUT, Menu.NONE, res.getString(R.string.Menu_About));
		return (super.onCreateOptionsMenu(menu));

	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item)

	{
		switch (item.getItemId())
		{
		//		case IDM_OPENEDITOR:
		//			Intent i = new Intent (this, EditCords.class);
		//			startActivity(i);
		//
		//			break;
		case IDM_OPENABOUT:
			Intent i = new Intent (this, About.class);
			startActivity(i);
			break;

		case IDM_OPENFAQ:
			Intent i2 = new Intent (this, Faq.class);
			startActivity(i2);
		default:
			return false;


		}


		return true;
	}










	public void yyyer (int a, TextView la){
		
		//String [] yearch = {"Крыса", "Бык", "Тигр", "Кролик", "Дракон", "Змея", "Лошадь", "Овца", "Обезьяна", "Петух", "Собака", "Свинья"};
		String [] yearch = res.getStringArray(R.array.ZodiacAnimal);
		a=(a-1900)%12;
		la.setText(yearch[a]);


	}
	public void zhir (TextView lala1, TextView lala2){
		lala1.setTypeface(null, 1);
		lala1.setTextSize(1,15);
		lala2.setTypeface(null, 1);
		lala1.setTextSize(1, 15);
	}

	public void but1click (View v){
		Intent i = new Intent(this, AstronomyActivity.class);
		startActivity(i);
		finish();
	} 
	public void but2click (View v){
		Intent i = new Intent(this, Sun.class);
		startActivity(i);
		finish();
	} 
	public void but3click (View v){
		Intent i = new Intent(this, Moon.class);
		startActivity(i);
		finish();
	}
	public void but4click (View v){
		Intent i = new Intent(this, Zodiac.class);
		startActivity(i);
		finish();
	} 

	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		// TODO Auto-generated method stub
		ArrayList<Prediction> predictions = mLibrary.recognize(gesture);

		if (predictions.size()>0)
		{
			Prediction prediction = predictions.get(0);
			if(prediction.score>1.0)
			{
				if (prediction.name.equals("right"))
				{
					but3click(null);
				}
				else if (prediction.name.equals("left"))
				{
					but1click(null);
				}

			}

		}

	}
}
