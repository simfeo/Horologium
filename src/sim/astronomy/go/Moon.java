package sim.astronomy.go;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

/**
 * @author id
 *
 */
public class Moon extends Activity implements OnGesturePerformedListener {

	private GestureLibrary mLibrary;

	private TextView fulll, nulll, percent, phase, distance, zodiac, age, zoddd;

	public static final int  IDM_OPENFAQ = 101, IDM_OPENABOUT=102;
	Resources res;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moon);
		fulll=(TextView)findViewById(R.id.fulll);
		nulll=(TextView)findViewById(R.id.nulll);
		percent=(TextView)findViewById(R.id.percent);
		phase=(TextView)findViewById(R.id.phase);
		zodiac=(TextView)findViewById(R.id.zodiac);
		age=(TextView)findViewById(R.id.age);
		distance=(TextView)findViewById(R.id.distance);
		zoddd=(TextView)findViewById(R.id.zodd);

		mLibrary=GestureLibraries.fromRawResource(this, R.raw.gestures);
		res = getResources();
		if (!mLibrary.load())
		{
			finish();
		}

		GestureOverlayView gesture = (GestureOverlayView)findViewById(R.id.gesturesm);
		gesture.addOnGesturePerformedListener(this);

		{
			String date = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
			String days[]=date.split(" ");

			int	day=Integer.parseInt(days[0]);
			int mon=Integer.parseInt(days[1]);
			int year=Integer.parseInt(days[2]);

			double Jd=367*year-7*(year+(mon+9)/12)/4+275*mon/9+day+1721013.5+0.5;
			double IP=	normalize( ( Jd - 2451550.1 ) / 29.530588853 );
			double AG= IP*29.53;






			String Phase=null;
			if(      AG <  1.84566 ) Phase = "NEW";
			else if( AG <  5.53699 ) Phase = "Evening crescent";
			else if( AG <  9.22831 ) Phase = "First quarter";
			else if( AG < 12.91963 ) Phase = "Waxing gibbous";
			else if( AG < 16.61096 ) Phase = "FULL";
			else if( AG < 20.30228 ) Phase = "Waning gibbous";
			else if( AG < 23.99361 ) Phase = "Last quarter";
			else if( AG < 27.68493 ) Phase = "Morning crescent";
			else                     Phase = "NEW";
			phase.setText(res.getString(R.string.Moon_Fase)+" "+Phase);
			String dneyy=null;
			String dneyyyy [] = res.getStringArray(R.array.MoonDays);
			if (Math.round(AG)==1 || Math.round(AG) ==21) dneyy=" "+dneyyyy[0];
			else if (Math.round(AG)>4 && Math.round(AG)<21) dneyy=" "+dneyyyy[1];
			else if (Math.round(AG)>24) dneyy=" "+dneyyyy[1];
			else if (Math.round(AG)>1 && Math.round(AG)<5) dneyy=" "+dneyyyy[2];
			else if (Math.round(AG)>21 && Math.round(AG)<25) dneyy=" "+dneyyyy[2];

			age.setText(res.getString(R.string.Moon_AGE)+" "+Math.round(AG)+dneyy);


			IP = IP*2*Math.PI;                      // Convert phase to radians

			// calculate moon's distance
			double DP = 2*Math.PI*normalize( ( Jd - 2451562.2 ) / 27.55454988 );
			double DI = 60.4 - 3.3*Math.cos( DP ) - 0.6*Math.cos( 2*IP - DP ) - 0.5*Math.cos( 2*IP );

			// calculate moon's ecliptic latitude
			double NP = 2*Math.PI*normalize( ( Jd - 2451565.2 ) / 27.212220817 );
			double LA = 5.1*Math.sin( NP );

			// calculate moon's ecliptic longitude
			double RP = normalize( ( Jd - 2451555.8 ) / 27.321582241 );
			double LO = 360*RP + 6.3*Math.sin( DP ) + 1.3*Math.sin( 2*IP - DP ) + 0.7*Math.sin( 2*IP );
			if ( LO > 360 ) LO = LO - 360;
			String Zodiac=null;

			if(      LO <  33.18 ) Zodiac = res.getString(R.string.z12);
			else if( LO <  51.16 ) Zodiac = res.getString(R.string.z1);
			else if( LO <  93.44 ) Zodiac = res.getString(R.string.z2);
			else if( LO < 119.48 ) Zodiac = res.getString(R.string.z3);
			else if( LO < 135.30 ) Zodiac = res.getString(R.string.z4);
			else if( LO < 173.34 ) Zodiac = res.getString(R.string.z5);
			else if( LO < 224.17 ) Zodiac = res.getString(R.string.z6);
			else if( LO < 242.57 ) Zodiac = res.getString(R.string.z7);
			else if( LO < 271.26 ) Zodiac = res.getString(R.string.z8);
			else if( LO < 302.49 ) Zodiac = res.getString(R.string.z9);
			else if( LO < 311.72 ) Zodiac = res.getString(R.string.z10);
			else if( LO < 348.58 ) Zodiac = res.getString(R.string.z11);
			else                   Zodiac = res.getString(R.string.z12);
			zodiac.setText(res.getString(R.string.Moon_Constellation));
			zoddd.setText (Zodiac);
			distance.setText (res.getString(R.string.Moon_Distance)+" "+(int)(DI*6342)+" km");

			/*    	    int aaa=(int)(year/100);
    	    double bbb=2-aaa+(int)(aaa/4);
    	    double jde = (int)(365.25*(year+4716))+(int)(30.600*(mon+1))+day+bbb-1524.5;
    	    System.out.println ("jde= "+jde);
    	    double tt=(jde-2451545)/36525;
    	    double dd=297.8502042+445267.1115168*tt-0.0016300*tt*tt+tt*tt*tt/545868.0-tt*tt*tt*tt/113065000.0;
    	    double mm=357.5291092+35999.0502909*tt-0.0001536*tt*tt+tt*tt*tt/24490000;
    	    double mmM=134.9634114+447198.8676314*tt+0.0089970*tt*tt+tt*tt*tt/69699-tt*tt*tt*tt/14712000;
    	    double ii=180-dd-6.289*ssin(mmM)+2.100*ssin(mm)-1.274*ssin(2*dd-mmM)-0.658*ssin(2*dd)-0.214*ssin(mmM)-0.110*ssin(dd);
    	    int kk =(int) Math.round((1+Math.cos(Math.toRadians(ii)))*100/2.0);*/

			String chas= new java.text.SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
			System.out.println ("time "+chas);
			int chass=Integer.parseInt(chas);
			String minute= new java.text.SimpleDateFormat("mm").format(Calendar.getInstance().getTime());
			System.out.println ("time "+chas);
			int minutes=Integer.parseInt(chas);


			double jde = (367*year-7*(year+(mon+9)/12)/4+275*mon/9+day+1721013+chass/24.0);



			double JDFull, JDNew;
			///////////FFFFFFFFFFFFFFFFFFFFFFFFUUUUUUUUUUULLLLLLLLLLL MMMMOOOOOOONNNNNN

			if (AG>0 && AG<29.53/2.0 )
			{
				JDFull = (367*year-7*(year+(mon+9)/12)/4+275*mon/9+day+1721013)+0.5+ 29.53/2-AG+1;
				JDNew = (367*year-7*(year+(mon+9)/12)/4+275*mon/9+day+1721013)+0.5+ 29.53-AG+1;
			}
			else
			{
				JDNew = (367*year-7*(year+(mon+9)/12)/4+275*mon/9+day+1721013)+0.5+ 29.53-AG+1;
				JDFull = JDNew+29.53/2+1;
			}

			fulll.setText(unYerJD(JDFull)+"-"+unMonJD(JDFull)+"-"+unJD(JDFull)+"  "+(int)((JDFull%1*1440)/60)+":"+((Math.round((JDFull%1*1440)%60))<10?("0"+(Math.round((JDFull%1*1440)%60))):(Math.round((JDFull%1*1440)%60))));
			nulll.setText(unYerJD(JDNew)+"-"+unMonJD(JDNew)+"-"+unJD(JDNew)+"  "+(int)((JDNew%1*1440)/60)+":"+((Math.round((JDNew%1*1440)%60))<10?("0"+(Math.round((JDNew%1*1440)%60))):(Math.round((JDNew%1*1440)%60))));  	    


			//System.out.println ("jde= "+jde);
			double tt=(jde-2451545+0.5)/36525; //v originale bez 0.5
			double dd=297.8502042+445267.1115168*tt-0.0016300*tt*tt+tt*tt*tt/545868.0-tt*tt*tt*tt/113065000.0;
			double mm=357.5291092+35999.0502909*tt-0.0001536*tt*tt+tt*tt*tt/24490000;
			double mmM=134.9634114+447198.8676314*tt+0.0089970*tt*tt+tt*tt*tt/69699-tt*tt*tt*tt/14712000;
			double ii=180-dd-6.289*ssin(mmM)+2.100*ssin(mm)-1.274*ssin(2*dd-mmM)-0.658*ssin(2*dd)-0.214*ssin(mmM)-0.110*ssin(dd);
			int kk =(int) Math.round((1+Math.cos(Math.toRadians(ii)))*100)/2;
			percent.setText(res.getString(R.string.Moon_Visibility)+" "+kk+"%");


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

	///////////------------------------methods

	public static double normalize (double a){
		a=a-Math.floor(a);
		if (a<0)
			a=a+1;
		return a;

	}

	public static double round2 (double a){
		return (Math.round(a*100)/100.0);

	}
	public static double ssin (double a){

		return Math.sin(Math.toRadians(a));

	}
	////---
	public static int  unJD (double jd){
		int z=(int) ((int)jd+0.5);

		int alf = (int)((z-1867216.25)/36524.25);
		double a=z+1+alf-(int)(alf/4);
		a=(z<2299161)?z:a;
		double b = a+1524;
		int c = (int)((b-122.1)/365.25);
		int d = (int)(365.25*c);
		int e=(int)((b-d)/30.6001);
		return (int)(b-d-(int)(30.6001*e));
	}

	public static int  unMonJD (double jd){
		int z=(int) ((int)jd+0.5);

		int alf = (int)((z-1867216.25)/36524.25);
		double a=z+1+alf-(int)(alf/4);
		a=(z<2299161)?z:a;
		double b = a+1524;
		int c = (int)((b-122.1)/365.25);
		int d = (int)(365.25*c);
		int e=(int)((b-d)/30.6001);
		return (e<13.5)?e-1: e-13;
	}


	public static int  unYerJD (double jd){
		int z=(int) ((int)jd+0.5);

		int alf = (int)((z-1867216.25)/36524.25);
		double a=z+1+alf-(int)(alf/4);
		a=(z<2299161)?z:a;
		double b = a+1524;
		int c = (int)((b-122.1)/365.25);
		int d = (int)(365.25*c);
		int e=(int)((b-d)/30.6001);
		int m = (e<13.5)?e-1: e-13;
		return (m>2.5)?c-4716:c-4715;
	}

	///////////------------------------buttons
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
					but2click(null);
				}
				else if (prediction.name.equals("left"))
				{
					but4click(null);
				}

			}

		}

	}
}
