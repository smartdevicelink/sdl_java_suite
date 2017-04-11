package com.smartdevicelink.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;

public class SdlViewHelper {	
	private static final AtomicInteger sNextMenuId = new AtomicInteger(1);

	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

	public static int generateViewId() {
	    for (;;) {
	        final int result = sNextGeneratedId.get();
	        // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
	        int newValue = result + 1;
	        if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
	        if (sNextGeneratedId.compareAndSet(result, newValue)) {
	            return result;
	        }
	    }
	}
	
	public static int generateMenuId() {
	    for (;;) {
	        final int result = sNextMenuId.get();
	        // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
	        int newValue = result + 1;
	        if (newValue > 0x00FFFFFF){
	        	newValue = 1; // Roll over to 1, not 0.
	        }
	        if (sNextMenuId.compareAndSet(result, newValue)) {
	            return result;
	        }
	    }
	}
	
	public static <C> List<C> asList(SparseArray<C> sparseArray) {
	    if (sparseArray == null) return null;
	    List<C> arrayList = new ArrayList<C>(sparseArray.size());
	    for (int i = 0; i < sparseArray.size(); i++)
	        arrayList.add(sparseArray.valueAt(i));
	    return arrayList;
	}
	
	public static Bitmap drawableToBitmap (Drawable drawable) {
		   if(drawable==null){
			   return null;
		   }
			if (drawable instanceof BitmapDrawable) {
		        return ((BitmapDrawable)drawable).getBitmap();
		    }

		    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
		    Canvas canvas = new Canvas(bitmap); 
		    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		    drawable.draw(canvas);

		    return bitmap;
		}
}
