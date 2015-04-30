package com.zifei.corebeau.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Region;
import android.os.Environment;

public class RegionDBHelper {

    private static final String TABLE_CITY = "T_City";
    private static final String TABLE_PROVINCE = "T_Province";
    private static final String TABLE_ZONE = "T_Zone";

    private static RegionDBHelper instance;

    private SQLiteDatabase db;

    public static void init(Context context) throws IOException {

        File dir = new File("/data/data/com.zifei.corebeau/databases/");
        
        // 일치하는 디랙토리가 없으면 생성
        if( !dir.exists() ) {    
                dir.mkdirs();    }


        // DB파일 패키지 설치 폴더에 복사
        File file = new File(
              "/data/data/com.zifei.corebeau/databases/region.db");

        AssetManager mgr = context.getAssets();
        InputStream is = null;
        OutputStream os = null;
        try {
            byte[] buffer = new byte[8192];
            int count = 0;
            is = mgr.open("china_Province_city_zone");
            os = new FileOutputStream(file);
            while ((count = is.read(buffer)) != -1) {
                os.write(buffer, 0, count);
            }
            os.flush();
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
                if (os != null) {
                    os.close();
                    os = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private RegionDBHelper() {
    	try {
    		db = SQLiteDatabase.openDatabase("/data/data/com.zifei.corebeau/databases/region.db", null, SQLiteDatabase.OPEN_READONLY|SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		} catch (SQLiteException e) {
			// TODO: handle exception
		}catch (Exception e) {
			// TODO: handle exception
		}
    }

    public static synchronized RegionDBHelper getInstance() {
        if (instance == null) {
            instance = new RegionDBHelper();
        }
        return instance;
    }

    public List<String> getAllProvinceRegion() {
        if (db == null) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        Cursor cursor = db.query(TABLE_PROVINCE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0));
        }

        return list;
    }

}
