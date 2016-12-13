package com.html;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HTMLUtil {

	public static void main(String[] args) {
//		// 获取散标列表
//		String str = getDataByHtml("http://127.0.0.1:8080/financial.do", "pctophone");
//		// 获取债权转让列表
//		String str = getDataByHtml("http://127.0.0.1:8080/transfer.do", "pctophone");
		// 获取汇付产品详情
		String str = getDataByHtml("http://127.0.0.1:8080/product/intoHfProduct.do?pid=40288c7858b8e17c0158b8eefe4f000a", "pctophone");
		
		String[] oldArray = new String[]{"PROJECT_NAME"};
		String[] newArray = new String[]{"xmmc"};
		JSONArray jsonArray = replaceDataByArray(getDataToJson(str).toString(), oldArray, newArray);
		for (int i = 0; i < jsonArray.size(); i++) {
			System.out.println(jsonArray.getJSONObject(i).toString());
		}
	}
	
	/**
	 * 获取url中指定标签的数据
	 * @param url 请求地址
	 * @param tag 标签
	 * @return
	 */
	public static String getDataByHtml(String url, String tag) {
		String result = "error";
		try {
			/* 第一步，构建一个Parser */
			Parser parser = new Parser();
			parser.setURL(url);// 请求地址
			parser.setEncoding(parser.getEncoding());
			/*
			 * 第二步，对网页解析，用parse(NodeFilter filter)
			 * 一方面，可以用实现NodeFilter接口的子类，如TagNameFilter
			 * 另一方面，可以修改NodeFilter接口方法：boolean accept(Node node)，去构建自己的方法
			 */
			/*-------- 示例1 ---- */
			NodeList nodes = parser.parse(new TagNameFilter(tag)); // 获取的标签
			/*-------- 示例2 ---- */
			// NodeList nodes = parser.parse(new MyNodeFilter(new
			// String[]{"input","font"}));
			for (int i = 0; i < nodes.size(); i++) {// nodes.size()
				TagNode node = (TagNode) nodes.elementAt(i);
				result = node.getText().replace(tag, "");// 获取tag的属性信息， 把开头的标签去掉
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取旧的数据中得值 放入新的数组中）(oldArra与newArray字段必须对上)
	 * @param str 读取html中得数据
	 * @param oldArray html中整理的array
	 * @param newArray 要返回的array
	 * @return
	 */
	public static JSONArray replaceDataByArray(String str, String[] oldArray, String[] newArray) {
		// 返回的jsonArray
		JSONArray jsonArrays = new JSONArray();
		try {
			// 字符串转jsonArray
			JSONArray jsonArray = JSONArray.fromObject(str);
			// 循环遍历jsonArray
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				
				if(oldArray != null && newArray != null && oldArray.length == newArray.length) {
					JSONObject json = new JSONObject();
					// 循环遍历数组
					for (int j = 0; j < oldArray.length; j++) {
						// 把oldArray值赋值到新的json中
						json.put(newArray[j], jsonObject.getString(oldArray[j]));
					}
					jsonArrays.add(json);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return jsonArrays;
	}
	
	/**
	 * 字符串转jsonArray
	 * @param str 
	 * @return
	 */
	public static JSONArray getDataToJson(String str) {
		JSONArray jsonArray = new JSONArray();
		try {
			jsonArray = JSONArray.fromObject(str);
		} catch (Exception e) {
			try {
				JSONObject jsonObject = JSONObject.fromObject(str.replaceAll("\\\\","").replace(", json=(this Map)", ""));
				jsonArray.add(jsonObject);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return jsonArray;
	}

}