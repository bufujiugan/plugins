package com.upload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class Upload extends HttpServlet {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
	
	@SuppressWarnings("unused")
    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();  
        javax.servlet.ServletInputStream inputStream = request.getInputStream();
        int position=-1;
        FileOutputStream outputStream = null;
        byte[] buffer = new byte[1024];   
        position=inputStream.readLine(buffer, 0, buffer.length);
        String breakStr = "";
        String objRef = "";
        if(position!=-1){
            breakStr = new String(buffer,0,28);
            objRef = new String(buffer,29,position);
        }
        position = inputStream.readLine(buffer, 0, buffer.length);
        String upFileName = "";
        String result = "";
        if(position!=-1){
            upFileName = new String(buffer,0,position);
			int fileNamePosition = 0;
            if((fileNamePosition=upFileName.indexOf("filename=\""))!=-1){
                String path = request.getSession().getServletContext().getRealPath("/");
                result = Calendar.getInstance().getTimeInMillis()+ ".jpg";
                outputStream=new FileOutputStream(path + Calendar.getInstance().getTimeInMillis()+ ".jpg" /*fileType*/);
            }
        }
        position = inputStream.readLine(buffer, 0, buffer.length);
        String type = "";
        if(position != -1){
            type = new String(buffer, 0, position);
        }
        String isBreakStr="";
        inputStream.readLine(buffer, 0, buffer.length);
        while((position=inputStream.readLine(buffer, 0, buffer.length))!=-1){
            isBreakStr = new String(buffer, 0, position);
            if (isBreakStr.length() > 28
                    && isBreakStr.substring(0, 28).equals(breakStr)) {
                break;
            }
            outputStream.write(buffer, 0, position);
        }
        out.print(result);
        outputStream.flush();
        inputStream.close();
        outputStream.close();
        out.close();
    }
	
}