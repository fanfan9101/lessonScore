package com.cl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import com.cl.model.s_lesson;
import com.cl.model.t_lesson;
import com.cl.model.user;
import com.cl.util.DBUtil;
import com.cl.util.MD5Utils;

public class s_lessonDao {
	public static s_lesson returnSLesson(ResultSet resultSet) throws SQLException {
		int snumber = resultSet.getInt(1);
		String sno = resultSet.getString(2);
		int lno = resultSet.getInt(3);
		double checkScore = resultSet.getDouble(4);
		double homeScore = resultSet.getDouble(5);
		double examScore = resultSet.getDouble(6);
		double finalScore = resultSet.getDouble(7);
		s_lesson s = new s_lesson(snumber,sno,lno,checkScore,homeScore,examScore,finalScore);
		return s;
	}
//	������ʦ�Ŀγ̺� �ҵ���Ӧѧ��
	public static ArrayList<s_lesson> findSLesson(int lno,int pages,int limit) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		 ArrayList<s_lesson> s = new  ArrayList<s_lesson>();
		 int count = 0;
		try {
			String sql = "select count(*) from s_lesson where lno =? ";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, lno);
			ResultSet result = pstm.executeQuery();
			if(result.next()) {
				count = result.getInt(1);
			}
			result =pstm.executeQuery("select * from s_lesson where lno = "+lno +"  limit " + (pages - 1) * limit + "," + limit);
			while(result.next()) {
				 s.add( returnSLesson(result));
			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
//	��ʦ����ѧ���ɼ�
	public static void setFinalExam(String sno ,int lno ,double examScore) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		user u = new user();
		try {
			NumberFormat  nf=new  DecimalFormat( "0.0");
			examScore = Double.parseDouble(nf.format(examScore));
			System.out.println(examScore);
			String sql = "update  s_lesson set examScore=?  where sno =? and lno =?";
			pstm = conn.prepareStatement(sql);
			pstm.setDouble(1, examScore);
			pstm.setString(2, sno);;
			pstm.setInt(3, lno);
			pstm.executeUpdate();
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	ͨ��lno�ҵ����е�ѧ��
	public static ArrayList<s_lesson> findUno(int lno){
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		ArrayList<s_lesson> s = new ArrayList<s_lesson>();
		try {
			String sql = "select * from s_lesson where lno =?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, lno);
			ResultSet result = pstm.executeQuery();
			while(result.next()) {
				s.add(returnSLesson(result));
			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
//	���¸���ǩ���ɼ�
	public static void updateCheckScore(String uno ,int lno,double checkScore) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		try {
			NumberFormat  nf=new  DecimalFormat( "0.0");
			checkScore = Double.parseDouble(nf.format(checkScore));
			String sql = "update  s_lesson set checkScore=?  where sno =? and lno =?";
			pstm = conn.prepareStatement(sql);
			pstm.setDouble(1, checkScore);
			pstm.setString(2, uno);;
			pstm.setInt(3, lno);
			pstm.executeUpdate();
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	���¸�����ҵ�ɼ�
	public static void updateHomeScore(String uno ,int lno,double checkScore) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		try {
			NumberFormat  nf=new  DecimalFormat( "0.0");
			checkScore = Double.parseDouble(nf.format(checkScore));
			String sql = "update  s_lesson set homeworkScore=?  where sno =? and lno =?";
			pstm = conn.prepareStatement(sql);
			pstm.setDouble(1, checkScore);
			pstm.setString(2, uno);;
			pstm.setInt(3, lno);
			pstm.executeUpdate();
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	���¸������ճɼ�
	public static void updateFinalScore(s_lesson s,t_lesson t) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		try {
			
			double finalScore = (s.getCheckScore()*t.getCheckScore()+s.getExamScore()*t.getExamScore()+s.getHomeworkScore()*t.getHomeworkScore())/100;
			NumberFormat  nf=new  DecimalFormat( "0.0");
			finalScore = Double.parseDouble(nf.format(finalScore));
			System.out.println("finalScore = "+finalScore);
			String sql = "update  s_lesson set  finalScore =?  where sno =? and lno =?";
			pstm = conn.prepareStatement(sql);
			pstm.setDouble(1, finalScore);
			pstm.setString(2,s.getSno());
			pstm.setInt(3, s.getLno());
			pstm.executeUpdate();
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	����uno lno ����һ��s_lesson
	public static s_lesson  getSLesson(String uno ,int lno) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		s_lesson s = new s_lesson();
		try {
			String sql = "select * from s_lesson where lno =? and sno =?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, lno);
			pstm.setString(2, uno);
			ResultSet result = pstm.executeQuery();
			while(result.next()) {
				s = returnSLesson(result);
			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
//	��ʦ����ѧ����ҵ�ɼ�
	public static void setHomeScore(String sno ,int lno ,double homeScore) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		user u = new user();
		try {
			NumberFormat  nf=new  DecimalFormat( "0.0");
			homeScore = Double.parseDouble(nf.format(homeScore));
			String sql = "update  homework set homeworkScore=?  where uno =? and lno =?";
			pstm = conn.prepareStatement(sql);
			pstm.setDouble(1, homeScore);
			pstm.setString(2, sno);;
			pstm.setInt(3, lno);
			pstm.executeUpdate();
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	ѧ�������Լ�ѧ�Ų���������Ŀγ�
	public static ArrayList<s_lesson> findJoinLesson(String uno,int pages,int limit) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		 ArrayList<s_lesson> s = new  ArrayList<s_lesson>();
		 int count = 0;
		try {
			String sql = "select count(*) from s_lesson where sno =?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, uno);
			ResultSet result = pstm.executeQuery();
			if(result.next()) {
				count = result.getInt(1);
			}
			result =pstm.executeQuery("select * from s_lesson where sno = "+uno +"  limit " + (pages - 1) * limit + "," + limit);
			while(result.next()) {
				 s.add( returnSLesson(result));
			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
//	ѧ������γ�
	public static boolean buildSLesson(String uno ,int lno) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		boolean flag = false;
		int count =0;
		try {
			String sql = "select count(*) from s_lesson where sno =? and lno =?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, uno);
			pstm.setInt(2, lno);
			ResultSet result = pstm.executeQuery();
			if(result.next()) {
				count = result.getInt(1);
			}
			if(count ==0 ){
				pstm.executeUpdate("insert into s_lesson(sno,lno ) values("+uno +","+lno+")");
			}
			else flag = true;
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
//	��������ѧ��һ����γ�
	public static int getSNum(String uno) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		int count = 0;
		try {
			String sql = "select count(*) from s_lesson where sno =? ";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, uno);
			ResultSet result = pstm.executeQuery();
			if (result.next()) {
				count = result.getInt(1);
			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
