package com.wx.utils.pay;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.wx.utils.pay.wx.WXmodel;
import com.wx.utils.pay.wx.WXutil;




public class PayUtils {
	
	private static Logger log = Logger.getLogger(PayUtils.class);

	/**
	 * 获取生成预付费订单
	 * @throws UnsupportedEncodingException 
	 * */
	public static Map<String,String> getMap(Integer money,String orderId,String openid,String ip) throws UnsupportedEncodingException{
		String fee = money+"";
		String bodys = "平台注册费";
		WXmodel wx = new WXmodel(bodys, orderId,fee,orderId,openid,ip);
		String xml = wx.getXml(wx.getParam());
		System.out.println(xml);
		Map<String,String> map = WXutil.post(xml);
		return map;
	}
	
	/**
	 * 订单查询
	 * @throws UnsupportedEncodingException 
	 * */
	public static Map<String,String> postOrderQuery(String orderId) throws UnsupportedEncodingException{
		WXmodel wx = new WXmodel(orderId);
		String xml = wx.getXml(wx.getParam());
		System.out.println(xml);
		Map<String,String> map = WXutil.postOrderQuery(xml);
		return map;
	}
	
	/**
	 * 退费申请
	 * @throws UnsupportedEncodingException 
	 * */
	public static Map<String,String> getRefundMap(String fee,String orderId,String out_refund_no) throws UnsupportedEncodingException{
//		Float feef = money*100;
//		String feestr = feef.toString();
//		String fee = feestr.substring(0, feestr.indexOf(".")>0?(feestr.indexOf(".")):feestr.length()-1);
		WXmodel wx = new WXmodel(orderId,fee,out_refund_no);
		String xml = wx.getXml(wx.getParam());
		System.out.println(xml);
		Map<String,String> map = WXutil.postRefund(xml);
		return map;
	}
	
	/**
	 * 提现申请
	 * @throws UnsupportedEncodingException 
	 * */
	public static Map<String,String> getTransfersMap(String body,String total_fee,String openid,String out_trade_no,String ip) throws UnsupportedEncodingException{
		WXmodel wx = new WXmodel(body,out_trade_no,total_fee,openid,ip);
		String xml = wx.getXml(wx.getParam());
		System.out.println(xml);
		Map<String,String> map = WXutil.postTransfers(xml);
		return map;
	}
	
	/**
	 * 参数拼接
	 * */
    public static String paramLinks(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    
	
	/**
	 * xml字符串解析
	 * */
	public static Map<String,String> resolve(String xml) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		List<Map> argMapList = new ArrayList<Map>();
		try {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = (Document) sb.build(source);
			Element root = doc.getRootElement();// 指向根节点
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					map.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return map;
	}
	
	/**
	 * 参数ASCII排序
	 * */
	
	public static List<String> sort(Map<String,String> param){
		List<String> reList = new ArrayList<String>();
		if(param!=null){
			List<String> list = new ArrayList(param.entrySet());
			String[] str = (String[]) list.toArray();
			Arrays.sort(str);
			reList = Arrays.asList();
		}
		return reList;
	}
	
	/**
	 * byte转hex
	 * */
	private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
	
	/**
	 * 过滤空参数
	 * */
	 public static Map<String, String> paraFilter(Map<String, String> sArray) {

	        Map<String, String> result = new HashMap<String, String>();

	        if (sArray == null || sArray.size() <= 0) {
	            return result;
	        }

	        for (String key : sArray.keySet()) {
	            String value = sArray.get(key);
	            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
	                || key.equalsIgnoreCase("sign_type")) {
	                continue;
	            }
	            result.put(key, value);
	        }

	        return result;
	    }
	 
	 public static void main(String args[]){
		 float money = 99.79999999999999999999999f;
			Float feef = money*100;
			BigDecimal b= new BigDecimal(feef);  
			Float f1 = b.setScale(2,  BigDecimal.ROUND_HALF_UP).floatValue();
			String feestr = f1.toString();
			System.out.println(feestr);
	 }

}
