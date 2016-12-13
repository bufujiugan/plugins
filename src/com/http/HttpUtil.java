package com.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 发送http请求工具类
 * main测试方法
 * httpRequest 发送请求类
 * doPost  接收数据方法
 * @author 于志强
 * 创建时间：2016-11-14 下午1:55:36   
 * 修改人：于志强  
 * 修改时间：2016-11-14 下午1:55:36
 */

public class HttpUtil {
	
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		String jsonArray = "[" +
							"{\"name\":\"1\",\"value\":\"0\"}," +
							"{\"name\":\"6101\",\"value\":\"北京市\"}," +
							"{\"name\":\"6102\",\"value\":\"天津市\"}," +
							"{\"name\":\"6103\",\"value\":\"上海市\"}," +
							"{\"name\":\"6104\",\"value\":\"重庆市\"}," +
							"{\"name\":\"6105\",\"value\":\"渭南市\"}" +
							"]";
		httpRequest("http://192.168.12.123:8080/IT/test.do", jsonArray);
	}
	
	
	/**
	 * 接收http请求数据(创建个servlet  把doPost方法改成下面方法即可)
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	/*public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String result = "";
		java.io.BufferedReader reader = null;
		try {
			reader = request.getReader();// 获得字符流
			StringBuffer content = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line + "\r\n");
			}
			result = content.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				reader = null;
			} catch (Exception e) {

			}
		}
		System.out.println(result);
	}*/

	/**
	 * 访问http请求
	 * @param pathUrl
	 * @param param
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static String httpRequest(String pathUrl,String param) throws UnsupportedEncodingException, IOException {
		StringBuffer buffer = null;
		try {
			// 建立连接   
			URL url = new URL(pathUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod("POST");

			// 当outputStr不为null时向输出流写数据
			OutputStream outputStream = conn.getOutputStream();
			// 注意编码格式
			outputStream.write(param.getBytes("UTF-8"));
			outputStream.close();

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
}
