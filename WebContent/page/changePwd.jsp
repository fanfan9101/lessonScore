<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
		<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/common.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/login.css">
		<link rel="icon" type="x-ico" href="../img/head.ico">
<title>修改密码</title>
</head>
<body>
	<main>
	<p >欢迎来到课堂计分系统</p>
	<form action="${pageContext.servletContext.contextPath}/page/realChangePwd.do"
		method="post" name="form_changePwd">
			<div>
				<label for="pwd" id="inputLeft75">密码:</label> <input
					id="pwd" type="" placeholder="请输入密码" name="pwd" onBlur="checkPwd()" style="width:245px">
			</div>
			<div>
				<label for="rePwd" class="inputLeft58">确认密码:</label> <input id="rePwd"
					type="text" placeholder="确认密码" onBlur="ConfirmPassword()" name="rePwd" style="width:245px">
			</div>
		<div id="conPasswordErr"></div>
		<div>
			<button type="submit" value="修改密码" id="Submitt"> 修改</button>
			<a href="login.jsp">登录</a>
		</div>
		<div class="showUser"></div>
	</form>
	</main>
	<script src="${pageContext.servletContext.contextPath}/js/checkPwd.js"
		type="text/javascript" charset="utf-8"></script>
</body>
</html>
