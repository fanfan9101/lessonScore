package com.cl.model;

import java.util.Date;

public class homework {
	
	private int hno;
	private String uno;
	private int lno;
	private double homeworkScore;
	private java.util.Date deadline;
	private String inform;
	private boolean isFinished ;
	private String form ;
	private int number;
	public homework() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public homework(int hno, String uno, int lno, double homeworkScore, Date deadline, String inform,
			boolean isFinished, String form, int number) {
		super();
		this.hno = hno;
		this.uno = uno;
		this.lno = lno;
		this.homeworkScore = homeworkScore;
		this.deadline = deadline;
		this.inform = inform;
		this.isFinished = isFinished;
		this.form = form;
		this.number = number;
	}

	public int getHno() {
		return hno;
	}
	public void setHno(int hno) {
		this.hno = hno;
	}
	public String getUno() {
		return uno;
	}
	public void setUno(String uno) {
		this.uno = uno;
	}
	public int getLno() {
		return lno;
	}
	public void setLno(int lno) {
		this.lno = lno;
	}
	public double getHomeworkScore() {
		return homeworkScore;
	}
	public void setHomeworkScore(double homeworkScore) {
		this.homeworkScore = homeworkScore;
	}
	public java.util.Date getDeadline() {
		return deadline;
	}
	public void setDeadline(java.util.Date deadline) {
		this.deadline = deadline;
	}
	public String getInform() {
		return inform;
	}
	public void setInform(String inform) {
		this.inform = inform;
	}
	public boolean isFinished() {
		return isFinished;
	}
	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
		if(this.isFinished==false) {
			form = "未提交";
		}
		else {
			form = "已提交";
		}
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	

}
