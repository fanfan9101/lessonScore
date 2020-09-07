<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/common.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/login.css">
		<link rel="icon" type="x-ico" href="../img/head.ico">
		<title>用户登录</title>
	</head>
	<body>
		<main>
			<p class="sweet">欢迎来到课堂计分系统</p>
			<form action="${pageContext.servletContext.contextPath}/page/login.do" method="post" name="form_register">
				<div class="register">
					<div>
						<label for="input1" id="inputLeft83">I&nbsp;D:</label> <input id="input1" type="text" placeholder="请输入ID" name="id">
					</div>
					<div>
						<label for="pwd" class="inputLeft70">密码:</label> <input id="pwd" type="password" placeholder="请输入密码" onBlur="checkPwd()"
						 name="pwd">
					</div>
				</div>
				<div id="conPasswordErr"></div>
				<div>
					<button type="submit" value="登录" id="Submit">登录</button>
					<a href="register.jsp">注册</a>
					<a href="${pageContext.servletContext.contextPath}/page/changePwd.do?uno=${uno}">忘记密码 </a>
				</div>
			</form>
		</main>
		<script src="${pageContext.servletContext.contextPath}/js/checkPwd.js" type="text/javascript" charset="utf-8"></script>
	</body>
</html>
