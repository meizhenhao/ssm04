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
<title>上传多个文件</title>
</head>
<body>
<form action="file/saveMutipleFile" method="post" enctype="multipart/form-data">  
    选择文件1:<input type="file" name="myFiles">  <br>
    文件描述1:<input type="text" name="description"> <br>
    选择文件2:<input type="file" name="myFiles">  <br>
    文件描述2:<input type="text" name="description"> <br>
    选择文件3:<input type="file" name="myFiles">  <br>
    文件描述3:<input type="text" name="description"> <br>
 <input type="submit" value="提交">  
 <span style="color:red;">${fileError}</span> 
</form> 
</body>
</html>