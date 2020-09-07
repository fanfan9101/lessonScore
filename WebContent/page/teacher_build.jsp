<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath}/css/teacher_index.css" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/css/common.css">
	<link rel="icon" type="x-ico" href="../img/head.ico">
<title>创建课程</title>
</head>
<body onload="startTime()">
	<header>
		<img src="../img/student.jpg">
		<div class="header_title">课堂计分系统</div>
		<!-- <div>一些句子之类的（保留是否实现效果的时候看是否要保留）</div> -->
		<div class="header_school">
			<span></span>长沙理工大学
		</div>
		<div id="header_date"></div>
	</header>
	<main>
	<section>
		<div class="ulStyle">
			<ul>
				<li class="li_left"><a href="${pageContext.servletContext.contextPath}/page/teacher_index.do"><strong>我的课程</strong></a>
				</li>
				<li class="li_new"><a href="${pageContext.servletContext.contextPath}/page/teacher_build.do"><strong>创建课程</strong></a>
					<span class="bottom_black"></span></li>
			</ul>
		</div>
		<form action="${pageContext.servletContext.contextPath}/page/teaBuildLesson.do" method="post" name="form_teacher_build_course">
			<div class="course">
				<div>
					<label for="input1" class="inputLeft1">院系:</label> <input
						id="input1" type="text" placeholder="请输入缩写院系名称" name="lfaculty">
				</div>
				<div>
					<label for="input1" class="inputLeft2">课程名:</label> <input
						id="input1" type="text" placeholder="请输入课程名" name="lname">
				</div>
				<div>
					<label for="input1" class="inputLeft3">签到占比:</label> <input
						id="input1" type="text" placeholder="请输入比例系数:如30" name="checkScore">
				</div>
				<div>
					<label for="input1" class="inputLeft3">作业占比:</label> <input
						id="input1" type="text" placeholder="请输入比例系数:如30" name="homeScore">
				</div>
				<div>
					<label for="input1" class="inputLeft4">期末考试占比:</label> <input
						id="input1" type="text" placeholder="请输入比例系数:如30" name="examScore">
				</div>
				<div>
					<button type="submit" value="注册" id="Submit">创建</button>
				</div>
			</div>
		</form>
	</section>
	<article>
		 <p> 欢迎  ${uname} 老师 ~</p>
		<div class="icon">
			<img src="../img/home.png" alt="">
			<div class="dialaing">
				<a href="${pageContext.servletContext.contextPath}/page/teacher_index.do"><strong>首页</strong></a>
				<a href="${pageContext.servletContext.contextPath}/page/exit.do"><strong>退出</strong></a>
			</div>
		</div>
	</article>
	</main>
	<script src="${pageContext.servletContext.contextPath}/js/getDate.js"
		type="text/javascript" charset="utf-8"></script>
</body>
</html>
