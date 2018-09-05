package com.base.production.factory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.production.domain.Table;
import com.base.production.tools.AssistTools;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class BaseFactory {

	public boolean create(Table table) throws Exception {

		String fileRoot = AssistTools.ifNull(table.getTemplateDirectory(), null);
		String fileName = AssistTools.ifNull(table.getTemplateName(),
				table.getTemplateName());
		String encoding = AssistTools.ifNull(table.getEncoding(), "UTF-8");

		File file = new File(table.getOutFilePath());
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		boolean state = true;
		Configuration cfg = new Configuration();
		cfg.setDefaultEncoding(encoding);
		File tempFile = null;
		if (fileRoot != null) {
			tempFile = new File(fileRoot);
		} else {
			URL resource = BaseFactory.class.getResource("/com/base/production/factory/template");
			String path = resource.getPath();
			tempFile = new File(path);
		}
		cfg.setDirectoryForTemplateLoading(tempFile);
		Template t = cfg.getTemplate(fileName);
		Writer out = new OutputStreamWriter(new FileOutputStream(file),
				encoding);

		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<Object, Object>> list = table.getList();
		if (list == null || list.size() == 0) {
			state = false;
		} else {
			map.put("columns", list);// 列
			map.put("packageName", table.getPackageName());// 包名称
			map.put("domainName", table.getDomainName());// 实体类
			map.put("pak", table.getPak());// 包名称
			map.put("tableName", table.getTableName());// 表名称
			map.put("pkName", table.getPkName());// 主键名称
			map.put("pkType", table.getPkLowType());// 主键类型(小写)
			map.put("pkUPType", table.getPkType());// 主键类型
			map.put("tableComment", table.getTableComment());// 表注释

			map.put("description", table.getDescription());// 类描述
			map.put("date", AssistTools.getCurrentDate());// 当前日期

			map.put("jl", "{");// 特殊字符
			map.put("jld", "}");// 特殊字符

			t.process(map, out);
		}
		out.close();
		String info = state ? "OK" : "Fail";
		System.out.println("-----" + info + "-----");
		return state;
	}
}
