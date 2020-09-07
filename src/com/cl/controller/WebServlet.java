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
		request.setCharacterEncoding("utf-8");// ���ñ���
		// ��ȡ�û���ַ���ĵ�ַ
		String url = request.getServletPath();// /b.do
		System.out.println("��������ĵ�ַ��" + url);
		// ���������������ݺ�ȡ���ݵĶ���
		HttpSession session = request.getSession();

		if ("/page/register.do".equals(url)) {
			// ���ǻ�ȡ���ǵ�name����
			String uno = (String) request.getParameter("id");
			String realUser = (String) request.getParameter("type");
			String uname = (String) request.getParameter("name");
			String realSex = (String) request.getParameter("sex");
			String upwd = request.getParameter("pwd");
			String user;
			if ("student".equals(realUser)) {
				user = "ѧ��";
			} else {
				user = "��ʦ";
			}

			String usex;
			if ("male".equals(realSex)) {
				usex = "��";
			} else {
				usex = "Ů";
			}
			// �����ڿ��ƴ������
			System.out.println(
					uno + " " + user + " " + uname + " " + usex + " " + " " + MD5Utils.MD5Encode(upwd, "utf-8"));
			// ����һ���û����� ����֮ǰ������ʵ���ഫ������
			user u = new user(uno, user, uname, usex, upwd);
			userDao.register(u);
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
//		��¼
		else if ("/page/login.do".equals(url)) {
			String uno = (String) request.getParameter("id");
			String pwd = (String) request.getParameter("pwd");
			user u = userDao.findUser(uno, MD5Utils.MD5Encode(pwd, "utf-8"));
			if (u == null) {
				System.out.println("false");
				request.setAttribute("loginInform", "�˺Ż������������");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			} else {
				request.setAttribute("loginInform", "��¼�ɹ�");
				session.setAttribute("uno", uno);
				session.setAttribute("uname", u.getUname());
				System.out.println(" uno = " + uno + " pwd =" + MD5Utils.MD5Encode(pwd, "utf-8"));
				if ("��ʦ".equals(u.getIsTeacher())) {
					System.out.println("��ʦ");
					session.setAttribute("u", u);
					request.getRequestDispatcher("/page/teacher_index.do").forward(request, response);
				} else {
					System.out.println("ѧ��");
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
			int pages = 0; // ����ʾҳ��
			int count = 0; // ������
			int totalpages = 0; // ��ҳ��
			int limit = 10; // ÿҳ��ʾ��¼����
			// �����¼�����ĵڶ��ְ취��ʹ��mysql�ľۼ�����count(*)
			count = s_lessonDao.getSNum(u.getUno());
			// �ɼ�¼��������ÿҳ��¼���ó���ҳ��
			totalpages = (int) Math.ceil(count / (limit * 1.0));
			// ��ȡ��ҳʱ�������ĵ�ǰҳ�����
			String strPage = request.getParameter("pages");
			// �жϵ�ǰҳ������ĺϷ��Բ�����Ƿ�ҳ�ţ�Ϊ������ʾ��һҳ��С��0����ʾ��һҳ��������ҳ������ʾ���һҳ��
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
			// ͨ��uno �ҵ�s_lesson��������Ŀγ̵õ�lno
			ArrayList<s_lesson> allSLesson = s_lessonDao.findJoinLesson(u.getUno(), pages, limit);
			// ��ͨ�� lno�ҵ��Կγ̵����� ��͸��uno �ҵ���ʦ������\
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
			int pages = 0; // ����ʾҳ��
			int count = 0; // ������
			int totalpages = 0; // ��ҳ��
			int limit = 10; // ÿҳ��ʾ��¼����
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
			// ��ʦѧ������+1
			System.out.println("lno =" + lno + " un11o " + uno);
			boolean flag = s_lessonDao.buildSLesson(uno, java.lang.Integer.parseInt(lno));
			if (flag == true)
				t_lessonDao.setStuNum(java.lang.Integer.parseInt(lno));
			request.getRequestDispatcher("/page/student_unJoin.do").forward(request, response);
		} else if ("/page/student_check.do".equals(url)) {
			user u = (user) session.getAttribute("u");
			session.setAttribute("u", u);
			String lno = (String) request.getParameter("lno");
			// ����Ѿ�����lno����ˢ�£�û�����̨��ȡ
			if (lno != null) {
				session.setAttribute("lno", lno);
			} else {
				lno = (String) session.getAttribute("lno");
			}

			int pages = 0; // ����ʾҳ��
			int count = 0; // ������
			int totalpages = 0; // ��ҳ��
			int limit = 10; // ÿҳ��ʾ��¼����
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

					c.setInform("�ѽ���");
				} else if (c.isChecked() == true)
					c.setInform("��ǩ��");
			}
			// ����ѧ��ǩ���ɼ�//����ѧ�����ճɼ�
			// ����ѧ��ǩ���ɼ�
			int checkNum = t_lessonDao.getCheckNum(Integer.parseInt((String) lno));
			int check = check_cDao.getRealCheck(u.getUno(), java.lang.Integer.parseInt(lno));
			double checkScore = ((double) check / checkNum) * 100;
			s_lessonDao.updateCheckScore(u.getUno(), java.lang.Integer.parseInt(lno), checkScore);
			// ����ѧ�����ճɼ�
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
			// ����Ѿ�����lno����ˢ�£�û�����̨��ȡ
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
			// ����Ѿ�����lno����ˢ�£�û�����̨��ȡ
			if (lno != null) {
				session.setAttribute("lno", lno);
			} else {
				lno = (String) session.getAttribute("lno");
			}
			int pages = 0; // ����ʾҳ��
			int count = 0; // ������
			int totalpages = 0; // ��ҳ��
			int limit = 10; // ÿҳ��ʾ��¼����
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
//			System.out.println("����ʱ�䣺 " + new java.sql.Timestamp(System.currentTimeMillis()));
//			System.out
//					.println(" pages = " + pages + " totalPages = " + totalpages + "count =" + count + " lno =" + lno);
			ArrayList<homework> allHomework = homeworkDao.findAllHomework(u.getUno(), java.lang.Integer.parseInt(lno),
					pages, limit);
			for (homework c : allHomework) {
				if (c.isFinished() == false
						&& new java.sql.Timestamp(System.currentTimeMillis()).compareTo(c.getDeadline()) > 0) {
					c.setForm("�ѽ���");
				} else if (c.isFinished() == true)
					c.setForm("���ύ");
			}
			// ����ѧ����ҵ�ɼ�//����ѧ�����ճɼ� �����ٴθ���ѧ����ҵ�ɼ� ��Ϊ��ʦ��û���� ����ô���¡�
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
			int pages = 0; // ����ʾҳ��
			int count = 0; // ������
			int totalpages = 0; // ��ҳ��
			int limit = 10; // ÿҳ��ʾ��¼����
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
//		��ʦ�����γ�
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
//		��ʦ������Ӧ�γ�����ҳ
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
			int pages = 0; // ����ʾҳ��
			int count = 0; // ������
			int totalpages = 0; // ��ҳ��
			int limit = 10; // ÿҳ��ʾ��¼����
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
			// �����ǵ�ǩ������ҵ�ɼ�����ĩ���и���
			t_lesson t = t_lessonDao.getTLesson(Integer.parseInt((String) lno));
			int checkNum = t_lessonDao.getCheckNum(Integer.parseInt((String) lno));
			int homeNum = t_lessonDao.getHomeNum(Integer.parseInt((String) lno));
			// System.out.println("checkScore = " + checkNum + " homeNum = " + homeNum + "
			// uno " + u.getUno());
			for (int i = 0; i < allStudent.size(); i++) {
				// ǩ��
				int check = check_cDao.getRealCheck(allStudent.get(i).getSno(), allStudent.get(i).getLno());
				double checkScore = ((double) check / checkNum) * 100;
				// System.out.println("checkScore = " + checkScore + " homeNum = " + homeNum);
				s_lessonDao.updateCheckScore(allStudent.get(i).getSno(), allStudent.get(i).getLno(), checkScore);
				// ��ҵ
				double totalScore = homeworkDao.getHomeScore(allStudent.get(i).getSno(), allStudent.get(i).getLno());
				double homeScore = totalScore / homeNum;
				s_lessonDao.updateHomeScore(allStudent.get(i).getSno(), allStudent.get(i).getLno(), homeScore);
				s_lesson s = s_lessonDao.getSLesson(allStudent.get(i).getSno(), Integer.parseInt((String) lno));
				s_lessonDao.updateFinalScore(s, t);
			}
			ArrayList<String> Uno = new ArrayList<String>();// ����ѧ���ҵ�ѧ������
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
//		��ʦ����ѧ���ɼ�
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
			// ����ѧ�������ճɼ�
			t_lesson t = t_lessonDao.getTLesson(java.lang.Integer.parseInt(lno));
			s_lesson s = s_lessonDao.getSLesson(sno, java.lang.Integer.parseInt(lno));
			System.out.println("s =" + s.getExamScore() + "  t= " + t.getExamScore());
			s_lessonDao.updateFinalScore(s, t);
			response.sendRedirect(request.getContextPath() + "/page/score.do");
		}
//		����ǩ��
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
			// ��ʦǩ����+1set ����lno��s_lesson�ҳ����е�ѧ����uno Ȼ�����sno lno ����ǩ����
			t_lessonDao.setCheckNum(u.getUno(), java.lang.Integer.parseInt(lno));
			int checkNum = t_lessonDao.getCheckNum(java.lang.Integer.parseInt(lno));
			ArrayList<s_lesson> stu = s_lessonDao.findUno(Integer.parseInt((String) lno));
			check_cDao.buildCheck(stu, checkNum, java.lang.Integer.parseInt(lno), time1);
			request.getRequestDispatcher("/page/score.do").forward(request, response);
		}
//		������ҵ
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
			int pages = 0; // ����ʾҳ��
			int count = 0; // ������
			int totalpages = 0; // ��ҳ��
			int limit = 10; // ÿҳ��ʾ��¼����
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
			ArrayList<String> Uno = new ArrayList<String>();// ����ѧ���ҵ�ѧ������
			for (homework s : allHome) {
				Uno.add(s.getUno());
			}
			for (homework c : allHome) {
				if (c.isFinished() == false && new java.sql.Timestamp(0).compareTo(c.getDeadline()) > 0)
					c.setInform("�ѽ���");
				else if (c.isFinished() == true)
					c.setInform("���ύ");
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
//		��ʦ����ѧ���ɼ�
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
			// ����ѧ�������ճɼ�
			t_lesson t = t_lessonDao.getTLesson(java.lang.Integer.parseInt(lno));
			s_lesson s = s_lessonDao.getSLesson(sno, java.lang.Integer.parseInt(lno));
			s_lessonDao.updateFinalScore(s, t);
			response.sendRedirect(request.getContextPath() + "/page/teacher_homework.do");
		}
//		��ʦ�鿴ǩ�����
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
			int pages = 0; // ����ʾҳ��
			int count = 0; // ������
			int totalpages = 0; // ��ҳ��
			int limit = 10; // ÿҳ��ʾ��¼����
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
					c.setInform("�ѳ�ʱ");
				else if (c.isChecked() == true)
					c.setInform("��ǩ��");
			}
			ArrayList<String> Uno = new ArrayList<String>();// ����ѧ���ҵ�ѧ������
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
			// �������true ��֤����ҵҲ�����������ϴ�Ҳ����//
			if (homeworkDao.findH(uno, java.lang.Integer.parseInt(lno), java.lang.Integer.parseInt(num)) == false) {
				// �õ��ϴ��ļ��ı���Ŀ¼�����ϴ����ļ������WEB-INFĿ¼�£����������ֱ�ӷ��ʣ���֤�ϴ��ļ��İ�ȫ

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
				// ������������
				if (items != null) {
					for (FileItem item : items) {
						// ѭ�������ͽ�����
						if (item.isFormField()) {
							System.out.println("fla");
						} else {
							String oldName = item.getName();
							String tail = oldName.substring(oldName.lastIndexOf("."));
							// ȷ��Ҫ�ϴ�����������λ��
							String path = request.getServletContext().getRealPath("/Files");
							String realPath = path + "/" + "�γ�" + lno + "/" + "��" + num + "��/";
							File file = new File(realPath);
							if (!file.exists()) {
								file.mkdirs();
							}
							System.out.println(realPath);
							// �ļ���
							String name = uname + "_" + uno + "_��" + number + "����ҵ" + tail;

							try {
								item.write(new File(realPath, name));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				// ������ҪΪ���Ǳ߸���
				homeworkDao.setHomework(uno, java.lang.Integer.parseInt(lno), java.lang.Integer.parseInt(num));
				out.write("�ϴ��ɹ���");
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
			String realPath = path + "/" + "�γ�" + lno + "/��" + num + "��/" + cname + "_" + uno + "_��" + num + "����ҵ.docx";
			File file = new File(realPath);
			if (!file.exists()) {
				realPath = path + "/" + "�γ�" + lno + "/��" + num + "��/" + cname + "_" + uno + "_��" + num + "����ҵ.docx";
				file = new File(realPath);
			}
			System.out.println(file.getName());
			InputStream ins = new FileInputStream(file);
			/* �����ļ�ContentType���ͣ��������ã����Զ��ж������ļ����� */
			response.setContentType("multipart/form-data");
			/* �����ļ�ͷ�����һ�����������������ļ��� */
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
