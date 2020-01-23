
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="library.book.Book"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图书管理系统</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/index.css" />
<!-- <script type="text/javascript" src="./js/jquery-3.4.1.js"></script> -->
<!-- <script type="text/javascript">
	$(document).ready(function() {
		//顶行的偶数列
		$("#top th:even").css("background-color", "#c4f3f9");
		$("#top th:odd").css("background-color", "#bedbfa");
		$("#add_tr th").css("background-color", "#ffe");
	});
</script> -->

</head>
<body>

	<div id="all" align="center">
		<div id="box" align="center">
			<!-- border="1" cellspacing="0" cellpadding="0" -->
			<table border="3" align='center' style="border-collapse: collapse;">
				<caption>图书信息表</caption>
				<tr id="top">
					<th>图书编号</th>
					<th>图书名</th>
					<th>图书数量</th>
					<th>借出的限制时间</th>
					<th>是否存在未借出</th>
					<th>删除图书</th>
				</tr>
				<%
					List<Book> books = (List<Book>) request.getAttribute("books");
					for (Book book : books) {
				%>
				<tr>
					<th><%=book.getbId()%></th>
					<th><%=book.getbName()%></th>
					<th><%=book.getbNum()%></th>
					<th><%=book.getlimitDay()%></th>
					<th><%=book.existReturn()%></th>
					<th><a href="DeleteServlet?bId=<%=book.getbId()%>">选择</a></th>
				</tr>
				<%
					}
				%>

				<form action="AddServlet" method="post">

					<tr id="add_tr">
						<!-- required="required" 提示 -->
						<th><input type="text" required="required"
							placeholder="输入图书编号" name="bId" class="t"
							onkeypress="changeColor()"></input></th>
						<th><input type="text" required="required"
							placeholder="输入图书书名" name="bName" class="t"
							onkeypress="changeColor()"></input></th>
						<th><input type="text" required="required"
							placeholder="输入图书数量" name="bNum" class="t"
							onkeypress="changeColor()"></input></th>
						<th><input type="text" required="required"
							placeholder="输入借出的限制时间" name="limitDay" class="t"
							onkeypress="changeColor()"></input></th>
						<th><input type="text" list="listItem" required="required"
							placeholder="输入是否存在未借出" name="existReturn" class="t"
							onkeypress="changeColor()"></input></th>
						<th><button class="butt" id="butt" type="submit">点击添加</button></th>
					</tr>
				</form>

			</table>
			<div id="selete">
				<form action="SeleteByIdServlet" method="post">
					<input type="text" placeholder="根据图书编号查询" id="seleteId"
						name="seleteId" />
					<button type="submit">查询</button>
				</form>
				<form action="SeleteByNameServlet" method="post">
					<input type="text" placeholder="根据图书名查询" id="seleteName"
						name="seleteName" />
					<button type="submit">查询</button>
				</form>
			</div>
		</div>
	</div>
</body>

</html>