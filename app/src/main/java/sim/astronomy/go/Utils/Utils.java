package sim.astronomy.go.Utils;

import static java.security.AccessController.getContext;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Utils {

    public static String [] readingProcedure (Context ctx) throws IOException
    {
        InputStream inStream = ctx.openFileInput ("coords.txt");
        BufferedReader buffReader = new BufferedReader (new InputStreamReader(inStream));
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


    public static void writingProcedure ( Context ctx, String towrite) throws IOException
    {
        OutputStream outStream = ctx.openFileOutput ("coords.txt", 0x00000000);
        OutputStreamWriter outStreamWrite = new OutputStreamWriter (outStream);
        try 	{
            outStreamWrite.write (towrite);
        }
        finally {
            outStreamWrite.close();
        }
    }
}
