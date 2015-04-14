package com.zifei.corebeau.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.common.io.ByteStreams;
import com.zifei.corebeau.common.CorebeauApp;

import android.content.Context;

public class FileUtils {

    public static String getFileNameByUrl(String url) {
        if (StringUtil.isEmpty(url)) {
            return null;
        }
        int index = url.lastIndexOf('?');
        int index2 = url.lastIndexOf("/");
        if (index > 0 && index2 >= index) {
            return UUID.randomUUID().toString();
        }
        return url.substring(index2 + 1, index < 0 ? url.length() : index);
    }

    public static String getFileExtendName(String fileName) {
        if (StringUtil.isEmpty(fileName)) {
            return null;
        }
        int index = fileName.lastIndexOf('.');
        if (index < 0) {
            return "unknown";
        } else {
            return fileName.substring(index + 1);
        }
    }

    public static boolean isFileExists(String filePath) {
        if (StringUtil.isEmpty(filePath)) {
            return false;
        }
        return new File(filePath).exists();
    }
    
    public static boolean deleteFile(String filePath) {
        if (StringUtil.isEmpty(filePath)) {
            return false;
        }
        return new File(filePath).delete();
    }
    
    public static void delete(String url){
    	new File(url).delete();
    }
    
    public static void storeJSON(String fileName, Object data, boolean encrypt) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
	    	String dataStr = objectMapper.writeValueAsString(data);
	    	storeString(fileName, dataStr, encrypt);
		} catch (Exception e) {
		}
    }
    
    public static <T> T readJSON(String fileName, boolean encrypt, Class<T> cls) {
		try {
			String dataStr = readString(fileName, encrypt);
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(dataStr, cls);
		} catch (Exception e) {
		}
		return null;
    }
    
    public static void storeString(String fileName, String data, boolean encrypt) {
		FileOutputStream outStream = null;
		try {
			if (encrypt) {
	    		data = AESTools.encode(data);
	    	}
			outStream = CorebeauApp.app.getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
			outStream.write(data.getBytes());
		} catch (Exception e) {
		} finally {
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
				}
			}
		}
    }
    
    public static String readString(String fileName, boolean encrypt) {
    	FileInputStream inputStream = null;
		try {
			inputStream = CorebeauApp.app.getApplicationContext().openFileInput(fileName);
			if (inputStream != null) {
				String data = new String(ByteStreams.toByteArray(inputStream));
				if (encrypt) {
					data = AESTools.decode(data);
				}
				return data;
			}
		} catch (Exception e) {
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
    }
}
