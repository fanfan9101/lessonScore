<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/css/common.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath}/css/score.css" />
	<link rel="icon" type="x-ico" href="../img/head.ico">
<title>课程详情</title>
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
				<li class="li_new"><a href="score"><strong>学生成绩</strong></a> <span
					class="bottom_black"></span></li>
			</ul>
		</div>
		<table>
			<thead>
				<tr>
					<th scope="col">姓名</th>
					<th scope="col">签到</th>
					<th scope="col">作业</th>
					<th scope="col">期末考试</th>
					<th scope="col">实时</th>
					<th scope="col">最终成绩</th>
					<th scope="col">提交</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${allStudent}" var="a" varStatus="loop">
					<form
						action="${pageContext.servletContext.contextPath}/page/setExamScore.do?sno=${a.sno}&&lno=${lno}"
						method="post" name="form_teacher_build_course">
						<tr>
							<td scope="row">${stuName[loop.count-1].uname}</td>
							<td scope="row">${a.checkScore}</td>
							<td scope="row">${a.homeworkScore}</td>
							<td scope="row"><input id="input1" type="text"
								placeholder="请输入期末成绩" name="examScore" style="width: 130px;"
								name="examScore"></td>
							<td scope="row">${a.examScore }</td>
							<td scope="row">${a.finalScore}</td>
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
					<a
						href="${pageContext.servletContext.contextPath}/page/teacher_index.do"><strong>首页</strong></a>
					<a href="${pageContext.servletContext.contextPath}/page/exit.do"><strong>退出</strong></a>
				</div>
			</div>
		</div>
		<!-- 单纯间隔 -->
		<div class="jiange"></div>
		<!-- 签到  input1其实没什么含义-->
		<!-- 为了使我的checkIn有缩放的效果，适应css属性 transform-->
		<div class="checkIn">
			<form
				action="${pageContext.servletContext.contextPath}/page/buildCheck.do"
				method="post">
				<div>
					<label for="input1" class="fontSize">截至时间</label> <input
						id="input1" type="text" placeholder="请输入签到时长" name="checkDeadline">
				</div>
				<div>
					<button type="submit" value="发起签到" id="Submit">发起</button>
					<a
						href="${pageContext.servletContext.contextPath}/page/teacher_check.do?lno=${lno}">查看结果</a>
				</div>
			</form>
		</div>
		<div class="jiange"></div>
		<form
			action="${pageContext.servletContext.contextPath}/page/buildHomework.do"
			method="post" accept-charset="UTF-8" method="post">
			<div class="homework">
				<div>
					<label for="input1" class="fontSize">截止时间:</label> <input
						id="input1" type="date" placeholder="请输入截止时间" name="homeDeadline">
				</div>
				<div>
					<span class="inform">作业详情:</span>
					<textarea id="input1" cols="23" rows="10" placeholder="请输入作业内容"
						name="neirong" enctype="multipart/form-data"></textarea>
				</div>
				<div style="margin-top: 170px;">
					<button type="submit" value="发布作业" id="Submit">发布</button>
					<a
						href="${pageContext.servletContext.contextPath}/page/teacher_homework.do?lno=${lno}"
						class="aLeft1">查看结果</a>
				</div>
			</div>
		</form>
	</article>
	</main>
	<script src="${pageContext.servletContext.contextPath}/js/getDate.js"
		type="text/javascript" charset="utf-8"></script>
</body>
</html>
