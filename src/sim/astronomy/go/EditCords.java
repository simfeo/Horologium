package sim.astronomy.go;


import android.app.Activity;
import android.content.Intent;
import android.content.res.*;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.io.*;
import java.text.StringCharacterIterator;



public class EditCords extends Activity {
	/** Called when the activity is first created. */
	TextView long1, long2, long3, lat1, lat2, lat3, City, gmt;
	RadioButton eastRadio, westRadio, northRadio, southRadio;
	ToggleButton togBut;
	String [] earth ={"north","south","west","east"};
	String [] arrayContainer = new String [11];
	public static final int IDM_SunACT = 101;
	public static final int IDM_Save = 102;
	public static final int IDM_Cancel = 103;
	Resources res;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.savingcords_v1);
		res = getResources();
		long1 =(TextView)findViewById(R.id.longitude_1);
		long2 =(TextView)findViewById(R.id.longitude_2);
		long3 =(TextView)findViewById(R.id.longitude_3);
		lat1 =(TextView)findViewById(R.id.latitude_1);
		lat2 =(TextView)findViewById(R.id.latitude_2);
		lat3 =(TextView)findViewById(R.id.latitude_3);
		City =(TextView)findViewById(R.id.city);
		gmt =(TextView)findViewById(R.id.gmtSet);
		eastRadio = (RadioButton)findViewById(R.id.East);
		westRadio = (RadioButton)findViewById(R.id.West);
		northRadio = (RadioButton)findViewById(R.id.Nrd);
		southRadio = (RadioButton)findViewById(R.id.Sth);
		togBut = (ToggleButton)findViewById(R.id.toggleButton1);
		Classing (long1, 0);
		Classing (long2, 0);
		Classing (long3, 0);
		Classing (lat1, 0);
		Classing (lat2, 0);
		Classing (lat3, 0);
		

		long1.setFilters (new InputFilter[]{new InputFilterMinMax("0", "180")});
		long2.setFilters (new InputFilter[]{new InputFilterMinMax("0", "59")});
		long3.setFilters (new InputFilter[]{new InputFilterMinMax("0", "59")});
		lat1.setFilters (new InputFilter[]{new InputFilterMinMax("0", "90")});
		lat2.setFilters (new InputFilter[]{new InputFilterMinMax("0", "59")});
		lat3.setFilters (new InputFilter[]{new InputFilterMinMax("0", "59")});



		//ส่ๅโ +2 on north 50 27 00 east 30 30 00
		// city gmt sum/win latit -- -- -- longit -- -- --


		for (String tmp: arrayContainer)
		{
			tmp = " ";
		}
		try
		{


			textSetInPosit();


		}
		catch (IOException e)
		{

			try {
				writingProcedure("ส่ๅโ +2 on north 50 27 00 east 30 30 00");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				textSetInPosit();

			}
			catch (IOException e2)
			{
				e2.printStackTrace();
			}
		}
	}

	@Override 

	public boolean onCreateOptionsMenu (Menu menu)
	{


		menu.add(Menu.NONE, IDM_SunACT, Menu.NONE, res.getString(R.string.Menu_RETURN));
		menu.add(Menu.NONE, IDM_Cancel, Menu.NONE, res.getString(R.string.Menu_CANCEL));
		menu.add(Menu.NONE, IDM_Save, Menu.NONE, res.getString(R.string.Menu_SAVE));
		return (super.onCreateOptionsMenu(menu));

	}

	@Override

	public boolean onOptionsItemSelected (MenuItem item)
	{

		switch (item.getItemId())
		{
		case IDM_SunACT:
			//    		Intent i = new Intent (this, Sun.class);
			//    		startActivity(i);
			finish();
			break;
		case IDM_Cancel:
			try {
				btClShow();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case IDM_Save:
			try {
				btClSave();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		default:
			return false;  	

		}
		return true;


	}




	public void writingProcedure (String write) throws IOException
	{
		OutputStream outStream = openFileOutput ("coords.txt", 0x00000000);
		OutputStreamWriter outStrWrite = new OutputStreamWriter (outStream);
		try {
			outStrWrite.write(write);}
		finally{
			outStrWrite.close();}

	}

	public String [] readingProcedure () throws IOException
	{
		InputStream inStream = openFileInput ("coords.txt");
		BufferedReader buffReader = new BufferedReader (new InputStreamReader (inStream));
		StringBuilder stringBuild = new StringBuilder();

		try{
			String str;
			while ((str=buffReader.readLine())!=null)
			{
				stringBuild.append(str);
			}
		}
		finally{
			buffReader.close();}

		String [] arrStr = stringBuild.toString().split(" ");
		return arrStr;
	}




	////-----


	public void btClSave () throws IOException
	{
		//    	String toWrite = null;
		//    	toWrite = City.getText().toString() + gmt.getText().toString();
		//    	writingProcedure (toWrite);
		//    	//ส่ๅโ +2 on north 50 27 00 east 30 30 00
		// city gmt sum/win latit -- -- -- longit -- -- --



		//String [] toWrite = new String [11];

		String writingLine;
		String [] stWrite = new String [7];
		stWrite[0]=""+zoneDob (gmt);
		stWrite[1]=lat1.getText().toString();
		stWrite[2]=lat2.getText().toString();
		stWrite[3]=lat3.getText().toString();
		stWrite[4]=long1.getText().toString();
		stWrite[5]=long2.getText().toString();
		stWrite[6]=long3.getText().toString();
		stWrite[1]=Diapasone (stWrite[1],90,0,0);
		stWrite[2]=Diapasone (stWrite[2],59,0,0);
		stWrite[3]=Diapasone (stWrite[3],59,0,0);
		stWrite[4]=Diapasone (stWrite[4],180,0,0);
		stWrite[5]=Diapasone (stWrite[5],59,0,0);
		stWrite[6]=Diapasone (stWrite[6],59,0,0);

		String CityName1=City.getText().toString();
		String CityName="";
		for (int i=0; i<CityName1.length(); i++)
		{
			if(CityName1.charAt(i) != ' ')
				CityName=CityName +CityName1.charAt(i);
		}
		if (CityName.length()<=1)
			CityName="Default";
		writingLine =CityName +" ";
		writingLine += stWrite[0]+" ";
		writingLine += togBut.isChecked() == true ? "on ": "of ";
		writingLine += northRadio.isChecked() == true ? earth[0]+" ": earth[1]+" ";
		writingLine += stWrite[1]+" "+stWrite[2]+" "+stWrite[3]+" ";
		writingLine += eastRadio.isChecked() == true ? earth[3]+" ": earth[2]+" ";
		writingLine += stWrite[4]+" "+stWrite[5]+" "+stWrite[6];
		writingProcedure(writingLine);


	}

	public void btClShow () throws IOException
	{
		textSetInPosit ();

	}

	public void textSetInPosit () throws IOException
	{
		arrayContainer = readingProcedure();
		City.setText (arrayContainer[0]);
		
		
		double zone = Double.parseDouble(arrayContainer[1]);
		zoneSet(gmt, zone);

		//gmt.setText (arrayContainer[1]);
		lat1.setText (arrayContainer[4]);
		lat2.setText (arrayContainer[5]);
		lat3.setText (arrayContainer[6]);
		long1.setText (arrayContainer[8]);
		long2.setText (arrayContainer[9]);
		long3.setText (arrayContainer[10]);
		if (arrayContainer[2].equals("on"))
			togBut.setChecked(true);
		else
			togBut.setChecked(false);
		if (arrayContainer[3].equals(earth[0]))
			northRadio.setChecked(true);
		else
			southRadio.setChecked(true);
		if (arrayContainer[7].equals(earth[3]))
			eastRadio.setChecked(true);
		else
			westRadio.setChecked(true);


	}

	public String Diapasone (String str, int max, int min, int def)
	{
		try {

			int num = Integer.parseInt(str);
			if (num<min)
				num=min;
			else if (num>max)
				num=max;
			return str=""+num;


		}
		catch (Exception e)
		{
			str=""+def;

		}
		return str;
	}

	////////// classing of inout type
	public void Classing (TextView edText, int parametr){

		switch (parametr)
		{
		case 0:
			edText.setInputType(InputType.TYPE_CLASS_NUMBER);
			break;
		case 1: 
			edText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
			break;
		default:
			edText.setInputType(InputType.TYPE_CLASS_NUMBER);
			break;
		}
	}


	/*
	double _char_to_number(String sStr) //func converting string to double
	{

		boolean flag, point = false;
		double number;

		if (sStr.charAt(0) == '-')
			flag=true;
		else
			flag = false;

		int start=flag ? 1: 0;
		double devin=10;

		char a = sStr.charAt(start);
		String A= ""+a;
		number=Integer.parseInt(A);


		for (int i=start+1; i< sStr.length(); i++)
		{
			if (sStr.charAt(i)=='.')
			{
				point = true;	
				continue;
			}


			if (point)
			{
				a=sStr.charAt(i);
				A=""+a;
				number+=(Integer.parseInt(A)/devin);
				devin*=10;
			}
			else 
			{
				a=sStr.charAt(i);
				A=""+a;
				number=number*10+Integer.parseInt(A);
			}

		}

		if (flag)
			number*=-1;

		return number;
	}
	 */
	/////////////////////////////////////////BBUUTTTOONNNSSS
	
	
	
	
	
	public void zoneSet (TextView edZone, double zone)
	{
		
		if (zone != 0){
			String sZone =(int)zone+":"+((int)(Math.abs(zone)%1*60) <10 ? "0"+(int)(Math.abs(zone)%1*60):(int)(Math.abs(zone)%1*60));
			if (zone>0)
				sZone="+"+sZone;
			edZone.setText(sZone);
		}
		else
			edZone.setText(""+0);
	}
	public double zoneDob (TextView edZone)
	{
		double zone;
		if (edZone.getText().toString().equals("0"))
			return zone =0;
		else {
			int  zoneM;
			double zoneH;

			String sss[] = edZone.getText().toString().split(":");
			zoneH =Double.parseDouble( sss[0]);
			zoneM =Integer.parseInt(sss[1]);
			if (zoneH <0)
				zoneM*=-1;

			zone = zoneH + zoneM/60.0;
		}
		return zone;
		
	}

	
	
	public void plusButtonClick (View v)
	{
		double zone = zoneDob(gmt);

		if (zone<14)
		{
			if (zone == 5.5 || zone == 5.75)
				zone+=0.25;
			else if ( (zone >=-5 &&  zone <-3) || (zone >=3 && zone<7) || (zone >=9 && zone<10))
				zone+=0.5;
			else
				++zone;	
			zoneSet (gmt, zone);
		}

	}

	public void minusButtonClick (View v)
	{
		double zone = zoneDob(gmt);

		
		if (zone >-12)
		{
			if (zone == 6 || zone == 5.75)
				zone-=0.25;
			else if ((zone<=10 && zone>9) || (zone <=7 && zone>3) || (zone <=-3 && zone>-5))
				zone-=0.5;
			else
				--zone;
			zoneSet (gmt, zone);


		}


	}
	/*
	public void plusButtonClick (View v)
	{
		int som;
		try {
			som =Integer.parseInt (gmt.getText().toString());
		}
		catch (Exception e)
		{
			String sSom=gmt.getText().toString().substring(1);
			som =Integer.parseInt (sSom);
		}
		if (som <14)
		{
			++som;
			String sSom;
			if (som > 0)
				sSom="+"+som;
			else
				sSom=""+som;
			gmt.setText(sSom);
		}
	}

	public void minusButtonClick (View v)
	{
		int som;
		try {
			som =Integer.parseInt (gmt.getText().toString());
		}
		catch (Exception e)
		{
			String sSom=gmt.getText().toString().substring(1);
			som =Integer.parseInt (sSom);
		}
		if (som >-12)
		{
			--som;
			String sSom;
			if (som > 0)
				sSom="+"+som;
			else
				sSom=""+som;
			gmt.setText(sSom);
		}

	}*/
}