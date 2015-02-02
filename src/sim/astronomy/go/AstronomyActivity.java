package sim.astronomy.go;


import java.util.ArrayList;
import java.util.Calendar;
import java.lang.*;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.gesture.*;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class AstronomyActivity extends Activity implements OnGesturePerformedListener {
	/** Called when the activity is first created. */
	private GestureLibrary mLibrary;

	private TextView sss1, sss2, sss3, sss4, sss5;
	TextView sss15, sss16, sss17, sss18, sss19, sss20, sss21, sss22, sss23, sss24, sss25, sss26, sss27, sss28, sss29, sss30, sss31, sss32, sss33, sss34, 
	sss35, sss36, sss37, sss38, sss39, sss40, sss41, sss42, sss43, sss44, sss45, sss46, sss47, sss48, sss49, sss50, sss51, sss52, sss53, sss54, sss55, sss56, 
	sss57, sss58, sss59, sss60, sss61, sss62, settedMon, settedYear;
	int iYear, iMon, iDay;
	//String sMonths [] ={"0","январь", "февраль", "март", "апрель","май","июнь","июль","август","сентябрь","октябрь","ноябрь", "декабрь"};
	Resources res;


	public static final int  IDM_OPENFAQ = 101, IDM_OPENABOUT=102;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		res = getResources();


		mLibrary=GestureLibraries.fromRawResource(this, R.raw.gestures);
		if (!mLibrary.load())
		{
			finish();
		}

		GestureOverlayView gesture = (GestureOverlayView)findViewById(R.id.gesturesh);
		gesture.addOnGesturePerformedListener(this);



		sss15=(TextView)findViewById(R.id.textView15);
		sss16=(TextView)findViewById(R.id.textView16);
		sss17=(TextView)findViewById(R.id.textView17);
		sss18=(TextView)findViewById(R.id.textView18);
		sss19=(TextView)findViewById(R.id.textView19);
		sss20=(TextView)findViewById(R.id.textView20);
		sss21=(TextView)findViewById(R.id.textView21);
		sss22=(TextView)findViewById(R.id.textView22);
		sss23=(TextView)findViewById(R.id.textView23);
		sss24=(TextView)findViewById(R.id.textView24);
		sss25=(TextView)findViewById(R.id.textView25);
		sss26=(TextView)findViewById(R.id.textView26);
		sss27=(TextView)findViewById(R.id.textView27);
		sss28=(TextView)findViewById(R.id.textView28);
		sss29=(TextView)findViewById(R.id.textView29);
		sss30=(TextView)findViewById(R.id.textView30);
		sss31=(TextView)findViewById(R.id.textView31);
		sss32=(TextView)findViewById(R.id.textView32);
		sss33=(TextView)findViewById(R.id.textView33);
		sss34=(TextView)findViewById(R.id.textView34);
		sss35=(TextView)findViewById(R.id.textView35);
		sss36=(TextView)findViewById(R.id.textView36);
		sss37=(TextView)findViewById(R.id.textView37);
		sss38=(TextView)findViewById(R.id.textView38);
		sss39=(TextView)findViewById(R.id.textView39);
		sss40=(TextView)findViewById(R.id.textView40);
		sss41=(TextView)findViewById(R.id.textView41);
		sss42=(TextView)findViewById(R.id.textView42);
		sss43=(TextView)findViewById(R.id.textView43);
		sss44=(TextView)findViewById(R.id.textView44);
		sss45=(TextView)findViewById(R.id.textView45);
		sss46=(TextView)findViewById(R.id.textView46);
		sss47=(TextView)findViewById(R.id.textView47);
		sss48=(TextView)findViewById(R.id.textView48);
		sss49=(TextView)findViewById(R.id.textView49);
		sss50=(TextView)findViewById(R.id.textView50);
		sss51=(TextView)findViewById(R.id.textView51);
		sss52=(TextView)findViewById(R.id.textView52);
		sss53=(TextView)findViewById(R.id.textView53);
		sss54=(TextView)findViewById(R.id.textView54);
		sss55=(TextView)findViewById(R.id.textView55);
		sss56=(TextView)findViewById(R.id.textView56);
		sss57=(TextView)findViewById(R.id.textView57);
		sss58=(TextView)findViewById(R.id.textView58);
		sss59=(TextView)findViewById(R.id.textView59);
		sss60=(TextView)findViewById(R.id.textView60);
		sss61=(TextView)findViewById(R.id.textView61);
		sss62=(TextView)findViewById(R.id.textView62);
		settedMon=(TextView)findViewById(R.id.settedMon);
		settedYear=(TextView)findViewById(R.id.settedYear);

		///////////
		{ String dt=new java.text.SimpleDateFormat("dd-MM-yyyy").format(java.util.Calendar.getInstance ().getTime());

		String days[]=dt.split("-");

		int	day=Integer.parseInt(days[0]);
		int mon=Integer.parseInt(days[1]);
		int year=Integer.parseInt(days[2]);

		iYear= year;
		iMon = mon;
		iDay = day;



		ClendarSET (year, mon, day);

		int [] mes = {31, 28+viis(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int denNedeli=dayOfWeek(JD(year,mon,1));
		int nomerDnya=daynum(year, mon,1);
		int startDay, startMon;

		if (denNedeli!=1){
			do
			{
				if (mon == 1){
					nomerDnya= daynum(year-1, 12, 31);
					for (;;){
						startDay=daday(year-1,nomerDnya)[1];
						startMon=daday(year-1,nomerDnya)[0];
						denNedeli=dayOfWeek(JD(year-1, startMon, startDay));
						if (denNedeli!=1) nomerDnya= nomerDnya-1;
						else break;
					}
				}
				else {
					nomerDnya= daynum(year, mon-1, mes[mon-2]);
					for (;;){
						startDay=daday(year,nomerDnya)[1];
						startMon=daday(year,nomerDnya)[0];
						denNedeli=dayOfWeek(JD(year, startMon, startDay));
						if (denNedeli!=1) nomerDnya= nomerDnya-1;
						else break;
					}
				}	


			}
			while (denNedeli !=1);}
		else {
			startDay=day;
			startMon=mon;
			nomerDnya=daynum (year,mon,1);

		}
		int curv;
		if (startMon == 12) curv=365+viis(year-1);
		else  curv=365+viis(year);

		double jd1=JD(((startMon==12)?year-1:year), startMon, startDay);





		sss15.setBackgroundColor(0xFF222222);
		sss23.setBackgroundColor(0xFF222222);
		sss31.setBackgroundColor(0xFF222222);
		sss39.setBackgroundColor(0xFF222222);
		sss47.setBackgroundColor(0xFF222222);
		sss55.setBackgroundColor(0xFF222222);

		//// block of first 5 textviews

		sss1= (TextView)findViewById(R.id.textView1);
		sss2= (TextView)findViewById(R.id.textView2);
		sss3= (TextView)findViewById(R.id.textView3);
		sss4= (TextView)findViewById(R.id.textView4);
		sss5= (TextView)findViewById(R.id.textView5);
		sss1.setText(""+day+"-"+mon+"-"+year);
		//int dd = dayOfWeek(JD(year, mon, day));
		String weekStr [] = res.getStringArray(R.array.Weekday);
		sss2.setText(weekStr[dayOfWeek(JD(year, mon, day))]);
		/*
		switch (dd){
		case 0: sss2.setText ("Воскресение");
		break;
		case 1: sss2.setText ("Понедельник");
		break;
		case 2: sss2.setText ("Вторник");
		break;
		case 3: sss2.setText ("Среда");
		break;
		case 4: sss2.setText ("Четверг");
		break;
		case 5: sss2.setText ("Пятница");
		break;
		case 6: sss2.setText ("Суббота");
		break;    	
		}

		sss3.setText("Неделя номер  "+WN  (year, mon, day));
		sss4.setText("Прошло дней "+daynum (year, mon, day));
		sss5.setText("Осталось "+(curv - daynum (year, mon, day)));
		 */
		sss3.setText(res.getString(R.string.Weeknumber)+"  "+WN  (year, mon, day));
		sss4.setText(res.getString(R.string.Dayspass)+"  "+daynum (year, mon, day));
		sss5.setText(res.getString(R.string.Daylast)+"  "+(curv - daynum (year, mon, day)));

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



	public static double JD (int year, int mon, int day){

		return (367*year-7*(year+(mon+9)/12)/4+275*mon/9+day+1721013.5);
	}

	public static void calCallend (double jd)

	{


	}

	public static int dayOfWeek (double aaa)
	{
		return (int)(aaa+1.5)%7;
	}
	public static int daynum (int y, int m, int d)
	{
		int vis; //leep year 
		{ if (y%4==0 && y%100!=0) vis=1;
		else if (y%400==0) vis=1;
		else vis=0;	
		}
		return (int)(m*275/9)-(int)((2-vis)*(m+9)/12)+d-30; 
	}

	public static int viis(int year)
	{
		int vis; //leep year 
		{ if (year%4==0 && year%100!=0) vis=1;
		else if (year%400==0) vis=1;
		else vis=0;	
		}
		//true = 1
		return vis;
	}


	public static int [] daday (int year, int num){
		int vis; //leep year 
		{ if (year%4==0 && year%100!=0) vis=1;
		else if (year%400==0) vis=1;
		else vis=0;	
		}

		int a=1889;
		if (vis == 1) a=1523; 
		int b = (int)((num+a-122.1)/365.25);
		int c = num+a-(int)(365.25*b);
		int e = (int)(c/30.6001);
		int m = (e>13.5)?e-13:e-1;
		int d = c-(int)(30.6001*e);
		int aa [] = new int [2];
		aa[0]=m;
		aa[1]=d;
		return aa;



	}
	public static int  unJD (double jd){
		int z=(int) (jd+0.5);

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
		int z=(int) (jd+0.5);

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
		int z=(int) (jd+0.5);

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

	public static int WN  (int year, int mon, int day)
	{
		//return (int) (Math.floor((daynum (year, mon, day)+day-1)/7)+1);
		int x = dayOfWeek (JD (year,mon,day)); //from 0 to 6

		if (x==0)
			x=7;
		return (int)(daynum(year, mon, day)-x+10)/7;

	}

	public static void textBackgroundBold (TextView la)
	{
		la.setTypeface(null, 1);
		la.setBackgroundColor(0xFF333333);

	}

	public static void textToNormal (TextView la)
	{
		la.setTypeface(null, 0);
		la.setBackgroundColor(0);

	}

	public void ClearClalendar ()
	{
		textToNormal(sss16);
		textToNormal(sss17);
		textToNormal(sss18);
		textToNormal(sss19);
		textToNormal(sss20);
		textToNormal(sss21);
		textToNormal(sss22);
		textToNormal(sss24);
		textToNormal(sss25);
		textToNormal(sss26);
		textToNormal(sss27);
		textToNormal(sss28);
		textToNormal(sss29);
		textToNormal(sss30);
		textToNormal(sss32);
		textToNormal(sss33);
		textToNormal(sss34);
		textToNormal(sss35);
		textToNormal(sss36);
		textToNormal(sss37);
		textToNormal(sss38);
		textToNormal(sss40);
		textToNormal(sss41);
		textToNormal(sss42);
		textToNormal(sss43);
		textToNormal(sss44);
		textToNormal(sss45);
		textToNormal(sss46);
		textToNormal(sss48);
		textToNormal(sss49);
		textToNormal(sss50);
		textToNormal(sss51);
		textToNormal(sss52);
		textToNormal(sss53);
		textToNormal(sss54);
		textToNormal(sss56);
		textToNormal(sss57);
		textToNormal(sss58);
		textToNormal(sss59);
		textToNormal(sss60);
		textToNormal(sss61);
		textToNormal(sss62);


	}



	public void ClendarSET (int year, int mon, int day)
	{
		int [] mes = {31, 28+viis(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int denNedeli=dayOfWeek(JD(year,mon,1));
		int nomerDnya=daynum(year, mon,1);
		int startDay, startMon;

		if (denNedeli!=1){
			do
			{
				if (mon == 1){
					nomerDnya= daynum(year-1, 12, 31);
					for (;;){
						startDay=daday(year-1,nomerDnya)[1];
						startMon=daday(year-1,nomerDnya)[0];
						denNedeli=dayOfWeek(JD(year-1, startMon, startDay));
						if (denNedeli!=1) nomerDnya= nomerDnya-1;
						else break;
					}
				}
				else {
					nomerDnya= daynum(year, mon-1, mes[mon-2]);
					for (;;){
						startDay=daday(year,nomerDnya)[1];
						startMon=daday(year,nomerDnya)[0];
						denNedeli=dayOfWeek(JD(year, startMon, startDay));
						if (denNedeli!=1) nomerDnya= nomerDnya-1;
						else break;
					}
				}	


			}
			while (denNedeli !=1);}
		else {
			startDay=1;
			startMon=mon;
			nomerDnya=daynum (year,mon,1);

		}
		int curv;
		if (startMon == 12) curv=365+viis(year-1);
		else  curv=365+viis(year);

		double jd1=JD(((startMon==12)?year-1:year), startMon, startDay);


		sss16.setText(""+unJD(jd1+0));
		sss17.setText(""+unJD(jd1+1));
		sss18.setText(""+unJD(jd1+2));
		sss19.setText(""+unJD(jd1+3));
		sss20.setText(""+unJD(jd1+4));
		sss21.setText(""+unJD(jd1+5));
		sss22.setText(""+unJD(jd1+6));
		sss24.setText(""+unJD(jd1+7));
		sss25.setText(""+unJD(jd1+8));
		sss26.setText(""+unJD(jd1+9));
		sss27.setText(""+unJD(jd1+10));
		sss28.setText(""+unJD(jd1+11));
		sss29.setText(""+unJD(jd1+12));
		sss30.setText(""+unJD(jd1+13));
		sss32.setText(""+unJD(jd1+14));
		sss33.setText(""+unJD(jd1+15));
		sss34.setText(""+unJD(jd1+16));
		sss35.setText(""+unJD(jd1+17));
		sss36.setText(""+unJD(jd1+18));
		sss37.setText(""+unJD(jd1+19));
		sss38.setText(""+unJD(jd1+20));
		sss40.setText(""+unJD(jd1+21));
		sss41.setText(""+unJD(jd1+22));
		sss42.setText(""+unJD(jd1+23));
		sss43.setText(""+unJD(jd1+24));
		sss44.setText(""+unJD(jd1+25));
		sss45.setText(""+unJD(jd1+26));
		sss46.setText(""+unJD(jd1+27));
		sss48.setText(""+unJD(jd1+28));
		sss49.setText(""+unJD(jd1+29));
		sss50.setText(""+unJD(jd1+30));
		sss51.setText(""+unJD(jd1+31));
		sss52.setText(""+unJD(jd1+32));
		sss53.setText(""+unJD(jd1+33));
		sss54.setText(""+unJD(jd1+34));
		sss56.setText(""+unJD(jd1+35));
		sss57.setText(""+unJD(jd1+36));
		sss58.setText(""+unJD(jd1+37));
		sss59.setText(""+unJD(jd1+38));
		sss60.setText(""+unJD(jd1+39));
		sss61.setText(""+unJD(jd1+40));
		sss62.setText(""+unJD(jd1+41));

		if (unMonJD (jd1+0) == mon) textBackgroundBold( sss16);
		if (unMonJD (jd1+1) == mon) textBackgroundBold( sss17);
		if (unMonJD (jd1+2) == mon) textBackgroundBold( sss18);
		if (unMonJD (jd1+3) == mon) textBackgroundBold( sss19);
		if (unMonJD (jd1+4) == mon) textBackgroundBold( sss20);
		if (unMonJD (jd1+5) == mon) textBackgroundBold( sss21);
		if (unMonJD (jd1+6) == mon) textBackgroundBold( sss22);
		if (unMonJD (jd1+7) == mon) textBackgroundBold( sss24);
		if (unMonJD (jd1+8) == mon) textBackgroundBold( sss25);
		if (unMonJD (jd1+9) == mon) textBackgroundBold( sss26);
		if (unMonJD (jd1+10) == mon) textBackgroundBold( sss27);
		if (unMonJD (jd1+11) == mon) textBackgroundBold( sss28);
		if (unMonJD (jd1+12) == mon) textBackgroundBold( sss29);
		if (unMonJD (jd1+13) == mon) textBackgroundBold( sss30);
		if (unMonJD (jd1+14) == mon) textBackgroundBold( sss32);
		if (unMonJD (jd1+15) == mon) textBackgroundBold( sss33);
		if (unMonJD (jd1+16) == mon) textBackgroundBold( sss34);
		if (unMonJD (jd1+17) == mon) textBackgroundBold( sss35);
		if (unMonJD (jd1+18) == mon) textBackgroundBold( sss36);
		if (unMonJD (jd1+19) == mon) textBackgroundBold( sss37);
		if (unMonJD (jd1+20) == mon) textBackgroundBold( sss38);
		if (unMonJD (jd1+21) == mon) textBackgroundBold( sss40);
		if (unMonJD (jd1+22) == mon) textBackgroundBold( sss41);
		if (unMonJD (jd1+23) == mon) textBackgroundBold( sss42);
		if (unMonJD (jd1+24) == mon) textBackgroundBold( sss43);
		if (unMonJD (jd1+25) == mon) textBackgroundBold( sss44);
		if (unMonJD (jd1+26) == mon) textBackgroundBold( sss45);
		if (unMonJD (jd1+27) == mon) textBackgroundBold( sss46);
		if (unMonJD (jd1+28) == mon) textBackgroundBold( sss48);
		if (unMonJD (jd1+29) == mon) textBackgroundBold( sss49);
		if (unMonJD (jd1+30) == mon) textBackgroundBold( sss50);
		if (unMonJD (jd1+31) == mon) textBackgroundBold( sss51);
		if (unMonJD (jd1+32) == mon) textBackgroundBold( sss52);
		if (unMonJD (jd1+33) == mon) textBackgroundBold( sss53);
		if (unMonJD (jd1+34) == mon) textBackgroundBold( sss54);
		if (unMonJD (jd1+35) == mon) textBackgroundBold( sss56);
		if (unMonJD (jd1+36) == mon) textBackgroundBold( sss57);
		if (unMonJD (jd1+37) == mon) textBackgroundBold( sss58);
		if (unMonJD (jd1+38) == mon) textBackgroundBold( sss59);
		if (unMonJD (jd1+39) == mon) textBackgroundBold( sss60);
		if (unMonJD (jd1+40) == mon) textBackgroundBold( sss61);
		if (unMonJD (jd1+41) == mon) textBackgroundBold( sss62);

		sss15.setText(""+WN (unYerJD (jd1), unMonJD (jd1), unJD (jd1) ));
		sss23.setText(""+WN (unYerJD (jd1+7), unMonJD (jd1+7), unJD (jd1+7) ));
		sss31.setText(""+WN (unYerJD (jd1+14), unMonJD (jd1+14), unJD (jd1+14) ));
		sss39.setText(""+WN (unYerJD (jd1+21), unMonJD (jd1+21), unJD (jd1+21) ));
		sss47.setText(""+WN (unYerJD (jd1+28), unMonJD (jd1+28), unJD (jd1+28) ));
		sss55.setText(""+WN (unYerJD (jd1+35), unMonJD (jd1+35), unJD (jd1+35) ));
		String sMonths [] = res.getStringArray(R.array.Months);
		settedMon.setText(" "+sMonths[mon]+"  ");
		settedYear.setText("  "+year+"  ");



	}

	///////////////////////////////////////////////////////////

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

	public void CalendLeftButClick (View v)
	{
		if (iYear >=2005)
		{
			if (iMon == 1)
			{
				iMon=12;
				--iYear;
			}
			else
			{
				--iMon;
			}
			ClearClalendar();
			ClendarSET(iYear, iMon, iDay);
		}

	}


	public void CalendRightButClick(View v)
	{
		if (iYear<=2050)
		{
			if (iMon == 12)
			{
				iMon=1;
				++iYear;
			}
			else
			{
				++iMon;
			}

			ClearClalendar();
			ClendarSET(iYear, iMon, iDay);
		}

	}



	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {



		ArrayList<Prediction> predictions = mLibrary.recognize(gesture);

		// We want at least one prediction
		if (predictions.size() > 0) {
			Prediction prediction = predictions.get(0);
			// We want at least some confidence in the result
			if (prediction.score > 1.0) {
				// Show the spell
				//Toast.makeText(this, prediction.name, Toast.LENGTH_SHORT).show();

				/*vText.setText(prediction.name);
				if (prediction.name.equals("right"))
				{
					vText.setTextColor(0xFF000000);
					lLay.setBackgroundColor(0xFFFF0000);


				}
				else if (prediction.name.equals("left"))
				{
					vText.setTextColor(0xFF000000);
					lLay.setBackgroundColor(0xFFFFFF00);
				}
				 */

				if (prediction.name.equals("right")) //-1
				{
					but4click(null);
				}
				else if (prediction.name.equals("left"))//+1
				{
					but2click(null);
				}
				else if (prediction.name.equals("calendnx"))
				{
					CalendRightButClick(null);
				}
				else if (prediction.name.equals("calendprev"))
				{
					CalendLeftButClick(null);
				}
			}
		}		
	}
}

