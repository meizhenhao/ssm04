<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示多个上传的文件</title>
</head>
<body>
	<table>
		<tr>
			<td>详情</td><td>文件名</td>
		</tr>
		<!-- 同时取两个数组的元素 -->
		<c:forEach items="${mutipleFileDomain.description}" var="description" varStatus="loop">
			<tr>
				
				<td>${description}</td>
				<td>
					<a href="download?filename=${mutipleFileDomain.myFiles[loop.count-1].originalFilename}" rel="external nofollow" >
	  				${mutipleFileDomain.myFiles[loop.count-1].originalFilename}</a>
  				</td>
			</tr>
		</c:forEach>
		<!-- fileDomain.getMyfile().getOriginalFilename() -->
		
	</table>
	
</body>
</html>