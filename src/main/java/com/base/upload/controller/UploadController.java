package com.base.upload.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.base.controller.BaseController;
import com.base.utils.ConfigConstants;
import com.base.utils.PicCompression;
@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController{
	// 上传本地文件
	@RequestMapping(value = "/exec")
	public String execNative(HttpServletRequest request, HttpServletResponse response,String proVal,String type) {

		JSONObject reObject = new JSONObject();
		reObject.put("code", -1);

		List<Map<String, Object>> reList = new ArrayList<Map<String, Object>>();
		// 获取文件扩展名
		if (StringUtils.isBlank(proVal)) {
			return reObject.toString();
		}
		try {
			String fileToday = DateFormatUtils.format(new Date(), "yyyy/MM/dd");

			// 获取保存文件的最终路径
			String saveFilePath = ConfigConstants.UPLOAD_FILE_ROOT;
			String proPath = ConfigConstants.getPropValue(proVal);
			if (saveFilePath == null) {
				writeErrorMsg("保持图片根目录不能为空", null, response);
				return null;
			}
			// 获取文件
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			List<MultipartFile> uploadlist = multipartRequest.getFiles("file");

			String oldFileName = "", newFileName = "";
			for (int i = 0; i < uploadlist.size(); i++) {
				MultipartFile item = (MultipartFile) uploadlist.get(i);
				if (!item.isEmpty()) {// 处理文件上传域// 忽略其他不是文件域的所有表单信息
					String uuName = System.currentTimeMillis() + UUID.randomUUID().toString().split("-")[0];// 新文件名
					String filename = item.getOriginalFilename();// 取到客户端完整 路径+文件名
					oldFileName = FilenameUtils.getName(filename);// 取到文件名
					String ext = FilenameUtils.getExtension(filename);// 扩展名
					if(!StringUtils.isNoneBlank(ext)){
						String contentType = item.getContentType();
						if("image/png".equals(contentType)){
							ext = ".png";
						}else if("image/jpeg".equals(contentType)){
							ext = ".jpg";
						}else if("image/jpg".equals(contentType)){
							ext = ".jpg";
						}else if("image/gif".equals(contentType)){
							ext = ".gif";
						}
					}
					newFileName = proPath + File.separator + fileToday + File.separator + uuName + "." + ext;// 新路径
					File file = new File(saveFilePath + newFileName);
					if (!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
					}
					SaveFileFromInputStream(item.getInputStream(), saveFilePath, newFileName);//源文件
					if (StringUtils.isNotBlank(type) && type.equals("pic")) {
						PicCompression .resize(file,file);//等比例压缩文件
						PicCompression .resize(file, new File(getSPic( file.getAbsolutePath(), "_200")), 200);//200像素文件
						PicCompression .resize(file, new File(getSPic( file.getAbsolutePath(), "_100")), 100);//100像素文件
					}
					Map<String, Object> rtnMap = new HashMap<String, Object>();
					rtnMap.put("filePath", newFileName.replace("\\", "/"));
					rtnMap.put("fileName", oldFileName);
					reList.add(rtnMap);
				}
			}
			reObject.put("code", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		reObject.put("list", reList);
		writeJsonObject(reObject, response);
		return null;
	}
	private String getSPic(String path, String exName) {
		if (path != null) {
			String ex = path.substring(path.lastIndexOf("."), path.length());
			String fp = path.substring(0, path.lastIndexOf(".")) + exName;
			return fp + ex;
		}
		return null;
	}
	/**
	 * 
	 * @param stream 文件流
	 * @param path 路径
	 * @param filename 文件名称
	 * @throws IOException
	 */
	private void SaveFileFromInputStream(InputStream stream, String path, String filename) throws IOException {
		FileOutputStream fs = new FileOutputStream(path + "/" + filename);
		byte[] buffer = new byte[1024 * 1024];
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}
}
