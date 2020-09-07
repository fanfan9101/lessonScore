package com.cl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cl.model.check_c;
import com.cl.model.homework;
import com.cl.model.s_lesson;
import com.cl.model.t_lesson;
import com.cl.util.DBUtil;

public class homeworkDao {
//	
	public static void buildHomework(ArrayList<s_lesson> s, int checkNum, int lno, java.util.Date time, String inform) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(time.getTime());
		try {
			for (int i = 0; i < s.size(); i++) {
				String sql = "insert into homework(uno, lno, deadLine, number,inform) values(?,?,?,?,?)";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, s.get(i).getSno());
				pstm.setInt(2, lno);
				pstm.setTimestamp(3, sqlDate);
				pstm.setInt(4, checkNum);
				pstm.setString(5, inform);
				pstm.executeUpdate();
			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static double getHomeScore(String uno, int lno) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		double total = 0;
		try {
			String sql = "select * from homework where uno =? and lno =?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, uno);
			pstm.setInt(2, lno);
			ResultSet result = pstm.executeQuery();
			while (result.next()) {
				homework h = returnHomework(result);
				total = total + h.getHomeworkScore();
			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	public static homework returnHomework(ResultSet resultSet) throws SQLException {
		int hno = resultSet.getInt(1);
		String uno = resultSet.getString(2);
		int lno = resultSet.getInt(3);
		double homeScore = resultSet.getDouble(4);
		java.sql.Timestamp deadline = resultSet.getTimestamp(5);
		String inform = resultSet.getString(6);
		boolean isFinished = resultSet.getBoolean(7);
		String form = resultSet.getString(8);
		int number = resultSet.getInt(9);
		homework h = new homework(hno, uno, lno, homeScore, deadline, inform, isFinished, form, number);
		return h;
	}

	//[start] 老师寻找本次作业的所有人的作业列表
		public static ArrayList<homework> tCheckHomework(int lno,int num,int pages,int limit)
		{
			Connection conn = DBUtil.getConnection();
			PreparedStatement pstm = null;
			 ArrayList<homework> tea = new  ArrayList<homework>();
			 int count = 0;
			try {
				String sql = "select count(*) from homework where lno =? and number =?";
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, lno);
				pstm.setInt(2, num);
				ResultSet result = pstm.executeQuery();
				if(result.next()) {
					count = result.getInt(1);
				}
				result =pstm.executeQuery("select * from homework where lno = "+lno +" and number ="+ num+"  limit " + (pages - 1) * limit + "," + limit);
				while(result.next()) {
					 tea.add( returnHomework(result));
				}
				DBUtil.closeDB(pstm, conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return tea;
		}

		public static ArrayList<homework> findAllHomework(String uno, int lno, int pages, int limit) {
			Connection conn = DBUtil.getConnection();
			PreparedStatement pstm = null;
			ArrayList<homework> tea = new ArrayList<homework>();
			int count = 0;
			try {
				String sql = "select count(*) from homework where lno =? and uno=?";
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, lno);
				pstm.setString(2, uno);
				ResultSet result = pstm.executeQuery();
				if (result.next()) {
					count = result.getInt(1);
				}
				result = pstm.executeQuery("select * from homework where lno = " + lno + " and uno =" + uno + "  limit "
						+ (pages - 1) * limit + "," + limit);
				while (result.next()) {
					tea.add(returnHomework(result));
				}
				DBUtil.closeDB(pstm, conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return tea;
		}
		public static boolean findH(String uno, int lno, int number) {
			Connection conn = DBUtil.getConnection();
			PreparedStatement pstm = null;
			boolean flag = false;
			try {
				String sql = "select * from homework where lno =? and uno=? and number =?";
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, lno);
				pstm.setString(2, uno);
				pstm.setInt(3, number);
				ResultSet result = pstm.executeQuery();
				while (result.next()) {
					homework c = returnHomework(result);
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

		public static void setHomework(String uno, int lno, int number) {
			Connection conn = DBUtil.getConnection();
			PreparedStatement pstm = null;
			try {
				String sql = "update  homework set isFinished =?, form =?  where uno = ? and lno = ?";
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
