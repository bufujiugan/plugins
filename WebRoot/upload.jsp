<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'upload.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
	<script src="js/jquery-1.7.1.js" type="text/javascript"></script>
    <script src="js/ajaxfileupload.js"></script>
    <script>
    /* 上传 */
    function uptoload(){
	    var url = 'upload.do';
		$.ajaxFileUpload ({
			url : url,
			fileElementId :'file1',
			dataType : 'text',
			success : function (data){
				data = data.replace('<pre style="word-wrap: break-word; white-space: pre-wrap;">','').replace('</pre>','');
				$("#img1").attr("src", data);
				$("#_img").val(data);
			},
			error: function (){
				alert('error');
			}
		});
	}
    </script>
</head>

<body>
	<form action="sub.do" method="post">
	    <p>
	    	<input type="file" id="file1" name="file" />
	    </p>
	    <input onclick="uptoload()" type="button" value="上传" />
	    <p>
	    	<img id="img1" src="" /><!-- 图片回显 -->
	    </p>
	    <p><input type="text" name="_text" /></p><!-- 乱写的输入框 -->
	    <p><input type="text" name="_img" id="_img" /></p><!-- 图片地址 -->
	    <input type="submit" value="提交表单" /><!-- 提交按钮 -->
    </form>
</body>
</html>
