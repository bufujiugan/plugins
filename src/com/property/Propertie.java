package com.property;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件
 * @author 于志强
 * 创建时间：2016-11-14 下午3:37:04   
 * 修改人：于志强  
 * 修改时间：2016-11-14 下午3:37:04
 */
public class Propertie {
	/**
	 * 根据key获取配置文件 
	 * @param key
	 * @return
	 */
	public static String readProp(String key) {
		Propertie p = new Propertie();
		return p.ReadProperties(key);
	}
	private String ReadProperties(String sKey){
		 Properties props = new Properties();
		 InputStream in;
		 try{
			 // 配置文件名字
			 in = getClass().getClassLoader().getResourceAsStream("config.properties");
			 props.load(in);
		 } catch(Exception e) {
			 return "";
		 }
	  if(props.isEmpty())
	  {
	   return "";
	  }
	  
	  return props.get(sKey) == null ? null : props.get(sKey).toString();
	}
	
	
	
	public static String readProp(File file) {
		Propertie p = new Propertie();
		return p.ReadProperties(file);
	}
	private String ReadProperties(File file){
		 Properties   props   =  new  Properties();
		  InputStream in;
		  try{
		  in=getClass().getResourceAsStream("config.properties");
		  props.load(in);
	  }
	  catch(Exception e)
	  {
	   return "";
	  }
	  if(props.isEmpty())
	  {
	   return "";
	  }
	  
	  return props.get(file).toString();
	}
}
