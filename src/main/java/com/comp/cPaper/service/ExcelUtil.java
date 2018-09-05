package com.comp.cPaper.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelUtil {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	
	/**
	 * 从某一行起，是否连续三行都是空行，如果是，则返回-1，如果不是，则返回非空行的索引
	 * @param sheet Sheet对象
	 * @param startRowNum 开始计算的行
	 * @return 如果连续三行为空，则不再继续遍历Excel，返回-1，如果三行内有不为空的行，则返回行索引
	 * @author HuKaiXuan 2014-5-14 上午9:57:24
	 */
	public static int hasThreeContinuousEmptyRows(Sheet sheet,int startRowNum) {
		if(!isEmptyRow(sheet.getRow(startRowNum)))
			return startRowNum;
		if(!isEmptyRow(sheet.getRow(startRowNum + 1)))
			return startRowNum + 1;
		if(!isEmptyRow(sheet.getRow(startRowNum + 2)))
			return startRowNum + 2;
		return -1;
	}
	
	/**
	 * 判断数据行是否为一个空行
	 * @param row
	 * @return
	 * @author HuKaiXuan 2014-5-14 上午9:57:24
	 */
	private static boolean isEmptyRow(Row row) {
		if (row == null) {
			return true;
		}
		for (int i = 0; i < row.getLastCellNum(); i++) {
			if (!isEmptyCell(row.getCell(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断单元格是否为空
	 * @param cell
	 * @return boolean
	 * @author HuKaiXuan 2014-5-15 下午3:25:24
	 */
	public static boolean isEmptyCell(Cell cell) {
		if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			return true;
		}
		String value = getCellStringValue(cell);
		if(value == null) {
			return true;
		}
		value = value.replaceAll(" ", ",").replaceAll("　","");
		if("".equals(value)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 将单元格中的内容转换为String类型后返回
	 * @param cell
	 * @return String
	 * @author HuKaiXuan 2014-5-14 上午10:07:20
	 */
	public static String getCellStringValue(Cell cell) {
		// 如果该单元格不存在则返回空
		if (null == cell)
			return "";

		// 根据不同的数据类型，对数据进行格式化
		String returnValue = "";
		switch (cell.getCellType()) {
		// 空单元格
		case Cell.CELL_TYPE_BLANK:
			returnValue = "";
			break;
		// 数字或日期单元格
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				returnValue = sdf.format(cell.getDateCellValue());
			} else {
				returnValue = getStringValueFromNumericCell(cell);
			}
			break;
		// 字符串单元格
		case Cell.CELL_TYPE_STRING:
			returnValue = cell.getStringCellValue();
			break;

		// 公式格式
		case Cell.CELL_TYPE_FORMULA:
			if (DateUtil.isCellDateFormatted(cell)) {
				returnValue = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
			} else {

				returnValue = String.valueOf(cell.getNumericCellValue());
				if("NaN".equals(returnValue)) {
					returnValue = cell.getRichStringCellValue().toString();
				} else {
					if (!StringUtils.isEmpty(returnValue))
						returnValue = new BigDecimal(returnValue).toString();
					if (returnValue.lastIndexOf(".") != -1) {
						// 如果本来就是不带小数的数字（解析后会自动加上 .0 ）去掉 .0
						if (returnValue.endsWith(".0"))
							returnValue = returnValue.substring(0, returnValue.indexOf("."));
						
						// 如果小数点后长度大于3,则进行四舍五入取小数点后3位
						if ((returnValue.length() - returnValue.lastIndexOf(".") - 1) > 3)
							returnValue = new BigDecimal(returnValue).setScale(4, BigDecimal.ROUND_HALF_UP).toString();
					}
				}
				
			}
			break;
		}
		return returnValue;
	}
	
	/** 从数值类型的单元格中取出文本类型的值 */
	private static String getStringValueFromNumericCell(Cell c) {
		String df = c.getCellStyle().getDataFormatString(); // 数据的格式，如 0.0_、0.00_、0.0000000000_、General、0;[Red]0 
		String value = String.valueOf(c.getNumericCellValue());
		if("General".equals(df) || "0;[Red]0".equals(df) ){ // General代表没有设定小数点
			// 分两种情况：
			// 1.位数小于8位，会在后面自动加.0，如123会变成123.0
			if(value.endsWith(".0")) // 截位
				value = value.substring(0,value.length() - 2);
			// 2.位数大于等于8位，会变成科学计数法，如：1.5070031975E10,1.2345678E8,1.2345678E9,1.000000032E7(注意后三个数的差别)
			if(value.indexOf('E') != -1) {
				String[] arr = value.split("E");
				value = arr[0].replaceAll("\\.",""); // 去掉小数点
				int num = Integer.parseInt(arr[1]); // 位数
				if((value.length() - 1) != num) {
					int fill = num - (value.length() - 1); // 如果fill>0则在后面补0，如果fill<0则往前加小数点
					if(fill < 0) 
						value = new StringBuffer().append(value).insert(value.length() + fill, ".").toString();
					else
						for(int i = 0; i < fill ; i++ )
							value += "0";
				} 
			}
		}
		if(df.indexOf("0.0") != -1){ // 设定了小数点
			df = df.replaceAll("_","").replaceAll(" ", "");
			int valueNum = value.split("\\.")[1].length(); // 单个的"."是正则表达式，需要转义
			int dfNum = df.split("\\.")[1].length();
			
			if(valueNum != dfNum) {
				int fill = dfNum - valueNum;
				for(int i = 0; i < fill ; i++ )
					value += "0";
			} 
		}
		return value;
	}

}
