package com.cl.model;

public class user {
	private String uno ;
	private String isTeacher;
	private String uname;
	private String usex;
	private String upwd;
	
	public user() {
		super();
		// TODO Auto-generated constructor stub
	}

	public user(String uno, String isTeacher, String uname, String usex, String upwd) {
		super();
		this.uno = uno;
		this.isTeacher = isTeacher;
		this.uname = uname;
		this.usex = usex;
		this.upwd = upwd;
	}

	public String getUno() {
		return uno;
	}

	public void setUno(String uno) {
		this.uno = uno;
	}

	public String getIsTeacher() {
		return isTeacher;
	}

	public void setIsTeacher(String isTeacher) {
		this.isTeacher = isTeacher;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUsex() {
		return usex;
	}

	public void setUsex(String usex) {
		this.usex = usex;
	}

	public String getUpwd() {
		return upwd;
	}

	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}
	
	
}
