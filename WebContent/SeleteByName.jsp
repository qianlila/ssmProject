<%@page import="library.book.Book"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>根据图书名查询</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/selete.css" />  
</head>
<body>
	<%
	Book b = (Book)request.getAttribute("book");
	%>
	<div id ="selete">
	<form action="UpadatServlet" method="post" onsubmit="back()">
	图书编号<input type="text" value="<%=b.getbId()%>" readonly="readonly" name = "bId"><br>
	图书名<input type="text" value="<%=b.getbName()%>" name = "bName"><br>
	图书数量<input type="text" value="<%=b.getbNum()%>" name = "bNum"><br>
	借出限制时间<input type="text" value="<%=b.getlimitDay()%>" name = "limitDay"><br>
	是否存在未借出<input type="text" value="<%=b.existReturn()%>" name = "existReturn"><br>
	<button type="submit">修改</button><br>
	<button type="reset">重置</button><br>
	<button type="submit" id="back">返回上一页</button>
	</form>
	</div>
</body>
<script type="text/javascript">
if(document.getElementById("back").isclick == 1){
	function back(){
	window.history.go(-1);  
	}
	setTimeout("back()",3000);
	}
</script>
</html>