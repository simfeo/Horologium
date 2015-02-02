package sim.astronomy.go;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Julian extends Activity {
	 /** Called when the activity is first created. */
//	private TextView chtoto;
	private TextView JulDayNum, DayOfWeek, LeepYear,DayNumber, ForTheEndYear, DayMonYear;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.julian);
 //       chtoto=(TextView)findViewById(R.id.textView1);
        JulDayNum=(TextView)findViewById(R.id.julDayNum);
		DayOfWeek=(TextView)findViewById(R.id.dayOfWeek);
		LeepYear=(TextView)findViewById(R.id.leepYear);
		DayNumber=(TextView)findViewById(R.id.dayNumber);
		ForTheEndYear=(TextView)findViewById(R.id.forTheEndYear);
		DayMonYear=(TextView)findViewById(R.id.dayMonYear);
        
        
        {String dt=new java.text.SimpleDateFormat("dd-MM-yyyy").format(java.util.Calendar.getInstance ().getTime());

    	String days[]=dt.split("-");

    	int	day=Integer.parseInt(days[0]);
    	int mon=Integer.parseInt(days[1]);
    	int year=Integer.parseInt(days[2]);
    	{mon=(mon==0)?12:mon;}

    	double Jd=367*year-7*(year+(mon+9)/12)/4+275*mon/9+day+1721013.5; //юлианский день

    	
    	int vis; //leep year 
    	{ if (year%4==0 && year%100!=0) vis=1;
    	else if (year%400==0) vis=1;
    	else vis=0;	
    	}
    	
    	int daynum=(int)(mon*275/9)-(int)((2-vis)*(mon+9)/12)+day-30;
    	int dayend=365-daynum+vis;
        
 //       	chtoto.setText(""+Jd);
    	
    	
    	int dd = (int)(Jd+1.5)%7;
		switch (dd){
		case 0: DayOfWeek.setText ("Sunday");
		break;
		case 1: DayOfWeek.setText ("Monday");
		break;
		case 2: DayOfWeek.setText ("Tuesday");
		break;
		case 3: DayOfWeek.setText ("Wednesday");
		break;
		case 4: DayOfWeek.setText ("Thursday");
		break;
		case 5: DayOfWeek.setText ("Friday");
		break;
		case 6: DayOfWeek.setText ("Suttarday");
		break;    	
		}
		JulDayNum.setText (""+Jd);
		LeepYear.setText((vis==1)?R.string.daVis:R.string.netVis);
		DayNumber.setText(""+daynum);
		ForTheEndYear.setText(""+dayend);
		DayMonYear.setText(""+day+"-"+mon+"-"+year);
        }
        
    }
    
    
    
    public void clickback (View v){
    	 Intent i = new Intent(this, AstronomyActivity.class);
	        startActivity(i);
	        finish();
    }
    public void closeclick (View v)
    {
    	finish();
    }
}
