package com.zifei.corebeau.utils;

import java.util.Calendar;

/**
 * Created by im14s_000 on 2015/4/1.
 */
public class DateUtil {
	
	public static String caculatePassTime(long uploadTime ){
		long  passMiniutes = (System.currentTimeMillis() - uploadTime)/60000;
		if(passMiniutes < 60 ){
			return passMiniutes + " 分前" ;
		}
		long passhours = passMiniutes / 60;
		if(passhours < 24){
			return passhours + " 时前";
		}
		
		long passDays = passhours /24 ;
		if(passDays <30){
			return passDays + " 天前";
		}
		
		long passMonths = passDays / 30;
		if(passMonths <12){
			return  passMonths + " 月前";
		}
		
		return passMonths /12 + " 年前";
		
	}
	
	public static void main(String[] args){
	    Calendar instance = Calendar.getInstance();
	    instance.add(Calendar.MINUTE, -10);
	    
	    System.out.println(caculatePassTime(instance.getTimeInMillis()));
	}

}
