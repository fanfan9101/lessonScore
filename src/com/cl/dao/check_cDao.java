package com.cl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.cl.model.check_c;
import com.cl.model.homework;
import com.cl.model.s_lesson;
import com.cl.model.user;
import com.cl.util.DBUtil;
import com.cl.util.MD5Utils;

public class check_cDao {
	public static void buildCheck(ArrayList<s_lesson> s, int checkNum, int lno, int time) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		java.util.Date now = new java.util.Date();
		long after = time * 1000 * 60;
		java.util.Date afterTime = new java.util.Date(now.getTime() + after);
		System.out.println(afterTime);
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(afterTime.getTime());
		try {
			for (int i = 0; i < s.size(); i++) {
				String sql = "insert into check_c (uno, lno, deadLine, number) values(?,?,?,?)  ";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, s.get(i).getSno());
				pstm.setInt(2, lno);
				pstm.setTimestamp(3, sqlDate);
				pstm.setInt(4, checkNum);
				pstm.executeUpdate();
			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getRealCheck(String sno, int lno) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		int checkNum = 0;
		try {
			String sql = "select * from check_c where uno =? and lno =?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, sno);
			pstm.setInt(2, lno);
			ResultSet result = pstm.executeQuery();
			while (result.next()) {
				check_c c = returnCheck(result);
				if (c.isChecked() == true)
					checkNum = checkNum + 1;
			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkNum;
	}

	public static check_c returnCheck(ResultSet resultSet) throws SQLException {
		int cno = resultSet.getInt(1);
		String uno = resultSet.getString(2);
		int lno = resultSet.getInt(3);
		java.sql.Timestamp deadline = resultSet.getTimestamp(4);
		boolean isChecked = resultSet.getBoolean(5);
		int number = resultSet.getInt(6);
		String inform = resultSet.getString(7);
		check_c c = new check_c(cno, uno, lno, deadline, isChecked, number, inform);
		return c;
	}

	public static ArrayList<check_c> tCheck(int lno, int num, int pages, int limit) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		ArrayList<check_c> tea = new ArrayList<check_c>();
		int count = 0;
		try {
			String sql = "select count(*) from check_c where lno =? and number =?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, lno);
			pstm.setInt(2, num);
			ResultSet result = pstm.executeQuery();
			if (result.next()) {
				count = result.getInt(1);
			}
			result = pstm.executeQuery("select * from check_c where lno = " + lno + " and number =" + num + "  limit "
					+ (pages - 1) * limit + "," + limit);
			while (result.next()) {
				tea.add(returnCheck(result));
			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tea;
	}

//	学生找到自己所有的签到
	public static ArrayList<check_c> findAllCheck(String uno, int lno, int pages, int limit) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		ArrayList<check_c> tea = new ArrayList<check_c>();
		int count = 0;
		try {
			String sql = "select count(*) from check_c where lno =? and uno=?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, lno);
			pstm.setString(2, uno);
			ResultSet result = pstm.executeQuery();
			if (result.next()) {
				count = result.getInt(1);
			}
			result = pstm.executeQuery("select * from check_c where lno = " + lno + " and uno =" + uno + "  limit "
					+ (pages - 1) * limit + "," + limit);
			while (result.next()) {
				tea.add(returnCheck(result));
			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tea;

	}

	public static boolean findC(String uno, int lno, int number) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		boolean flag = false;
		try {
			String sql = "select * from check_c where lno =? and uno=? and number =?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, lno);
			pstm.setString(2, uno);
			pstm.setInt(3, number);
			ResultSet result = pstm.executeQuery();
			while (result.next()) {
				check_c c = returnCheck(result);
				if (new java.sql.Timestamp(System.currentTimeMillis()).compareTo(c.getDeadline()) > 0) {
					flag = true;
				}
				else {
					flag = false;
				}

			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static void setCheck(String uno, int lno, int number) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		try {
			String sql = "update  check_c set isChecked =?, inform =?  where uno = ? and lno = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setBoolean(1, true);
			pstm.setString(2, "done");
			pstm.setString(3, uno);
			pstm.setInt(4, lno);
			pstm.executeUpdate();
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}