package com.cl.util;

import java.util.Random;
//验证码数字的随机生成
public class CodeUtil {
	public static String str(){
		String str = "ABCDEFGHJKLMNPQRSTUVWXY3456789";
		Random r = new Random();
		char c = str.charAt(r.nextInt(str.length()));
		return c+"";
	}
	
	public static void main(String[] args) {
		System.out.println(str());
	}
}
