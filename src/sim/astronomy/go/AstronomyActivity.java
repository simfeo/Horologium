package sim.astronomy.go;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class AstronomyActivity extends FragmentActivity {

	static final String TAG = "myLogs";
	static final int PAGE_COUNT = 4;
	Resources resmain;
	
	public static final int  IDM_OPENFAQ = 101, IDM_OPENEDITOR = 102, IDM_OPENABOUT=103;

	String pageTitles [] = {"Home","Sun","Moon","Zodiac"};

	ViewPager pager;
	PagerAdapter pagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainest_one);
		
		resmain = getResources();

		pager = (ViewPager) findViewById(R.id.pager);
		pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(pagerAdapter);

		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				Log.d(TAG, "onPageSelected, position = " + position);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}


		});
	}
	
	@Override
	public boolean onCreateOptionsMenu (Menu menu)
	{


		menu.add(Menu.NONE, IDM_OPENEDITOR, Menu.NONE, resmain.getString(R.string.Menu_EDITOR) );

		menu.add(Menu.NONE, IDM_OPENABOUT, Menu.NONE, resmain.getString(R.string.Menu_About));
		return (super.onCreateOptionsMenu(menu));

	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item)

	{
		switch (item.getItemId())
		{

		case IDM_OPENABOUT:
			Intent i = new Intent (this, About.class);
			startActivity(i);
			break;

		case IDM_OPENEDITOR:
			Intent i1 = new Intent (this, EditCords.class);
			startActivityForResult(i1, 1);
			break;
		default:
			return false;

		}

		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1)
		{
			if(resultCode == RESULT_OK){
				Intent i = new Intent(this, AstronomyActivity.class);
				startActivity(i);
				finish();
			}
			else if (resultCode == RESULT_CANCELED) {
			}
		}

	} 
	public void CalendLeftButClick (View v)
	{
		HomeScreen.CalendLeftButClick();
	}

	public void CalendRightButClick (View v)
	{
		HomeScreen.CalendRightButClick();
	}

	private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position)
			{case 1:
			{
				String [] arrayContainer = new String [11];
				try {
					arrayContainer = readingProcedure();

				}
				catch (IOException e) {
					try
					{
						writingProcedure("ส่ๅโ +2 on north 50 27 00 east 30 30 00");
					}
					catch (IOException e1)
					{

					}
					try {
						arrayContainer=readingProcedure();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

				}
				return SunScreen.newInstance(position, arrayContainer);
			}
			case 2:
				return MoonScreen.newInstance(position);
			case 3: 
				return ZodiacScreen.newInstance(position);
			default:
				return HomeScreen.newInstance(position);
			}
		}

		@Override
		public int getCount() {
			return PAGE_COUNT;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return pageTitles[position];
		}

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


	public void writingProcedure (String towrite) throws IOException
	{
		OutputStream outStream = openFileOutput ("coords.txt", 0x00000000);
		OutputStreamWriter outStreamWrite = new OutputStreamWriter (outStream);
		try 	{
			outStreamWrite.write (towrite);
		}
		finally {
			outStreamWrite.close();
		}


	}

}