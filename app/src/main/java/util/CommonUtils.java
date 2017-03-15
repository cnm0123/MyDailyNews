package util;

import android.annotation.SuppressLint;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
	private static DecimalFormat df = new DecimalFormat("#.00");
	public static String getFileSize(long filesize){
		if (filesize <= 1024) {
			return filesize+"B";			
		}else if(filesize <= 1024*1024){
			return df.format(filesize/1024)+"KB";
		}else if(filesize <= 1024*1024*1024){
			return df.format(filesize/1024/1024)+"M";
		}else{
			return df.format(filesize/1024/1024/1024)+"G";
		}
	}
	@SuppressLint("SimpleDateFormat")
	public static String getFormatTime(long time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sdf.format(new Date(time));
	}
}
