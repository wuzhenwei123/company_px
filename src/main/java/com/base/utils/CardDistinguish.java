package com.base.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.imageio.stream.FileImageInputStream;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

public class CardDistinguish {
	
	public static final String APP_ID = "10880257";
    public static final String API_KEY = "sQmgGvhjLrNBKvEGVoUk2uTW";
    public static final String SECRET_KEY = "gwbQrUG363TGlfyf78NPbZL3IaGBSgxf";
    
    public static AipOcr client = null;
    
    static{
    	client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
    }
    
    public static void main(String args[]){
    	CardDistinguish obj = new CardDistinguish();
//    	obj.idDistinguish("E://111111.jpg", "front");
    	obj.idDistinguish("E://222222.jpg", "back");
    }
    
    /**
     * 识别身份证
     * @param imageUrl 图片地址
     * @param idCardSide 正反面 front - 身份证含照片的一面  back - 身份证带国徽的一面
     */
    public com.alibaba.fastjson.JSONObject idDistinguish(String imageUrl,String idCardSide) {
    	
    	com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
    	try{
    		// 传入可选参数调用接口
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("detect_direction", "true");
            options.put("detect_risk", "false");
            
            // 参数为本地图片路径
            JSONObject res = client.idcard(imageUrl, idCardSide, options);
            System.out.println(res.toString(2));

            // 参数为本地图片二进制数组
            byte[] file = readImageFile(imageUrl);
            res = client.idcard(file, idCardSide, options);
            if(res.get("image_status")!=null){
            	json.put("image_status", res.get("image_status").toString());
            	if("normal".equals(res.get("image_status").toString())){
        			if("front".equals(idCardSide)){
        				json.put("number", res.getJSONObject("words_result").getJSONObject("公民身份号码").get("words").toString());
        				json.put("name", res.getJSONObject("words_result").getJSONObject("姓名").get("words").toString());
            		}else{
            			json.put("end_time", res.getJSONObject("words_result").getJSONObject("失效日期").get("words").toString());
            		}
            	}
            }else{
            	json.put("image_status", "non_idcard");
            }
            System.out.println(res.getJSONObject("words_result").toString());
    	}catch(Exception e){
    		json.put("image_status", "non_idcard");
    		e.printStackTrace();
    	}
        return json;   
    }
    
    
    /**
     * 银行卡识别
     * @param imageUrl
     * @return
     */
    public com.alibaba.fastjson.JSONObject  bankDistinguish(String imageUrl) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        try{
        	// 参数为本地图片路径
            JSONObject res = client.bankcard(imageUrl, options);
            // 参数为本地图片二进制数组
            byte[] file = readImageFile(imageUrl);
            res = client.bankcard(file, options);
            if(res.get("result")!=null){
            	json.put("bank_card_type", res.getJSONObject("result").get("bank_card_type"));
            	if(!"0".equals(res.getJSONObject("result").get("bank_card_type").toString())){
            		json.put("number", res.getJSONObject("result").get("bank_card_number").toString());
            	}
            }else{
            	json.put("bank_card_type", 0);
            }
            System.out.println(res.toString());
        }catch(Exception e){
        	json.put("bank_card_type", 0);
        	e.printStackTrace();
        }
        
        return json;   
    }
    
    
	public byte[] readImageFile(String path) {
		byte[] data = null;
		FileImageInputStream input = null;
		try {
			input = new FileImageInputStream(new File(path));
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int numBytesRead = 0;
			while ((numBytesRead = input.read(buf)) != -1) {
				output.write(buf, 0, numBytesRead);
			}
			data = output.toByteArray();
			output.close();
			input.close();
		} catch (FileNotFoundException ex1) {
			ex1.printStackTrace();
		} catch (IOException ex1) {
			ex1.printStackTrace();
		}
		return data;
	}

}
