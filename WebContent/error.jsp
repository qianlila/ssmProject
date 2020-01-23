<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String error = (String)request.getAttribute("error");
%>
<script type="text/javascript">alert("<%=error%>")</script>
<%
//实现0秒钟后，刷新跳转到SeleteAllInfoServlet
//这个refresh是使用get方式跳转，url中可以携带参数和数据。
response.setHeader("Refresh", "0;url=SeleteAllInfoServlet");
//response.sendRedirect("SeleteAllInfoServlet");
%>
</body>
</html>