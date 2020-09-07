<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath}/css/score.css" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/css/common.css">
	<link rel="icon" type="x-ico" href="../img/head.ico">
<title>查看签到</title>
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
				<li class="li_new"><a href="${pageContext.servletContext.contextPath}/page/teacher_check.do?lno=${lno}""><strong>签到情况</strong></a> <span
					class="bottom_black"></span></li>
					<li class="li_new"><a href="${pageContext.servletContext.contextPath}/page/teacher_homework.do?lno=${lno}"><strong>查看作业</strong></a></li>
			</ul>
		</div>
		<table>
			<thead>
				<tr>
					<th scope="col">姓名</th>
					<th scope="col">状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${allCheck}" var="a" varStatus="loop">
					<tr>
						<td scope="row">${stuName[loop.count-1].uname}</td>
						<td scope="row">${a.inform }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<table id="showMessage">
			<tr>
				<td>第 ${pages} 页 共 ${totalpages} 页</td>
				<td><a href="teacher_index.do?pages=1">首页</a></td>
				<td><a href="teacher_index.do?pages=${totalpages}">最后一页</a></td>
				<td><span style="margin-right: 15px;">转到第:</span><input
					type="text" name="pages" size="8"><span>页</span>
					<button type="submit" value="GO">GO</button></td>
			</tr>
		</table>
	</section>
	<article>
		<div class="welcome">
			<p>欢迎 ${uname} 老师 ~</p>
			<div class="icon">
				<img src="../img/home.png" alt="">
				<div class="dialaing">
					<a href="${pageContext.servletContext.contextPath}/page/teacher_index.do"><strong>首页</strong></a>
					<a href="${pageContext.servletContext.contextPath}/page/score.do?u=${u}&&lno=${lno}"><strong>上一页</strong></a>
					<a href="${pageContext.servletContext.contextPath}/page/exit.do"><strong>退出</strong></a>
				</div>
			</div>
		</div>
		<!-- 单纯间隔 -->
		<div class="jiange"></div>
		<!-- 显示第几次签到信息 -->
		<div class="checkInNumber">
			<table>
				<thead>
					<tr>
						<th>签到</th>
						<th>详情</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${number}" var="n">
						<tr>
							<td>第 ${n} 次签到</td>
							<td><a
								href="${pageContext.servletContext.contextPath}/page/teacher_check.do?number=${n}">查看</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</article>
	</main>
	<script src="${pageContext.servletContext.contextPath}/js/getDate.js"
		type="text/javascript" charset="utf-8"></script>
</body>
</html>
