<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/css/common.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath}/css/student_index.css" />
	<link rel="icon" type="x-ico" href="../img/head.ico">
<title>学生主页</title>
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
				<li class="li_new"><a
					href="${pageContext.servletContext.contextPath}/page/student_index.do"><strong>我的课程</strong></a>
					<span class="bottom_black"></span></li>
				<li class="li_left"><a
					href="${pageContext.servletContext.contextPath}/page/student_unJoin.do"><strong>课程列表</strong></a>
				</li>
			</ul>
		</div>
		<table>
			<thead>
				<tr>
					<th scope="col"><a href="">课程名称</a></th>
					<th scope="col">主讲老师</th>
				</tr>
			</thead>
			<!-- tbody 用于显示课程信息 -->
			<tbody>
				<c:forEach items="${allTLesson}" var="a" varStatus="loop">
					<tr>
						<td scope="row"><a
							href="${pageContext.servletContext.contextPath}/page/student_check.do?lno=${a.lno}">${a.lname}</a></td>
						<td scope="row">${teaName[loop.count-1].uname }</td>
					</tr>
				</c:forEach>
			</tbody>
			<!-- 分页条先保留 -->
		</table>
		<table id="showMessage">
			<tr>
				<td>第 ${pages} 页 共 ${totalpages} 页</td>
				<td><a
					href="${pageContext.servletContext.contextPath}/page/student_index.do?pages=1">首页</a></td>
				<td><a
					href="${pageContext.servletContext.contextPath}/page/student_index.do?pages=${totalpages}">最后一页</a></td>
				<td><span style="margin-right: 15px;">转到第:</span><input
					type="text" name="pages" size="8"><span>页</span><button type="submit" value="GO">GO</button>
				</td>
			</tr>
		</table>
	</section>
	<article>
		<p>欢迎 ${uname} 同学 ~</p>
		<div class="icon">
			<img src="../img/home.png" alt="">
			<div class="dialaing">
				<a
					href="${pageContext.servletContext.contextPath}/page/teacher_index.do"><strong>首页</strong></a>
				<a href="${pageContext.servletContext.contextPath}/page/exit.do"><strong>退出</strong></a>
			</div>
		</div>
	</article>
	</main>
	<script src="${pageContext.servletContext.contextPath}/js/getDate.js"
		type="text/javascript" charset="utf-8"></script>
</body>
</html>
