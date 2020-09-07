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
<title>查看作业</title>
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
				<li class="li_new"><a href="${pageContext.servletContext.contextPath}/page/teacher_homework.do?lno=${lno}"><strong>作业详情</strong></a> 
				<span class="bottom_black"></span></li>
			<li class="li_new"><a href="${pageContext.servletContext.contextPath}/page/teacher_check.do?lno=${lno}""><strong>签到详情</strong></a></li>
			</ul>
		</div>
		<table>
			<thead>
				<tr>
					<th scope="col">姓名</th>
					<th scope="col">截止时间</th>
					<th scope="col">作业详情</th>
					<th scope="col">分数</th>
					<th scope="col">实时</th>
					<th scope="col">状态</th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${allHome}" var="a" varStatus="loop">
					<form
						action="${pageContext.servletContext.contextPath}/page/setHomeScore.do?sno=${a.uno}&&lno=${a.lno}"
						method="post" accept-charset="UTF-8" method="post">
						<tr>
							<td scope="row">${stuName[loop.count-1].uname}</td>
							<td scope="row">${a.deadline }</td>
							<td scope="row"><a
								href="${pageContext.servletContext.contextPath}/page/download.do?uno=${a.uno}&&homew_num=${a.number}">下载</a></td>
							<td scope="row"><input type="text" placeholder="请输入作业分数"
								name="homeScore"/></td>
							<td scope="row">${a.homeworkScore}</td>
							<td scope="row">${a.form}</td>
							<td scope="row"><button type="submit">确认</button></td>
						</tr>
					</form>
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
		<div class="homeworkNumber">
			<table>
				<thead>
					<tr>
						<th>作业</th>
						<th>详情</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${number}" var="n">
						<tr>
							<td>第 ${n} 次作业</td>
							<td><a
								href="${pageContext.servletContext.contextPath}/page/teacher_homework.do?number=${n}">查看</a></td>
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
