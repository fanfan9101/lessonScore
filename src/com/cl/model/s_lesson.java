package com.cl.model;

public class s_lesson {
	
	private int snumber;
	private String sno;
	private int lno;
	private double checkScore;
	private double homeworkScore;
	private double examScore;
	private double finalScore;
	public s_lesson(int snumber, String sno, int lno, double checkScore, double homeworkScore, double examScore,
			double finalScore) {
		super();
		this.snumber = snumber;
		this.sno = sno;
		this.lno = lno;
		this.checkScore = checkScore;
		this.homeworkScore = homeworkScore;
		this.examScore = examScore;
		this.finalScore = finalScore;
	}
	public s_lesson() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getSnumber() {
		return snumber;
	}
	public void setSnumber(int snumber) {
		this.snumber = snumber;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public int getLno() {
		return lno;
	}
	public void setLno(int lno) {
		this.lno = lno;
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
	public double getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(double finalScore) {
		this.finalScore = finalScore;
	}
	

}
