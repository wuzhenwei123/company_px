package com.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class MakeImei {
	public static void main(String[] args) {
		MakeImei makeImei = new MakeImei();
//		for(int i=0;i<10000;i++){
//			System.out.println(makeImei.getCode()); 
//		}
		System.out.println(makeImei.checkImei("14979257807639328873")); 
		
	}
	
	public String getCode(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		Random random = new Random();
		String imeiString = new Date().getTime()+"";
		
		for(int i=0;i<5;i++){
			int result=random.nextInt(10);
			imeiString = imeiString + result;
		}
		System.out.println(imeiString);
		int imei1 = this.getImei1(imeiString);
		int imei2 = this.getImei2(imeiString);
		//把校验码1放入第10位，把校验码2放入最后一位
		String sub1 = imeiString.substring(0, 10);
		String sub2 = imeiString.substring(10, imeiString.length());
		return sub1 + imei1 + sub2 + imei2;
	}
	
	
	/**
	 * 检验码1计算：
	 * (1).将偶数位数字分别乘以2，分别计算个位数和十位数之和
	 * (2).将奇数位数字相加，再加上上一步算得的值
	 * (3).如果得出的数个位是0则校验位为0，否则为10减去个位数
	 */
	public int getImei1(String imeiString){
		char[] imeiChar=imeiString.toCharArray();
		int resultInt=0;
		for (int i = 0; i < imeiChar.length; i++) {
			int a=Integer.parseInt(String.valueOf(imeiChar[i]));
			i++;
			final int temp=Integer.parseInt(String.valueOf(imeiChar[i]))*2;
			final int b=temp<10?temp:temp-9;
			resultInt+=a+b;
		}
		resultInt%=10;
		resultInt=resultInt==0?0:10-resultInt;
		return resultInt;
	}
	
	/**
	 * 校验登录名是否合法
	 * @param imeiString
	 * @return
	 */
	public boolean checkImei(String imeiString){
		String sub1 = imeiString.substring(0, 10);
		String sub2 = imeiString.substring(11, imeiString.length()-1);
		String imeiString1 = sub1 + sub2;
		int imei1 = this.getImei1(imeiString1);
		int imei2 = this.getImei2(imeiString1);
		if((sub1 + imei1 + sub2 + imei2).equals(imeiString)){
			return true;
		}
		return false;
	}
	
	/**
	 * 检验码2计算：
	 * (1).将奇数位的和与偶数位的和相加
	 * (2).将相加结果取10的余数
	 */
	public int getImei2(String number){
		int sumOdd = 0;
		int sumEven = 0;
		int length = number.length();
		int[] wei = new int[length];
		for (int i = 0; i < number.length(); i++) {
			wei[i] = Integer.parseInt(number.substring(length - i - 1, length
					- i));// 从最末一位开始提取，每一位上的数值
		}
		for (int i = 0; i < length / 2; i++) {
			sumOdd += wei[2 * i];
			if ((wei[2 * i + 1] * 2) > 9)
				wei[2 * i + 1] = wei[2 * i + 1] * 2 - 9;
			else
				wei[2 * i + 1] *= 2;
			sumEven += wei[2 * i + 1];
		}
		return (sumOdd + sumEven) % 10;
	}
}
