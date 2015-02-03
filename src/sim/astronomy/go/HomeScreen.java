package sim.astronomy.go;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeScreen extends Fragment {

	static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

	int pageNumber;

	private TextView sss1, sss2, sss3, sss4, sss5;
	static TextView sss15, sss16, sss17, sss18, sss19, sss20, sss21, sss22, sss23, sss24, sss25, sss26, sss27, sss28, sss29, sss30, sss31, sss32, sss33, sss34, 
	sss35, sss36, sss37, sss38, sss39, sss40, sss41, sss42, sss43, sss44, sss45, sss46, sss47, sss48, sss49, sss50, sss51, sss52, sss53, sss54, sss55, sss56, 
	sss57, sss58, sss59, sss60, sss61, sss62, settedMon, settedYear;
	static int iYear, iMon, iDay;
	static int iYearF, iMonF, iDayF;
	
	//String sMonths [] ={"0","январь", "февраль", "март", "апрель","май","июнь","июль","август","сентябрь","октябрь","ноябрь", "декабрь"};
	static Resources res;
	View view;
	//  static TextView tvPage_1;

	static HomeScreen newInstance(int page) {
		HomeScreen fragFrag = new HomeScreen();
		Bundle arguments = new Bundle();
		arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
		fragFrag.setArguments(arguments);
		return fragFrag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

		res = getResources();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.home, null);



		sss15=(TextView) view.findViewById(R.id.textView15);
		sss16=(TextView) view.findViewById(R.id.textView16);
		sss17=(TextView) view.findViewById(R.id.textView17);
		sss18=(TextView) view.findViewById(R.id.textView18);
		sss19=(TextView) view.findViewById(R.id.textView19);
		sss20=(TextView) view.findViewById(R.id.textView20);
		sss21=(TextView) view.findViewById(R.id.textView21);
		sss22=(TextView) view.findViewById(R.id.textView22);
		sss23=(TextView) view.findViewById(R.id.textView23);
		sss24=(TextView) view.findViewById(R.id.textView24);
		sss25=(TextView) view.findViewById(R.id.textView25);
		sss26=(TextView) view.findViewById(R.id.textView26);
		sss27=(TextView) view.findViewById(R.id.textView27);
		sss28=(TextView) view.findViewById(R.id.textView28);
		sss29=(TextView) view.findViewById(R.id.textView29);
		sss30=(TextView) view.findViewById(R.id.textView30);
		sss31=(TextView) view.findViewById(R.id.textView31);
		sss32=(TextView) view.findViewById(R.id.textView32);
		sss33=(TextView) view.findViewById(R.id.textView33);
		sss34=(TextView) view.findViewById(R.id.textView34);
		sss35=(TextView) view.findViewById(R.id.textView35);
		sss36=(TextView) view.findViewById(R.id.textView36);
		sss37=(TextView) view.findViewById(R.id.textView37);
		sss38=(TextView) view.findViewById(R.id.textView38);
		sss39=(TextView) view.findViewById(R.id.textView39);
		sss40=(TextView) view.findViewById(R.id.textView40);
		sss41=(TextView) view.findViewById(R.id.textView41);
		sss42=(TextView) view.findViewById(R.id.textView42);
		sss43=(TextView) view.findViewById(R.id.textView43);
		sss44=(TextView) view.findViewById(R.id.textView44);
		sss45=(TextView) view.findViewById(R.id.textView45);
		sss46=(TextView) view.findViewById(R.id.textView46);
		sss47=(TextView) view.findViewById(R.id.textView47);
		sss48=(TextView) view.findViewById(R.id.textView48);
		sss49=(TextView) view.findViewById(R.id.textView49);
		sss50=(TextView) view.findViewById(R.id.textView50);
		sss51=(TextView) view.findViewById(R.id.textView51);
		sss52=(TextView) view.findViewById(R.id.textView52);
		sss53=(TextView) view.findViewById(R.id.textView53);
		sss54=(TextView) view.findViewById(R.id.textView54);
		sss55=(TextView) view.findViewById(R.id.textView55);
		sss56=(TextView) view.findViewById(R.id.textView56);
		sss57=(TextView) view.findViewById(R.id.textView57);
		sss58=(TextView) view.findViewById(R.id.textView58);
		sss59=(TextView) view.findViewById(R.id.textView59);
		sss60=(TextView) view.findViewById(R.id.textView60);
		sss61=(TextView) view.findViewById(R.id.textView61);
		sss62=(TextView) view.findViewById(R.id.textView62);
		settedMon=(TextView) view.findViewById(R.id.settedMon);
		settedYear=(TextView) view.findViewById(R.id.settedYear);

		///////////
		{ String dt=new java.text.SimpleDateFormat("dd-MM-yyyy").format(java.util.Calendar.getInstance ().getTime());

		String days[]=dt.split("-");

		int	day=Integer.parseInt(days[0]);
		int mon=Integer.parseInt(days[1]);
		int year=Integer.parseInt(days[2]);

		iYear= year;
		iMon = mon;
		iDay = day;
		
		iYearF = year;
		iMonF  = mon;
		iDayF  = day;



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

		sss1= (TextView) view.findViewById(R.id.textView1);
		sss2= (TextView) view.findViewById(R.id.textView2);
		sss3= (TextView) view.findViewById(R.id.textView3);
		sss4= (TextView) view.findViewById(R.id.textView4);
		sss5= (TextView) view.findViewById(R.id.textView5);
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


		return view;
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

	public static void textBackgroundBold (TextView la, double d)
	{
		la.setTypeface(null, 1);
		int day,mon,year;
		day = unJD(d);
		mon = unMonJD(d);
		year = unYerJD(d);
		if (day != iDayF || (day == iDayF && ( mon != iMonF || year != iYearF)))
		{
			la.setBackgroundColor(0xFF333333);
		}
		else 
		{
			la.setBackgroundColor(0xFFCC3333);
		}

	}

	public static void textToNormal (TextView la)
	{
		la.setTypeface(null, 0);
		la.setBackgroundColor(0);

	}

	public static void ClearClalendar ()
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



	public static void ClendarSET (int year, int mon, int day)
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

		if (unMonJD (jd1+0) == mon) textBackgroundBold( sss16, jd1+0 );
		if (unMonJD (jd1+1) == mon) textBackgroundBold( sss17, jd1+1 );
		if (unMonJD (jd1+2) == mon) textBackgroundBold( sss18, jd1+2 );
		if (unMonJD (jd1+3) == mon) textBackgroundBold( sss19, jd1+3 );
		if (unMonJD (jd1+4) == mon) textBackgroundBold( sss20, jd1+4 );
		if (unMonJD (jd1+5) == mon) textBackgroundBold( sss21, jd1+5 );
		if (unMonJD (jd1+6) == mon) textBackgroundBold( sss22, jd1+6 );
		if (unMonJD (jd1+7) == mon) textBackgroundBold( sss24, jd1+7 );
		if (unMonJD (jd1+8) == mon) textBackgroundBold( sss25, jd1+8 );
		if (unMonJD (jd1+9) == mon) textBackgroundBold( sss26, jd1+9 );
		if (unMonJD (jd1+10) == mon) textBackgroundBold( sss27, jd1+10 );
		if (unMonJD (jd1+11) == mon) textBackgroundBold( sss28, jd1+11 );
		if (unMonJD (jd1+12) == mon) textBackgroundBold( sss29, jd1+12 );
		if (unMonJD (jd1+13) == mon) textBackgroundBold( sss30, jd1+13 );
		if (unMonJD (jd1+14) == mon) textBackgroundBold( sss32, jd1+14 );
		if (unMonJD (jd1+15) == mon) textBackgroundBold( sss33, jd1+15 );
		if (unMonJD (jd1+16) == mon) textBackgroundBold( sss34, jd1+16 );
		if (unMonJD (jd1+17) == mon) textBackgroundBold( sss35, jd1+17 );
		if (unMonJD (jd1+18) == mon) textBackgroundBold( sss36, jd1+18 );
		if (unMonJD (jd1+19) == mon) textBackgroundBold( sss37, jd1+19 );
		if (unMonJD (jd1+20) == mon) textBackgroundBold( sss38, jd1+20 );
		if (unMonJD (jd1+21) == mon) textBackgroundBold( sss40, jd1+21 );
		if (unMonJD (jd1+22) == mon) textBackgroundBold( sss41, jd1+22 );
		if (unMonJD (jd1+23) == mon) textBackgroundBold( sss42, jd1+23 );
		if (unMonJD (jd1+24) == mon) textBackgroundBold( sss43, jd1+24 );
		if (unMonJD (jd1+25) == mon) textBackgroundBold( sss44, jd1+25 );
		if (unMonJD (jd1+26) == mon) textBackgroundBold( sss45, jd1+26 );
		if (unMonJD (jd1+27) == mon) textBackgroundBold( sss46, jd1+27 );
		if (unMonJD (jd1+28) == mon) textBackgroundBold( sss48, jd1+28 );
		if (unMonJD (jd1+29) == mon) textBackgroundBold( sss49, jd1+29 );
		if (unMonJD (jd1+30) == mon) textBackgroundBold( sss50, jd1+30 );
		if (unMonJD (jd1+31) == mon) textBackgroundBold( sss51, jd1+31 );
		if (unMonJD (jd1+32) == mon) textBackgroundBold( sss52, jd1+32 );
		if (unMonJD (jd1+33) == mon) textBackgroundBold( sss53, jd1+33 );
		if (unMonJD (jd1+34) == mon) textBackgroundBold( sss54, jd1+34 );
		if (unMonJD (jd1+35) == mon) textBackgroundBold( sss56, jd1+35 );
		if (unMonJD (jd1+36) == mon) textBackgroundBold( sss57, jd1+36 );
		if (unMonJD (jd1+37) == mon) textBackgroundBold( sss58, jd1+37 );
		if (unMonJD (jd1+38) == mon) textBackgroundBold( sss59, jd1+38 );
		if (unMonJD (jd1+39) == mon) textBackgroundBold( sss60, jd1+39 );
		if (unMonJD (jd1+40) == mon) textBackgroundBold( sss61, jd1+40 );
		if (unMonJD (jd1+41) == mon) textBackgroundBold( sss62, jd1+41 );

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

	public static void CalendLeftButClick ()
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




	public static void CalendRightButClick()
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


}
