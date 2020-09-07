package com.cl.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.cl.dao.check_cDao;
import com.cl.dao.homeworkDao;
import com.cl.dao.s_lessonDao;
import com.cl.dao.t_lessonDao;
import com.cl.dao.userDao;
import com.cl.model.*;
import com.cl.util.MD5Utils;

public class WebServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");// 设置编码
		// 获取用户地址栏的地址
		String url = request.getServletPath();// /b.do
		System.out.println("本次请求的地址：" + url);
		// 可以用来保存数据和取数据的对象
		HttpSession session = request.getSession();

		if ("/page/register.do".equals(url)) {
			// 都是获取他们的name属性
			String uno = (String) request.getParameter("id");
			String realUser = (String) request.getParameter("type");
			String uname = (String) request.getParameter("name");
			String realSex = (String) request.getParameter("sex");
			String upwd = request.getParameter("pwd");
			String user;
			if ("student".equals(realUser)) {
				user = "学生";
			} else {
				user = "老师";
			}

			String usex;
			if ("male".equals(realSex)) {
				usex = "男";
			} else {
				usex = "女";
			}
			// 这是在控制窗口输出
			System.out.println(
					uno + " " + user + " " + uname + " " + usex + " " + " " + MD5Utils.MD5Encode(upwd, "utf-8"));
			// 建立一个用户对象 就是之前设立的实体类传入数据
			user u = new user(uno, user, uname, usex, upwd);
			userDao.register(u);
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
//		登录
		else if ("/page/login.do".equals(url)) {
			String uno = (String) request.getParameter("id");
			String pwd = (String) request.getParameter("pwd");
			user u = userDao.findUser(uno, MD5Utils.MD5Encode(pwd, "utf-8"));
			if (u == null) {
				System.out.println("false");
				request.setAttribute("loginInform", "账号或密码输入错误");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			} else {
				request.setAttribute("loginInform", "登录成功");
				session.setAttribute("uno", uno);
				session.setAttribute("uname", u.getUname());
				System.out.println(" uno = " + uno + " pwd =" + MD5Utils.MD5Encode(pwd, "utf-8"));
				if ("老师".equals(u.getIsTeacher())) {
					System.out.println("老师");
					session.setAttribute("u", u);
					request.getRequestDispatcher("/page/teacher_index.do").forward(request, response);
				} else {
					System.out.println("学生");
					session.setAttribute("u", u);
					request.getRequestDispatcher("/page/student_index.do").forward(request, response);
				}
			}
		} else if ("/page/changePwd.do".equals(url)) {
			String uno = request.getParameter("uno");
			user u = userDao.findUserUno(uno);
			session.setAttribute("u", u);
			request.getRequestDispatcher("changePwd.jsp").forward(request, response);
		} else if ("/page/realChangePwd.do".equals(url)) {
			user u = (user) session.getAttribute("u");
			String pwd = request.getParameter("pwd");
			userDao.changePwd(u.getUno(), pwd);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else if ("/page/student_index.do".equals(url)) {
			user u = (user) session.getAttribute("u");
			session.setAttribute("u", u);
			int pages = 0; // 待显示页面
			int count = 0; // 总条数
			int totalpages = 0; // 总页数
			int limit = 10; // 每页显示记录条数
			// 计算记录总数的第二种办法：使用mysql的聚集函数count(*)
			count = s_lessonDao.getSNum(u.getUno());
			// 由记录总数除以每页记录数得出总页数
			totalpages = (int) Math.ceil(count / (limit * 1.0));
			// 获取跳页时传进来的当前页面参数
			String strPage = request.getParameter("pages");
			// 判断当前页面参数的合法性并处理非法页号（为空则显示第一页，小于0则显示第一页，大于总页数则显示最后一页）
			if (strPage == null) {
				pages = 1;
			} else {
				try {
					pages = java.lang.Integer.parseInt(strPage);
				} catch (Exception e) {
					pages = 1;
				}

				if (pages < 1) {
					pages = 1;
				}

				if (pages > totalpages) {
					pages = totalpages;
				}
			}
			System.out.println(" pages = " + pages + " totalPages = " + totalpages);
			// 通过uno 找到s_lesson中所加入的课程得到lno
			ArrayList<s_lesson> allSLesson = s_lessonDao.findJoinLesson(u.getUno(), pages, limit);
			// 在通过 lno找到对课程的名字 再透过uno 找到老师的名字\
			ArrayList<Integer> Lno = new ArrayList<Integer>();
			for (int i = 0; i < allSLesson.size(); i++) {
				Lno.add(allSLesson.get(i).getLno());
			}
			ArrayList<t_lesson> allTLesson = t_lessonDao.findLName(Lno);
			ArrayList<String> teaUno = new ArrayList<String>();
			for (int i = 0; i < allTLesson.size(); i++) {
				teaUno.add(allTLesson.get(i).getUno());
			}
			ArrayList<user> teaName = userDao.findUserName(teaUno);
			request.setAttribute("teaName", teaName);
			request.setAttribute("allTLesson", allTLesson);
			request.setAttribute("pages", pages);
			request.setAttribute("uname", u.getUname());
			request.setAttribute("totalpages", totalpages);
			request.getRequestDispatcher("student_index.jsp").forward(request, response);
		} else if ("/page/student_unJoin.do".contentEquals(url)) {
			user u = (user) session.getAttribute("u");
			session.setAttribute("u", u);
			int pages = 0; // 待显示页面
			int count = 0; // 总条数
			int totalpages = 0; // 总页数
			int limit = 10; // 每页显示记录条数
			count = s_lessonDao.getSNum(u.getUno());
			totalpages = (int) Math.ceil(count / (limit * 1.0));
			String strPage = request.getParameter("pages");
			if (strPage == null) {
				pages = 1;
			} else {
				try {
					pages = java.lang.Integer.parseInt(strPage);
				} catch (Exception e) {
					pages = 1;
				}

				if (pages < 1) {
					pages = 1;
				}

				if (pages > totalpages) {
					pages = totalpages;
				}
			}
			ArrayList<t_lesson> allTLesson = t_lessonDao.getAllTLesson();
			ArrayList<String> teaUno = new ArrayList<String>();
			for (int i = 0; i < allTLesson.size(); i++) {
				teaUno.add(allTLesson.get(i).getUno());
			}
			ArrayList<user> teaName = userDao.findUserName(teaUno);
			request.setAttribute("teaName", teaName);
			request.setAttribute("allTLesson", allTLesson);
			request.setAttribute("uname", u.getUname());
			request.setAttribute("pages", pages);
			request.setAttribute("totalpages", totalpages);
			request.getRequestDispatcher("student_unJoin.jsp").forward(request, response);
		} else if ("/page/joinLesson.do".equals(url)) {
			user u = (user) session.getAttribute("u");
			session.setAttribute("u", u);
			String lno = request.getParameter("lno");
			String uno = request.getParameter("uno");
			// 老师学生人数+1
			System.out.println("lno =" + lno + " un11o " + uno);
			boolean flag = s_lessonDao.buildSLesson(uno, java.lang.Integer.parseInt(lno));
			if (flag == true)
				t_lessonDao.setStuNum(java.lang.Integer.parseInt(lno));
			request.getRequestDispatcher("/page/student_unJoin.do").forward(request, response);
		} else if ("/page/student_check.do".equals(url)) {
			user u = (user) session.getAttribute("u");
			session.setAttribute("u", u);
			String lno = (String) request.getParameter("lno");
			// 如果已经传了lno，就刷新，没有则后台获取
			if (lno != null) {
				session.setAttribute("lno", lno);
			} else {
				lno = (String) session.getAttribute("lno");
			}

			int pages = 0; // 待显示页面
			int count = 0; // 总条数
			int totalpages = 0; // 总页数
			int limit = 10; // 每页显示记录条数
			count = t_lessonDao.getCheckNum(java.lang.Integer.parseInt(lno));
			totalpages = (int) Math.ceil(count / (limit * 1.0));
			String strPage = request.getParameter("pages");
			if (strPage == null) {
				pages = 1;
			} else {
				try {
					pages = java.lang.Integer.parseInt(strPage);
				} catch (Exception e) {
					pages = 1;
				}

				if (pages < 1) {
					pages = 1;
				}

				if (pages > totalpages) {
					pages = totalpages;
				}
			}

			ArrayList<check_c> allCheck = check_cDao.findAllCheck(u.getUno(), java.lang.Integer.parseInt(lno), pages,
					limit);
			for (check_c c : allCheck) {
				if (c.isChecked() == false
						&& new java.sql.Timestamp(System.currentTimeMillis()).compareTo(c.getDeadline()) > 0) {

					c.setInform("已截至");
				} else if (c.isChecked() == true)
					c.setInform("已签到");
			}
			// 更新学生签到成绩//更新学生最终成绩
			// 更新学生签到成绩
			int checkNum = t_lessonDao.getCheckNum(Integer.parseInt((String) lno));
			int check = check_cDao.getRealCheck(u.getUno(), java.lang.Integer.parseInt(lno));
			double checkScore = ((double) check / checkNum) * 100;
			s_lessonDao.updateCheckScore(u.getUno(), java.lang.Integer.parseInt(lno), checkScore);
			// 更新学生最终成绩
			s_lesson s = s_lessonDao.getSLesson(u.getUno(), java.lang.Integer.parseInt(lno));
			t_lesson t = t_lessonDao.getTLesson(Integer.parseInt((String) lno));
			s_lessonDao.updateFinalScore(s, t);
			s_lesson s1 = s_lessonDao.getSLesson(u.getUno(), java.lang.Integer.parseInt(lno));
			request.setAttribute("s", s1);
			request.setAttribute("allCheck", allCheck);
			request.setAttribute("pages", pages);
			request.setAttribute("totalpages", totalpages);
			session.setAttribute("uname", u.getUname());
			request.getRequestDispatcher("/page/student_check.jsp").forward(request, response);
		} else if ("/page/myCheck.do".equals(url)) {
			String cno = (String) session.getAttribute("uno");
			String lno = (String) request.getParameter("lno");
			String num = request.getParameter("number");
			// 如果已经传了lno，就刷新，没有则后台获取
			if (lno != null) {
				session.setAttribute("lno", lno);
			} else {
				lno = (String) session.getAttribute("lno");
			}
			System.out.println("cno " + cno + "  lno = " + lno + " num =" + num);
			if (check_cDao.findC(cno, java.lang.Integer.parseInt(lno), java.lang.Integer.parseInt(num)) == false) {
				check_cDao.setCheck(cno, java.lang.Integer.parseInt(lno), java.lang.Integer.parseInt(num));
			}
			response.sendRedirect(request.getContextPath() + "/page/student_check.do");
		} else if ("/page/student_homework.do".equals(url)) {
			user u = (user) session.getAttribute("u");
			session.setAttribute("u", u);
			String lno = (String) request.getParameter("lno");
			// 如果已经传了lno，就刷新，没有则后台获取
			if (lno != null) {
				session.setAttribute("lno", lno);
			} else {
				lno = (String) session.getAttribute("lno");
			}
			int pages = 0; // 待显示页面
			int count = 0; // 总条数
			int totalpages = 0; // 总页数
			int limit = 10; // 每页显示记录条数
			count = t_lessonDao.getHomeNum(java.lang.Integer.parseInt(lno));// ok
			System.out.println("t home = " + count);
			totalpages = (int) Math.ceil(count / (limit * 1.0));
			String strPage = request.getParameter("pages");
			if (strPage == null) {
				pages = 1;
			} else {
				try {
					pages = java.lang.Integer.parseInt(strPage);
				} catch (Exception e) {
					pages = 1;
				}

				if (pages < 1) {
					pages = 1;
				}

				if (pages > totalpages) {
					pages = totalpages;
				}
			}
//			System.out.println("现在时间： " + new java.sql.Timestamp(System.currentTimeMillis()));
//			System.out
//					.println(" pages = " + pages + " totalPages = " + totalpages + "count =" + count + " lno =" + lno);
			ArrayList<homework> allHomework = homeworkDao.findAllHomework(u.getUno(), java.lang.Integer.parseInt(lno),
					pages, limit);
			for (homework c : allHomework) {
				if (c.isFinished() == false
						&& new java.sql.Timestamp(System.currentTimeMillis()).compareTo(c.getDeadline()) > 0) {
					c.setForm("已截至");
				} else if (c.isFinished() == true)
					c.setForm("已提交");
			}
			// 更新学生作业成绩//更新学生最终成绩 无需再次更行学生作业成绩 因为老师还没改呢 你怎么更新、
			s_lesson s = s_lessonDao.getSLesson(u.getUno(), java.lang.Integer.parseInt(lno));
			request.setAttribute("s", s);
			request.setAttribute("allHomework", allHomework);
			request.setAttribute("pages", pages);
			request.setAttribute("totalpages", totalpages);
			session.setAttribute("uname", u.getUname());
			session.setAttribute("uno", u.getUno());
			request.getRequestDispatcher("/page/student_homework.jsp").forward(request, response);
		} else if ("/page/teacher_index.do".equals(url)) {
			user u = (user) session.getAttribute("u");
			session.setAttribute("u", u);
			int pages = 0; // 待显示页面
			int count = 0; // 总条数
			int totalpages = 0; // 总页数
			int limit = 10; // 每页显示记录条数
			count = t_lessonDao.getTNum(u.getUno());
			totalpages = (int) Math.ceil(count / (limit * 1.0));
			System.out.println("count = " + count);
			String strPage = request.getParameter("pages");
			if (strPage == null) {
				pages = 1;
			} else {
				try {
					pages = java.lang.Integer.parseInt(strPage);
				} catch (Exception e) {
					pages = 1;
				}
				if (pages < 1) {
					pages = 1;
				}
				if (pages > totalpages) {
					pages = totalpages;
				}
				System.out.println("pages11 = " + pages + " limit22 = " + limit + "totalPages = " + totalpages);
			}
			ArrayList<t_lesson> teaLesson = t_lessonDao.findTLessons(u.getUno(), pages, limit);
			request.setAttribute("teaLesson", teaLesson);
			request.setAttribute("pages", pages);
			request.setAttribute("uno", u.getUno());
			request.setAttribute("totalpages", totalpages);
			session.setAttribute("uname", u.getUname());
			request.getRequestDispatcher("teacher_index.jsp").forward(request, response);
		} else if ("/page/teacher_build.do".equals(url)) {
			user u = (user) session.getAttribute("u");
			session.setAttribute("u", u);
			request.getRequestDispatcher("teacher_build.jsp").forward(request, response);
		}
//		老师创建课程
		else if ("/page/teaBuildLesson.do".equals(url)) {
			user u = (user) session.getAttribute("u");
			session.setAttribute("u", u);
			System.out.println("uname = " + u.getUname());
			String lfaculty = request.getParameter("lfaculty");
			String lname = request.getParameter("lname");
			double checkScore = java.lang.Double.parseDouble(request.getParameter("checkScore"));
			double homeScore = java.lang.Double.parseDouble(request.getParameter("homeScore"));
			double examScore = java.lang.Double.parseDouble(request.getParameter("examScore"));
			System.out.println(lfaculty + " " + lname + " " + checkScore + " " + homeScore + " " + examScore);
			t_lessonDao.buildTLesson(lname, lfaculty, u.getUno(), checkScore, homeScore, examScore);
			request.getRequestDispatcher("/page/teacher_build.do").forward(request, response);
		}
//		老师进入相应课程详情页
		else if ("/page/score.do".equals(url)) {
			user u = (user) session.getAttribute("u");
			if (u == null) {
				u = (user) request.getAttribute("u");
				System.out.println("nini" + u.getUname());
			}
			Object lno = request.getParameter("lno");
			if (lno != null) {
				session.setAttribute("lno", lno);
			} else {
				lno = (String) session.getAttribute("lno");
			}
			session.setAttribute("u", u);
			session.setAttribute("lno", lno);
			int pages = 0; // 待显示页面
			int count = 0; // 总条数
			int totalpages = 0; // 总页数
			int limit = 10; // 每页显示记录条数
			count = t_lessonDao.getTNum(u.getUno());
			totalpages = (int) Math.ceil(count / (limit * 1.0));
			String strPage = request.getParameter("pages");
			if (strPage == null) {
				pages = 1;
			} else {
				try {
					pages = java.lang.Integer.parseInt(strPage);
				} catch (Exception e) {
					pages = 1;
				}

				if (pages < 1) {
					pages = 1;
				}

				if (pages > totalpages) {
					pages = totalpages;
				}
			}
			ArrayList<s_lesson> allStudent = s_lessonDao.findSLesson(Integer.parseInt((String) lno), pages, limit);
			// 对他们的签到、作业成绩、期末进行更新
			t_lesson t = t_lessonDao.getTLesson(Integer.parseInt((String) lno));
			int checkNum = t_lessonDao.getCheckNum(Integer.parseInt((String) lno));
			int homeNum = t_lessonDao.getHomeNum(Integer.parseInt((String) lno));
			// System.out.println("checkScore = " + checkNum + " homeNum = " + homeNum + "
			// uno " + u.getUno());
			for (int i = 0; i < allStudent.size(); i++) {
				// 签到
				int check = check_cDao.getRealCheck(allStudent.get(i).getSno(), allStudent.get(i).getLno());
				double checkScore = ((double) check / checkNum) * 100;
				// System.out.println("checkScore = " + checkScore + " homeNum = " + homeNum);
				s_lessonDao.updateCheckScore(allStudent.get(i).getSno(), allStudent.get(i).getLno(), checkScore);
				// 作业
				double totalScore = homeworkDao.getHomeScore(allStudent.get(i).getSno(), allStudent.get(i).getLno());
				double homeScore = totalScore / homeNum;
				s_lessonDao.updateHomeScore(allStudent.get(i).getSno(), allStudent.get(i).getLno(), homeScore);
				s_lesson s = s_lessonDao.getSLesson(allStudent.get(i).getSno(), Integer.parseInt((String) lno));
				s_lessonDao.updateFinalScore(s, t);
			}
			ArrayList<String> Uno = new ArrayList<String>();// 根据学号找到学生名字
			for (s_lesson s : allStudent) {
				Uno.add(s.getSno());
			}
			ArrayList<user> stuName = userDao.findUserName(Uno);
			request.setAttribute("allStudent", allStudent);
			request.setAttribute("stuName", stuName);
			request.setAttribute("pages", pages);
			request.setAttribute("totalpages", totalpages);
			session.setAttribute("uname", u.getUname());
			request.getRequestDispatcher("score.jsp").forward(request, response);

		}
//		老师设置学生成绩
		else if ("/page/setExamScore.do".equals(url)) {
			user u = (user) session.getAttribute("u");
			String lno = (String) request.getParameter("lno");
			if (lno != null) {
				session.setAttribute("lno", lno);
			}
			lno = (String) session.getAttribute("lno");
			session.setAttribute("u", u);
			session.setAttribute("lno", lno);
			String sno = request.getParameter("sno");
			String examScore = request.getParameter("examScore");
			s_lessonDao.setFinalExam(sno, Integer.parseInt((String) lno), java.lang.Double.parseDouble(examScore));
			// 更新学生的最终成绩
			t_lesson t = t_lessonDao.getTLesson(java.lang.Integer.parseInt(lno));
			s_lesson s = s_lessonDao.getSLesson(sno, java.lang.Integer.parseInt(lno));
			System.out.println("s =" + s.getExamScore() + "  t= " + t.getExamScore());
			s_lessonDao.updateFinalScore(s, t);
			response.sendRedirect(request.getContextPath() + "/page/score.do");
		}
//		发布签到
		else if ("/page/buildCheck.do".equals(url)) {
			user u = (user) session.getAttribute("u");
			String lno = (String) request.getParameter("lno");
			if (lno != null) {
				session.setAttribute("lno", lno);
			}
			lno = (String) session.getAttribute("lno");
			session.setAttribute("u", u);
			session.setAttribute("lno", lno);
			Object t1 = request.getParameter("checkDeadline");
			int time1 = 0;
			if (t1 != null) {
				time1 = Integer.parseInt((String) t1);
			}
			// 老师签到数+1set 根据lno从s_lesson找出所有的学生的uno 然后根据sno lno 创建签到表
			t_lessonDao.setCheckNum(u.getUno(), java.lang.Integer.parseInt(lno));
			int checkNum = t_lessonDao.getCheckNum(java.lang.Integer.parseInt(lno));
			ArrayList<s_lesson> stu = s_lessonDao.findUno(Integer.parseInt((String) lno));
			check_cDao.buildCheck(stu, checkNum, java.lang.Integer.parseInt(lno), time1);
			request.getRequestDispatcher("/page/score.do").forward(request, response);
		}
//		发布作业
		else if ("/page/buildHomework.do".equals(url)) {
			user u = (user) session.getAttribute("u");
			String lno = (String) request.getParameter("lno");
			if (lno != null) {
				session.setAttribute("lno", lno);
			}
			lno = (String) session.getAttribute("lno");
			session.setAttribute("u", u);
			session.setAttribute("lno", lno);
			Object t2 = request.getParameter("homeDeadline");
			java.util.Date date = new Date();
			if (t2 != null) {
				SimpleDateFormat dateReal = new SimpleDateFormat("yyyy-MM-dd");
				try {
					date = new Date(dateReal.parse((String) t2).getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String inform = request.getParameter("neirong");
			System.out.println(" date = " + date + " inform = " + inform);
			t_lessonDao.setHomeNum(u.getUno(), java.lang.Integer.parseInt(lno));
			int homeNum = t_lessonDao.getHomeNum(java.lang.Integer.parseInt(lno));
			ArrayList<s_lesson> stu = s_lessonDao.findUno(Integer.parseInt((String) lno));
			homeworkDao.buildHomework(stu, homeNum, java.lang.Integer.parseInt(lno), date, inform);
			response.sendRedirect(request.getContextPath() + "/page/score.do");
		} else if ("/page/teacher_homework.do".equals(url)) {
			user u = (user) session.getAttribute("u");
			String lno = (String) request.getParameter("lno");
			if (lno != null) {
				session.setAttribute("lno", lno);
			} else {
				lno = (String) session.getAttribute("lno");
			}
			session.setAttribute("u", u);
			session.setAttribute("lno", lno);
			int homeNum = t_lessonDao.getHomeNum(java.lang.Integer.parseInt(lno));
			Object num = request.getParameter("number");
			int number = 0;
			if (num == null)
				number = homeNum;
			else
				number = java.lang.Integer.parseInt((String) num);
			int pages = 0; // 待显示页面
			int count = 0; // 总条数
			int totalpages = 0; // 总页数
			int limit = 10; // 每页显示记录条数
			count = t_lessonDao.getTNum(u.getUno());
			totalpages = (int) Math.ceil(count / (limit * 1.0));
			String strPage = request.getParameter("pages");
			if (strPage == null) {
				pages = 1;
			} else {
				try {
					pages = java.lang.Integer.parseInt(strPage);
				} catch (Exception e) {
					pages = 1;
				}

				if (pages < 1) {
					pages = 1;
				}

				if (pages > totalpages) {
					pages = totalpages;
				}
			}
			ArrayList<Integer> integers = new ArrayList<Integer>();
			for (int i = 1; i <= homeNum; i++) {
				integers.add(i);
			}
			ArrayList<homework> allHome = homeworkDao.tCheckHomework(java.lang.Integer.parseInt(lno), number, pages,
					limit);
			ArrayList<String> Uno = new ArrayList<String>();// 根据学号找到学生名字
			for (homework s : allHome) {
				Uno.add(s.getUno());
			}
			for (homework c : allHome) {
				if (c.isFinished() == false && new java.sql.Timestamp(0).compareTo(c.getDeadline()) > 0)
					c.setInform("已截至");
				else if (c.isFinished() == true)
					c.setInform("已提交");
			}
			ArrayList<user> stuName = userDao.findUserName(Uno);
			request.setAttribute("allHome", allHome);
			request.setAttribute("stuName", stuName);
			request.setAttribute("number", integers);
			request.setAttribute("pages", pages);
			request.setAttribute("totalpages", totalpages);
			session.setAttribute("uname", u.getUname());
			request.getRequestDispatcher("teacher_homework.jsp").forward(request, response);
		}
//		老师设置学生成绩
		else if ("/page/setHomeScore.do".equals(url)) {
			user u = (user) session.getAttribute("u");
			String lno = (String) request.getParameter("lno");
			if (lno != null) {
				session.setAttribute("lno", lno);
			}
			lno = (String) session.getAttribute("lno");
			session.setAttribute("u", u);
			session.setAttribute("lno", lno);
			String sno = request.getParameter("sno");
			String homeScore = request.getParameter("homeScore");
			double home = java.lang.Double.parseDouble(homeScore);
			NumberFormat nf = new DecimalFormat("0.0");
			home = Double.parseDouble(nf.format(home));
			s_lessonDao.setHomeScore(sno, Integer.parseInt((String) lno), home);
			// 更新学生的最终成绩
			t_lesson t = t_lessonDao.getTLesson(java.lang.Integer.parseInt(lno));
			s_lesson s = s_lessonDao.getSLesson(sno, java.lang.Integer.parseInt(lno));
			s_lessonDao.updateFinalScore(s, t);
			response.sendRedirect(request.getContextPath() + "/page/teacher_homework.do");
		}
//		老师查看签到情况
		else if ("/page/teacher_check.do".equals(url)) {
			user u = (user) session.getAttribute("u");
			String lno = (String) request.getParameter("lno");
			if (lno != null) {
				session.setAttribute("lno", lno);
			} else {
				lno = (String) session.getAttribute("lno");
			}
			session.setAttribute("u", u);
			session.setAttribute("lno", lno);
			int homeNum = t_lessonDao.getCheckNum(java.lang.Integer.parseInt((String) lno));
			Object num = request.getParameter("number");
			int number = 0;
			if (num == null)
				number = homeNum;
			else
				number = java.lang.Integer.parseInt((String) num);
			int pages = 0; // 待显示页面
			int count = 0; // 总条数
			int totalpages = 0; // 总页数
			int limit = 10; // 每页显示记录条数
			count = t_lessonDao.getTNum(u.getUno());
			totalpages = (int) Math.ceil(count / (limit * 1.0));
			String strPage = request.getParameter("pages");
			if (strPage == null) {
				pages = 1;
			} else {
				try {
					pages = java.lang.Integer.parseInt(strPage);
				} catch (Exception e) {
					pages = 1;
				}

				if (pages < 1) {
					pages = 1;
				}

				if (pages > totalpages) {
					pages = totalpages;
				}
			}
			ArrayList<Integer> integers = new ArrayList<Integer>();
			for (int i = 1; i <= homeNum; i++) {
				integers.add(i);
			}
			ArrayList<check_c> allCheck = check_cDao.tCheck(java.lang.Integer.parseInt(lno), number, pages, limit);
			for (check_c c : allCheck) {
				if (c.isChecked() == false && new java.sql.Timestamp(0).compareTo(c.getDeadline()) > 0)
					c.setInform("已超时");
				else if (c.isChecked() == true)
					c.setInform("已签到");
			}
			ArrayList<String> Uno = new ArrayList<String>();// 根据学号找到学生名字
			for (check_c s : allCheck) {
				Uno.add(s.getUno());
			}
			ArrayList<user> stuName = userDao.findUserName(Uno);
			request.setAttribute("allCheck", allCheck);
			request.setAttribute("stuName", stuName);
			request.setAttribute("number", integers);
			request.setAttribute("pages", pages);
			request.setAttribute("totalpages", totalpages);
			session.setAttribute("uname", u.getUname());
			request.getRequestDispatcher("teacher_check.jsp").forward(request, response);
		} else if ("/page/exit.do".equals(url)) {
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else if ("/page/upload.do".equals(url)) {
			request.setCharacterEncoding("UTF-8");
			String uno = (String) session.getAttribute("uno");
			String uname = (String) session.getAttribute("uname");
			String lno = request.getParameter("lno");
			String num = request.getParameter("num");
			System.out.println(uno);
			int number = java.lang.Integer.parseInt(num);
			// 如果它是true 则证明作业也截至，就算上传也无用//
			if (homeworkDao.findH(uno, java.lang.Integer.parseInt(lno), java.lang.Integer.parseInt(num)) == false) {
				// 得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全

				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				DiskFileItemFactory dfif = new DiskFileItemFactory();
				ServletFileUpload parser = new ServletFileUpload(dfif);
				List<FileItem> items = null;
				try {
					items = parser.parseRequest(request);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 处理请求内容
				if (items != null) {
					for (FileItem item : items) {
						// 循环根本就进不来
						if (item.isFormField()) {
							System.out.println("fla");
						} else {
							String oldName = item.getName();
							String tail = oldName.substring(oldName.lastIndexOf("."));
							// 确定要上传到服务器的位置
							String path = request.getServletContext().getRealPath("/Files");
							String realPath = path + "/" + "课程" + lno + "/" + "第" + num + "次/";
							File file = new File(realPath);
							if (!file.exists()) {
								file.mkdirs();
							}
							System.out.println(realPath);
							// 文件名
							String name = uname + "_" + uno + "_第" + number + "次作业" + tail;

							try {
								item.write(new File(realPath, name));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				// 这里需要为了那边更新
				homeworkDao.setHomework(uno, java.lang.Integer.parseInt(lno), java.lang.Integer.parseInt(num));
				out.write("上传成功！");
			}
			request.getRequestDispatcher("/page/student_homework.do").forward(request, response);
		} else if ("/page/download.do".equals(url)) {
			response.reset();
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			String uno = request.getParameter("uno");
			user u = userDao.findUserUno(uno);
			String cname = u.getUname();
			System.out.println("cname = " + cname + "uno =" + uno);
			String lno = (String) session.getAttribute("lno");
			int num = Integer.parseInt(request.getParameter("homew_num"));
			String path = request.getServletContext().getRealPath("/Files");
			String realPath = path + "/" + "课程" + lno + "/第" + num + "次/" + cname + "_" + uno + "_第" + num + "次作业.docx";
			File file = new File(realPath);
			if (!file.exists()) {
				realPath = path + "/" + "课程" + lno + "/第" + num + "次/" + cname + "_" + uno + "_第" + num + "次作业.docx";
				file = new File(realPath);
			}
			System.out.println(file.getName());
			InputStream ins = new FileInputStream(file);
			/* 设置文件ContentType类型，这样设置，会自动判断下载文件类型 */
			response.setContentType("multipart/form-data");
			/* 设置文件头：最后一个参数是设置下载文件名 */
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
			try {
				OutputStream os = response.getOutputStream();
				byte[] b = new byte[1024];
				int len;
				while ((len = ins.read(b)) > 0) {
					os.write(b, 0, len);
				}
				os.flush();
				os.close();
				ins.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
