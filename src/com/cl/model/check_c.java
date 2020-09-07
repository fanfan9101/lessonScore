package com.cl.model;

import java.util.Date;

public class check_c {

	private int cno ;
	private String uno;
	private int lno;
	private java.util.Date deadline;
	private boolean isChecked;
	private int number ;
	private String inform;
	public check_c() {
		super();
		// TODO Auto-generated constructor stub
	}
	public check_c(int cno, String uno, int lno, Date deadline, boolean isChecked, int number, String inform) {
		super();
		this.cno = cno;
		this.uno = uno;
		this.lno = lno;
		this.deadline = deadline;
		this.isChecked = isChecked;
		this.number = number;
		this.inform = inform;
	}
	public int getCno() {
		return cno;
	}
	public void setCno(int cno) {
		this.cno = cno;
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
	public java.util.Date getDeadline() {
		return deadline;
	}
	public void setDeadline(java.util.Date deadline) {
		this.deadline = deadline;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
		if(this.isChecked==false)
		{
			inform="Î´Ç©µ½";
		}
		else
		{
			inform="ÒÑÇ©µ½";
		}
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getInform() {
		return inform;
	}
	public void setInform(String inform) {
		this.inform = inform;
	}
	
}
