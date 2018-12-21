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
<title>显示单个上传文件</title>
</head>
<body>
	${oneFileDomain.description }<br>
	<!-- fileDomain.getMyfile().getOriginalFilename() -->
	<a href="download?filename=${oneFileDomain.myFile.originalFilename }" rel="external nofollow" >
  ${oneFileDomain.myFile.originalFilename }
</a>
</body>
</html>