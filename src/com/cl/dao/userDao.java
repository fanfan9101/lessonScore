package com.cl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cl.util.DBUtil;
import com.cl.util.MD5Utils;
import com.cl.model.*;

public class userDao {
	
//	注册账号
	public static void register(user user) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		try {
			String sql = "insert into user (uno, isTeacher, uname, usex, upwd) values(?,?,?,?,?) ";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, user.getUno());
			pstm.setString(2, user.getIsTeacher());
			pstm.setString(3, user.getUname());
			pstm.setString(4, user.getUsex());
			pstm.setString(5, MD5Utils.MD5Encode(user.getUpwd(), "utf-8"));
			pstm.executeUpdate();
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	根据账号和密码找到用户
	public static user findUser(String uno ,String pwd) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		user u = null;
		try {
			String sql = "select * from user where uno =? and upwd =?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, uno);
			pstm.setString(2, pwd);
			ResultSet result = pstm.executeQuery();
			while(result.next()) {
				System.out.println("true");
				 u = returnUser(result);
			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}
//	返回用户
	public static user returnUser(ResultSet resultSet) throws SQLException{
		
		String uno = resultSet.getString(1);
		String isTeacher = resultSet.getString(2);
		String uname = resultSet.getString(3);
		String usex = resultSet.getString(4);
		String upwd = resultSet.getString(5);
		user u = new user(uno,isTeacher,uname ,usex,upwd);
		return u;
	}
//  根据账号找到用户//修改密码第一步
	public static user findUserUno(String uno) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		user u = new user();
		try {
			String sql = "select * from user where uno =? ";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, uno);
			ResultSet result = pstm.executeQuery();
			while(result.next()) {
				 u = returnUser(result);
			}
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}
//	更新密码
	public static user changePwd(String uno,String pwd) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		user u = new user();
		try {
			String sql = "update  user set upwd =?  where uno =?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, MD5Utils.MD5Encode(pwd, "utf-8"));
			pstm.setString(2, uno);
			pstm.executeUpdate();
			DBUtil.closeDB(pstm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}
//	根据学号找到user 
	public static ArrayList<user> findUserName(ArrayList<String> uno){
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		ArrayList<user> u = new ArrayList<user> ();
		try {
			for(int i=0;i<uno.size();i++)
			{
				String sql = "select * from user where uno =? ";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, uno.get(i));
				ResultSet result = pstm.executeQuery();
				while(result.next()) {
					u.add(returnUser(result));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}
}
