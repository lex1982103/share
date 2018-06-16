package lerrain.project.mshell;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Ui
{
	public static float dp = 1;
	
	public static int width = 800;
	public static int height = 1280;
	
	public static int dp(int x)
	{
		return Math.round(x * dp);
	}
	
	public static int colorOf(int alpha, int color)
	{
		return alpha << 24 | (color & 0x00FFFFFF);
	}
	
	public static Paint paintOf(int color)
	{
		return paintOf(color, dp);
	}
	
	public static Paint paintOf(int color, float dp)
	{
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setColor(color);
		p.setStyle(Style.FILL_AND_STROKE);
		p.setStrokeWidth(dp);
		
		return p;
	}
	
	public static ColorFilter colorFilterOf(int color)
	{
		return colorFilterOf((float)((color & 0x00FF0000) >> 16) / 255, (float)((color & 0x0000FF00) >> 8) / 255, (float)(color & 0x000000FF) / 255, 1);
	}
	
	public static ColorFilter colorFilterOf(float r, float g, float b, float alpha)
	{
		float[] m = {
				0, 0, 0, r, 0,
				0, 0, 0, g, 0,
				0, 0, 0, b, 0,
				0, 0, 0, alpha, 0 };
		ColorMatrix cm = new ColorMatrix();
		cm.set(m);

		return new ColorMatrixColorFilter(cm);
	}
	
	public static String reviseAni(String ani)
	{
		if ("rl".equals(ani))
			return "lr";
		else if ("lr".equals(ani))
			return "rl";
		else if ("drop".equals(ani))
			return "fold";
		else if ("fold".equals(ani))
			return "drop";
		
		return null;
	}
}
