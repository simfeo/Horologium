package sim.horologium.app.EditCords;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * @author idimus a.k.a simfeo
 */
public class InputFilterMinMax implements InputFilter {
	 
	private int min, max;
 
	public InputFilterMinMax(int min, int max) {
		this.min = min;
		this.max = max;
	}
 
	@Override
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
		try {
			String startString = dest.toString().substring(0, dStart);
			String insert = source.toString();
			String endString = dest.toString().substring(dEnd);
			String parseThis = startString+insert+endString;
			int input = Integer.parseInt (parseThis);
			if (isInRange(min, max, input))
				return null;
		} catch (NumberFormatException nfe) { }		
		return "";
	}
 
	private boolean isInRange(int a, int b, int c) {
		return b > a ? c >= a && c <= b : c >= b && c <= a;
	}
}
