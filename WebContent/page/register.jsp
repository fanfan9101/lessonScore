<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="../css/register.css">
<link rel="icon" type="x-ico" href="../img/head.ico">
<title>用户注册</title>
</head>
<body>
	<main>
	<p class="sweet">欢迎来到课堂计分系统</p>
	<form action="${pageContext.servletContext.contextPath}/page/register.do"
		method="post" name="form_register">
		<div class="register">
			<div>
				<label for="input1" id="inputLeft93">I&nbsp;D:</label> <input
					id="input1" type="text" placeholder="ID为学号or教师号" name="id">
			</div>
			<div>
				<label style="font-size: 15px;"> <input type="radio"
					name="type" value="teacher" id="radioStyle1">老师
				</label> <label style="font-size: 15px;"> <input type="radio"
					name="type" value="student" id="radioStyle2">学生
				</label>
			</div>
			<div>
				<label for="input1" class="inputLeft80">姓名:</label> <input
					id="input1" type="text" placeholder="请输入姓名" name="name">
			</div>
			<div>
				<label style="font-size: 15px;"> <input type="radio"
					name="sex" value="male" id="radioStyle1">男
				</label> <label style="font-size: 15px;"> <input type="radio"
					name="sex" value="female" id="radioStyle3">女
				</label>
			</div>
			<div>
				<label for="pwd" class="inputLeft80">密码:</label> <input id="pwd"
					type="password" placeholder="请输入密码" onBlur="checkPwd()" name="pwd">
			</div>
			<div>
				<label for="rePwd" class="inputLeft40">确认密码:</label> <input
					id="rePwd" type="password" onBlur="ConfirmPassword()"
					placeholder="再次确认密码">
			</div>
		</div>
		<div id="conPasswordErr"></div>
		<ul>
			<li class="li-left"><a href="login.jsp">登录</a></li>
			<li class="li-right"><button type="submit"> 注册</button></li>
		</ul>
	</form>
	</main>
	<script src="../js/checkPwd.js" type="text/javascript" charset="utf-8"></script>
</body>
</html>
