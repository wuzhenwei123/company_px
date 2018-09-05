package com.base.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

public class StandardOutput {
	public static int PAGE_NO = 1;
	public static int PAGE_SIZE = 10;

	public static void printObject(HttpServletResponse response, Object obj) {
		Object object = null;
		if (obj instanceof ResultRsp) {
			Map<String, Object> reMap = new HashMap<String, Object>();
			ResultRsp responseList = (ResultRsp) obj;
			reMap.put("code", responseList.getCode());
			reMap.put("message", responseList.getMessage());

			if (responseList.getData() instanceof ResponseList) {
				ResponseList<?> responseList2 = (ResponseList<?>) responseList.getData();
				reMap.put("total", responseList2.getTotalResults());
				reMap.put("rows", responseList2);

			} else if (responseList.getData() instanceof PageInfo) {
				PageInfo<?> pageInfo = (PageInfo<?>) responseList.getData();
				reMap.put("total", pageInfo.getTotal());
				reMap.put("rows", pageInfo.getList());
				reMap.put("pages", pageInfo.getPages());
				reMap.put("pageNum", pageInfo.getPageNum());
			}else{
				reMap.put("rows", responseList.getData());
			}
			Object result = JSONObject.toJSON(reMap);
			object = result;
		} else {
			Object result = JSONObject.toJSON(obj);
			object = result;
		}
		if(object!=null){
			printBaseObject(response,object);
		}
	}
	public static void printBaseObject(HttpServletResponse response, Object obj) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.print(obj);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
