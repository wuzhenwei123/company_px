package com.comp.cPaper.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.comp.cPaper.dao.CPaperDAO;
import com.comp.cPaper.model.CPaper;
import com.comp.cQuestion.model.CQuestion;

/**
 * @author	wzw
 * @time	2018-03-23 09:21:18
 */
@Service
@Transactional
public class CPaperServiceImpl implements CPaperService{
	
	@Resource
    private CPaperDAO cPaperDAO;
	
	public List<CPaper> getCPaperList(CPaper cPaper) {
        return cPaperDAO.getCPaperList(cPaper);
    }

    public CPaper getCPaperById(String id) { 
        return cPaperDAO.getCPaperById(id);
    }

    public long insertCPaper(CPaper cPaper){
        return cPaperDAO.insertCPaper(cPaper);
    }

    public int updateCPaperById(CPaper cPaper){
        return cPaperDAO.updateCPaperById(cPaper);
    }
    
    public int deleteCPaperById(String id){
        return cPaperDAO.deleteCPaperById(id);
    }
    
    public JSONObject checkExcel(Workbook book){
    	JSONObject json = new JSONObject();
    	StringBuilder allMsg = new StringBuilder();
    	try{
    		if(book == null) {
    			allMsg.append("Excel对象为空，请检查。" + "\n");
    			return null;
    		}
    		Sheet sheet = book.getSheetAt(0);
    		int lastRowNum = sheet.getLastRowNum();	// 最后一行行号
    		if(lastRowNum > 3003) {
    			allMsg.append("一次导入不能超过3000行，请分批次导入。" + "\n");
    			return null;
    		}
    		List<CQuestion> result = new ArrayList<CQuestion>();
    		int currentRow = 1; // 当前行索引
    		while((currentRow = ExcelUtil.hasThreeContinuousEmptyRows(sheet, currentRow)) != -1) {
    			Row row = sheet.getRow(currentRow);	// 得到行对象
    			CQuestion question = (CQuestion) rowToPojo(row,CQuestion.class,sheet.getSheetName(),allMsg);
    			result.add(question);
    			currentRow ++;
    		}
    		if(result.size()<1){
    			allMsg.append("导入内容不能为空。" + "\n");
    		}
    		json.put("allMsg", allMsg);
    		json.put("result", result);
    		return json;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
    /**
	 * 根据规则，将一个Row对象转换成Pojo对象
	 * @param row 行对象
	 * @param ruleList 行规则
	 * @param clazz 要转换成的对象的字节码
	 * @param sheetName 标签页的名称
	 * @return Object 返回的Pojo类对象
	 * @author HuKaiXuan 2014-5-14 上午10:20:35
	 */
	private Object rowToPojo(Row row,Class<?> clazz, String sheetName,StringBuilder allMsg) throws Exception {
		Object obj = clazz.newInstance();
		String errorMsg = "导入失败：";
		for(int i = 0; i < 9 ; i ++ ) {
			// 给对象赋值
			String setMethodName = null;// set方法名
			String getMethodName = null;// get方法名
			
			String columnNum = "A";
			String columnName = null;
			if(i==0){
				columnName = "试题类型";
				setMethodName = "setStyleStr";
				getMethodName = "getStyleStr";
			}else if(i==1){
				columnNum = "B";
				columnName = "题干";
				setMethodName = "setContent";
				getMethodName = "getContent";
			}else if(i==2){
				columnNum = "C";
				columnName = "正确答案";
				setMethodName = "setRightAnswer";
				getMethodName = "getRightAnswer";
			}else if(i==3){
				columnNum = "D";
				columnName = "分值";
				setMethodName = "setScore";
				getMethodName = "getScore";
			}else if(i==4){
				columnNum = "E";
				columnName = "选项A";
				setMethodName = "setOptionA";
				getMethodName = "getOptionA";
			}else if(i==5){
				columnNum = "F";
				columnName = "选项B";
				setMethodName = "setOptionB";
				getMethodName = "getOptionB";
			}else if(i==6){
				columnNum = "G";
				columnName = "选项C";
				setMethodName = "setOptionC";
				getMethodName = "getOptionC";
			}else if(i==7){
				columnNum = "H";
				columnName = "选项D";
				setMethodName = "setOptionD";
				getMethodName = "getOptionD";
			}else if(i==8){
				columnNum = "I";
				columnName = "选项E";
				setMethodName = "setOptionE";
				getMethodName = "getOptionE";
			}
			else if(i==9){
				columnNum = "J";
				columnName = "选项F";
				setMethodName = "setOptionF";
				getMethodName = "getOptionF";
			}
			
			errorMsg = sheetName + "第" + (row.getRowNum() + 1) + "行、第" + columnNum + "列（" + columnName + "）：" + "\n";
			boolean usedMsg = false;	// 是否用过消息了，确保errorMsg只被添加一次
			Cell cell =row.getCell(i); // 单元格
			
			String value = ExcelUtil.getCellStringValue(cell);	// 获取String类型的值
			Class<?> returnType = clazz.getMethod(getMethodName, new Class[]{}).getReturnType(); // 返回值类型
			Method setMethod = clazz.getMethod(setMethodName,returnType);
			if(returnType == String.class) {				// String类型
				setMethod.invoke(obj,new Object[]{value});	// 调用set方法赋值
			}else if(returnType == Integer.class) {		// Integer类型
				Integer param = Double.valueOf(value).intValue();
				setMethod.invoke(obj,new Object[]{param});
			}
			CQuestion question = (CQuestion) obj;
			if("A".equals(columnNum)) {
				 String stringSex = question.getStyleStr();
				 if(!"单选题".equals(stringSex) && !"多选题".equals(stringSex)&& !"问答题".equals(stringSex)) {
					if(!usedMsg) {
						allMsg.append(errorMsg);
						usedMsg = true;
					}
					allMsg.append("　　题型不能为空" + "\n");
				 } else {
					 if("单选题".equals(stringSex)){
						 question.setStyle(1); 
					 }else if("多选题".equals(stringSex)){
						 question.setStyle(2); 
					 }else if("问答题".equals(stringSex)){
						 question.setStyle(3); 
					 }
				 }
			 }else if("B".equals(columnNum)) {
				 String content = question.getContent();
				 if (content==null) {
					if(!usedMsg) {
						allMsg.append(errorMsg);
						usedMsg = true;
					}
					allMsg.append("　　题干不能为空，请检查。" + "\n");
				}else{
					question.setContent(content); 
				 }
			 }else if("C".equals(columnNum)) {
				 String rightAnswer = question.getRightAnswer();
				 if (rightAnswer==null) {
					if(!usedMsg) {
						allMsg.append(errorMsg);
						usedMsg = true;
					}
					allMsg.append("　　正确答案不能为空，请检查。" + "\n");
				}else{
					question.setRightAnswer(rightAnswer); 
				 }
			 }else if("D".equals(columnNum)) {
				 Integer score = question.getScore();
				 if (score==null) {
					if(!usedMsg) {
						allMsg.append(errorMsg);
						usedMsg = true;
					}
					allMsg.append("　　分值不能为空，请检查。" + "\n");
				}else{
					question.setScore(score); 
				 }
			 }else if("I".equals(columnNum)) {
				 String optionE = question.getOptionE();
				 if (!StringUtils.isNotBlank(optionE)) {
					 question.setOptionE(null);
				}
			 }
		}
		return obj;
	}
    
}
