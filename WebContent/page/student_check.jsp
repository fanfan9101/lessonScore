<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath}/css/student_homework.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath}/css/common.css" />
	<link rel="icon" type="x-ico" href="../img/head.ico">
<title>学生签到</title>
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
					href="${pageContext.servletContext.contextPath}/page/student_check.do"><strong>签到</strong></a>
					<span class="bottom_black"></span></li>
				<li class="li_left"><a
					href="${pageContext.servletContext.contextPath}/page/student_homework.do"><strong>作业</strong></a>
				</li>
			</ul>
		</div>
		<table>
			<thead>
				<tr>
					<th scope="col">标题</th>
					<th scope="col">截止时间</th>
					<th scope="col">签到</th>
					<th scope="col">状态</th>
				</tr>
			</thead>
			<!-- tbody 用于显示课程信息 -->
			<tbody>
				<c:forEach items="${allCheck}" var="a">
					<tr>
						<td scope="row">第 ${a.number} 次</td>
						<td scope="row">${a.deadline }</td>
						<td scope="row"><a
							href="${pageContext.servletContext.contextPath}/page/myCheck.do?uno=${u.uno}&&lno=${a.lno}&&number=${a.number}">
								<button type="submit">签到</button>
						</a></td>
						<td scope="row">${a.inform }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<table id="showMessage">
			<tr>
				<td>第 ${pages} 页 共 ${totalpages} 页</td>
				<td><a
					href="${pageContext.servletContext.contextPath}/page/student_check.do?pages=1">首页</a></td>
				<td><a
					href="${pageContext.servletContext.contextPath}/page/student_check.do?pages=${totalpages}">最后一页</a></td>
				<td><span style="margin-right: 15px;">转到第:</span><input
					type="text" name="pages" size="8"><span>页</span>
					<button type="submit" value="GO">GO</button>
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
					href="${pageContext.servletContext.contextPath}/page/student_index.do"><strong>首页</strong></a>
				<a href="${pageContext.servletContext.contextPath}/page/exit.do"><strong>退出</strong></a>
			</div>
		</div>
		<div class="left_text5" style="display: none;">
			<table>
				<tr>
					<td id="checkScore">${s.checkScore}</td>
					<td id="homeworkScore">${s.homeworkScore}</td>
					<td id="examScore">${s.examScore}</td>
					<td id="finalScore">${s.finalScore}</td>
				</tr>
			</table>
		</div>
		<div class="canvasStyle">
			<canvas id="a_canvas"></canvas>
		</div>
	</article>
	</main>
	<footer> </footer>
	<script src="${pageContext.servletContext.contextPath}/js/canvas.js"
		type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.servletContext.contextPath}/js/getDate.js"
		type="text/javascript" charset="utf-8"></script>
</body>
</html>
