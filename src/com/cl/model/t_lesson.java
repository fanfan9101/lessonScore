package com.cl.model;

public class t_lesson {

	private  int lno;
	private String lname;
	private String lfaculty;
	private String uno;
	private double checkScore;
	private double homeworkScore;
	private double examScore;
	private int checkNum;
	private int homeworkNum;
	private int stuNum;
	public t_lesson() {
		super();
		// TODO Auto-generated constructor stub
	}
	public t_lesson(int lno, String lname, String lfaculty, String uno, double checkScore, double homeworkScore,
			double examScore, int checkNum, int homeworkNum, int stuNum) {
		super();
		this.lno = lno;
		this.lname = lname;
		this.lfaculty = lfaculty;
		this.uno = uno;
		this.checkScore = checkScore;
		this.homeworkScore = homeworkScore;
		this.examScore = examScore;
		this.checkNum = checkNum;
		this.homeworkNum = homeworkNum;
		this.stuNum = stuNum;
	}
	public int getLno() {
		return lno;
	}
	public void setLno(int lno) {
		this.lno = lno;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getLfaculty() {
		return lfaculty;
	}
	public void setLfaculty(String lfaculty) {
		this.lfaculty = lfaculty;
	}
	public String getUno() {
		return uno;
	}
	public void setUno(String uno) {
		this.uno = uno;
	}
	public double getCheckScore() {
		return checkScore;
	}
	public void setCheckScore(double checkScore) {
		this.checkScore = checkScore;
	}
	public double getHomeworkScore() {
		return homeworkScore;
	}
	public void setHomeworkScore(double homeworkScore) {
		this.homeworkScore = homeworkScore;
	}
	public double getExamScore() {
		return examScore;
	}
	public void setExamScore(double examScore) {
		this.examScore = examScore;
	}
	public int getCheckNum() {
		return checkNum;
	}
	public void setCheckNum(int checkNum) {
		this.checkNum = checkNum;
	}
	public int getHomeworkNum() {
		return homeworkNum;
	}
	public void setHomeworkNum(int homeworkNum) {
		this.homeworkNum = homeworkNum;
	}
	public int getStuNum() {
		return stuNum;
	}
	public void setStuNum(int stuNum) {
		this.stuNum = stuNum;
	}
	
}
