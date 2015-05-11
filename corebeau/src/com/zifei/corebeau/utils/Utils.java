package com.zifei.corebeau.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;


public class Utils {
    
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (NameNotFoundException e) {

        }
        return apiKey;
    }
    
    @SuppressLint("NewApi")
    public static int[] getDeviceSize(Context context) {
        int[] size = new int[2];

        int Measuredwidth = 0;
        int Measuredheight = 0;
        Point point = new Point();
        WindowManager wm = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            wm.getDefaultDisplay().getSize(point);
            Measuredwidth = point.x;
            Measuredheight = point.y;
        } else {
        	DisplayMetrics dm = context.getResources().getDisplayMetrics();
            Measuredwidth = dm.widthPixels;
            Measuredheight = dm.heightPixels;
        }

        size[0] = Measuredwidth;
        size[1] = Measuredheight;
        return size;
    }
    
    /**
     * dp to px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    
    /**
     * px to dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        return w_screen;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int h_screen = dm.heightPixels;
        return h_screen;
    }
    
    public static int getDeviceWidth(Context context){
        return getDeviceSize(context)[0];
    }
    
    public static int getDeviceHeight(Context context){
        return getDeviceSize(context)[1];
    }
    
    public static float getDensity(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
        wm.getDefaultDisplay().getMetrics(metrics);

        return metrics.density;
    }
    
//    private static String buildTransaction(final String type) {
//        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
//    }
    
    /**
     * convert bitmap to byte array
     *
     * @param bmp
     * @param needRecycle
     * @return
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    
    public static Bitmap getImageFromResource(Resources res, int resId) {
        Bitmap bitmap = null ; 
        if (resId <= 0) {
            return bitmap;
        }
       
        try {
            bitmap = BitmapFactory.decodeResource(res, resId);
        } catch (OutOfMemoryError ooe) {
            ooe.printStackTrace();
            System.gc();
            try {
                bitmap = BitmapFactory.decodeResource(res, resId);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                System.gc();
            }
        }
        return bitmap;
    }
    
    public static float adjustFontSize(int screenWidth, int screenHeight) {
        if (screenHeight > screenWidth) {
            screenWidth = screenHeight;
        } else {
        }
        float rate = ((float) screenWidth / 320); 
        return rate;
    }
    
    /**
     * if the phone has the app named packageName
     *
     * @param packageName
     * @return
     */
    public static boolean isPackageExisted(Context context, String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
        }
        return packageInfo != null;
    }
    
    /**
     * open another app
     *
     * @param context
     * @param packageName
     * @throws android.content.pm.PackageManager.NameNotFoundException
     */
    public static void openApp(Context context, String packageName) throws NameNotFoundException {
        PackageInfo pi = context.getPackageManager().getPackageInfo(packageName, 0);

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);

        List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(resolveIntent, 0);

        Iterator<ResolveInfo> iterator = apps.iterator();
        while (iterator.hasNext()) {
            ResolveInfo ri = iterator.next();
            if (ri != null) {
                packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName(packageName, className);
                intent.setComponent(cn);
                context.startActivity(intent);
            }
        }
    }

    /**
     * get all apps in ms not including system app
     *
     * @param context
     * @return
     */
    public static List<PackageInfo> getNonSystemApps(Context context) {
        List<PackageInfo> list = context.getPackageManager().getInstalledPackages(0);
        List<PackageInfo> systemApps = new ArrayList<PackageInfo>();
        for (PackageInfo info : list) {
            if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                // system app
                systemApps.add(info);
            }
        }
        list.removeAll(systemApps);
        return list;
    }

    /**
     * 此方法比较特殊，因为接口问题，返回值是一个奖励多少钱的字符串，所以从里面用正则表达是匹配出相应的数字
     * @param _string
     * @return
     */
    public static String getFigureFromStr(String _string){
        String regex = "\\d*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(_string);
        while (m.find()) {
            if (!"".equals(m.group())) {
                _string =  m.group();
            }
        }
        return _string;
    }
    
    /**
	 * is mobileNum right
	 * 
	 * @param mobiles
	 */
	public static boolean isMobileNO(String mobiles) {boolean flag = false;
	try {
		mobiles = mobiles.replace(" ", "");
		mobiles = mobiles.replace("+86", "");
		Pattern p = Pattern.compile("^((1[0-9]))\\d{9}$");
		Matcher m = p.matcher(mobiles);
		flag = m.matches();
	} catch (Exception e) {
		flag = false;
	}
	return flag;}
	
    

    public static void showToast(Context context, int id) {
        Toast.makeText(context, context.getResources().getString(id), Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(Context context, int id) {
        Toast.makeText(context, context.getResources().getString(id), Toast.LENGTH_LONG).show();
    }

    public static void showToastLong(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }
    
    
    public static int getVersionCode() {

//		int versionCode = Integer.MAX_VALUE;
//		try {
//			versionCode = HuaqianApplication.app.getPackageManager().getPackageInfo(
//					HuaqianApplication.app.getPackageName(), 0).versionCode;
//		} catch (NameNotFoundException e) {
//			e.printStackTrace();
//		}
//		return versionCode;
        return 0;
	}
	
	
    public static Map<String, Object> buildMap(Object ...keyValues) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (int i = 0; i < keyValues.length; i += 2) {
			resultMap.put((String) keyValues[i], keyValues[i + 1]);
		}
		return resultMap;
	}
    
    public static String TAG="UTIL";
	public static Bitmap getbitmap(String imageUri) {
		Log.v(TAG, "getbitmap:" + imageUri);
		// 显示网络上的图片
		Bitmap bitmap = null;
		try {
			URL myFileUrl = new URL(imageUri);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();

			Log.v(TAG, "image download finished." + imageUri);
		} catch (IOException e) {
			e.printStackTrace();
			Log.v(TAG, "getbitmap bmp fail---");
			return null;
		}
		return bitmap;
	}
    
}
