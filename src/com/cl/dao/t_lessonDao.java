package com.cl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cl.model.t_lesson;
import com.cl.model.user;
import com.cl.util.DBUtil;
import com.cl.util.MD5Utils;

public class t_lessonDao {

//	������ʦ��ѧ�� ��ҳ�ҵ���ʦ�γ�
	public static ArrayList<t_lesson> findTLessons(String uno, int pages, int limit) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		ArrayList<t_lesson> tea = new ArrayList<t_lesson>();
		int count = 0;
		try {
			String sql = "select count(*) from t_lesson where uno =? ";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, uno);
			ResultSet result = pstm.executeQuery();
			if (result.next()) {
				count = result.getInt(1);
			}
			System.out.println("pages = "+pages +" limit22 = "+limit);
			result = pstm.executeQuery("select * from t_lesson where uno = " + uno + "  limit " + (pages-1)*limit + "," + limit);
			while (result.next()) {
				tea.add(returnTLesson(result));
			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tea;
	}

//	������ʦ�γ�
	public static t_lesson returnTLesson(ResultSet resultSet) throws SQLException {
		int lno = resultSet.getInt(1);
		String lname = resultSet.getString(2);
		String lfaculty = resultSet.getString(3);
		String uno = resultSet.getString(4);
		double checkScore = resultSet.getDouble(5);
		double homeworkScore = resultSet.getDouble(6);
		double examScore = resultSet.getDouble(7);
		int checkNum = resultSet.getInt(8);
		int homeworkNum = resultSet.getInt(9);
		int stuNum = resultSet.getInt(10);
		t_lesson t = new t_lesson(lno, lname, lfaculty, uno, checkScore, homeworkScore, examScore, checkNum,
				homeworkNum, stuNum);
		return t;
	}

//	������ʦ�ܴ����Ŀγ���
	public static int getTNum(String uno) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		int count = 0;
		try {
			String sql = "select count(*) from t_lesson where uno =? ";
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

//	��ʦ�����γ�
	public static void buildTLesson(String lname, String lfaculty, String uno, double checkScore, double homeScore,
			double examScore) {

		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		try {
			String sql = "insert into t_lesson(lname,lfaculty,uno,checkScore, homeworkScore ,examScore) value(?,?,?,?,?,?) ";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, lname);
			pstm.setString(2, lfaculty);
			pstm.setString(3, uno);
			pstm.setDouble(4, checkScore);
			pstm.setDouble(5, homeScore);
			pstm.setDouble(6, examScore);
			pstm.executeUpdate();
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	��ʦǩ����+1
	public static void setCheckNum(String uno, int lno) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		try {
			String sql = "update  t_lesson  set checkNum = checkNum +1  where uno =? and lno =?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, uno);
			pstm.setInt(2, lno);
			pstm.executeUpdate();
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	��ȡ��ʦ��ǰǩ������
	public static int getCheckNum( int lno) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		int checkNum = 0;
		try {
			String sql = "select * from t_lesson where lno =?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, lno);
			ResultSet result = pstm.executeQuery();
			while (result.next()) {
				t_lesson t = returnTLesson(result);
				checkNum = t.getCheckNum();
				System.out.println("tchenum "+t.getCheckNum());
			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkNum;
	}

//	��ʦ��ҵ��+1
	public static void setHomeNum(String uno, int lno) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		try {
			String sql = "update  t_lesson  set homeworkNum = homeworkNum +1  where uno =? and lno =?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, uno);
			pstm.setInt(2, lno);
			pstm.executeUpdate();
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	��ȡ��ʦ��ǰ��ҵ����
	public static int getHomeNum( int lno) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		int homeNum = 0;
		try {
			String sql = "select * from t_lesson where  lno= ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, lno);
			ResultSet result = pstm.executeQuery();
			while (result.next()) {
				t_lesson t = returnTLesson(result);
				homeNum = t.getHomeworkNum();
			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return homeNum;
	}

//	����uno ����һ��t_lesson
	public static t_lesson getTLesson(int lno) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		t_lesson t = new t_lesson();
		try {
			String sql = "select * from t_lesson where lno =?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, lno);
			ResultSet result = pstm.executeQuery();
			while (result.next()) {
				t = returnTLesson(result);
			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

//	����lѧ���ҵ���lno���ҵ� �γ���
	public static ArrayList<t_lesson> findLName(ArrayList<Integer> lno) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		ArrayList<t_lesson> tea = new ArrayList<t_lesson>();
		try {
			for (int i = 0; i < lno.size(); i++) {
				String sql = "select * from t_lesson where lno =? ";
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, lno.get(i));
				ResultSet result = pstm.executeQuery();
				while (result.next()) {
					tea.add(returnTLesson(result));
				}
			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tea;
	}
//	ѧ���鿴���еĿγ�
	public static ArrayList<t_lesson> getAllTLesson(){
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		ArrayList<t_lesson> tea = new ArrayList<t_lesson>();
		try {
				String sql = "select * from t_lesson ";
				pstm = conn.prepareStatement(sql);
				ResultSet result = pstm.executeQuery();
				while (result.next()) {
					tea.add(returnTLesson(result));
				}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tea;
	}
	//ѧ������γ���ʦstuNum+1
	public static void setStuNum(int lno) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		try {
			String sql = "update  t_lesson  set stuNum = stuNum +1  where  lno =?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, lno);
			pstm.executeUpdate();
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
