package sim.astronomy.go;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SunScreen extends Fragment {

	static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

	Resources res;
	TextView cityName, gmtt, latitude, longitude, sunrise, sunset, daylon;

	static String [] arrayContainer = new String [11];
	int lat1, lat2, lat3, lon1, lon2, lon3;

	int pageNumber;
	int backColor;
	View view;


	static SunScreen newInstance(int page, String [] arrayToSet) {
		SunScreen fragFrag = new SunScreen();
		Bundle arguments = new Bundle();
		arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
		fragFrag.setArguments(arguments);
		arrayContainer = arrayToSet;
		return fragFrag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

		res=getResources();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.sun, null);

		cityName=(TextView)view.findViewById(R.id.cityName);
		gmtt = (TextView)view.findViewById(R.id.gmtTime);
		latitude = (TextView)view.findViewById(R.id.latitude);
		longitude = (TextView)view.findViewById(R.id.longitude);
		sunrise = (TextView)view.findViewById(R.id.sunrise);
		sunset = (TextView)view.findViewById(R.id.sunset);
		daylon = (TextView)view.findViewById(R.id.winterSummer);
		
		cityName.setText(arrayContainer[0]);
		double zone =Double.parseDouble(arrayContainer[1]);

		gmtt.setText("GMT "+zoneString(zone));
		latitude.setText(res.getString(R.string.Sun_LATITUDE)+" "+
				arrayContainer[4]+"° "+arrayContainer[5]+"' "+arrayContainer[6]+"''  " +
				(arrayContainer[3].equals("north") == true?
						res.getString(R.string.Sun_North):res.getString(R.string.Sun_South)));

		longitude.setText(res.getString(R.string.Sun_LONGITUDE)+" "+ arrayContainer[8]+"° "+
				arrayContainer[9]+"' "+arrayContainer[10]+"''  "+
				(arrayContainer[7].equals("east") == true?
						res.getString(R.string.Sun_East):res.getString(R.string.Sun_West)));


		try
		{
			lat1=Integer.parseInt(arrayContainer[4]);

		}
		catch (Exception e)
		{
			lat1=0;
		}

		try
		{
			lat2=Integer.parseInt(arrayContainer[5]);

		}
		catch (Exception e)
		{
			lat2=0;
		}

		try
		{
			lat3=Integer.parseInt(arrayContainer[6]);

		}
		catch (Exception e)
		{
			lat3=0;
		}

		try
		{
			lon1=Integer.parseInt(arrayContainer[8]);

		}
		catch (Exception e)
		{
			lon1=0;
		}

		try
		{
			lon2=Integer.parseInt(arrayContainer[9]);

		}
		catch (Exception e)
		{
			lon2=0;
		}

		try
		{
			lon3=Integer.parseInt(arrayContainer[10]);

		}
		catch (Exception e)
		{
			lon3=0;
		}


		String dt=new java.text.SimpleDateFormat("dd-MM-yyyy").format(java.util.Calendar.getInstance ().getTime());

		String days[]=dt.split("-");

		int	day=Integer.parseInt(days[0]);
		int mon=Integer.parseInt(days[1]);
		int year=Integer.parseInt(days[2]);
		//		    	int day = 3;
		//				int mon = 12;
		//				int year= 2011;


		//	System.out.println (dt);

		int vis; //leep year 
		{ if (year%4==0 && year%100!=0) vis=1;
		else if (year%400==0) vis=1;
		else vis=0;	
		}

		double Jd=367*year-7*(year+(mon+9)/12)/4+275*mon/9+day+1721013.5;

		//	System.out.println ("Jd="+Jd);

		int daynum=Math.round(mon*275/9)-Math.round((2-vis)*(mon+9)/12)+day-29;

		//	System.out.println ("daynum " +daynum);

		double T=(Jd-2415020.0)/36525.0;
		double L=279.6968+36000.76892*T/1.0+0.000325*T*T/1.0;
		double M=358.47583+35999.04975*T/1.0-0.000150*T*T/1.0-0.0000033*T*T*T/1.0;
		double C=(1.919460-0.004789*T-0.000014*T*T)*sinSim(M)
				+(0.020094-0.000100*T)*sinSim(2*M)
				+0.000193*sinSim(3*M);
		double SOL=L+C;
		double EPS=23.452294-0.0130125*T
				-0.00000164*T*T+0.000000503*T*T*T;
		double Delta=Math.asin (sinSim(EPS)*sinSim(SOL));


		//fi dlya kieva
		//        double fi=50+27/60.0+0/3600.0;	
		//        double fiRad=degToRad(fi);
		//        double lw=(30+30/60.0+0/3600.0);

		//		double fi = 4 + 5 + 6            3
		// 		double lw = 8 + 9 + 10		     7

		double fi = lat1+lat2/60.0 + lat3/3600.0;
		double fiRad = Math.toRadians(fi); //////////////////////////////////////////////// -1 south / +1 north
		fiRad = (arrayContainer[3].equals("north") == true?fiRad:fiRad*(-1));
		double lw = lon1+lon2/60.0+lon3/3600.0;
		lw = (arrayContainer[7].equals("east")==true)?lw:-1*lw;


		double UTC=Double.parseDouble(arrayContainer[1]);
		if (arrayContainer[2].equals("on")==true && IsSummerTime(year, mon, daynum)==true)
			++UTC;
		//double riseTime=12.0-timeCor+smesh/15.0 - EoT/60+UTCCorrect;
		//double sunsetTime=12.0+timeCor+smesh/15.0 -EoT/60+UTCCorrect;

		double riseTime = SunRise (year, mon, day, fi,lw);
		double sunsetTime = SunSet(year, mon, day, fi,lw);

		if (sunsetTime != 1000 && riseTime != 1000 )
		{

			double DayLong = sunsetTime-riseTime;

			riseTime+=UTC;
			sunsetTime+=UTC;

			String addRise="", addSet="";

			if (riseTime>24)
			{
				riseTime-=24;
				addRise=" nx.";
			}
			else if (riseTime<0)
			{
				riseTime+=24;
				addRise=" prv.";
			}
			if (sunsetTime>24)
			{
				sunsetTime-=24;
				addSet=" nx.";
			}
			else if (sunsetTime<0)
			{
				sunsetTime+=24;
				addSet=" prv.";
			}


			int iDayLong = (int)DayLong;
			int iDayLongM=(int) Math.round((DayLong%1)*60);
			if (iDayLongM==60)
			{
				iDayLongM=0;
				++iDayLong;

			}
			String sDayLong=res.getString(R.string.Sun_DAYLONG)+" "+iDayLong+":"+((iDayLongM<10)?("0"+iDayLongM):(iDayLongM));

			int riseTimeInt=(int) (riseTime/1);
			int sunsetTimeInt=(int)(sunsetTime/1);

			String riseTimeMin = ""+ ((Math.round(riseTime%1.0*60)<10)?"0"+Math.round(riseTime%1.0*60):Math.round(riseTime%1.0*60));
			if (riseTimeMin.equalsIgnoreCase("60"))
			{
				riseTimeMin = "00";
				++riseTimeInt; 
			}
			String sunsetTimeMin = ""+ ((Math.round(sunsetTime%1.0*60)<10)?"0"+Math.round(sunsetTime%1.0*60):Math.round(sunsetTime%1.0*60));
			if (sunsetTimeMin.equals("60"))
			{
				sunsetTimeMin = "00";
				++sunsetTimeInt;
			}

			sunrise.setText(res.getString(R.string.Sun_SUNRISE)+" "+riseTimeInt+ ":"+riseTimeMin+addRise);
			sunset.setText(res.getString(R.string.Sun_SUNSET)+" "+sunsetTimeInt+":"+sunsetTimeMin+addSet);
			daylon.setText(sDayLong);
			daylon.setTextSize(12);
		}
		else
		{
			sunrise.setText("N/D");
			sunset.setText("N/D");
			daylon.setText("N/D");


		}



		return view;
	}


	///-----------------------------------------------------------
	public String zoneString ( double zone)
	{

		if (zone != 0){
			String sZone =(int)zone+":"+((int)(Math.abs(zone)%1*60) <10 ? "0"+(int)(Math.abs(zone)%1*60):(int)(Math.abs(zone)%1*60));
			if (zone>0)
				sZone="+"+sZone;
			return sZone;
		}
		else
			return (""+0);
	}

	//////////////-------------------- blok perobrazovaniy deg-- rad
	public static double degToRad (int a){
		return a*(Math.PI/180.0);
	} 
	public static double degToRad (double a){
		return a*(Math.PI/180.0);
	} 
	public static double radToDeg (int a){
		return a*(180.0/Math.PI);
	} 
	public static double radToDeg (double a){
		return a*(180.0/Math.PI);
	} 
	public static double sinSim (double a)
	{
		return Math.sin(degToRad (a));
	}
	public static double cosSim (double a)
	{
		return Math.cos (degToRad (a));




	} 
	//////----------------------------------- READING WRITING

	////////--------------------------------

	public static double SunRise (int year, int mon, int day, double fi, double lw) ///lat = fi    lon ==lw
	{
		////zenith:  Sun's zenith for sunrise/sunset
		///  offical      = 90 degrees 50'
		///		  civil        = 96 degrees
		///		  nautical     = 102 degrees
		///		  astronomical = 108 degrees
		//--------------------------------------------------------
		double zenith=90+50/60.0;
		///step1
		int N1=(int) (275*mon/9);
		int N2=(int)((mon+9)/12);
		int N3 = (1+(int)((year-4*(int)(year/4)+2)/3));
		int N = N1-(N2*N3)+day-30;
		///step2
		///if rising time is desired:
		///	  t = N + ((6 - lngHour) / 24)
		///	if setting time is desired:
		///	  t = N + ((18 - lngHour) / 24)
		double lngHour=lw/15.0;
		double t=N+((6-lngHour)/24.0);
		///step3
		double M=(0.9856*t)-3.289;
		//step4
		double L=M+(1.916*Math.sin(Math.toRadians(M))+(0.020*Math.sin(Math.toRadians(2*M))))+282.634;
		if (L>360)
			L-=360;
		else if (L<0)
			L+=360;
		if (L>360)
			L-=360;
		else if (L<0)
			L+=360;
		if (L>360)
			L-=360;
		else if (L<0)
			L+=360;
		///step5a
		double RA=Math.toDegrees(Math.atan(0.91764*Math.tan(Math.toRadians(L))));
		if (RA>360)
			RA-=360;
		else if (RA<-360)
			RA+=360;
		if (RA>360)
			RA-=360;
		else if (RA<-360)
			RA+=360;
		if (RA>360)
			RA-=360;
		else if (RA<-360)
			RA+=360;
		///step5b
		int Lquadrant =(int) (Math.floor(L/90)*90);
		int RAquadrant = (int)(Math.floor(RA/90)*90);
		RA=RA+(Lquadrant-RAquadrant);
		///step5c
		RA/=15;
		///step6
		double sinDec=0.39782*Math.sin(Math.toRadians(L));
		double cosDec=Math.cos(Math.asin(sinDec));
		///step7a
		double cosH=(Math.cos (Math.toRadians(zenith))-(sinDec*Math.sin(Math.toRadians(fi)))) /
				(cosDec*Math.cos(Math.toRadians(fi)));
		if (cosH>1 || cosH<-1)
			return 1000;
		///step7b
		//if if rising time is desired:
		//	  H = 360 - acos(cosH)
		//	if setting time is desired:
		//	  H = acos(cosH)
		double H = 360-Math.toDegrees(Math.acos(cosH));
		H/=15;
		///step 8
		double T = H+RA-(0.06571*t)-6.622;
		///step9
		double UT = T-lngHour;
		if  (UT>24)
			UT-=24;
		else if (UT<0)
			UT+=24;
		if  (UT>24)
			UT-=24;
		else if (UT<0)
			UT+=24;
		if  (UT>24)
			UT-=24;
		else if (UT<0)
			UT+=24;
		return UT;


	}

	public static double SunSet (int year, int mon, int day, double fi, double lw) ///lat = fi    lon ==lw
	{
		////zenith:  Sun's zenith for sunrise/sunset
		///  offical      = 90 degrees 50'
		///		  civil        = 96 degrees
		///		  nautical     = 102 degrees
		///		  astronomical = 108 degrees
		//--------------------------------------------------------
		double zenith=90+50/60.0;
		///step1
		int N1=(int) (275*mon/9);
		int N2=(int)((mon+9)/12);
		int N3 = (1+(int)((year-4*(int)(year/4)+2)/3));
		int N = N1-(N2*N3)+day-30;
		///step2
		///if rising time is desired:
		///	  t = N + ((6 - lngHour) / 24)
		///	if setting time is desired:
		///	  t = N + ((18 - lngHour) / 24)
		double lngHour=lw/15.0;
		double t1=N+((18-lngHour)/24.0);
		///step3
		double M1=(0.9856*t1)-3.289;
		//step4
		double L1=M1+(1.916*Math.sin(Math.toRadians(M1))+(0.020*Math.sin(Math.toRadians(2*M1))))+282.634;
		if (L1>360)
			L1-=360;
		else if (L1<0)
			L1+=360;
		if (L1>360)
			L1-=360;
		else if (L1<0)
			L1+=360;
		if (L1>360)
			L1-=360;
		else if (L1<0)
			L1+=360;
		///step5a
		double RA=Math.toDegrees(Math.atan(0.91764*Math.tan(Math.toRadians(L1))));
		if (RA>360)
			RA-=360;
		else if (RA<-360)
			RA+=360;
		if (RA>360)
			RA-=360;
		else if (RA<-360)
			RA+=360;
		if (RA>360)
			RA-=360;
		else if (RA<-360)
			RA+=360;
		///step5b
		int Lquadrant1 =(int) (Math.floor(L1/90)*90);
		int RAquadrant = (int)(Math.floor(RA/90)*90);
		double RA1=RA+(Lquadrant1-RAquadrant);
		///step5c
		RA1/=15;
		///step6
		double sinDec1=0.39782*Math.sin(Math.toRadians(L1));
		double cosDec=Math.cos(Math.asin(sinDec1));
		///step7a
		double cosH1=(Math.cos (Math.toRadians(zenith))-(sinDec1*Math.sin(Math.toRadians(fi)))) /
				(cosDec*Math.cos(Math.toRadians(fi)));
		if (cosH1>1 || cosH1<-1)
			return 1000;
		///step7b
		//if if rising time is desired:
		//	  H = 360 - acos(cosH)
		//	if setting time is desired:
		//	  H = acos(cosH)
		double H1 = Math.toDegrees(Math.acos(cosH1));
		H1/=15;
		///step 8
		double T1 = H1+RA1-(0.06571*t1)-6.622;
		///step9
		double UT1 = T1-lngHour;
		if  (UT1>24)
			UT1-=24;
		else if (UT1<0)
			UT1+=24;
		if  (UT1>24)
			UT1-=24;
		else if (UT1<0)
			UT1+=24;
		if  (UT1>24)
			UT1-=24;
		else if (UT1<0)
			UT1+=24;

		return UT1;



	}


	////////---------Calculations-----------
	static int SummerStart (int year)
	{
		return (31-((((5*year)/4)+4)%7));
	}
	static int WinterStart (int year)
	{

		return(31-((((5*year)/4)+1)%7));		
	}
	////	////	////	/////	////	////	////
	static boolean IsSummerTime (int year, int mon, int day)
	{
		if (mon >3 && mon<11)
			return true;
		if (mon==3 && day>=SummerStart(year))
			return true;
		else if (mon == 11 && day <WinterStart(year))
			return true;
		return false;
	}



	static int EssenUTCCalc (boolean east, int lat1, int lat2,int lat3)
	{
		int toret = (int) Math.round((lat1+lat2/60.0+lat3/3600.0)/15);


		return east?toret:-1*toret;


	}




}
