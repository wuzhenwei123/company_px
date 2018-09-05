package com.base.production.glocal;

public class GlobalParam
{
	public static String getTemplateName(int index)
	{
		String name = "";
		switch (index)
		{
		case 1://beanName
			name = "model.ftl";
			break;
		case 2:// op action name
			name = "controller.ftl";
			break;
		case 3:// dao name
			name = "dao.ftl";
			break;
		case 4:// service name
			name = "service.ftl";
			break;
		case 5:// service impl name
			name = "service_impl.ftl";
			break;
		case 6:// sql name
			name = "sql.ftl";
			break;
		case 7:// jsp name
			name = "jsp_index.ftl";
			break;
		case 8:// jsp name
			name = "jsp_add.ftl";
			break;
		case 9:// jsp name
			name = "jsp_update.ftl";
			break;

		default:
			break;
		}
		return name;
	}
}
