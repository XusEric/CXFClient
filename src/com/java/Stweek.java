package com.java;

import java.io.InputStream;
import java.util.Properties;
public class Stweek {
	static private String startdate = null;
	static private String totalweek = null;
	static{
		loads();
	}
	synchronized static public void loads(){
		if(startdate == null || totalweek == null)
		{
			InputStream is = Stweek.class.getResourceAsStream("/Config.properties");
			Properties dbProps = new Properties();
			try {
				dbProps.load(is);
				startdate = dbProps.getProperty("serviceClass");
				totalweek = dbProps.getProperty("address");
			}
			catch (Exception e) {
				System.err.println("���ܶ�ȡ�����ļ�. " +
				"��ȷ��db.properties��CLASSPATHָ����·����");
			}
		}
	}
	
	public static String getStartdate() {
		if(startdate==null)
			loads();
			return startdate;
	}
	
	public static String getTotalweek() {
		if(startdate==null)
			loads();
			return totalweek;
	}
}
