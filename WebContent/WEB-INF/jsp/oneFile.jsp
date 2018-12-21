<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>单个文件上传</title>
</head>
<body>
<form action="file/saveOneFile" method="post" enctype="multipart/form-data">  
    选择文件:<input type="file" name="myFile">  <br>
    文件描述:<input type="text" name="description"> <br>
 <input type="submit" value="提交">   
 <span style="color:red;">${fileError}</span>
</form> 
</body>
</html>