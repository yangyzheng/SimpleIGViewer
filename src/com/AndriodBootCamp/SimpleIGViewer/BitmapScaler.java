package com.AndriodBootCamp.SimpleIGViewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class BitmapScaler
{
	// scale and keep aspect ratio
	public static Bitmap scaleToFitWidth(Bitmap b, int width)
	{
		float factor = width / (float) b.getWidth();
		return Bitmap.createScaledBitmap(b, width, (int) (b.getHeight() * factor), true);
	}
 
 
	// scale and keep aspect ratio
	public static Bitmap scaleToFitHeight(Bitmap b, int height)
	{
		float factor = height / (float) b.getHeight();
		return Bitmap.createScaledBitmap(b, (int) (b.getWidth() * factor), height, true);
	}
 
 
	// scale and keep aspect ratio
	public static Bitmap scaleToFill(Bitmap b, int width, int height)
	{
		float factorH = height / (float) b.getWidth();
		float factorW = width / (float) b.getWidth();
		float factorToUse = (factorH > factorW) ? factorW : factorH;
		return Bitmap.createScaledBitmap(b, (int) (b.getWidth() * factorToUse), 
		  (int) (b.getHeight() * factorToUse), true);
	}
 
 
	// scale and don't keep aspect ratio
	public static Bitmap strechToFill(Bitmap b, int width, int height)
	{
		float factorH = height / (float) b.getHeight();
		float factorW = width / (float) b.getWidth();
		return Bitmap.createScaledBitmap(b, (int) (b.getWidth() * factorW), 
		  (int) (b.getHeight() * factorH), true);
	}
	
	public Bitmap scaleToActualAspectRatio(Context context, Bitmap bitmap) 
	{
		if (bitmap != null) {
			boolean flag = true;

			DisplayMetrics metrics = new DisplayMetrics();
	        WindowManager windowManager = (WindowManager) context
	                .getSystemService(Context.WINDOW_SERVICE);
	        windowManager.getDefaultDisplay().getMetrics(metrics);	
	    	        
			int deviceWidth = metrics.widthPixels;
			int deviceHeight = metrics.heightPixels;

			int bitmapHeight = bitmap.getHeight(); // 563
			int bitmapWidth = bitmap.getWidth(); // 900

			// aSCPECT rATIO IS Always WIDTH x HEIGHT rEMEMMBER 1024 x 768

			if (bitmapWidth > deviceWidth) {
				flag = false;

				// scale According to WIDTH
				int scaledWidth = deviceWidth;
				int scaledHeight = (scaledWidth * bitmapHeight) / bitmapWidth;

				try {
					if (scaledHeight > deviceHeight)
						scaledHeight = deviceHeight;

					bitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth,
							scaledHeight, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (flag) {
				if (bitmapHeight > deviceHeight) {
					// scale According to HEIGHT
					int scaledHeight = deviceHeight;
					int scaledWidth = (scaledHeight * bitmapWidth)
							/ bitmapHeight;

					try {
						if (scaledWidth > deviceWidth)
							scaledWidth = deviceWidth;

						bitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth,
								scaledHeight, true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return bitmap;
	}
}