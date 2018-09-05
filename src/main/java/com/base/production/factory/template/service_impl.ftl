package ${packageName}.${pak};

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import ${packageName}.dao.${domainName?cap_first}DAO;
import ${packageName}.model.${domainName?cap_first};

/**
 * ${description}
 * @time	${date}
 */
@Service
@Transactional
public class ${domainName?cap_first}ServiceImpl implements ${domainName?cap_first}Service{
	@Resource
    private ${domainName?cap_first}DAO ${domainName}DAO;
    
    public List<${domainName?cap_first}> get${domainName?cap_first}List(${domainName?cap_first} ${domainName}) {
        return ${domainName}DAO.get${domainName?cap_first}List(${domainName});
    }

    public ${domainName?cap_first} get${domainName?cap_first}By${pkName?cap_first}(${pkType} ${pkName}) { 
        return ${domainName}DAO.get${domainName?cap_first}By${pkName?cap_first}(${pkName});
    }

    public ${pkType} insert${domainName?cap_first}(${domainName?cap_first} ${domainName}){
        return ${domainName}DAO.insert${domainName?cap_first}(${domainName});
    }

    public int update${domainName?cap_first}By${pkName?cap_first}(${domainName?cap_first} ${domainName}){
        return ${domainName}DAO.update${domainName?cap_first}By${pkName?cap_first}(${domainName});
    }
    
    public int delete${domainName?cap_first}By${pkName?cap_first}(${pkType} ${pkName}){
        return ${domainName}DAO.delete${domainName?cap_first}By${pkName?cap_first}(${pkName});
    }
}
