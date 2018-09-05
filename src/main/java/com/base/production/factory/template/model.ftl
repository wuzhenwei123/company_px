package ${packageName}.${pak};

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * ${tableComment}	
 * ${description}
 * @time	${date}
 */
public class ${domainName?cap_first} extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	<#list columns as item>
	/** ${item.remarks} **/
	private ${item.type} ${item.javaName};
	</#list>
		
	<#list columns as item>
	/**
	 * ${item.remarks}
	 * @return ${item.name}
	 */
	public ${item.type} get${item.javaName?cap_first}() {
		return ${item.javaName};
	}
	/**
	 * ${item.remarks}
	 */
	public void set${item.javaName?cap_first}(${item.type} ${item.javaName}) {
		this.${item.javaName} = ${item.javaName};
	}
	</#list>
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}