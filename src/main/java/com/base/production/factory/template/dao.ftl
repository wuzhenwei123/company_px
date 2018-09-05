package ${packageName}.${pak};

import java.util.List;

import ${packageName}.model.${domainName?cap_first};
/**
 * ${description}
 * @time	${date}
 */
public interface ${domainName?cap_first}DAO{
	
	public List<${domainName?cap_first}> get${domainName?cap_first}List(${domainName?cap_first} ${domainName});

	public ${domainName?cap_first} get${domainName?cap_first}By${pkName?cap_first}(${pkType} ${pkName});

    public ${pkType} insert${domainName?cap_first}(${domainName?cap_first} ${domainName});

    public int update${domainName?cap_first}By${pkName?cap_first}(${domainName?cap_first} ${domainName});
    
    public int delete${domainName?cap_first}By${pkName?cap_first}(${pkType} ${pkName});
}
