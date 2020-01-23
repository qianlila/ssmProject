<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
 <meta charset="UTF-8">  
                <title>图书管理系统</title>  
                <link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/login.css" />  
            </head>  
            <body>  
                <div id="all">
                <div id="login">  
                    <h1>图书管理员登陆</h1>
                    <form action="SeleteAllInfoServlet" method="post" onsubmit="return cheked();">  
                        <input type="text" required="required" placeholder="用户名" id = "user" name="user"></input>  
                        <input type="password" required="required" placeholder="密码" id = "pwd" name="pwd"></input>  
                        <button class="butt" type="submit">登录</button>  
                    </form> 
                </div>
            </div>
            
            </body>
            <script type="text/javascript">
            function cheked(){
            	 var user = document.getElementById('user').value;
                 var pwd = document.getElementById('pwd').value;
                 //去掉左边空格
                 user = user.replace(/(\s*$)/g,"");
                 pwd = pwd.replace(/(\s*$)/g,"");
                 //去掉右边空格
                 user = user.replace(/(^\s*)|(\s*$)/g, "");
                 pwd = pwd.replace(/(^\s*)|(\s*$)/g, "");
             if(user==""||pwd==""||user==null||pwd==null){
                 alert('用户名或密码不为空!开头含空格及结尾含空格无效！！');
                 return false;
             }
             else if(user.length<2||user.length>6){
                 alert('用户名长度不低于2个字符，不高于6个字符!');
                 return false;
                 //window.history.back(-1);
             }
             else if(pwd.length<6||pwd.length>10){
                 alert('密码长度不低于6个字符，不高于10个字符!');
                 return false;
             }else{
                 return true;
             }
            }
            </script>
</html>